package com.bootcamp.mypos.mypos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long id;
    private String orderName;
    private String orderStatus;
    private Date dateCreated;
    private Date dateModified;

    @Setter
    @Getter(onMethod = @__( @JsonIgnore ))
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_order_id")
    private User user;

    @Setter
    @Getter(onMethod = @__( @JsonIgnore ))
    @OneToMany(mappedBy ="order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

}