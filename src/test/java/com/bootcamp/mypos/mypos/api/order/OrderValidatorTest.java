package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.exception.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderValidatorTest {


    @Mock
    OrderRepository orderRepository;



    @Test (expected = ValidationException.class)
    public void validateOrderThrowsExceptionOnInvalidStatel() throws Exception  {


        Order order = new Order();
        order.setOrderStatus("notopenorclose");
        order.setId(4L);

        Order returned = new Order();
        returned.setId(4L);

        OrderValidator validator = new OrderValidator();
        validator.validateOrder(order);
    }

    @Test
    public void validateOrderOnValidState() throws Exception  {


        Order order = new Order();
        order.setOrderStatus("open");
        order.setId(4L);

        Order returned = new Order();
        returned.setId(4L);

        OrderValidator validator = new OrderValidator();
        validator.validateOrder(order);
        assert true;
    }



}