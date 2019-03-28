package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.exception.validation_errors.ItemValidationError;
import com.bootcamp.mypos.mypos.exception.ValidationException;

import java.util.Optional;

class ItemValidator {

    void validateItem(Item item, ItemRepository itemRepository) {

        // check itemname
        if (item.getItemName() != null && !item.getItemName().trim().equals("")) {
            Item existingItem = itemRepository.findOneByItemName(item.getItemName());

            // again making sure item is not matched with his/herself
            if (existingItem != null && !existingItem.getId().equals(item.getId())) {
                throw new ValidationException(ItemValidationError.ITEM_NAME_EXISTS);
            }
        } else {
            throw new ValidationException(ItemValidationError.EMPTY_ITEM_NAME);
        }

        // check amount
        if(item.getAmountAvailable() < 0)
            throw new ValidationException(ItemValidationError.INVALID_AMOUNT_AVAILABLE);

    }


    Item validateId(Long itemId, ItemRepository itemRepository){

        // return valid item if found. else throw error
        Optional<Item> found = itemRepository.findById(itemId);

        if (!found.isPresent()) {
            throw new ValidationException(ItemValidationError.NON_EXISTENT_ID);
        } else {
            return found.get();
        }
    }
}
