package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.api.item.ItemRepository;
import com.bootcamp.mypos.mypos.api.user.UserRepository;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.OrderItem;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.OrderDTO;
import com.bootcamp.mypos.mypos.entity.dto.OrderItemDTO;
import com.bootcamp.mypos.mypos.exception.OrderValidationException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ItemRepository itemRepository;

    @Mock
    OrderItemRepository orderItemRepository;

    @Test
    public void testCreateOrderSuccessfully() throws Exception{
        OrderDTO order = new OrderDTO();
        order.setOrderName("some place in colombo");
        order.setOrderStatus("open");


        Order returnedOrder = new ModelMapper().map(order,Order.class);
        returnedOrder.setId(10L);

        Mockito.when(orderRepository.saveAndFlush(Mockito.any())).thenReturn(returnedOrder);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(new User()));
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

    @Test
    public void addOrderSuccessfully() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order()));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(new Item()));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getItemId()).isEqualTo(10L);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getOrderId()).isEqualTo(15L);
    }

    @Test(expected = OrderValidationException.class)
    public void throwExceptionOnInvalidOrderId() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(new Item()));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getItemId()).isEqualTo(10L);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getOrderId()).isEqualTo(15L);
    }

    @Test(expected = OrderValidationException.class)
    public void throwExceptionOnInvaliItemId() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order()));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getItemId()).isEqualTo(10L);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getOrderId()).isEqualTo(15L);
    }
}