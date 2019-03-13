package com.bootcamp.mypos.mypos.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class OrderDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;

    @JsonProperty("orderName")
    private String orderName;

    @JsonProperty("orderStatus")
    private String orderStatus;

    @JsonProperty("dateCreated")
    private Date dateCreated;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
}
