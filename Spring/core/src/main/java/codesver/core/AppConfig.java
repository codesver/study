package codesver.core;

import codesver.core.discount.DiscountPolicy;
import codesver.core.discount.RateDiscountPolicy;
import codesver.core.member.MemberRepository;
import codesver.core.member.MemberService;
import codesver.core.member.MemberServiceImpl;
import codesver.core.member.MemoryMemberRepository;
import codesver.core.order.OrderService;
import codesver.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
