package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.api.item.ItemRepository;
import com.bootcamp.mypos.mypos.api.user.UserRepository;
import com.bootcamp.mypos.mypos.entity.Item;
import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.OrderItem;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.OrderDTO;
import com.bootcamp.mypos.mypos.entity.dto.OrderItemDTO;
import com.bootcamp.mypos.mypos.entity.id.CompositeOrderItemId;
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

        // if the order item already added return the existing item
        Optional<OrderItem> orderItemExisting = orderItemRepository
                .findById(new CompositeOrderItemId(orderItemDTO.getOrderId(),orderItemDTO.getItemId()));

        if(orderItemExisting.isPresent()){
            throw new OrderValidationException(OrderValidationError.ITEM_ALREADY_EXISTS_IN_ORDER);
        }


        Optional<Order> orderOptional = orderRepository.findById(orderItemDTO.getOrderId());
        Optional<Item>  itemOptional = itemRepository.findById(orderItemDTO.getItemId());

        if(!orderOptional.isPresent())
            throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ORDER_ID);
        if(!itemOptional.isPresent())
            throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ITEM_ID);

        Item item = itemOptional.get();
        Order order = orderOptional.get();

        if(item.getAmountAvailable() < orderItemDTO.getQuantity())
            throw new OrderValidationException(OrderValidationError.QUANTITY_LARGER_THAN_AVAILABLE);

        if(orderItemDTO.getQuantity() < 1)
            throw new OrderValidationException(OrderValidationError.INVALID_QUANTITY);


        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setItem(item);
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItem.setOrderId(orderItemDTO.getOrderId());
        orderItem.setItemId(orderItemDTO.getItemId());

        return orderItemRepository.saveAndFlush(orderItem);

    }

    OrderItem changeOrderItemQuantity(OrderItemDTO orderItemDTO) throws OrderValidationException {
        Optional<Item>  itemOptional = itemRepository.findById(orderItemDTO.getItemId());

        CompositeOrderItemId id = new CompositeOrderItemId(orderItemDTO.getOrderId(), orderItemDTO.getItemId());

        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(id);

        if(!itemOptional.isPresent())
            throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ITEM_ID);

        if(!orderItemOptional.isPresent())
            throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ID);

        Item item = itemOptional.get();


        if(item.getAmountAvailable() < orderItemDTO.getQuantity())
            throw new OrderValidationException(OrderValidationError.QUANTITY_LARGER_THAN_AVAILABLE);

        if(orderItemDTO.getQuantity() < 1)
            throw new OrderValidationException(OrderValidationError.INVALID_QUANTITY);


        OrderItem orderItem = orderItemOptional.get();
        orderItem.setQuantity(orderItemDTO.getQuantity());


        return orderItemRepository.saveAndFlush(orderItem);

    }

    OrderItem deleteOrderItem(OrderItemDTO orderItemDTO) throws OrderValidationException {

        CompositeOrderItemId id = new CompositeOrderItemId(orderItemDTO.getOrderId(), orderItemDTO.getItemId());

        Optional<OrderItem> orderItemOptional = orderItemRepository.findById(id);


        if(!orderItemOptional.isPresent())
            throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ID);


        OrderItem orderItem = orderItemOptional.get();

        Long userId = orderItem.getOrder().getUser().getId();

        if(userId != orderItemDTO.getUserId())
            throw new OrderValidationException(OrderValidationError.UNAUTHORIZED_USER);

        return orderItemRepository.saveAndFlush(orderItem);

    }
}