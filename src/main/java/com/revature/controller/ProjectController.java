package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.InvitationDTO;
import com.revature.dtos.InvitationStatusDTO;
import com.revature.models.Project;
import com.revature.models.Story;
import com.revature.models.UserProject;
import com.revature.service.ProjectService;

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
		
		@PostMapping("/create")
		public Project saveProject(@RequestBody Project project) {
			return this.projectService.createProject(project);
		}
		
		@GetMapping("/{id}")
		public Project viewProject(@PathVariable(name="id") int id) {
			return this.projectService.viewProject(id);
		}
		
		@PostMapping("/invite")
		public Project inviteUser(@RequestBody InvitationDTO invitation) {
			return this.projectService.sendInvitation(invitation);
		}
		
		@PostMapping("/viewInvitations")
		public List<UserProject> viewInvitations(@RequestBody int uID) {
			return this.projectService.getInvitations(uID);
		}
		
		@PostMapping("/processInvitation")
		public void processInvitation(@RequestBody InvitationStatusDTO invStat) {
			this.projectService.processInvitations(invStat);
		}

		@GetMapping("/{id}/stories")
		public List<Story> getStoriesByProject(@PathVariable(name="id") int id){
			return this.projectService.getStoriesByProject(id);
		}
		
		@PostMapping("/{id}/stories")
		public Story addNewStoryToProject(@PathVariable(name="id") int projectID, @RequestBody Story story) {
			return this.projectService.addNewStoryToProject(projectID, story);
		}
}
