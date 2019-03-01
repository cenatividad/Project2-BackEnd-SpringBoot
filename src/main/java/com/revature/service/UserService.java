package com.revature.service;

import org.mindrot.jbcrypt.BCrypt;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.util.InputValidationUtil;

/**
 * User-related business logic
 * @author tiand
 *
 */
@Service
public class UserService {
	UserRepository userRepository;
	
	/**
	 * Constructor with relevant dependency injection
	 * @param userRepository
	 */
	@Autowired
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	/**
	 * Verifies proper members before delegating user creation to the repository layer.
	 * @param user
	 * @return
	 */
	public User createUser(User user) {
		InputValidationUtil.isEmailOK(user.getEmail());
		InputValidationUtil.isUsernameOK(user.getUsername());
		InputValidationUtil.isPasswordOK(user.getPassword());
		InputValidationUtil.isFirstNameOK(user.getFirstName());
		InputValidationUtil.isLastNameOK(user.getLastName());
		
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		
		try {
			return userRepository.createUser(user);
		} catch (PSQLException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}