package codesver.itemservice.config;

import codesver.itemservice.repository.ItemRepository;
import codesver.itemservice.repository.jpa.JpaItemRepositoryV3;
import codesver.itemservice.repository.v2.ItemQueryRepositoryV2;
import codesver.itemservice.repository.v2.ItemRepositoryV2;
import codesver.itemservice.service.ItemService;
import codesver.itemservice.service.ItemServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class V2Config {

    private final EntityManager em;
    private final ItemRepositoryV2 itemRepositoryV2;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV2(itemRepositoryV2, itemQueryRepositoryV2());
    }

    @Bean
    public ItemQueryRepositoryV2 itemQueryRepositoryV2() {
        return new ItemQueryRepositoryV2(em);
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV3(em);
    }
}
