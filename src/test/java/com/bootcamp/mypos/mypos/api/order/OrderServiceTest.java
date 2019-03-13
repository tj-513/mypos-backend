package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.Order;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Test
    public void testCreateOrderSuccessfully() throws Exception{
        Order order = new Order();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Order returnedOrder = new ModelMapper().map(order,Order.class);
        returnedOrder.setId(10L);

        Mockito.when(orderRepository.saveAndFlush(order)).thenReturn(returnedOrder);
        Assertions.assertThat(orderService.createOrder(order).getId()).isNotEqualTo(0L);
    }

    @Test
    public void updateOrderSuccessfully() throws Exception{

        Order order = new Order();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Order newOrder = new ModelMapper().map(order,Order.class);
        newOrder.setOrderName("Smith");

        Optional<Order> optionalOrder = Optional.of(newOrder);

        Mockito.when(orderRepository.saveAndFlush(order)).thenReturn(newOrder);
        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(optionalOrder);
        Assertions.assertThat(orderService.updateOrder(order).getOrderName()).isEqualTo(newOrder.getOrderName());
    }

    @Test
    public void getOrderSucessfully() throws Exception{

        Order order = new Order();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Order newOrder = new ModelMapper().map(order,Order.class);
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Optional<Order> optionalOrder = Optional.of(newOrder);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(optionalOrder);
        Assertions.assertThat(orderService.getOrder(Mockito.any()).getOrderName()).isEqualTo(newOrder.getOrderName());
    }

    @Test
    public void deleteOrder() throws Exception {

        Order order = new Order();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Order newOrder = new ModelMapper().map(order,Order.class);
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Optional<Order> optionalOrder = Optional.of(newOrder);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(optionalOrder);
        Assertions.assertThat(orderService.deleteOrder(Mockito.any())).isTrue();
    }
}