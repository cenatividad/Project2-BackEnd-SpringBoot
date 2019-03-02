package com.revature.dtos;

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
	public InvitationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "InvitationDTO [email=" + email + ", projectID=" + projectID + "]";
	}
	
	
}
