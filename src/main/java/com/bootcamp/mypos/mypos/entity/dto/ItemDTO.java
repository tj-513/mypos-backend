package com.bootcamp.mypos.mypos.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Date;


public class ItemDTO {

    private Long id;

    @JsonProperty("itemName")
    private String itemName;

    @JsonProperty("amountAvailable")
    private Integer amountAvailable;

    @JsonProperty("unitPrice")
    private Double unitPrice;

    @JsonProperty("dateAdded")
    private Date dateAdded;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(Integer amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}