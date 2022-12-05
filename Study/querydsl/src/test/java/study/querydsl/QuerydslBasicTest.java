package study.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberDTO;
import study.querydsl.dto.QMemberDTO;
import study.querydsl.dto.UserDTO;
import study.querydsl.entity.Member;
import study.querydsl.entity.QMember;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;
import static study.querydsl.entity.QTeam.team;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory query;

    @BeforeEach
    void before() {
        query = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member memberA = new Member("memberA", 10, teamA);
        Member memberB = new Member("memberB", 20, teamA);
        Member memberC = new Member("memberC", 30, teamB);
        Member memberD = new Member("memberD", 40, teamB);
        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        em.persist(memberD);
    }

    @Test
    void startJPQL() {
        // Find memberA
        Member foundMember = em.createQuery("" +
                        "select m " +
                        "from Member m " +
                        "where m.username = :username", Member.class)
                .setParameter("username", "memberA")
                .getSingleResult();
        assertThat(foundMember.getUsername()).isEqualTo("memberA");
    }

    @Test
    void startQuerydsl() {
        Member foundMember = query
                .select(member)
                .from(member)
                .where(member.username.eq("memberA"))
                .fetchOne();
        assert foundMember != null;
        assertThat(foundMember.getUsername()).isEqualTo("memberA");
    }

    @Test
    void search() {
        Member foundMember = query
                .selectFrom(member)
                .where(member.username.eq("memberA'").and(member.age.eq(10)))
                .fetchOne();
        assert foundMember != null;
        assertThat(foundMember.getUsername()).isEqualTo("memberA");
    }

    @Test
    void searchAndParam() {
        Member foundMember = query
                .selectFrom(member)
                .where(
                        member.username.eq("memberA"),
                        member.age.eq(10)
                )
                .fetchOne();
    }

    @Test
    void resultFetch() {
        List<Member> foundMembers = query
                .selectFrom(member)
                .fetch();

        Member foundMember = query
                .selectFrom(member)
                .fetchOne();

        Member foundFirstMember = query
                .selectFrom(member)
                .fetchFirst();
    }

    /**
     * Member sorting sequence
     * 1. Sorting in descending order by age.
     * 2. Sorting in ascending order by name
     * 2-1. If username is null than sort at last
     */
    @Test
    void sort() {
        em.persist(new Member(null, 100));
        em.persist(new Member("memberD", 100));
        em.persist(new Member("memberE", 100));

        List<Member> foundMembers = query
                .selectFrom(member)
                .where(member.age.eq(100))
                .orderBy(member.age.desc(), member.username.asc().nullsLast())
                .fetch();

        Member memberD = foundMembers.get(0);
        Member memberE = foundMembers.get(1);
        Member memberNull = foundMembers.get(2);

        assertThat(memberD.getUsername()).isEqualTo("memberD");
        assertThat(memberE.getUsername()).isEqualTo("memberE");
        assertThat(memberNull.getUsername()).isNull();
    }

    @Test
    void paging() {
        List<Member> foundPagedMembers = query
                .selectFrom(member)
                .orderBy(member.username.desc())
                .offset(1)
                .limit(2)
                .fetch();

        assertThat(foundPagedMembers.size()).isEqualTo(2);
    }

    @Test
    void aggregation() {
        List<Tuple> foundMemberTuples = query
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = foundMemberTuples.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    /**
     * Get team's name and team's average age.
     */
    @Test
    void group() {
        List<Tuple> result = query
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    /**
     * Find all member in team A
     */
    @Test
    void join() {
        List<Member> teamAMembers = query
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();
        assertThat(teamAMembers)
                .extracting("username")
                .containsExactly("memberA", "memberB");
    }

    /**
     * Theta join
     * Find which member name and team name is same
     */
    @Test
    void theta_join() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Member> foundMembers = query
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(foundMembers)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /**
     * Join Member and Team but only team A and all members
     * JPQL: select m, t from Member m left join m.team on t.name = 'teamA'
     */
    @Test
    void joinOnFiltering() {
        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team)
                .on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /**
     * Outer joining entity with no relation
     * Outer join member.username == team.name
     */
    @Test
    void joinOnNoRelation() {
        em.persist(new Member("teamA"));
        em.persist(new Member("teamB"));
        em.persist(new Member("teamC"));

        List<Tuple> result = query
                .select(member, team)
                .from(member)
                .leftJoin(team).on(member.username.eq(team.name))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    void fetchJoinNo() {
        em.flush();
        em.clear();

        Member memberA = query
                .selectFrom(member)
                .where(member.username.eq("memberA"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(memberA.getTeam());
        assertThat(loaded).isFalse();
    }

    @Test
    void fetchJoinUser() {
        em.flush();
        em.clear();

        Member memberA = query
                .selectFrom(member)
                .join(member.team, team).fetchJoin()
                .where(member.username.eq("memberA"))
                .fetchOne();

        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(memberA.getTeam());
        assertThat(loaded).isTrue();
    }

    /**
     * Search the oldest member
     */
    @Test
    void subQuery() {
        QMember memberSub = new QMember("memberSub");
        List<Member> foundMembers = query
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                )).fetch();

        assertThat(foundMembers)
                .extracting("age")
                .containsExactly(40);
    }

    /**
     * Search member older than average age
     */
    @Test
    void subQueryGoe() {
        QMember memberSub = new QMember("memberSub");
        List<Member> foundMembers = query
                .selectFrom(member)
                .where(member.age.goe(
                        select(memberSub.age.avg())
                                .from(memberSub)
                )).fetch();

        assertThat(foundMembers)
                .extracting("age")
                .containsExactly(30, 40);
    }

    @Test
    void subQueryIn() {
        QMember memberSub = new QMember("memberSub");
        List<Member> foundMembers = query
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                )).fetch();

        assertThat(foundMembers)
                .extracting("age")
                .containsExactly(20, 30, 40);
    }

    @Test
    void selectSubQuery() {
        QMember memberSub = new QMember("memberSub");
        List<Tuple> result = query
                .select(member.username,
                        select(memberSub.age.avg())
                                .from(memberSub))
                .from(member)
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    void basicCase() {
        List<String> foundMembers = query
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
    }

    @Test
    void complexCase() {
        List<String> foundMembers = query
                .select(new CaseBuilder()
                        .when(member.age.between(0, 20)).then("0~20살")
                        .when(member.age.between(21, 30)).then("21~30살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
    }

    @Test
    void constant() {
        List<Tuple> foundMembers = query
                .select(member.username, Expressions.constant("A"))
                .from(member)
                .fetch();

        for (Tuple foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
    }

    @Test
    void concat() {
        List<String> foundMembers = query
                .select(member.username.concat("_").concat(member.age.stringValue()))
                .from(member)
                .where(member.username.eq("memberA"))
                .fetch();

        for (String foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
    }

    @Test
    void simpleProjection() {
        List<String> result = query
                .select(member.username)
                .from(member)
                .fetch();
        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    void tupleProjection() {
        List<Tuple> result = query
                .select(member.username, member.age)
                .from(member)
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple.get(member.username) = " + tuple.get(member.username));
            System.out.println("tuple.get(member.age) = " + tuple.get(member.age));
        }
    }

    @Test
    void findDTOByJPQL() {
        List<MemberDTO> foundMemberDTOs = em.createQuery(
                "select new study.querydsl.dto.MemberDTO(m.username, m.age) " +
                        "from Member m", MemberDTO.class
        ).getResultList();
        for (MemberDTO foundMemberDTO : foundMemberDTOs) {
            System.out.println("foundMemberDTO = " + foundMemberDTO);
        }
    }

    @Test
    void findDTOBySetter() {
        List<MemberDTO> foundMemberDTOs = query
                .select(Projections.bean(MemberDTO.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDTO foundMemberDTO : foundMemberDTOs) {
            System.out.println("foundMemberDTO = " + foundMemberDTO);
        }
    }

    @Test
    void findDTOByField() {
        List<MemberDTO> foundMemberDTOs = query
                .select(Projections.fields(MemberDTO.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDTO foundMemberDTO : foundMemberDTOs) {
            System.out.println("foundMemberDTO = " + foundMemberDTO);
        }
    }

    @Test
    void findDTOByConstructor() {
        List<MemberDTO> foundMemberDTOs = query
                .select(Projections.constructor(MemberDTO.class, member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDTO foundMemberDTO : foundMemberDTOs) {
            System.out.println("foundMemberDTO = " + foundMemberDTO);
        }
    }

    @Test
    void findUserDTO() {
        List<UserDTO> foundUserDTOs = query
                .select(Projections.fields(UserDTO.class, member.username.as("name"), member.age))
                .from(member)
                .fetch();
        for (UserDTO foundUserDTO : foundUserDTOs) {
            System.out.println("foundUserDTO = " + foundUserDTO);
        }
    }

    @Test
    void findUserDTOSubQuery() {
        QMember memberSub = new QMember("memberSub");
        List<UserDTO> result = query
                .select(Projections.fields(UserDTO.class,
                        member.username.as("name"),
                        ExpressionUtils.as(
                                select(memberSub.age.max()).from(memberSub),
                                "age"
                        )
                ))
                .from(member)
                .fetch();
        for (UserDTO userDTO : result) {
            System.out.println("userDTO = " + userDTO);
        }
    }

    @Test
    void findDTOByQueryProjection() {
        List<MemberDTO> foundMemberDTOs = query
                .select(new QMemberDTO(member.username, member.age))
                .from(member)
                .fetch();
        for (MemberDTO foundMemberDTO : foundMemberDTOs) {
            System.out.println("foundMemberDTO = " + foundMemberDTO);
        }
    }

    @Test
    void dynamicQueryBooleanBuilder() {
        String usernameParam = "memberA";
        Integer ageParam = 10;

        List<Member> result = searchMemberA(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMemberA(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null)
            builder.and(member.username.eq(usernameCond));
        if (ageCond != null)
            builder.and(member.age.eq(ageCond));
        return query
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    @Test
    void dynamicQueryWhereParameter() {
        String usernameParam = "memberA";
        Integer ageParam = 10;

        List<Member> result = searchMemberB(usernameParam, ageParam);
        assertThat(result.size()).isEqualTo(1);
    }

    private List<Member> searchMemberB(String usernameCond, Integer ageCond) {
        return query
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }

    private Predicate usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }

    private Predicate ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    @Test
    void bulkUpdate() {
        long count = query
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();
        assertThat(count).isEqualTo(2);
    }
}
