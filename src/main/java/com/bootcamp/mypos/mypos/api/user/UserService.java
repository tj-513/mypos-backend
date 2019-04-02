package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.Order;
import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public
class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserValidator userValidator = new UserValidator();

    public User createUser(User user) {

        userValidator.validateUser(user, userRepository);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        return userRepository.saveAndFlush(user);

    }

    User updateUser(User user){

        // validate id, validate attributes make update
        userValidator.validateId(user.getId(),userRepository);
        userValidator.validateUser(user, userRepository);

        return userRepository.saveAndFlush(user);

    }


    List<Order> getOrderList(Long userId){
        User user = userValidator.validateId(userId, userRepository);
        List<Order> orders = user.getOrderList();
        orders.sort(Comparator.comparing(Order::getDateModified).reversed());
        return orders;
    }

    User getUser(Long userId){
        return userValidator.validateId(userId, userRepository);
    }

    User deleteUser(Long userId){

        // remove if id is valid
        User found = userValidator.validateId(userId, userRepository);
        userRepository.delete(found);
        return found;
    }


    public User userLogin(UserDTO userDTO) {
        User user = userRepository.findOneByUsername(userDTO.getUsername());
        if(user == null) return null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if( encoder.matches(userDTO.getPassword(), user.getPassword())){
            return user;
        }else{
            return null;
        }
    }
}