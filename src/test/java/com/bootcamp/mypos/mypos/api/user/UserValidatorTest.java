package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.exception.UserValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.Silent.class)
public class UserValidatorTest {


    @Mock
    UserRepository userRepository;


    @Test(expected = UserValidationException.class)
    public void validateUserThrowsExceptionOnExistingEmail() throws Exception {
        User user = new User();
        user.setEmail("valid@gmail.com");
        user.setId(4L);

        User returned = new User();
        returned.setId(5L);


        UserValidator validator = new UserValidator();
        Mockito.when(userRepository.findOneByEmail(user.getEmail())).thenReturn(returned);
        validator.validateUser(user,userRepository);

    }

    @Test (expected = UserValidationException.class)
    public void validateUserThrowsExceptionOnNullEmail() throws Exception {
        User user = new User();
        UserValidator validator = new UserValidator();
        Mockito.when(userRepository.findOneByEmail(user.getEmail())).thenReturn(new User());
        validator.validateUser(user,userRepository);


    }
    @Test (expected = UserValidationException.class)
    public void validateUserThrowsExceptionOnInvalidEmail() throws Exception  {


        User user = new User();
        user.setEmail("nvalidmail.com");
        user.setId(4L);

        User returned = new User();
        returned.setId(4L);


        UserValidator validator = new UserValidator();
        Mockito.when(userRepository.findOneByEmail(user.getEmail())).thenReturn(returned);
        validator.validateUser(user,userRepository);
    }

    @Test (expected = UserValidationException.class)
    public void validateUserThrowsExceptionOnInvalidUsername() throws Exception {
        User user = new User();
        user.setEmail("nvali@dmail.com");
        user.setUsername("user");
        user.setId(4L);

        User returned = new User();
        returned.setUsername("user");
        returned.setId(4L);

        User returned1 = new User();
        returned1.setUsername("user");
        returned1.setId(5L);


        UserValidator validator = new UserValidator();
        Mockito.when(userRepository.findOneByEmail(user.getEmail())).thenReturn(returned);
        Mockito.when(userRepository.findOneByUsername(Mockito.anyString())).thenReturn(returned1);
        validator.validateUser(user,userRepository);

    }


}