package com.bootcamp.mypos.mypos.api.item;

import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.dto.ItemDTO;
import com.bootcamp.mypos.mypos.exception.ItemValidationError;
import com.bootcamp.mypos.mypos.exception.ItemValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/items")
class ItemController {

    private static final String MSG_SERVER_ERROR = "Server Error Occurred";
    private static final int CODE_SERVER_ERROR = 500;

    @Autowired
    private ItemService itemService;

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
