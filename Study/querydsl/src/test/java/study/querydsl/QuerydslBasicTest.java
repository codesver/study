package study.querydsl;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static study.querydsl.entity.QMember.member;

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
        Member memberB = new Member("memberB", 10, teamA);
        Member memberC = new Member("memberC", 10, teamB);
        Member memberD = new Member("memberD", 10, teamB);
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
}
