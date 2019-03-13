package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.exception.OrderValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderValidatorTest {


    @Mock
    OrderRepository orderRepository;



    @Test (expected = OrderValidationException.class)
    public void validateOrderThrowsExceptionOnInvalidStatel() throws Exception  {


        Order order = new Order();
        order.setOrderStatus("notopenorclose");
        order.setId(4L);

        Order returned = new Order();
        returned.setId(4L);

        OrderValidator validator = new OrderValidator();
        validator.validateOrder(order,orderRepository);
    }

    @Test
    public void validateOrderOnValidState() throws Exception  {


        Order order = new Order();
        order.setOrderStatus("open");
        order.setId(4L);

        Order returned = new Order();
        returned.setId(4L);

        OrderValidator validator = new OrderValidator();
        validator.validateOrder(order,orderRepository);
        assert true;
    }



}