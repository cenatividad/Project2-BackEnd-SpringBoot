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

import com.revature.dtos.InvitationDTO;
import com.revature.dtos.InvitationStatusDTO;
import com.revature.models.Project;
import com.revature.models.Story;
import com.revature.models.UserProject;
import com.revature.service.ProjectService;

/**
 * Controller for project-centered requests. Communicates with the appropriate service methods to
 * resolve the requests.
 */
@RestController
@RequestMapping("/project")
@CrossOrigin(origins="*", allowedHeaders="*")
public class ProjectController {
		ProjectService projectService;
		
		@Autowired
		public ProjectController(ProjectService projectService) {
			super();
			this.projectService = projectService;
		}
		
		/**
		 * Handles a request to create a new project
		 */
		@PostMapping("/create")
		public Project saveProject(@RequestBody Project project) {
			return this.projectService.createProject(project);
		}
		
		/**
		 * Handles a request to find a specific project by its ID.
		 */
		@GetMapping("/{id}")
		public Project viewProject(@PathVariable(name="id") int id) {
			return this.projectService.viewProject(id);
		}
		
		/**
		 * Handles a request for inviting a user into a project
		 */
		@PostMapping("/invite")
		public Project inviteUser(@RequestBody InvitationDTO invitation) {
			return this.projectService.sendInvitation(invitation);
		}
		
		/**
		 * Handles a request for viewing all project invitations that a user's received. User's
		 * found by the provided ID.
		 */
		@PostMapping("/viewInvitations")
		public List<UserProject> viewInvitations(@RequestBody int uID) {
			return this.projectService.getInvitations(uID);
		}
		
		/**
		 * Handles a request to process a user's response to a project invitation. It expects an
		 * InvitationStatusDTO containing the pertinent information.
		 */
		@PostMapping("/processInvitation")
		public void processInvitation(@RequestBody InvitationStatusDTO invStat) {
			this.projectService.processInvitations(invStat);
		}

		/**
		 * Handles a request to get all stories by the ID of the project they're a part of.
		 */
		@GetMapping("/{id}/stories")
		public List<Story> getStoriesByProject(@PathVariable(name="id") int id){
			return this.projectService.getStoriesByProject(id);
		}
		
		/**
		 * Handles a request to create a new story and add it under the specified project.
		 */
		@PostMapping("/{id}/stories")
		public Story addNewStoryToProject(@PathVariable(name="id") int projectID, @RequestBody Story story) {
			return this.projectService.addNewStoryToProject(projectID, story);
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
