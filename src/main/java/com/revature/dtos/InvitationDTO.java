package com.revature.dtos;

/**
 * DTO for invitations users receive in relation to objects. Used for handling invitation requests
 * and consolidating the relevant information into a simple, convenient class.
 */
public class InvitationDTO {
	private String email;
	private int projectID;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + projectID;
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
		InvitationDTO other = (InvitationDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (projectID != other.projectID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InvitationDTO [email=" + email + ", projectID=" + projectID + "]";
	}
	public InvitationDTO(String email, int projectID) {
		super();
		this.email = email;
		this.projectID = projectID;
	}
	public InvitationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
