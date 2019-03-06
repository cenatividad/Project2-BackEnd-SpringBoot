package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Story Entity
 * @author tiand
 */
@Entity
public final class Story {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "story_id")
	private int storyID;
	
	// JsonIgnore to avoid issues with JSON infinitely recursing through the lists.
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;
	
	@Column(name = "story_name", nullable=false)
	private String storyName;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private StoryStatus status;
	
	private int points;
	
	// JsonIgnore to avoid issues with JSON infinitely recursing through the lists.
	@JsonIgnore
	@ManyToMany(mappedBy="stories")
	private List<User> owners;

	public int getStoryID() {
		return storyID;
	}

	public void setStoryID(int storyID) {
		this.storyID = storyID;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getStoryName() {
		return storyName;
	}

	public void setStoryName(String storyName) {
		this.storyName = storyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Enumerated(EnumType.STRING)
	public StoryStatus getStatus() {
		return status;
	}

	@Enumerated(EnumType.STRING)
	public void setStatus(StoryStatus status) {
		this.status = status;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public List<User> getOwners() {
		return owners;
	}

	public void setOwners(List<User> owners) {
		this.owners = owners;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((owners == null) ? 0 : owners.hashCode());
		result = prime * result + points;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + storyID;
		result = prime * result + ((storyName == null) ? 0 : storyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Story other = (Story) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (owners == null) {
			if (other.owners != null)
				return false;
		} else if (!owners.equals(other.owners))
			return false;
		if (points != other.points)
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (status != other.status)
			return false;
		if (storyID != other.storyID)
			return false;
		if (storyName == null) {
			if (other.storyName != null)
				return false;
		} else if (!storyName.equals(other.storyName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Story [storyID=" + storyID + ", project=" + project + ", storyName=" + storyName + ", description="
				+ description + ", status=" + status + ", points=" + points + ", owners=" + owners + "]";
	}

	public Story(int storyID, Project project, String storyName, String description, StoryStatus status, int points,
			List<User> owners, List<UserProject> userProject) {
		super();
		this.storyID = storyID;
		this.project = project;
		this.storyName = storyName;
		this.description = description;
		this.status = status;
		this.points = points;
		this.owners = owners;
	}

	public Story() {
		super();
		// TODO Auto-generated constructor stub
	}
}
