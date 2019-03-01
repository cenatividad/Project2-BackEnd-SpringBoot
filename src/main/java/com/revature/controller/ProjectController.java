package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Project;
import com.revature.service.ProjectService;

@RestController
@RequestMapping("/project")
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
		
}
