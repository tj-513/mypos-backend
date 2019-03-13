package com.bootcamp.mypos.mypos.api.greeting;

public class Greeting {
    private String greetingMessage;

    Greeting(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }

    public String getGreetingMessage() {
        return greetingMessage;
    }

    public void setGreetingMessage(String greetingMessage) {
        this.greetingMessage = greetingMessage;
    }
}
