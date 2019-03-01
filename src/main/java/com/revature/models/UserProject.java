package com.revature.models;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.revature.models.Project;

@Entity
@Table(name="user_project")
public final class UserProject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "u_p_id")
	private int uPID;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
	@Column(name = "invite_status")
	@Enumerated(EnumType.STRING)
	private InviteStatus inviteStatus;
	
	@Column(name = "contribution_score")
	private int contributionScore;
	
	@ManyToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@ManyToOne(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY)
	@JoinColumn(name="project_id")
	private Project project;

	public int getuPID() {
		return uPID;
	}

	public void setuPID(int uPID) {
		this.uPID = uPID;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public InviteStatus getInviteStatus() {
		return inviteStatus;
	}

	public void setInviteStatus(InviteStatus inviteStatus) {
		this.inviteStatus = inviteStatus;
	}

	public int getContributionScore() {
		return contributionScore;
	}

	public void setContributionScore(int contributionScore) {
		this.contributionScore = contributionScore;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contributionScore;
		result = prime * result + ((inviteStatus == null) ? 0 : inviteStatus.hashCode());
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + uPID;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserProject other = (UserProject) obj;
		if (contributionScore != other.contributionScore)
			return false;
		if (inviteStatus != other.inviteStatus)
			return false;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (role != other.role)
			return false;
		if (uPID != other.uPID)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserProject [uPID=" + uPID + ", role=" + role + ", inviteStatus=" + inviteStatus
				+ ", contributionScore=" + contributionScore + ", user=" + user + ", project=" + project + "]";
	}

	public UserProject(int uPID, UserRole role, InviteStatus inviteStatus, int contributionScore, User user,
			Project project) {
		super();
		this.uPID = uPID;
		this.role = role;
		this.inviteStatus = inviteStatus;
		this.contributionScore = contributionScore;
		this.user = user;
		this.project = project;
	}

	public UserProject() {
		super();
		// TODO Auto-generated constructor stub
	}
}
