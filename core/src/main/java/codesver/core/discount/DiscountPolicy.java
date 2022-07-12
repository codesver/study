package codesver.core.discount;

import codesver.core.member.Member;

public interface DiscountPolicy {
    int discount(Member member, int price);
}
