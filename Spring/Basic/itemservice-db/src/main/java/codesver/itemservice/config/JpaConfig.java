package codesver.itemservice.config;

import codesver.itemservice.repository.ItemRepository;
import codesver.itemservice.repository.jpa.JpaItemRepository;
import codesver.itemservice.repository.mybatis.ItemMapper;
import codesver.itemservice.repository.mybatis.MyBatisItemRepository;
import codesver.itemservice.service.ItemService;
import codesver.itemservice.service.ItemServiceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
@RequiredArgsConstructor
public class JpaConfig {

    private final EntityManager em;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JpaItemRepository(em);
    }
}
