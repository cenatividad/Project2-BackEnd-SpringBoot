package com.revature.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Story;
import com.revature.service.StoryService;

/**
 * Controller to handle story-centered requests. Communicates with the StoryService bean to process
 * them.
 */
@RestController
@RequestMapping("/story")
@CrossOrigin(origins="*", allowedHeaders="*")
public class StoryController {
	StoryService storyService;
	
	public StoryController(StoryService storyService) {
		super();
		this.storyService = storyService;
	}
	
	/**
	 * Handles a request to update the story. It's a wholesale update and if any values shouldn't be
	 * changed, ensure the passed Story has the same values on those members.
	 */
	@PutMapping("")
	public Story updateStory(@RequestBody Story story) {
		return this.storyService.updateStory(story);
	}
}
