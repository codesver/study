package codesver.itemservice.service;

import codesver.itemservice.domain.Item;
import codesver.itemservice.repository.ItemSearchCond;
import codesver.itemservice.repository.ItemUpdateDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {

    Item save(Item item);

    void update(Long itemId, ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findItems(ItemSearchCond itemSearch);
}
