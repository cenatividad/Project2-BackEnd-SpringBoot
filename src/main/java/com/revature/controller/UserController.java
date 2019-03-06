package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.revature.models.Project;
import com.revature.models.User;
import com.revature.service.UserService;

/**
 * Controller for user-oriented requests, delegates requests to necessary services.
 * @author tiand
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins="*", allowedHeaders= "*")
public class UserController {

	UserService userService;
	
	/**
	 * Constructor for the user Controller, initializes the needed services.
	 * @param userService
	 */
	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	/**
	 * Handles a request to get a user based on its ID.
	 */
	@GetMapping("{id}")
	public User getUserById(@PathVariable(name="id") int id) {
		User user = new User();
		user.setUserID(id);
		return user;
	}
	
	/**
	 * Handles a request to get all objects related to a specific user.
	 */
	@GetMapping("{id}/projects")
	public List<Project> getUserProjects(@PathVariable(name="id") int id) {
		List<Project> projectList = userService.getUserProjects(id);
		return projectList;
	}
	
	/**
	 * Handles post requests to create and save a user when signing a new user up.
	 * @param user
	 * @return
	 */
	@PostMapping("")
	public User saveUser(@RequestBody User user) {
		return this.userService.createUser(user);
	}
	
	/**
	 * Handles post requests to log a user in.
	 * @param user
	 * @return
	 */
	@PostMapping("/login")
	public User loginUser(@RequestBody User user) {
		return this.userService.loginUser(user);
	}
	
	/**
	 * Exception handler that provides meaningful exception messages to the client.
	 * @param e
	 * @return
	 */
	@ExceptionHandler
	public ResponseEntity<String> handleException(HttpClientErrorException e){
		return ResponseEntity.status(e.getStatusCode().value()).body(e.getMessage());
	}
}