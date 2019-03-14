package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.UserDTO;
import com.bootcamp.mypos.mypos.exception.UserValidationError;
import com.bootcamp.mypos.mypos.exception.UserValidationException;
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
@Api(value="mypos", description="Operations pertaining to Users in in System")
@RequestMapping("api/users")
class UserController {

    private static final String MSG_SERVER_ERROR = "Server Error Occurred";
    private static final int CODE_SERVER_ERROR = 500;

    @Autowired
    private UserService userService;


    @ApiOperation(value = "View Details about the given user from id",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/{userId}")
    ResponseEntity getUser(@PathVariable Long userId) {

        try {

            User user = userService.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (UserValidationException e) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == UserValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + userId);
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

    @ApiOperation(value = "Create new user",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created user"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping()
    ResponseEntity createUser(@RequestBody UserDTO userDTO) {

        User user = new ModelMapper().map(userDTO, User.class);
        try {
            user = userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        } catch (UserValidationException ex) {

            ErrorMessage message = getErrorMessage(user, ex);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }

    @ApiOperation(value = "Update user",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated user"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping()
    ResponseEntity updateUser(@RequestBody UserDTO userDTO) {

        User user = new ModelMapper().map(userDTO, User.class);
        try {
            user = userService.updateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (UserValidationException ex) {
            ErrorMessage message = getErrorMessage(user, ex);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }

    @ApiOperation(value = "Delete user",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted user"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @DeleteMapping("/{userId}")
    ResponseEntity deleteUser(@PathVariable Long userId) {

        try {

            userService.deleteUser(userId);

            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (UserValidationException ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (ex.getValidationError() == UserValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + userId);
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

    @ApiOperation(value = "Returns a list of all orders for a given user",response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/orderlist/{userId}")
    ResponseEntity getOrderList(@PathVariable Long userId) {

        try {

            List<Order> orderList = userService.getOrderList(userId);
            return new ResponseEntity<>(orderList, HttpStatus.OK);

        } catch (UserValidationException e) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == UserValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + userId);
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



    // populates the error message
    private ErrorMessage getErrorMessage(User user, UserValidationException ex) {
        ErrorMessage message = new ErrorMessage();

        UserValidationError validationError = ex.getValidationError();
        switch (validationError) {
            case DUPLICATE_EMAIL:
            case INVALID_EMAIL:
            case EMPTY_EMAIL:
                message.setErrorMessageText(validationError.getMessage() + ": " + user.getEmail());
                break;

            case DUPLICATE_USERNAME:
            case INVALID_USERNAME:
                message.setErrorMessageText(validationError.getMessage() + ": " + user.getUsername());
                break;
            case NON_EXISTENT_ID:
                message.setErrorMessageText(validationError.getMessage() + ": " + user.getId());
                break;
            default:
                message.setErrorMessageText(validationError.getMessage());

        }

        message.setStatus(400);
        return message;
    }
}
