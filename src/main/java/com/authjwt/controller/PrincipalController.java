package com.authjwt.controller;

import com.authjwt.controller.request.CreateUserRequest;
import com.authjwt.models.ERole;
import com.authjwt.models.RoleEntity;
import com.authjwt.models.UserEntity;
import com.authjwt.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class PrincipalController {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/hello")
	public String hello() {
		return "Hello World Not Secured";
	}
	
	@GetMapping("/helloSecured")
	public String helloSecured() {
		return "Hello World Secured";
	}
	
	@PostMapping("/createUser")
	public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
		
		Set<RoleEntity> roles = createUserRequest.getRoles()
                                             .stream()
                                             .map(role -> RoleEntity.builder()
		                                                            .name(ERole.valueOf(role))
		                                                            .build())
                                             .collect(Collectors.toSet());
		
		UserEntity userEntity = UserEntity.builder()
		                                  .username(createUserRequest.getUsername())
		                                  .password(passwordEncoder.encode(createUserRequest.getPassword()))
		                                  .email(createUserRequest.getEmail())
		                                  .roles(roles)
		                                  .build();
		
		userRepository.save(userEntity);
		
		return ResponseEntity.ok(userEntity);
	}
	
	@DeleteMapping("/deleteUser")
	public String deleteUser(@RequestParam String id) {
		userRepository.deleteById(Long.parseLong(id));
		return "Se ha borrado el user con id".concat(id);
	}
}
