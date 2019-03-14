package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.api.item.ItemRepository;
import com.bootcamp.mypos.mypos.api.user.UserRepository;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.OrderItem;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.OrderDTO;
import com.bootcamp.mypos.mypos.entity.dto.OrderItemDTO;
import com.bootcamp.mypos.mypos.exception.OrderValidationError;
import com.bootcamp.mypos.mypos.exception.OrderValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    private OrderValidator orderValidator = new OrderValidator();

    Order createOrder(OrderDTO orderDTO) throws OrderValidationException {

        // add to user list here
        Order order = new ModelMapper().map(orderDTO, Order.class);
        orderValidator.validateOrder(order, orderRepository);

        Optional<User> userOptional = userRepository.findById(orderDTO.getUserId());

        userOptional.ifPresent(user -> userRepository.saveAndFlush(user));

        return orderRepository.saveAndFlush(order);

    }

    Order updateOrder(Order order) throws OrderValidationException {

        // validate id, validate attributes make update
        Order existingOrder = orderValidator.validateId(order.getId(), orderRepository);
        orderValidator.validateOrder(order, orderRepository);

        existingOrder.setOrderName(order.getOrderName());
        existingOrder.setOrderStatus(order.getOrderStatus());

        return orderRepository.saveAndFlush(order);

    }

    Order getOrder(Long orderId) throws OrderValidationException {

        // return if found
        return orderValidator.validateId(orderId, orderRepository);

    }

    boolean deleteOrder(Long orderId) throws OrderValidationException {

        // remove if id is valid
        Order found = orderValidator.validateId(orderId, orderRepository);
        orderRepository.delete(found);
        return true;
    }


    OrderItem addOrderItem(OrderItemDTO orderItemDTO) throws OrderValidationException {
        Optional<Order> order = orderRepository.findById(orderItemDTO.getOrderId());
        Optional<Item>  item = itemRepository.findById(orderItemDTO.getItemId());

        if(!order.isPresent()) throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ORDER_ID);
        if(!item.isPresent()) throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ITEM_ID);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order.get());
        orderItem.setItem(item.get());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrderId(orderItemDTO.getOrderId());
        orderItem.setItemId(orderItemDTO.getItemId());

        return orderItemRepository.saveAndFlush(orderItem);

    }
}