package com.revature.repositories;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.Project;
import com.revature.models.Story;
import com.revature.models.StoryStatus;

/**
 * Repository Bean that handles database interaction in relation to Stories.
 */
@Repository
public class StoryRepository {

	@Autowired
	EntityManagerFactory emf;
	
	@Autowired
	ProjectRepository projectRepository;
	
	/**
	 * Returns a list of all stories related to the User whose ID is passed.
	 */
	public List<Story> getStoriesByProject(int id) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()){
			Project project = session.get(Project.class, id);
			List<Story> stories = project.getStories();
			Hibernate.initialize(stories);
			return stories;
		}
	}

	/**
	 * Creates a new story with the passed data and related project, then saves it to the database
	 */
	public Story addNewStoryToProject(int projectID, Story story) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		// Get detached reference to project
		Project project = this.projectRepository.getProject(projectID);
		
		try (Session session = sf.openSession()) {
			
			// Persist the new story
			story.setProject(project);
			// Status of new stories must be PENDING
			story.setStatus(StoryStatus.PENDING);
			int id = (int) session.save(story);
			story.setStoryID(id);
			
			// Return the story
			return story;
		}
	}

	/**
	 * Updates a story with the information from the passed Story. This is a wholesale update and if
	 * any members are intended to remain the same, the passed Story should have the old values in 
	 * it. 
	 */
	public Story updateStory(Story story) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
	
		try (Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			Story s = session.get(Story.class, story.getStoryID());
			s.setDescription(story.getDescription());
			s.setPoints(story.getPoints());
			s.setStatus(story.getStatus());
			s.setStoryName(story.getStoryName());
			session.flush();
			tx.commit();
			return s;
		}
	}
}
