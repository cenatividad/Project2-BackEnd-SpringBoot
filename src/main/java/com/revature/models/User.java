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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User Entity
 * @author tiand
 *
 */
@Entity
@Table(name="users")
public final class User {
	// Some defined parameters for input validation in terms of string length
	public static final int MIN_FIRST_NAME_LENGTH = 1;
	public static final int MAX_FIRST_NAME_LENGTH = 255;
	public static final int MIN_LAST_NAME_LENGTH = 1;
	public static final int MAX_LAST_NAME_LENGTH = MAX_FIRST_NAME_LENGTH;
	public static final int MIN_USERNAME_LENGTH = 1;
	public static final int MAX_USERNAME_LENGTH = 255;
	public static final int MIN_PASSWORD_LENGTH = 1;
	public static final int MAX_PASSWORD_LENGTH = 255;
	public static final int MIN_EMAIL_LENGTH = 1;
	public static final int MAX_EMAIL_LENGTH = 255;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	@JsonProperty("id")
	private int userID;
	
	@Column(nullable=false, unique=true)
	private String username;
	
	@Column(nullable=false, unique=true)
	private String email;
	
	@Column(nullable=false)
	private String password;
	
	@Column(name = "first_name", nullable=false)
	private String firstName;
	
	@Column(name = "last_name", nullable=false)
	private String lastName;
	
	// JsonIgnore to avoid issues with JSON infinitely recursing through the lists.
	@JsonIgnore
	@ManyToMany(cascade= {CascadeType.ALL}, fetch=FetchType.LAZY)
	@JoinTable(name="user_story",
			joinColumns= {@JoinColumn(name="user_id")}, 
			inverseJoinColumns= {@JoinColumn(name="story_id")})
	private List<Story> stories;
	
	// JsonIgnore to avoid issues with JSON infinitely recursing through the lists.
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<UserProject> userProject;

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((stories == null) ? 0 : stories.hashCode());
		result = prime * result + userID;
		result = prime * result + ((userProject == null) ? 0 : userProject.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (stories == null) {
			if (other.stories != null)
				return false;
		} else if (!stories.equals(other.stories))
			return false;
		if (userID != other.userID)
			return false;
		if (userProject == null) {
			if (other.userProject != null)
				return false;
		} else if (!userProject.equals(other.userProject))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", stories=" + stories + ", userProject="
				+ userProject + "]";
	}

	public User(int userID, String username, String email, String password, String firstName, String lastName,
			List<Story> stories, List<UserProject> userProject) {
		super();
		this.userID = userID;
		this.username = username;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.stories = stories;
		this.userProject = userProject;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}