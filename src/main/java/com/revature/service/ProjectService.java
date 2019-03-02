package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Project;
import com.revature.models.Story;
import com.revature.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	ProjectRepository projectRepository;
	StoryService storyService;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository, StoryService storyService) {
		super();
		this.projectRepository = projectRepository;
		this.storyService = storyService;
	}
	
	public Project createProject(Project project) {
		return projectRepository.createProject(project);
	}

	public Project viewProject(int id) {
		return projectRepository.getProject(id);
	}

	public List<Project> getProjectsByUserId(int id) {
		return projectRepository.getProjectsByUserId(id);
	}

	public List<Story> getStoriesByProject(int id) {
		
		return storyService.getStoriesByProject(id);
	}
}
