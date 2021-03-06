package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.exception.validation_errors.OrderValidationError;
import com.bootcamp.mypos.mypos.exception.ValidationException;

import java.util.Optional;

class OrderValidator {

    void validateOrder(Order order) {

        // check order status
        String status = order.getOrderStatus();
        if (status != null
                &&
                !("open".equals(status.toLowerCase().trim())
                        || "closed".equals(status.toLowerCase().trim()))) {

            throw new ValidationException(OrderValidationError.UNIDENTIFIED_ORDER_STATE);
        }

    }


    Order validateId(Long orderId, OrderRepository orderRepository) {

        // return valid order if found. else throw error
        Optional<Order> found = orderRepository.findById(orderId);

        if (!found.isPresent()) {
            throw new ValidationException(OrderValidationError.NON_EXISTENT_ID);
        } else {
            return found.get();
        }
    }
}
