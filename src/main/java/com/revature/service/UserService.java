package com.revature.service;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Project;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.util.InputValidationUtil;

/**
 * Service bean that handles business logic for user-centered requests
 */
@Service
public class UserService {
	UserRepository userRepository;
	
	ProjectService projectService;
	
	/**
	 * Constructor with relevant dependency injection
	 * @param userRepository
	 */
	@Autowired
	public UserService(UserRepository userRepository, ProjectService projectService) {
		super();
		this.userRepository = userRepository;
		this.projectService = projectService;
	}

	/**
	 * Verifies proper members before delegating user creation to the repository layer.
	 * @throws HttpClientErrorException 
	 */
	public User createUser(User user) throws HttpClientErrorException {
		InputValidationUtil.isEmailOK(user.getEmail());
		InputValidationUtil.isUsernameOK(user.getUsername());
		InputValidationUtil.isPasswordOK(user.getPassword());
		InputValidationUtil.isFirstNameOK(user.getFirstName());
		InputValidationUtil.isLastNameOK(user.getLastName());
		
		user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
		
		try {
			User createdUser = userRepository.createUser(user);
			createdUser.setPassword(null);
			return createdUser;
		} catch (PSQLException e) {
			throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * Service to verify user credentials and log them in if they're correct. Will throw an 
	 * error if the credentials are invalid. Error will not be specific about which credential is
	 * wrong.
	 * @throws HttpClientErrorException 
	 */
	public User loginUser(User user) throws HttpClientErrorException {
		User targetUser = userRepository.getUserByUsername(user.getUsername());
		
		if(targetUser == null || !BCrypt.checkpw(user.getPassword(), targetUser.getPassword())) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Wrong credentials");
		}
		
		targetUser.setPassword(null);
		return targetUser;
	}

	/**
	 * Communicates with the ProjectService to retrieve a list of projects related to the User
	 * whose ID is passed.
	 * @throws com.revature.util.HttpClientErrorException 
	 */
	public List<Project> getUserProjects(int id) throws HttpClientErrorException {
		List<Project> projects = projectService.getProjectsByUserId(id);
		if(projects == null) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID not found");
		} else {
			return projects;
		}
	}
}