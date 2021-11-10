package com.alifetvaci.soccergame.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alifetvaci.soccergame.models.User;
import com.alifetvaci.soccergame.payload.request.UserRequest;
import com.alifetvaci.soccergame.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepoistory;
	
	@Autowired
	PasswordEncoder encoder;
	

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/user")
	public Page<User> getAllUser(Pageable pageable) {
		logger.info("findAll User ");
		return userRepoistory.findAll(pageable);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/user")
	public User createuser(@RequestBody UserRequest userRequest) {
		User user = new User();
		user.setEmail(userRequest.getEmail());
		user.setPassword(encoder.encode(userRequest.getPassword()));
		user.setRoles(userRequest.getRoles());
		user.setUsername(userRequest.getUsername());
		
		return userRepoistory.save(user);
		

	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/user/{userId}")
	public User updateUser(@PathVariable String userId, @Valid @RequestBody UserRequest userRequest) {

		User user = userRepoistory.findById(userId).orElseThrow();
		user.setEmail(userRequest.getEmail());
		user.setPassword(user.getPassword());
		user.setRoles(userRequest.getRoles());
		user.setUsername(userRequest.getUsername());

		return userRepoistory.save(user);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/user/{userId}")
	public ResponseEntity<?> deleteItem(@PathVariable String userId) {
		userRepoistory.deleteById(userId);
		return ResponseEntity.ok().build();
	}
}
