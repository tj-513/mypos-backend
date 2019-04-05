package com.bootcamp.mypos.mypos.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private String orderName;
    private String orderStatus;
    private Date dateCreated;
    @Setter
    @Getter(onMethod = @__( @JsonIgnore ))
    private Long userId;
}
