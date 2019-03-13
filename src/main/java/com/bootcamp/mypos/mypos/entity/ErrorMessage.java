package com.bootcamp.mypos.mypos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorMessage {

    private int status;
    @JsonProperty("message")
    private String errorMessageText;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMessageText() {
        return errorMessageText;
    }

    public void setErrorMessageText(String errorMessageText) {
        this.errorMessageText = errorMessageText;
    }

}

