package study.querydsl.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;

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
}