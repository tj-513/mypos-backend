package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.dto.OrderDTO;
import com.bootcamp.mypos.mypos.exception.OrderValidationError;
import com.bootcamp.mypos.mypos.exception.OrderValidationException;
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
public class OrderControllerTest {

    @InjectMocks
    OrderController orderController;

    @Mock
    OrderService orderService;

    @Test
    public void getOrderSuccessfully() throws Exception {


        Mockito.when(orderService.getOrder(Mockito.any())).thenReturn(new Order());
        Assertions.assertThat(orderController.getOrder(Mockito.any()).getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void getOrderReturnsErrorMsgOnInvalidId() throws Exception {
        final Long orderId = 100L;

        OrderValidationException validationException = new OrderValidationException(OrderValidationError.NON_EXISTENT_ID);
        Mockito.when(orderService.getOrder(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(orderController.getOrder(orderId).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(
                ((ErrorMessage) orderController.getOrder(orderId).getBody())
                        .getErrorMessageText())
                .isEqualTo(OrderValidationError.NON_EXISTENT_ID.getMessage() + ": " + orderId);
        Assertions.assertThat(
                ((ErrorMessage) orderController.getOrder(orderId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(400).value());


    }

    @Test
    public void getOrderReturnsErrorMsgOnServerError() throws Exception {

        final Long orderId = 100L;

        Mockito.when(orderService.getOrder(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(orderController.getOrder(orderId).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(
                ((ErrorMessage) orderController.getOrder(orderId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(500).value());
    }


    @Test
    public void createOrderSuccessfully() throws Exception {
        OrderDTO order = new OrderDTO();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");

        Order returnedOrder = new ModelMapper().map(order, Order.class);
        returnedOrder.setId(10l);

        Mockito.when(orderService.createOrder(Mockito.any())).thenReturn(returnedOrder);
        Assertions.assertThat(orderController.createOrder(order).getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(((Order) orderController.createOrder(order).getBody()).getId()).isEqualTo(10L);
    }

    @Test
    public void createOrderReturnsErrorMsgOnInvalidInfo() throws Exception {

        OrderDTO order = new OrderDTO();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");

        Order returnedOrder = new ModelMapper().map(order, Order.class);
        returnedOrder.setId(10l);

        OrderValidationException validationException = new OrderValidationException(OrderValidationError.UNIDENTIFIED_ORDER_STATE);

        Mockito.when(orderService.createOrder(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(orderController.createOrder(order).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(((ErrorMessage) orderController.createOrder(order).getBody())
                .getErrorMessageText())
                .isEqualTo(OrderValidationError.UNIDENTIFIED_ORDER_STATE.getMessage());

        Assertions.assertThat(((ErrorMessage) orderController.createOrder(order).getBody())
                .getStatus())
                .isEqualTo(400);

    }

    @Test
    public void createOrderReturnsErrorMsgOnServerError() throws Exception {

        OrderDTO order = new OrderDTO();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");

        Order returnedOrder = new ModelMapper().map(order, Order.class);
        returnedOrder.setId(10l);


        Mockito.when(orderService.createOrder(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(orderController.createOrder(order).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(((ErrorMessage) orderController.createOrder(order).getBody())
                .getStatus())
                .isEqualTo(500);
    }


    @Test
    public void updateOrderSuccessfully() throws Exception {

        OrderDTO order = new OrderDTO();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");

        Order returnedOrder = new ModelMapper().map(order, Order.class);
        returnedOrder.setId(10l);
        returnedOrder.setOrderName("newdoe");

        Mockito.when(orderService.updateOrder(Mockito.any())).thenReturn(returnedOrder);
        Assertions.assertThat(orderController.updateOrder(order).getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(((Order) orderController.updateOrder(order).getBody())
                .getOrderName()).isEqualTo(returnedOrder.getOrderName());

    }

    @Test
    public void updateOrderReturnsErrorMsgOnInvalidInfo() throws Exception {


        OrderDTO order = new OrderDTO();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Order returnedOrder = new ModelMapper().map(order, Order.class);
        returnedOrder.setId(10l);

        OrderValidationException validationException = new OrderValidationException(OrderValidationError.NON_EXISTENT_ID);

        Mockito.when(orderService.updateOrder(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(orderController.updateOrder(order).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(((ErrorMessage) orderController.updateOrder(order).getBody())
                .getErrorMessageText())
                .isEqualTo(OrderValidationError.NON_EXISTENT_ID.getMessage() + ": " + order.getOrderId());

        Assertions.assertThat(((ErrorMessage) orderController.updateOrder(order).getBody())
                .getStatus())
                .isEqualTo(400);

    }

    @Test
    public void updateOrderReturnsErrorMsgOnServerError() throws Exception {

        OrderDTO order = new OrderDTO();
        order.setOrderName("some place in colombo");

        Order returnedOrder = new ModelMapper().map(order, Order.class);
        returnedOrder.setId(10l);


        Mockito.when(orderService.updateOrder(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(orderController.updateOrder(order).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(((ErrorMessage) orderController.updateOrder(order).getBody())
                .getStatus())
                .isEqualTo(500);


    }


    @Test
    public void deleteOrderSuccessfully() throws Exception {

        Mockito.when(orderService.deleteOrder(Mockito.any())).thenReturn(true);
        Assertions.assertThat(orderController.deleteOrder(Mockito.any()).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteOrderReturnsErrorMsgOnInvalidId() throws Exception {
        final Long orderId = 100L;

        OrderValidationException validationException = new OrderValidationException(OrderValidationError.NON_EXISTENT_ID);
        Mockito.when(orderService.deleteOrder(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(orderController.deleteOrder(orderId).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(
                ((ErrorMessage) orderController.deleteOrder(orderId).getBody())
                        .getErrorMessageText())
                .isEqualTo(OrderValidationError.NON_EXISTENT_ID.getMessage() + ": " + orderId);
        Assertions.assertThat(
                ((ErrorMessage) orderController.deleteOrder(orderId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(400).value());

    }

    @Test
    public void deleteOrderReturnsErrorMsgOnServerError() throws Exception {
        final Long orderId = 100L;

        Mockito.when(orderService.deleteOrder(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(orderController.deleteOrder(orderId).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(
                ((ErrorMessage) orderController.deleteOrder(orderId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(500).value());
    }
}