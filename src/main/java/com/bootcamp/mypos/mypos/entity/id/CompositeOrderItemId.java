package com.bootcamp.mypos.mypos.entity.id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode(of ={"orderId", "itemId"})
public class CompositeOrderItemId implements Serializable {
    private Long orderId;
    private Long itemId;

}