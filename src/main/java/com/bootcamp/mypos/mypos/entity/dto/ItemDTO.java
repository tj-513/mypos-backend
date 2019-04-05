package com.bootcamp.mypos.mypos.entity.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class ItemDTO {
    private Long id;
    private String itemName;
    private Integer amountAvailable;
    private Double unitPrice;
    private Date dateAdded;
}