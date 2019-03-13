package com.bootcamp.mypos.mypos.api.greeting;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
    @GetMapping("/api")
    public ResponseEntity<Greeting> greeting() {
        Greeting greeting = new Greeting("Hiii");
        String current = greeting.getGreetingMessage();
        greeting.setGreetingMessage(current + " Greetings from mypos api");

        return new ResponseEntity<>(new Greeting("Greetings from mypos api"), HttpStatus.OK);
    }

}