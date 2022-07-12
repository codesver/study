package codesver.core;

import codesver.core.member.Grade;
import codesver.core.member.Member;
import codesver.core.member.MemberServiceImpl;
import codesver.core.order.Order;
import codesver.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberServiceImpl memberService = new MemberServiceImpl();
        OrderServiceImpl orderService = new OrderServiceImpl();

        long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println("order = " + order);
    }
}
