package com.bootcamp.mypos.mypos.entity.id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of ={"orderId", "itemId"})
public class CompositeOrderItemId implements Serializable {
    private Long orderId;
    private Long itemId;

}