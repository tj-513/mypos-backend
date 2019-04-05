package com.bootcamp.mypos.mypos.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {
    private Long orderId;
    private Long itemId;
    private Integer quantity;
    private Long userId;
}
