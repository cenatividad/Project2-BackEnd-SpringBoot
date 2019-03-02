package com.revature.repositories;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Project;
import com.revature.models.Story;

@Repository
public class StoryRepository {

	@Autowired
	EntityManagerFactory emf;
	
	public List<Story> getStoriesByProject(int id) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()){
			Project project = session.get(Project.class, id);
			List<Story> stories = project.getStories();
			Hibernate.initialize(stories);
			//for(Story s : project.getStories()) System.out.println(s.getStoryName());
			//System.out.println(project);
			return stories;
		}
	}

}
