package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.UserDTO;
import com.bootcamp.mypos.mypos.exception.validation_errors.UserValidationError;
import com.bootcamp.mypos.mypos.exception.ValidationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("Duplicates") // uses similar code to handle server validation_errors
@RestController
@Api(value="mypos", tags = {"User Controller"})
@RequestMapping("api/users")
class UserController {

    private static final String MSG_SERVER_ERROR = "Server Error Occurred";
    private static final int CODE_SERVER_ERROR = 500;

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "View Details about the given user from id",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId) {

        try {

            User user = userService.getUser(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == UserValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + userId);
            } else {
                message.setErrorMessageText(e.getValidationError().getMessage());
            }
            logger.error(message.getErrorMessageText());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            logger.error(message.getErrorMessageText());
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
    public ResponseEntity createUser(@RequestBody UserDTO userDTO) {

        User user = new ModelMapper().map(userDTO, User.class);
        try {
            user = userService.createUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        } catch (ValidationException ex) {
            ErrorMessage message = getErrorMessage(user, ex);
            logger.error(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(message.getErrorMessageText());
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
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {

        User user = new ModelMapper().map(userDTO, User.class);
        try {
            user = userService.updateUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (ValidationException ex) {
            ErrorMessage message = getErrorMessage(user, ex);
            logger.error(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(message.getErrorMessageText());
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
    public ResponseEntity deleteUser(@PathVariable Long userId) {

        try {

            User user = userService.deleteUser(userId);

            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (ValidationException ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (ex.getValidationError() == UserValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + userId);
            } else {
                message.setErrorMessageText(ex.getValidationError().getMessage());
            }
            logger.error(ex.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR + ": " + ex.getMessage());
            logger.error(message.getErrorMessageText());
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
    public ResponseEntity getOrderList(@PathVariable Long userId) {

        try {

            List<Order> orderList = userService.getOrderList(userId);
            return new ResponseEntity<>(orderList, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == UserValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + userId);
            } else {
                message.setErrorMessageText(e.getValidationError().getMessage());
            }
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            logger.error(message.getErrorMessageText());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }

    @ApiOperation(value = "User Login",response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully logged in user"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping("/login")
    public ResponseEntity userLogin(@RequestBody UserDTO userDTO) {

        try {
            User user = userService.userLogin(userDTO);

            if(user != null)
                return new ResponseEntity<>(user, HttpStatus.OK);
            else {
                Map<String,String> map = new HashMap<>();
                map.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
                map.put("message", "Invalid Username or Password");
                return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
            }

        } catch (Exception ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(message.getErrorMessageText());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }

    // populates the error message
    private ErrorMessage getErrorMessage(User user, ValidationException ex) {
        ErrorMessage message = new ErrorMessage();

        UserValidationError validationError =(UserValidationError) ex.getValidationError();
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
