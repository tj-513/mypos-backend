package com.bootcamp.mypos.mypos.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class OrderDTO {

    private Long id;

    @JsonProperty("orderName")
    private String orderName;

    @JsonProperty("orderStatus")
    private String orderStatus;

    @JsonProperty("dateCreated")
    private Date dateCreated;


    @JsonProperty("userId")
    private Long userId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonIgnore
    public Long getUserId() {
        return userId;
    }
    @JsonProperty("userId")
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
