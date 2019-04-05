package com.bootcamp.mypos.mypos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorMessage {

    private int status;
    @JsonProperty("message")
    private String errorMessageText;


}

