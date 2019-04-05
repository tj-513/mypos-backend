package com.bootcamp.mypos.mypos.entity;

import com.bootcamp.mypos.mypos.entity.id.CompositeOrderItemId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@IdClass(CompositeOrderItemId.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OrderItem {

    @Id
    private Long itemId;
    @Id
    private Long orderId;
    private Integer quantity;

    @Id
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_order_fk")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_item_fk")
    private Item item;

}
