package com.bootcamp.mypos.mypos.api.greeting;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class GreetingControllerTest {

    @InjectMocks
    GreetingController greetingController;

    @Test
    public void getGreetingSuccessfully() throws Exception {


        Assertions.assertThat(greetingController.greeting().getStatusCode()).isEqualTo(HttpStatus.OK);

    }

}