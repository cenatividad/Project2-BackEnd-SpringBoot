package com.revature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.InvitationDTO;
import com.revature.models.Project;
import com.revature.models.User;
import com.revature.repositories.ProjectRepository;
import com.revature.repositories.UserRepository;

@Service
public class ProjectService {
	
	ProjectRepository projectRepository;
	UserRepository userRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
		super();
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
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
		return projectRepository.addUser(user, projectID);
	}
}
