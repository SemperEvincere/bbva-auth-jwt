package com.authjwt;

import com.authjwt.models.ERole;
import com.authjwt.models.RoleEntity;
import com.authjwt.models.UserEntity;
import com.authjwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class SpringSecurityJwtApplication {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityJwtApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init() {
		return args -> {
			
			UserEntity userEntity = UserEntity.builder()
			                                  .email("admin@mail.com")
			                                  .username("Admin")
			                                  .password(passwordEncoder.encode("1234"))
			                                  .roles(Set.of(RoleEntity.builder()
			                                                          .name(ERole.valueOf(ERole.ADMIN.name()))
			                                                          .build()))
			                                  .build();
			
			UserEntity userEntity2 = UserEntity.builder()
			                                   .email("user@mail.com")
			                                   .username("User")
			                                   .password(passwordEncoder.encode("1234"))
			                                   .roles(Set.of(RoleEntity.builder()
			                                                           .name(ERole.valueOf(ERole.USER.name()))
			                                                           .build()))
			                                   .build();
			
			UserEntity userEntity3 = UserEntity.builder()
			                                   .email("invitado@mail.com")
			                                   .username("Invited")
			                                   .password(passwordEncoder.encode("1234"))
			                                   .roles(Set.of(RoleEntity.builder()
			                                                           .name(ERole.valueOf(ERole.INVITED.name()))
			                                                           .build()))
			                                   .build();
			
			userRepository.save(userEntity);
			userRepository.save(userEntity2);
			userRepository.save(userEntity3);
		};
	}
	
}
