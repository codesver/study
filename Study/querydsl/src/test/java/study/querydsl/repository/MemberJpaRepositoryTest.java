package study.querydsl.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDTO;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void basicTest() {
        Member memberA = new Member("memberA", 10);
        memberJpaRepository.save(memberA);

        Member foundMember = memberJpaRepository.findById(memberA.getId()).get();
        assertThat(foundMember).isEqualTo(memberA);

        List<Member> foundAllMembers = memberJpaRepository.findAll();
        assertThat(foundAllMembers).containsExactly(memberA);

        List<Member> foundMemberA = memberJpaRepository.findByUsername("memberA");
        assertThat(foundMemberA).containsExactly(memberA);
    }

    @Test
    void basicQuerydslTest() {
        Member memberA = new Member("memberA", 10);
        memberJpaRepository.save(memberA);

        Member foundMember = memberJpaRepository.findById(memberA.getId()).get();
        assertThat(foundMember).isEqualTo(memberA);

        List<Member> foundAllMembers = memberJpaRepository.findAllQuerydsl();
        assertThat(foundAllMembers).containsExactly(memberA);

        List<Member> foundMemberA = memberJpaRepository.findByUsernameQuerydsl("memberA");
        assertThat(foundMemberA).containsExactly(memberA);
    }

    @Test
    void searchTest() {
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

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDTO> result = memberJpaRepository.searchByBuilder(condition);

        assertThat(result).extracting("username").containsExactly("memberD");
    }
}