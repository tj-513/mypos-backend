package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.dto.ItemDTO;
import com.bootcamp.mypos.mypos.exception.ItemValidationError;
import com.bootcamp.mypos.mypos.exception.ItemValidationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(value="mypos", description="Operations pertaining to Items in System")
@RequestMapping("api/items")
class ItemController {

    private static final String MSG_SERVER_ERROR = "Server Error Occurred";
    private static final int CODE_SERVER_ERROR = 500;

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "View Details about the given product from id",response = Item.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved item"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping("/{itemId}")
    ResponseEntity getItem(@PathVariable Long itemId) {

        try {

            Item item = itemService.getItem(itemId);
            return new ResponseEntity<>(item, HttpStatus.OK);

        } catch (ItemValidationException e) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == ItemValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + itemId);
            } else {
                message.setErrorMessageText(e.getValidationError().getMessage());
            }
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }


    @ApiOperation(value = "Creates a new item",response = Item.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created item"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PostMapping()
    ResponseEntity createItem(@RequestBody ItemDTO itemDTO) {

        Item item = new ModelMapper().map(itemDTO, Item.class);
        try {
            item = itemService.createItem(item);
            return new ResponseEntity<>(item, HttpStatus.CREATED);

        } catch (ItemValidationException ex) {

            ErrorMessage message = getErrorMessage(item, ex);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }

    @ApiOperation(value = "Update an item's details",response = Item.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated item"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @PutMapping()
    ResponseEntity updateItem(@RequestBody ItemDTO itemDTO) {

        Item item = new ModelMapper().map(itemDTO, Item.class);
        try {
            item = itemService.updateItem(item);
            return new ResponseEntity<>(item, HttpStatus.OK);

        } catch (ItemValidationException ex) {
            ErrorMessage message = getErrorMessage(item, ex);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }

    @ApiOperation(value = "Delete a given item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted item"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @DeleteMapping("/{itemId}")
    ResponseEntity deleteItem(@PathVariable Long itemId) {

        try {

            itemService.deleteItem(itemId);

            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (ItemValidationException ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (ex.getValidationError() == ItemValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + itemId);
            } else {
                message.setErrorMessageText(ex.getValidationError().getMessage());
            }
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR + ": " + ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }


    @ApiOperation(value = "Endpoint for item autosuggestions",response = Item.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved item"),
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping("/search/{itemName}")
    ResponseEntity getItemSuggestions(@PathVariable String itemName) {

        try {

            List<Item> items = itemService.getMatchingItems(itemName);
            return new ResponseEntity<>(items, HttpStatus.OK);

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }


    // populates the error message
    private ErrorMessage getErrorMessage(Item item, ItemValidationException ex) {
        ErrorMessage message = new ErrorMessage();

        ItemValidationError validationError = ex.getValidationError();
        if (validationError == ItemValidationError.NON_EXISTENT_ID) {
            message.setErrorMessageText(validationError.getMessage() + ": " + item.getId());
        } else {
            message.setErrorMessageText(validationError.getMessage());
        }

        message.setStatus(400);
        return message;
    }
}
