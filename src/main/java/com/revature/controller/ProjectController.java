package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Project;
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
		
		@PostMapping("/viewProject")
		public Project viewProject(@RequestBody int id) {
			return this.projectService.viewProject(id);
		}
}
