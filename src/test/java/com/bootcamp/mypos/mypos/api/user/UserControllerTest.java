package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.ErrorMessage;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.UserDTO;
import com.bootcamp.mypos.mypos.exception.UserValidationError;
import com.bootcamp.mypos.mypos.exception.UserValidationException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    public void getUserSuccessfully() throws Exception {


        Mockito.when(userService.getUser(Mockito.any())).thenReturn(new User());
        Assertions.assertThat(userController.getUser(Mockito.any()).getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void getUserReturnsErrorMsgOnInvalidId() throws Exception {
        final Long userId = 100L;

        UserValidationException validationException = new UserValidationException(UserValidationError.NON_EXISTENT_ID);
        Mockito.when(userService.getUser(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(userController.getUser(userId).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(
                ((ErrorMessage) userController.getUser(userId).getBody())
                        .getErrorMessageText())
                .isEqualTo(UserValidationError.NON_EXISTENT_ID.getMessage() + ": " + userId);
        Assertions.assertThat(
                ((ErrorMessage) userController.getUser(userId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(400).value());


    }

    @Test
    public void getUserReturnsErrorMsgOnServerError() throws Exception {

        final Long userId = 100L;

        Mockito.when(userService.getUser(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(userController.getUser(userId).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(
                ((ErrorMessage) userController.getUser(userId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(500).value());
    }


    @Test
    public void createUserSuccessfully() throws Exception {
        UserDTO user = new UserDTO();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");


        User returnedUser = new ModelMapper().map(user, User.class);
        returnedUser.setId(10l);

        Mockito.when(userService.createUser(Mockito.any())).thenReturn(returnedUser);
        Assertions.assertThat(userController.createUser(user).getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(((User) userController.createUser(user).getBody()).getId()).isEqualTo(10L);
    }

    @Test
    public void createUserReturnsErrorMsgOnInvalidInfo() throws Exception {

        UserDTO user = new UserDTO();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");


        User returnedUser = new ModelMapper().map(user, User.class);
        returnedUser.setId(10l);

        UserValidationException validationException = new UserValidationException(UserValidationError.DUPLICATE_USERNAME);

        Mockito.when(userService.createUser(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(userController.createUser(user).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(((ErrorMessage) userController.createUser(user).getBody())
                .getErrorMessageText())
                .isEqualTo(UserValidationError.DUPLICATE_USERNAME.getMessage() + ": " + user.getUsername());

        Assertions.assertThat(((ErrorMessage) userController.createUser(user).getBody())
                .getStatus())
                .isEqualTo(400);

    }

    @Test
    public void createUserReturnsErrorMsgOnServerError() throws Exception {

        UserDTO user = new UserDTO();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");


        User returnedUser = new ModelMapper().map(user, User.class);
        returnedUser.setId(10l);


        Mockito.when(userService.createUser(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(userController.createUser(user).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(((ErrorMessage) userController.createUser(user).getBody())
                .getStatus())
                .isEqualTo(500);
    }


    @Test
    public void updateUserSuccessfully() throws Exception {

        UserDTO user = new UserDTO();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("newdoe");


        User returnedUser = new ModelMapper().map(user, User.class);
        returnedUser.setId(10l);
        returnedUser.setUsername("newdoe");

        Mockito.when(userService.updateUser(Mockito.any())).thenReturn(returnedUser);
        Assertions.assertThat(userController.updateUser(user).getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(((User) userController.updateUser(user).getBody())
                .getUsername()).isEqualTo(returnedUser.getUsername());

    }

    @Test
    public void updateUserReturnsErrorMsgOnInvalidInfo() throws Exception {


        UserDTO user = new UserDTO();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");


        User returnedUser = new ModelMapper().map(user, User.class);
        returnedUser.setId(10l);

        UserValidationException validationException = new UserValidationException(UserValidationError.DUPLICATE_USERNAME);

        Mockito.when(userService.updateUser(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(userController.updateUser(user).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(((ErrorMessage) userController.updateUser(user).getBody())
                .getErrorMessageText())
                .isEqualTo(UserValidationError.DUPLICATE_USERNAME.getMessage() + ": " + user.getUsername());

        Assertions.assertThat(((ErrorMessage) userController.updateUser(user).getBody())
                .getStatus())
                .isEqualTo(400);

    }

    @Test
    public void updateUserReturnsErrorMsgOnServerError() throws Exception {

        UserDTO user = new UserDTO();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");


        User returnedUser = new ModelMapper().map(user, User.class);
        returnedUser.setId(10l);


        Mockito.when(userService.updateUser(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(userController.updateUser(user).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(((ErrorMessage) userController.updateUser(user).getBody())
                .getStatus())
                .isEqualTo(500);


    }


    @Test
    public void deleteUserSuccessfully() throws Exception {

        Mockito.when(userService.deleteUser(Mockito.any())).thenReturn(true);
        Assertions.assertThat(userController.deleteUser(Mockito.any()).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteUserReturnsErrorMsgOnInvalidId() throws Exception {
        final Long userId = 100L;

        UserValidationException validationException = new UserValidationException(UserValidationError.NON_EXISTENT_ID);
        Mockito.when(userService.deleteUser(Mockito.any())).thenThrow(validationException);
        Assertions.assertThat(userController.deleteUser(userId).getStatusCode()).isEqualTo(HttpStatus.valueOf(400));
        Assertions.assertThat(
                ((ErrorMessage) userController.deleteUser(userId).getBody())
                        .getErrorMessageText())
                .isEqualTo(UserValidationError.NON_EXISTENT_ID.getMessage() + ": " + userId);
        Assertions.assertThat(
                ((ErrorMessage) userController.deleteUser(userId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(400).value());

    }

    @Test
    public void deleteUserReturnsErrorMsgOnServerError() throws Exception {
        final Long userId = 100L;

        Mockito.when(userService.deleteUser(Mockito.any())).thenThrow(new RuntimeException());
        Assertions.assertThat(userController.deleteUser(userId).getStatusCode()).isEqualTo(HttpStatus.valueOf(500));

        Assertions.assertThat(
                ((ErrorMessage) userController.deleteUser(userId).getBody())
                        .getStatus()).isEqualTo(HttpStatus.valueOf(500).value());
    }
}