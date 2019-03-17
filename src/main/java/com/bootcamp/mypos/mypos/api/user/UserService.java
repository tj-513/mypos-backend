package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.UserDTO;
import com.bootcamp.mypos.mypos.exception.UserValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserValidator userValidator = new UserValidator();

    User createUser(User user) throws UserValidationException {

        userValidator.validateUser(user, userRepository);
        return userRepository.saveAndFlush(user);

    }

    User updateUser(User user) throws UserValidationException{

        // validate id, validate attributes make update
        userValidator.validateId(user.getId(),userRepository);
        userValidator.validateUser(user, userRepository);

        return userRepository.saveAndFlush(user);

    }


    List<Order> getOrderList(Long userId) throws UserValidationException{
        User user = userValidator.validateId(userId, userRepository);
        return user.getOrderList();
    }

    User getUser(Long userId) throws UserValidationException{
        return userValidator.validateId(userId, userRepository);
    }

    boolean deleteUser(Long userId) throws UserValidationException{

        // remove if id is valid
        User found = userValidator.validateId(userId, userRepository);
        userRepository.delete(found);
        return true;
    }


    public User userLogin(UserDTO userDTO) {
        User user = userRepository.findOneByUsername(userDTO.getUsername());
        if(user == null) return null;
        if( user.getPassword().equals(userDTO.getPassword())){
            return user;
        }else{
            return null;
        }
    }
}