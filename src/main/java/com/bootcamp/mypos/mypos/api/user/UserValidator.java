package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.exception.validation_errors.UserValidationError;
import com.bootcamp.mypos.mypos.exception.ValidationException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class UserValidator {

    void validateUser(User user, UserRepository userRepository)  {
        // check email
        if (user.getEmail() != null) {
            User existingUser = userRepository.findOneByEmail(user.getEmail());

            // make sure his / her own mail is not matched
            if (existingUser != null && !existingUser.getId().equals(user.getId())) {
                throw new ValidationException(UserValidationError.DUPLICATE_EMAIL);
            }
        } else {
            throw new ValidationException(UserValidationError.EMPTY_EMAIL);
        }


        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(user.getEmail());
        if (!matcher.matches()) {
            throw new ValidationException(UserValidationError.INVALID_EMAIL);
        }

        // check username
        if (user.getUsername() != null && !user.getUsername().trim().equals("")) {
            User existingUser = userRepository.findOneByUsername(user.getUsername());

            // again making sure user is not matched with his/herself
            if (existingUser != null && !existingUser.getId().equals(user.getId())) {
                throw new ValidationException(UserValidationError.INVALID_USERNAME);
            }
        } else {
            throw new ValidationException(UserValidationError.INVALID_USERNAME);
        }

    }


    User validateId(Long userId, UserRepository userRepository)  {

        // return valid user if found. else throw error
        Optional<User> found = userRepository.findById(userId);

        if (!found.isPresent()) {
            throw new ValidationException(UserValidationError.NON_EXISTENT_ID);
        } else {
            return found.get();
        }
    }
}
