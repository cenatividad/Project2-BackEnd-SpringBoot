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
import javax.persistence.OneToMany;

@Entity
public final class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private int projectID;
	
	@OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY, mappedBy="project")
	private List<Story> stories;
	
	@Column(name = "project_name")
	private String projectName;
	
	private String description;
	
	@OneToMany(mappedBy="projectID")
	@JoinColumn(name="user_id")
	private List<UserProject> userProjects;

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + projectID;
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((stories == null) ? 0 : stories.hashCode());
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
		Project other = (Project) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (projectID != other.projectID)
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (stories == null) {
			if (other.stories != null)
				return false;
		} else if (!stories.equals(other.stories))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Project [projectID=" + projectID + ", stories=" + stories + ", projectName=" + projectName
				+ ", description=" + description + "]";
	}

	public Project(int projectID, List<Story> stories, String projectName, String description) {
		super();
		this.projectID = projectID;
		this.stories = stories;
		this.projectName = projectName;
		this.description = description;
	}

	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
}
