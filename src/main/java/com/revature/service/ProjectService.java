package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.InvitationDTO;
import com.revature.dtos.InvitationStatusDTO;
import com.revature.models.Project;
import com.revature.models.Story;
import com.revature.models.User;
import com.revature.models.UserProject;
import com.revature.repositories.ProjectRepository;
import com.revature.repositories.UserRepository;

/**
 * Service Bean to handle the business logic for Project-centered requests
 */
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
	
	/**
	 * Refers to the project repository to create a new project based on the passed model.
	 */
	public Project createProject(Project project) {
		return projectRepository.createProject(project);
	}

	/**
	 * Refers to the project repository to return a project related to the passed ID.
	 */
	public Project viewProject(int id) {
		return projectRepository.getProject(id);
	}

	/**
	 * Refers to the repository to create a new UserProject relation/invitation based on the passed
	 * DTO data
	 */
	public Project sendInvitation(InvitationDTO invitation) {
		String userEmail = invitation.getEmail();
		System.out.println(userEmail);
		User user = userRepository.getUserByEmail(userEmail);
		System.out.println("ProjectService.sendInvitation: user gotten");
		System.out.println("ProjectService.sendInvitation: got user: " + user.getUserID());
		int projectID = invitation.getProjectID();
		return projectRepository.inviteUser(user, projectID);
	}
	
	/**
	 * Refers to the repository to retrieve all projects related to the User who's ID is passed.
	 */
	public List<Project> getProjectsByUserId(int id) {
		return projectRepository.getProjectsByUserId(id);
	}

	/**
	 * Refers to the repository to retrieve all Stories related to the Project who's ID is passed.
	 * Communicates and cooperates with the StoryService to accomplish this.
	 */
	public List<Story> getStoriesByProject(int id) {
		return storyService.getStoriesByProject(id);
	}

	/**
	 * Refers to the repository to create a new story with the passed data and append it under the
	 * passed Project. Communicates and cooperates with the StoryService to accomplish this.
	 */
	public Story addNewStoryToProject(int projectID, Story story) {
		return this.storyService.addNewStoryToProject(projectID, story);
	}

	/**
	 * Refers to the repository to retrieve all UserProject relations based on the passed user ID.
	 */
	public List<UserProject> getInvitations(int uID) {
		return this.projectRepository.viewInvitations(uID);
	}

	/**
	 * Refers to the repository to process a UserProject relation with the passed invitation status
	 * change DTO
	 */
	public void processInvitations(InvitationStatusDTO invStat) {
		this.projectRepository.processInvitation(invStat);
	}
}
