package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.api.item.ItemValidator;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.exception.ItemValidationException;
import com.bootcamp.mypos.mypos.exception.ItemValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.Silent.class)
public class ItemValidatorTest {


    @Mock
    ItemRepository itemRepository;



    @Test(expected = ItemValidationException.class)
    public void validateItemThrowsExceptionOnExistingName() throws Exception {
        Item item = new Item();
        item.setItemName("apple");
        item.setId(4L);

        Item returned = new Item();
        returned.setItemName("apple");
        returned.setId(5L);


        ItemValidator validator = new ItemValidator();
        Mockito.when(itemRepository.findOneByItemName(item.getItemName())).thenReturn(returned);
        validator.validateItem(item,itemRepository);

    }

    @Test(expected = ItemValidationException.class)
    public void validateItemThrowsExceptionOnInvalidAmount() throws Exception {
        Item item = new Item();
        item.setItemName("orange");
        item.setAmountAvailable(-1);
        item.setId(4L);

        Item returned = new Item();
        returned.setItemName("apple");
        returned.setId(5L);


        ItemValidator validator = new ItemValidator();
        Mockito.when(itemRepository.findOneByItemName(item.getItemName())).thenReturn(returned);
        validator.validateItem(item,itemRepository);

    }


    @Test(expected = ItemValidationException.class)
    public void validateIdThrowsExceptionOnNonExistentId() throws Exception {
        Item item = new Item();
        item.setItemName("orange");
        item.setAmountAvailable(-1);
        item.setId(4L);

        Item returned = new Item();
        returned.setItemName("apple");
        returned.setId(5L);


        ItemValidator validator = new ItemValidator();
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        validator.validateId(item.getId(),itemRepository);

    }

    @Test(expected = ItemValidationException.class)
    public void validateIdThrowsExceptionOnEmptyItemName() throws Exception {
        Item item = new Item();
        item.setItemName("");
        item.setAmountAvailable(-1);
        item.setId(4L);

        Item returned = new Item();
        returned.setItemName("apple");
        returned.setId(5L);


        ItemValidator validator = new ItemValidator();
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        validator.validateItem(item,itemRepository);

    }

    @Test(expected = ItemValidationException.class)
    public void validateIdThrowsExceptionOnInvalidAmountAvailable() throws Exception {
        Item item = new Item();
        item.setItemName(null);
        item.setAmountAvailable(-1);
        item.setId(4L);

        Item returned = new Item();
        returned.setItemName("apple");
        returned.setId(5L);


        ItemValidator validator = new ItemValidator();
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        validator.validateItem(item,itemRepository);

    }

}