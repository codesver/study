package codesver.itemservice.config;

import codesver.itemservice.repository.ItemRepository;
import codesver.itemservice.repository.memory.MemoryItemRepository;
import codesver.itemservice.service.ItemService;
import codesver.itemservice.service.ItemServiceV1;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MemoryConfig {

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new MemoryItemRepository();
    }

}
