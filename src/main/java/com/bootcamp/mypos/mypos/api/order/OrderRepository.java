package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findAllByOrderName(String orderName);
}
