package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.entity.Item;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    ItemService itemService;

    @Mock
    ItemRepository itemRepository;

    @Test
    public void testCreateItemSuccessfully() throws Exception{
        Item item = new Item();
        item.setItemName("Orange");
        item.setAmountAvailable(10);

        Item returnedItem = new ModelMapper().map(item,Item.class);
        returnedItem.setId(10L);

        Mockito.when(itemRepository.saveAndFlush(item)).thenReturn(returnedItem);
        Assertions.assertThat(itemService.createItem(item).getId()).isNotEqualTo(0L);
    }

    @Test
    public void updateItemSuccessfully() throws Exception{

        Item item = new Item();
        item.setItemName("Orange");
        item.setAmountAvailable(10);


        Item newItem = new ModelMapper().map(item,Item.class);
        newItem.setItemName("Smith");

        Optional<Item> optionalItem = Optional.of(newItem);

        Mockito.when(itemRepository.saveAndFlush(item)).thenReturn(newItem);
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(optionalItem);
        Assertions.assertThat(itemService.updateItem(item).getItemName()).isEqualTo(newItem.getItemName());
    }

    @Test
    public void getItemSucessfully() throws Exception{

        Item item = new Item();
        item.setItemName("Orange");
        item.setAmountAvailable(10);


        Item newItem = new ModelMapper().map(item,Item.class);
        item.setItemName("Orange");
        item.setAmountAvailable(10);


        Optional<Item> optionalItem = Optional.of(newItem);

        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(optionalItem);
        Assertions.assertThat(itemService.getItem(Mockito.any()).getItemName()).isEqualTo(newItem.getItemName());
    }

    @Test
    public void deleteItem() throws Exception {

        Item item = new Item();
        item.setItemName("Orange");
        item.setAmountAvailable(10);


        Item newItem = new ModelMapper().map(item,Item.class);
        item.setItemName("Apple");
        item.setAmountAvailable(10);


        Optional<Item> optionalItem = Optional.of(newItem);

        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(optionalItem);
        Assertions.assertThat(itemService.deleteItem(Mockito.any())).isTrue();
    }
}