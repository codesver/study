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
class MemberRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberRepository;

    @Test
    void basicTest() {
        Member memberA = new Member("memberA", 10);
        memberRepository.save(memberA);

        Member foundMember = memberRepository.findById(memberA.getId()).get();
        assertThat(foundMember).isEqualTo(memberA);

        List<Member> foundAllMembers = memberRepository.findAll();
        assertThat(foundAllMembers).containsExactly(memberA);

        List<Member> foundMemberA = memberRepository.findByUsername("memberA");
        assertThat(foundMemberA).containsExactly(memberA);
    }
}