package com.revature.repositories;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Project;

@Repository
public class ProjectRepository {

	@Autowired
	EntityManagerFactory emf;
	
	public Project createProject(Project project) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()) {
			
			int id = (int) session.save(project);
			project.setProjectID(id);
			return project;
		}
		
		
		
	}
}
