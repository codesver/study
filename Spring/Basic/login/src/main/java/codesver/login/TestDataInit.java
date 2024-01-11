package codesver.login;

import codesver.login.domain.item.Item;
import codesver.login.domain.item.ItemRepository;
import codesver.login.domain.member.Member;
import codesver.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
        memberRepository.save(new Member("sumnerwon", "codesver", "wodnjs"));
    }

}