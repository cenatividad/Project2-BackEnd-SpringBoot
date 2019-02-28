package com.revature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

	UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	// Transaction Propagation
	// Concept: Contextual Sessions
	public User createUser(User user) {
		// Some internal business logic: validation, etc.
		return userRepository.createUser(user);
	}

}