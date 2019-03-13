package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.exception.OrderValidationError;
import com.bootcamp.mypos.mypos.exception.OrderValidationException;

import java.util.Optional;

class OrderValidator {

    void validateOrder(Order order, OrderRepository orderRepository) throws OrderValidationException {
        // check email

        String status = order.getOrderStatus();
        if (status != null
                &&
                !("open".equals(status.toLowerCase().trim())
                        || "closed".equals(status.toLowerCase().trim()))) {

            throw new OrderValidationException(OrderValidationError.UNIDENTIFIED_ORDER_STATE);
        }

    }


    Order validateId(Long orderId, OrderRepository orderRepository) throws OrderValidationException {

        // return valid order if found. else throw error
        Optional<Order> found = orderRepository.findById(orderId);

        if (!found.isPresent()) {
            throw new OrderValidationException(OrderValidationError.NON_EXISTENT_ID);
        } else {
            return found.get();
        }
    }
}
