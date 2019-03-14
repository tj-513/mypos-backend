package com.bootcamp.mypos.mypos.api.order;

/*
 *  create order
 *  delete order
 *  edit order
 *  add item
 *  remove item
 *  get all orders
 * */


import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.OrderItem;
import com.bootcamp.mypos.mypos.entity.dto.OrderDTO;
import com.bootcamp.mypos.mypos.entity.dto.OrderItemDTO;
import com.bootcamp.mypos.mypos.exception.OrderValidationError;
import com.bootcamp.mypos.mypos.exception.OrderValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/orders")
class OrderController {

    private static final String MSG_SERVER_ERROR = "Server Error Occurred";
    private static final int CODE_SERVER_ERROR = 500;

    @Autowired
    private OrderService orderService;

    @GetMapping("/{orderId}")
    ResponseEntity getOrder(@PathVariable Long orderId) {

        try {

            Order order = orderService.getOrder(orderId);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (OrderValidationException e) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == OrderValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderId);
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
    ResponseEntity createOrder(@RequestBody OrderDTO orderDTO) {
        Order order = null;
        try {
            order = orderService.createOrder(orderDTO);
            return new ResponseEntity<>(order, HttpStatus.CREATED);

        } catch (OrderValidationException ex) {

            ErrorMessage message = getErrorMessage(orderDTO, ex);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));
        }
    }


    @PutMapping()
    ResponseEntity updateOrder(@RequestBody OrderDTO orderDTO) {

        Order order = new ModelMapper().map(orderDTO, Order.class);
        try {
            order = orderService.updateOrder(order);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (OrderValidationException ex) {
            ErrorMessage message = getErrorMessage(orderDTO, ex);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }

    @DeleteMapping("/{orderId}")
    ResponseEntity deleteOrder(@PathVariable Long orderId) {

        try {

            orderService.deleteOrder(orderId);

            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (OrderValidationException ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (ex.getValidationError() == OrderValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + orderId);
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

    @PutMapping("/addItem")
    ResponseEntity addItemToOrder(@RequestBody OrderItemDTO orderItemDTO) {

        try {
            OrderItem order = orderService.addOrderItem(orderItemDTO);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (OrderValidationException ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);
            switch (ex.getValidationError()) {
                case NON_EXISTENT_ITEM_ID:
                    message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + orderItemDTO.getItemId());
                    break;
                case NON_EXISTENT_ORDER_ID:
                    message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + orderItemDTO.getOrderId());
                    break;
                case ITEM_ALREADY_EXISTS_IN_ORDER:
                    message.setErrorMessageText(ex.getValidationError().getMessage());
                    break;
                case QUANTITY_LARGER_THAN_AVAILABLE:
                case INVALID_QUANTITY:
                    message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + orderItemDTO.getQuantity());
                    break;

                default:

            }

            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }

    @PutMapping("/changeItemQuantity")
    ResponseEntity changeOrderItemQuantity(@RequestBody OrderItemDTO orderItemDTO) {

        try {
            OrderItem order = orderService.changeOrderItemQuantity(orderItemDTO);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (OrderValidationException ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);
            switch (ex.getValidationError()) {
                case NON_EXISTENT_ITEM_ID:
                    message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + orderItemDTO.getItemId());
                    break;
                case NON_EXISTENT_ID:
                    message.setErrorMessageText(ex.getValidationError().getMessage()
                            + ": order:"
                            + orderItemDTO.getOrderId()
                            + "  item:" + orderItemDTO.getItemId());
                    break;
                case QUANTITY_LARGER_THAN_AVAILABLE:
                case INVALID_QUANTITY:
                    message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + orderItemDTO.getQuantity());
                    break;

                default:

            }

            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }


    @DeleteMapping("/deleteOrderItem")
    ResponseEntity deleteOrderItem(@RequestBody OrderItemDTO orderItemDTO) {

        try {
            OrderItem order = orderService.deleteOrderItem(orderItemDTO);
            return new ResponseEntity<>(order, HttpStatus.OK);

        } catch (OrderValidationException ex) {
            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);
            switch (ex.getValidationError()) {

                case NON_EXISTENT_ID:
                    message.setErrorMessageText(ex.getValidationError().getMessage()
                            + ": order:"
                            + orderItemDTO.getOrderId()
                            + "  item:" + orderItemDTO.getItemId());
                    break;
                case UNAUTHORIZED_USER:
                    message.setErrorMessageText(ex.getValidationError().getMessage() + ": " + orderItemDTO.getUserId());
                    break;

                default:

            }

            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        } catch (Exception ex) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(CODE_SERVER_ERROR);
            message.setErrorMessageText(MSG_SERVER_ERROR);
            return new ResponseEntity<>(message, HttpStatus.valueOf(message.getStatus()));

        }
    }

    @GetMapping("/items/{orderId}")
    ResponseEntity getOrderItems(@PathVariable Long orderId) {

        try {

            Order order = orderService.getOrder(orderId);
            return new ResponseEntity<>(order.getOrderItems(), HttpStatus.OK);

        } catch (OrderValidationException e) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(400);

            if (e.getValidationError() == OrderValidationError.NON_EXISTENT_ID) {
                message.setErrorMessageText(e.getValidationError().getMessage() + ": " + orderId);
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
    private ErrorMessage getErrorMessage(OrderDTO order, OrderValidationException ex) {
        ErrorMessage message = new ErrorMessage();

        OrderValidationError validationError = ex.getValidationError();
        if (validationError == OrderValidationError.NON_EXISTENT_ID) {
            message.setErrorMessageText(validationError.getMessage() + ": " + order.getId());
        } else {
            message.setErrorMessageText(validationError.getMessage());
        }

        message.setStatus(400);
        return message;
    }
}
