package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public final class Story {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "story_id")
	private int storyID;
	
	@OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinColumn(name="cave_id")
	@Column(name = "project_id")
	private int projectID;
	
	@Column(name = "story_name")
	private String storyName;
	
	private String description;
	
	private String status;
	
	private int points;
	
	@ManyToMany
	@JoinTable(name="stories",
			joinColumns= {@JoinColumn(name="story_id")}, 
			inverseJoinColumns= {@JoinColumn(name="user_id")})
	private List<Story> owners;
	
	@OneToMany
	@JoinColumn(name="story_id")
	private List<UserProject> userProject;

	public int getStoryID() {
		return storyID;
	}

	public void setStoryID(int storyID) {
		this.storyID = storyID;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public List<Story> getOwners() {
		return owners;
	}

	public void setOwners(List<Story> owners) {
		this.owners = owners;
	}

	public List<UserProject> getUserProject() {
		return userProject;
	}

	public void setUserProject(List<UserProject> userProject) {
		this.userProject = userProject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((owners == null) ? 0 : owners.hashCode());
		result = prime * result + points;
		result = prime * result + projectID;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + storyID;
		result = prime * result + ((storyName == null) ? 0 : storyName.hashCode());
		result = prime * result + ((userProject == null) ? 0 : userProject.hashCode());
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
		if (projectID != other.projectID)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (storyID != other.storyID)
			return false;
		if (storyName == null) {
			if (other.storyName != null)
				return false;
		} else if (!storyName.equals(other.storyName))
			return false;
		if (userProject == null) {
			if (other.userProject != null)
				return false;
		} else if (!userProject.equals(other.userProject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Story [storyID=" + storyID + ", projectID=" + projectID + ", storyName=" + storyName + ", description="
				+ description + ", status=" + status + ", points=" + points + ", owners=" + owners + ", userProject="
				+ userProject + "]";
	}

	public Story(int storyID, int projectID, String storyName, String description, String status, int points,
			List<Story> owners, List<UserProject> userProject) {
		super();
		this.storyID = storyID;
		this.projectID = projectID;
		this.storyName = storyName;
		this.description = description;
		this.status = status;
		this.points = points;
		this.owners = owners;
		this.userProject = userProject;
	}

	public Story() {
		super();
		// TODO Auto-generated constructor stub
	}
}
