package com.bootcamp.mypos.mypos.api.order;
import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.OrderItem;
import com.bootcamp.mypos.mypos.entity.dto.OrderDTO;
import com.bootcamp.mypos.mypos.entity.dto.OrderItemDTO;
import com.bootcamp.mypos.mypos.exception.validation_errors.OrderValidationError;
import com.bootcamp.mypos.mypos.exception.validation_errors.ValidationError;
import com.bootcamp.mypos.mypos.exception.ValidationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("Duplicates") // uses similar code to handle server validation_errors
@RestController
@Api(value = "mypos", tags = {"Order Controller"})
@RequestMapping("api/orders")
class OrderController {

    private static final String MSG_SERVER_ERROR = "Server Error Occurred";
    private static final int CODE_SERVER_ERROR = 500;

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "View Details about the given order from id", response = Item.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity getOrder(@PathVariable Long orderId) {

        try {

            Order order = orderService.getOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == OrderValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderId);
            } else {
                message.setErrorMessageText(e.getValidationError().getMessage());
            }
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }


    @ApiOperation(value = "Create a new order", response = Order.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created order"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PostMapping()
    public ResponseEntity createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = null;
        try {
            order = orderService.createOrder(orderDTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (ValidationException e) {
            ErrorMessage message = getErrorMessage(orderDTO, e);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }


    @ApiOperation(value = "Update an order", response = Order.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated order"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping()
    public ResponseEntity updateOrder(@RequestBody OrderDTO orderDTO) {

        Order order = new ModelMapper().map(orderDTO, Order.class);
        try {
            order = orderService.updateOrder(order);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = getErrorMessage(orderDTO, e);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }


    @ApiOperation(value = "Delete an order", response = Order.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted order"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteOrder(@PathVariable Long orderId) {

        try {

            Order order = orderService.deleteOrder(orderId);

            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == OrderValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderId);
            } else {
                message.setErrorMessageText(e.getValidationError().getMessage());
            }
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR + ": " + e.getMessage());
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }


    @ApiOperation(value = "Add an order item to an order", response = OrderItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully added order item"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping("/addItem")
    public ResponseEntity addItemToOrder(@RequestBody OrderItemDTO orderItemDTO) {

        try {
            OrderItem order = orderService.addOrderItem(orderItemDTO);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);
            switch ((OrderValidationError)e.getValidationError()) {
                case NON_EXISTENT_ITEM_ID:
                    message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderItemDTO.getItemId());
                    break;
                case NON_EXISTENT_ORDER_ID:
                    message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderItemDTO.getOrderId());
                    break;
                case ITEM_ALREADY_EXISTS_IN_ORDER:
                    message.setErrorMessageText(e.getValidationError().getMessage());
                    break;
                case QUANTITY_LARGER_THAN_AVAILABLE:
                case INVALID_QUANTITY:
                    message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderItemDTO.getQuantity());
                    break;

                default:

            }

            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }


    @ApiOperation(value = "Update order item quantity", response = Order.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated order item"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @PutMapping("/changeItemQuantity")
    public ResponseEntity changeOrderItemQuantity(@RequestBody OrderItemDTO orderItemDTO) {

        try {
            OrderItem order = orderService.changeOrderItemQuantity(orderItemDTO);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);
            switch ((OrderValidationError)e.getValidationError()) {
                case NON_EXISTENT_ITEM_ID:
                    message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderItemDTO.getItemId());
                    break;
                case NON_EXISTENT_ID:
                    message.setErrorMessageText(e.getValidationError().getMessage()
                            + ": order:"
                            + orderItemDTO.getOrderId()
                            + "  item:" + orderItemDTO.getItemId());
                    break;
                case QUANTITY_LARGER_THAN_AVAILABLE:
                case INVALID_QUANTITY:
                    message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderItemDTO.getQuantity());
                    break;

                default:

            }

            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }


    @ApiOperation(value = "Delete an order  item", response = Order.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted order item"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @DeleteMapping("/deleteOrderItem")
    public ResponseEntity deleteOrderItem(@RequestBody OrderItemDTO orderItemDTO) {

        try {
            OrderItem order = orderService.deleteOrderItem(orderItemDTO);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);
            switch ((OrderValidationError)e.getValidationError()) {

                case NON_EXISTENT_ID:
                    message.setErrorMessageText(e.getValidationError().getMessage()
                            + ": order:"
                            + orderItemDTO.getOrderId()
                            + "  item:" + orderItemDTO.getItemId());
                    break;
                case UNAUTHORIZED_USER_DELETE:
                    message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderItemDTO.getUserId());
                    break;

                default:

            }

            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }


    @ApiOperation(value = "Get list of items in an order", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Server error")
    })
    @GetMapping("/items/{orderId}")
    public ResponseEntity getOrderItems(@PathVariable Long orderId) {

        try {

            Order order = orderService.getOrder(orderId);
            return new ResponseEntity<>(order.getOrderItems(), HttpStatus.OK);

        } catch (ValidationException e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == OrderValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderId);
            } else {
                message.setErrorMessageText(e.getValidationError().getMessage());
            }
            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception e) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);

            logger.error(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }

    // populates the error message
    private ErrorMessage getErrorMessage(OrderDTO order, ValidationException ex) {
        ErrorMessage message = new ErrorMessage();

        ValidationError validationError = ex.getValidationError();
        if (validationError == OrderValidationError.NON_EXISTENT_ID) {
            message.setErrorMessageText(validationError.getMessage() + ": " + order.getId());
        } else {
            message.setErrorMessageText(validationError.getMessage());
        }

        message.setStatus(400);
        return message;
    }
}
