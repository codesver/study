package codesver.core;

import codesver.core.discount.FixDiscountPolicy;
import codesver.core.member.MemberService;
import codesver.core.member.MemberServiceImpl;
import codesver.core.member.MemoryMemberRepository;
import codesver.core.order.OrderService;
import codesver.core.order.OrderServiceImpl;

public class AppConfig {
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
