package com.revature.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Story;
import com.revature.service.StoryService;

@RestController
@RequestMapping("/story")
@CrossOrigin(origins="*", allowedHeaders="*")
public class StoryController {
	StoryService storyService;
	
	public StoryController(StoryService storyService) {
		super();
		this.storyService = storyService;
	}
	
	@PutMapping("")
	public Story updateStory(@RequestBody Story story) {
		return this.storyService.updateStory(story);
	}
}
