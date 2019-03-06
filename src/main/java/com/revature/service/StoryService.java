package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Story;
import com.revature.repositories.StoryRepository;

@Service
public class StoryService {
	StoryRepository storyRepository;
	
	@Autowired
	public StoryService(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}

	public List<Story> getStoriesByProject(int id) {
		return storyRepository.getStoriesByProject(id);
	}

	public Story addNewStoryToProject(int projectID, Story story) {
		return this.storyRepository.addNewStoryToProject(projectID, story);
	}

	public Story updateStory(Story story) {
		return this.storyRepository.updateStory(story);
	}

}