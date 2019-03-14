package com.bootcamp.mypos.mypos.entity.id;

import java.io.Serializable;
import java.util.Objects;

public class CompositeOrderItemId implements Serializable {
    private Long orderId;
    private Long itemId;

    public CompositeOrderItemId() {
    }

    public CompositeOrderItemId(Long orderId, Long itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeOrderItemId itemId1 = (CompositeOrderItemId) o;
        if (orderId != itemId1.orderId) return false;
        return itemId == itemId1.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, itemId);
    }
}