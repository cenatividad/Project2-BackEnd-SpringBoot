package com.revature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Project;
import com.revature.repositories.ProjectRepository;

@Service
public class ProjectService {
	
	ProjectRepository projectRepository;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository) {
		super();
		this.projectRepository = projectRepository;
	}
	
	public Project createProject(Project project) {
		return projectRepository.createProject(project);
	}
}
