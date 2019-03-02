package com.revature.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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

	public Project getProject(int id) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		Project project = new Project();
		
		try(Session session = sf.openSession()) {
			project = session.get(Project.class, id);
			return project;
		}
	}

	public List<Project> getProjectsByUserId(int id) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try (Session session = sf.openSession()){
			Transaction tx = session.beginTransaction();
			List<?> projectIds = session.createQuery("select up.project.projectID from UserProject up where up.user.userID = :id")
					.setParameter("id", id).list();
			if (projectIds.size() == 0) {
				tx.commit();
				return new ArrayList<Project>();
			}
			
			List<?> projects = session.createQuery("from Project p where p.projectID in (:pIds)")
					.setParameter("pIds", projectIds).list();
			tx.commit();
			return (List<Project>) projects;
		}
		// TODO Auto-generated method stub
	}
}
