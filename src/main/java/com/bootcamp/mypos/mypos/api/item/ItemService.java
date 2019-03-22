package com.bootcamp.mypos.mypos.api.item;


import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.exception.ItemValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    private ItemValidator itemValidator = new ItemValidator();

    Item createItem(Item item) throws ItemValidationException {

        itemValidator.validateItem(item, itemRepository);
        item.setDateAdded(new Date());
        return itemRepository.saveAndFlush(item);

    }

    Item updateItem(Item item) throws ItemValidationException {

        // validate id, validate attributes make update
        Item existingItem = itemValidator.validateId(item.getId(), itemRepository);
        itemValidator.validateItem(item, itemRepository);

        existingItem.setAmountAvailable(item.getAmountAvailable());
        existingItem.setItemName(item.getItemName());

        return itemRepository.saveAndFlush(existingItem);

    }

    Item getItem(Long itemId) throws ItemValidationException {

        // return if found
        return itemValidator.validateId(itemId, itemRepository);

    }

    List<Item> getMatchingItems(String autoSuggestString) {

        // return if found
        final List<Item> result = new LinkedList<>();

        Item exact = itemRepository.findOneByItemName(autoSuggestString);
        List<Item> startingWith = itemRepository.findByItemNameStartingWith(autoSuggestString);
        List<Item> contains = itemRepository.findByItemNameContaining(autoSuggestString);

        if (exact != null) result.add(exact);

        if (startingWith != null)
            for (Item item : startingWith) {
                if (!result.contains(item)) {
                    result.add(item);
                }
            }

        if (contains != null)
            for (Item item : contains) {
                if (!result.contains(item)) {
                    result.add(item);
                }
            }

        return result.subList(0, result.size() >5 ? 5: result.size());

    }

    boolean deleteItem(Long itemId) throws ItemValidationException {

        // remove if id is valid
        Item found = itemValidator.validateId(itemId, itemRepository);
        itemRepository.delete(found);
        return true;
    }


}