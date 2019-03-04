package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.InvitationDTO;
import com.revature.models.Project;
import com.revature.models.User;
import com.revature.models.Story;
import com.revature.repositories.ProjectRepository;
import com.revature.repositories.UserRepository;

@Service
public class ProjectService {
	
	ProjectRepository projectRepository;
	UserRepository userRepository;
	
	StoryService storyService;
	@Autowired
	public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, StoryService storyService) {
		super();
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
		this.storyService = storyService;
	}
	
	public Project createProject(Project project) {
		return projectRepository.createProject(project);
	}

	public Project viewProject(int id) {
		return projectRepository.getProject(id);
	}

	public Project sendInvitation(InvitationDTO invitation) {
		String userEmail = invitation.getEmail();
		System.out.println(userEmail);
		User user = userRepository.getUserByEmail(userEmail);
		int projectID = invitation.getProjectID();
		return projectRepository.inviteUser(user, projectID);
	}
	
	public List<Project> getProjectsByUserId(int id) {
		return projectRepository.getProjectsByUserId(id);
	}

	public List<Story> getStoriesByProject(int id) {
		
		return storyService.getStoriesByProject(id);
	}
}
