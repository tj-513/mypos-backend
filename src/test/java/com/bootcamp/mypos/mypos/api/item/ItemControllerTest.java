package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.dto.ItemDTO;
import com.bootcamp.mypos.mypos.exception.ItemValidationError;
import com.bootcamp.mypos.mypos.exception.ItemValidationException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @InjectMocks
    ItemController itemController;

    @Mock
    ItemService itemService;

    @Test
    public void getItemSuccessfully() throws Exception {

        Mockito.when(itemService.getItem(Mockito.any())).thenReturn(new Item());
        Assertions.assertThat(itemController.getItem(Mockito.any()).getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void getItemReturnsErrorMsgOnInvalidId() throws Exception {
        final Long itemId = 100L;

        ItemValidationException validationException = new ItemValidationException(ItemValidationError.NON_EXISTENT_ID);
        Mockito.when(itemService.getItem(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(itemController.getItem(itemId).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(
                ((ErrorMessage) itemController.getItem(itemId).getBody())
                        .getErrorMessageText())
                .isEqualTo(ItemValidationError.NON_EXISTENT_ID.getMessage() + ": " + itemId);
        Assertions.assertThat(
                ((ErrorMessage) itemController.getItem(itemId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(400).value());


    }

    @Test
    public void getItemReturnsErrorMsgOnServerError() throws Exception {

        final Long itemId = 100L;

        Mockito.when(itemService.getItem(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(itemController.getItem(itemId).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(
                ((ErrorMessage) itemController.getItem(itemId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(500).value());
    }


    @Test
    public void createItemSuccessfully() throws Exception {
        ItemDTO item = new ItemDTO();
        item.setItemName("Oranges");
        item.setAmountAvailable(10);

        Item returnedItem = new ModelMapper().map(item, Item.class);
        returnedItem.setId(10l);

        Mockito.when(itemService.createItem(Mockito.any())).thenReturn(returnedItem);
        Assertions.assertThat(itemController.createItem(item).getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(((Item) itemController.createItem(item).getBody()).getId()).isEqualTo(10L);
    }

    @Test
    public void createItemReturnsErrorMsgOnInvalidInfo() throws Exception {

        ItemDTO item = new ItemDTO();
        item.setItemName("Oranges");
        item.setAmountAvailable(10);
        Item returnedItem = new ModelMapper().map(item, Item.class);
        returnedItem.setId(10l);

        ItemValidationException validationException = new ItemValidationException(ItemValidationError.ITEM_NAME_EXISTS);

        Mockito.when(itemService.createItem(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(itemController.createItem(item).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(((ErrorMessage) itemController.createItem(item).getBody())
                .getErrorMessageText())
                .isEqualTo(ItemValidationError.ITEM_NAME_EXISTS.getMessage());

        Assertions.assertThat(((ErrorMessage) itemController.createItem(item).getBody())
                .getStatus())
                .isEqualTo(400);

    }

    @Test
    public void createItemReturnsErrorMsgOnServerError() throws Exception {

        ItemDTO item = new ItemDTO();
        item.setItemName("Oranges");
        item.setAmountAvailable(10);
        Item returnedItem = new ModelMapper().map(item, Item.class);
        returnedItem.setId(10l);


        Mockito.when(itemService.createItem(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(itemController.createItem(item).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(((ErrorMessage) itemController.createItem(item).getBody())
                .getStatus())
                .isEqualTo(500);
    }


    @Test
    public void updateItemSuccessfully() throws Exception {

        ItemDTO item = new ItemDTO();
        item.setItemName("Oranges");
        item.setAmountAvailable(10);

        Item returnedItem = new ModelMapper().map(item, Item.class);
        returnedItem.setId(10l);
        returnedItem.setItemName("newdoe");

        Mockito.when(itemService.updateItem(Mockito.any())).thenReturn(returnedItem);
        Assertions.assertThat(itemController.updateItem(item).getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(((Item) itemController.updateItem(item).getBody())
                .getItemName()).isEqualTo(returnedItem.getItemName());

    }

    @Test
    public void updateItemReturnsErrorMsgOnInvalidInfo() throws Exception {


        ItemDTO item = new ItemDTO();
        item.setItemName("Oranges");
        item.setAmountAvailable(10);


        Item returnedItem = new ModelMapper().map(item, Item.class);
        returnedItem.setId(10l);

        ItemValidationException validationException = new ItemValidationException(ItemValidationError.NON_EXISTENT_ID);

        Mockito.when(itemService.updateItem(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(itemController.updateItem(item).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(((ErrorMessage) itemController.updateItem(item).getBody())
                .getErrorMessageText())
                .isEqualTo(ItemValidationError.NON_EXISTENT_ID.getMessage() + ": " + item.getId());

        Assertions.assertThat(((ErrorMessage) itemController.updateItem(item).getBody())
                .getStatus())
                .isEqualTo(400);

    }

    @Test
    public void updateItemReturnsErrorMsgOnServerError() throws Exception {

        ItemDTO item = new ItemDTO();
        item.setItemName("some place in colombo");

        Item returnedItem = new ModelMapper().map(item, Item.class);
        returnedItem.setId(10l);


        Mockito.when(itemService.updateItem(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(itemController.updateItem(item).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(((ErrorMessage) itemController.updateItem(item).getBody())
                .getStatus())
                .isEqualTo(500);


    }


    @Test
    public void deleteItemSuccessfully() throws Exception {

        Mockito.when(itemService.deleteItem(Mockito.any())).thenReturn(true);
        Assertions.assertThat(itemController.deleteItem(Mockito.any()).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteItemReturnsErrorMsgOnInvalidId() throws Exception {
        final Long itemId = 100L;

        ItemValidationException validationException = new ItemValidationException(ItemValidationError.NON_EXISTENT_ID);
        Mockito.when(itemService.deleteItem(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(itemController.deleteItem(itemId).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(
                ((ErrorMessage) itemController.deleteItem(itemId).getBody())
                        .getErrorMessageText())
                .isEqualTo(ItemValidationError.NON_EXISTENT_ID.getMessage() + ": " + itemId);
        Assertions.assertThat(
                ((ErrorMessage) itemController.deleteItem(itemId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(400).value());

    }

    @Test
    public void deleteItemReturnsErrorMsgOnServerError() throws Exception {
        final Long itemId = 100L;

        Mockito.when(itemService.deleteItem(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(itemController.deleteItem(itemId).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(
                ((ErrorMessage) itemController.deleteItem(itemId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(500).value());
    }
}