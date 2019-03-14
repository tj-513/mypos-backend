package com.bootcamp.mypos.mypos.api.order;

import com.bootcamp.mypos.mypos.entity.OrderItem;
import com.bootcamp.mypos.mypos.entity.id.CompositeOrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, CompositeOrderItemId> {
}
