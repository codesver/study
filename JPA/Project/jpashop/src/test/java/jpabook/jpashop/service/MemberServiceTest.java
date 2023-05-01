package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepositoryOld;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Test
    public void join() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        assertThat(member).isEqualTo(memberRepositoryOld.findOne(savedId));
    }

    @Test
    public void duplicateName() throws Exception {
        // given
        Member memberA = new Member();
        memberA.setName("member");

        Member memberB = new Member();
        memberB.setName("member");

        // when
        memberService.join(memberA);

        // then
        assertThatThrownBy(() -> memberService.join(memberB)).isInstanceOf(IllegalStateException.class);
    }

}