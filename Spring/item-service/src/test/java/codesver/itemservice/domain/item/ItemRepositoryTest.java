package codesver.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given
        Item item = new Item("Item", 10000, 10);

        // when
        Item savedItem = itemRepository.save(item);

        // then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(item);
    }

    @Test
    void findAll() {
        // given
        Item itemA = new Item("Item A", 10000, 10);
        Item itemB = new Item("Item B", 20000, 20);

        itemRepository.save(itemA);
        itemRepository.save(itemB);

        // when
        List<Item> items = itemRepository.findAll();

        // then
        assertThat(items.size()).isEqualTo(2);
        assertThat(items).contains(itemA, itemB);
    }

    @Test
    void updateItem() {
        // given
        Item item = new Item("Item", 10000, 10);
        Item savedItem = itemRepository.save(item);
        Long id = savedItem.getId();

        // when
        Item newItem = new Item("New Item", 20000, 20);
        itemRepository.update(id, newItem);

        // then
        Item foundItem = itemRepository.findById(id);
        assertThat(foundItem.getItemName()).isEqualTo(newItem.getItemName());
        assertThat(foundItem.getPrice()).isEqualTo(newItem.getPrice());
        assertThat(foundItem.getQuantity()).isEqualTo(newItem.getQuantity());
    }
}