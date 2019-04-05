package com.bootcamp.mypos.mypos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String username;

    @Setter
    @Getter(onMethod = @__( @JsonIgnore ))
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String address;

    @Setter
    @Getter(onMethod = @__( @JsonIgnore ))
    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();

}
