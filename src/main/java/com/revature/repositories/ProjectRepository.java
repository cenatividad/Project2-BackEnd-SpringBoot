package com.revature.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.InviteStatus;
import com.revature.models.Project;
import com.revature.models.User;
import com.revature.models.UserProject;
import com.revature.models.UserRole;

@Repository
public class ProjectRepository {

	@Autowired
	EntityManagerFactory emf;
	
	UserRepository userRepository = new UserRepository();
	
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

	public Project addUser(String userEmail, int projectID) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		User user = userRepository.getUserByEmail(userEmail);
		Project project = getProject(projectID);
		UserProject userProject = new UserProject();
		userProject.setProject(project);
		userProject.setUser(user);
		if (project.getUserProjects() == null) {
			userProject.setRole(UserRole.OWNER);
			userProject.setInviteStatus(InviteStatus.ACCEPTED);
			project.setUserProjects(new ArrayList<UserProject>());
		} else {
			userProject.setRole(UserRole.TEAM_MEMBER);
			userProject.setInviteStatus(InviteStatus.PENDING);
		}
				
		try(Session session = sf.openSession()) {
			int id = (int) session.save(userProject);
			userProject.setuPID(id);
		}
		
		List<UserProject> list = project.getUserProjects();
		list.add(userProject);
		project.setUserProjects(list);
		return project;
		
	}
}
