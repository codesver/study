package codesver.itemservice.config;

import codesver.itemservice.repository.ItemRepository;
import codesver.itemservice.repository.jpa.JpaItemRepository;
import codesver.itemservice.repository.jpa.JpaItemRepositoryV2;
import codesver.itemservice.repository.jpa.SpringDataJpaItemRepository;
import codesver.itemservice.service.ItemService;
import codesver.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

    private final SpringDataJpaItemRepository repository;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepositoryV2(repository);
    }
}
