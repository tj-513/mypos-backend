package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.UserDTO;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void testCreateUserSuccessfully() throws Exception{
        User user = new User();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");

        User returnedUser = new ModelMapper().map(user,User.class);
        returnedUser.setId(10L);

        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(returnedUser);
        Assertions.assertThat(userService.createUser(user).getId()).isNotEqualTo(0L);
    }

    @Test
    public void updateUserSuccessfully() throws Exception{

        User user = new User();
        user.setAddress("some place in colombo");
        user.setEmail("valid@gmail.com");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");

        User newUser = new ModelMapper().map(user,User.class);
        newUser.setLastName("Smith");

        Optional<User> optionalUser = Optional.of(newUser);

        Mockito.when(userRepository.saveAndFlush(user)).thenReturn(newUser);
        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(optionalUser);
        Assertions.assertThat(userService.updateUser(user).getLastName()).isEqualTo(newUser.getLastName());
    }

    @Test
    public void getUserSucessfully() throws Exception{

        User user = new User();
        user.setLastName("Smith");

        User newUser = new ModelMapper().map(user,User.class);
        newUser.setLastName("Smith");

        Optional<User> optionalUser = Optional.of(newUser);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(optionalUser);
        Assertions.assertThat(userService.getUser(Mockito.any()).getLastName()).isEqualTo(newUser.getLastName());
    }

    @Test
    public void deleteUser() throws Exception {

        User user = new User();
        user.setLastName("Smith");

        User newUser = new ModelMapper().map(user,User.class);
        newUser.setLastName("Smith");

        Optional<User> optionalUser = Optional.of(newUser);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(optionalUser);
        Assertions.assertThat(userService.deleteUser(Mockito.any())).isTrue();
    }

    @Test
    public void getUserOrdersListSucessfully() throws Exception{

        User user = new User();
        user.setLastName("Smith");

        User newUser = new ModelMapper().map(user,User.class);
        newUser.setLastName("Smith");

        Optional<User> optionalUser = Optional.of(newUser);

        Mockito.when(userRepository.findById(Mockito.any())).thenReturn(optionalUser);
        Assertions.assertThat(userService.getOrderList(Mockito.any())).isNotNull();
    }

    @Test
    public void userLoginSuccessfully() throws Exception{

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("somehashedpassword");



        User user = new ModelMapper().map(userDTO,User.class);

        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");

        User returnedUser = new ModelMapper().map(user,User.class);
        returnedUser.setId(10L);

        Mockito.when(userRepository.findOneByUsername(Mockito.any())).thenReturn(returnedUser);
        Assertions.assertThat(userService.userLogin(userDTO).getId()).isEqualTo(10L);
    }


    @Test
    public void userLoginReturnsNullOnWrongPassword() throws Exception{

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("not-somehashedpassword");



        User user = new ModelMapper().map(userDTO,User.class);

        user.setPassword("somehashedpassword");
        user.setUsername("jdoe");

        User returnedUser = new ModelMapper().map(user,User.class);
        returnedUser.setId(10L);

        Mockito.when(userRepository.findOneByUsername(Mockito.any())).thenReturn(returnedUser);
        Assertions.assertThat(userService.userLogin(userDTO)).isNull();
    }

}