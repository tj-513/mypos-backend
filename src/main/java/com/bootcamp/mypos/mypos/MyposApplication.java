package com.bootcamp.mypos.mypos;

import com.bootcamp.mypos.mypos.api.user.UserService;
import com.bootcamp.mypos.mypos.entity.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class MyposApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyposApplication.class, args);
	}


	@Bean
	public CommandLineRunner setupDefaultUser(UserService service) {
		return args -> {
//			User newUser = new User();
//			newUser.setUsername("user");
//			newUser.setEmail("someEmail@gmail.com");
//			newUser.setPassword("user");
//			service.createUser(newUser);

//			service.save(new User(
//					"user", //username
//					"user", //password
//					Arrays.asList(new Role("USER"), new Role("ACTUATOR")),//roles
//					true//Active
//			));
		};
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
