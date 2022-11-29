package study.querydsl;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;

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
}
