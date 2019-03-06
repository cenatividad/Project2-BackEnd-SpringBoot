package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Story;
import com.revature.repositories.StoryRepository;

/**
 * Service bean to handle business logic related to story-centered requests
 */
@Service
public class StoryService {
	StoryRepository storyRepository;
	
	@Autowired
	public StoryService(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}

	/**
	 * Refers to the repository to return all stories related to the Project whose ID is passed
	 */
	public List<Story> getStoriesByProject(int id) {
		return storyRepository.getStoriesByProject(id);
	}

	/**
	 * Refers to the repository to create a new story with the passed data and append it under the 
	 * passed project.
	 */
	public Story addNewStoryToProject(int projectID, Story story) {
		return this.storyRepository.addNewStoryToProject(projectID, story);
	}

	/**
	 * Refers to the repository to update a story with the passed data. This is a wholesale update
	 * and care should be had not to overwrite valid data with null data.
	 */
	public Story updateStory(Story story) {
		return this.storyRepository.updateStory(story);
	}

}
