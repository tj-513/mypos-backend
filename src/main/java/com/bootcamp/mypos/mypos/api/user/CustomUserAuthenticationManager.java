package com.bootcamp.mypos.mypos.api.user;

import com.bootcamp.mypos.mypos.entity.User;
import com.bootcamp.mypos.mypos.entity.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
public class CustomUserAuthenticationManager implements AuthenticationManager {

    @Autowired
    UserService userService;



    @Override
    public Authentication authenticate(Authentication authentication) {

        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);


        User authenticated = userService.userLogin(userDTO);

        if(authenticated == null) throw new BadCredentialsException("user not found");



        return new UsernamePasswordAuthenticationToken(username,password, Arrays.asList(new GrantedAuthority[0]));
    }
}
