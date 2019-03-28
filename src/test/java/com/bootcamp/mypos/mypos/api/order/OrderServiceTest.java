package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.api.item.ItemRepository;
import com.bootcamp.mypos.mypos.api.user.UserRepository;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.OrderItem;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.OrderDTO;
import com.bootcamp.mypos.mypos.entity.dto.OrderItemDTO;
import com.bootcamp.mypos.mypos.exception.ValidationException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
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

        Item item1 = new Item();
        item1.setAmountAvailable(100);

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setQuantity(100);
        orderItem1.setItem(item1);

        List<OrderItem> items = new ArrayList<>();
        items.add(orderItem1);

        order.setOrderItems(items);


        Optional<Order> optionalOrder = Optional.of(order);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(optionalOrder);
        Mockito.when(itemRepository.save(Mockito.any())).thenReturn(null);
        Assertions.assertThat(orderService.deleteOrder(Mockito.any())).isNotNull();
    }

    @Test
    public void addOrderItemSuccessfully() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);
        newOrder.setQuantity(5);

        Item item = new Item();
        item.setAmountAvailable(100);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order()));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getItemId()).isEqualTo(10L);
        Assertions.assertThat(orderService.addOrderItem(newOrder).getOrderId()).isEqualTo(15L);
    }

    @Test(expected = ValidationException.class)
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

    @Test(expected = ValidationException.class)
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

    @Test(expected = ValidationException.class)
    public void throwExceptionOnInvaliQuantity() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);
        newOrder.setQuantity(10);

        Item item = new Item();
        item.setId(10L);
        item.setAmountAvailable(5);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);
        orderService.addOrderItem(newOrder);
    }

    @Test(expected = ValidationException.class)
    public void throwExceptionOnNegativeQuantity() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);
        newOrder.setQuantity(-1);

        Item item = new Item();
        item.setId(10L);
        item.setAmountAvailable(5);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);
        orderService.addOrderItem(newOrder);
    }

    @Test
    public void deleteOrderItemSuccessfully() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);
        newOrder.setQuantity(5);
        newOrder.setUserId(10L);

        Item item = new Item();
        item.setAmountAvailable(100);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);


        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order()));
        Mockito.when(itemRepository.getOne(Mockito.any())).thenReturn(item);
        Mockito.when(orderItemRepository.findById(Mockito.any())).thenReturn(Optional.of(order));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);
        Assertions.assertThat(orderService.deleteOrderItem(newOrder).getItemId()).isEqualTo(10L);
        Assertions.assertThat(orderService.deleteOrderItem(newOrder).getOrderId()).isEqualTo(15L);
    }

    @Test
    public void changeOrderItemQuantitySuccessfully() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);
        newOrder.setQuantity(5);

        OrderItem found = new ModelMapper().map(newOrder, OrderItem.class);

        Item item = new Item();
        item.setAmountAvailable(100);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order()));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn((new Order()));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));
        Mockito.when(itemRepository.save(Mockito.any())).thenReturn((item));
        Mockito.when(orderItemRepository.findById(Mockito.any())).thenReturn(Optional.of(found));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);

        Assertions.assertThat(orderService.changeOrderItemQuantity(newOrder).getItemId()).isEqualTo(10L);
        Assertions.assertThat(orderService.changeOrderItemQuantity(newOrder).getOrderId()).isEqualTo(15L);
        Assertions.assertThat(orderService.changeOrderItemQuantity(newOrder).getQuantity()).isEqualTo(5);
    }


    @Test(expected = ValidationException.class)
    public void changeOrderItemQuantityThrowsExceptionOnNonExistentItemId() throws Exception {

        OrderItemDTO newOrder = new OrderItemDTO();
        newOrder.setItemId(10L);
        newOrder.setOrderId(15L);
        newOrder.setQuantity(5);

        OrderItem found = new ModelMapper().map(newOrder, OrderItem.class);

        Item item = new Item();
        item.setAmountAvailable(100);

        OrderItem order = new ModelMapper().map(newOrder, OrderItem.class);

        Mockito.when(orderRepository.findById(Mockito.any())).thenReturn(Optional.of(new Order()));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn((new Order()));
        Mockito.when(itemRepository.findById(Mockito.any())).thenReturn(Optional.of(item));
        Mockito.when(itemRepository.save(Mockito.any())).thenReturn((item));
        Mockito.when(orderItemRepository.findById(Mockito.any())).thenReturn(Optional.ofNullable(null));
        Mockito.when(orderItemRepository.saveAndFlush(Mockito.any())).thenReturn(order);

        orderService.changeOrderItemQuantity(newOrder);
    }
}