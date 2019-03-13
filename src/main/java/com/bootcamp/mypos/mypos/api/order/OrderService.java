package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.exception.OrderValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    private OrderValidator orderValidator = new OrderValidator();

    Order createOrder(Order order) throws OrderValidationException {

        orderValidator.validateOrder(order, orderRepository);
        return orderRepository.saveAndFlush(order);

    }

    Order updateOrder(Order order) throws OrderValidationException{

        // validate id, validate attributes make update
        Order existingOrder = orderValidator.validateId(order.getId(),orderRepository);
        orderValidator.validateOrder(order, orderRepository);

        existingOrder.setOrderName(order.getOrderName());
        existingOrder.setOrderStatus(order.getOrderStatus());

        return orderRepository.saveAndFlush(order);

    }

    Order getOrder(Long orderId) throws OrderValidationException{

        // return if found
        return orderValidator.validateId(orderId, orderRepository);

    }

    boolean deleteOrder(Long orderId) throws OrderValidationException{

        // remove if id is valid
        Order found = orderValidator.validateId(orderId, orderRepository);
        orderRepository.delete(found);
        return true;
    }


}