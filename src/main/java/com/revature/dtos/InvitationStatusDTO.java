package com.revature.dtos;

/**
 * DTO for invitation status change requests. Allows for convenient information transfer for 
 * the relevant requests
 */
public class InvitationStatusDTO {
	private int upid;
	private String status;
	
	public int getUpid() {
		return upid;
	}
	public void setUpid(int upid) {
		this.upid = upid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + upid;
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
		InvitationStatusDTO other = (InvitationStatusDTO) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (upid != other.upid)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "InvitationStatusDTO [upid=" + upid + ", status=" + status + "]";
	}
	public InvitationStatusDTO(int upid, String status) {
		super();
		this.upid = upid;
		this.status = status;
	}
	public InvitationStatusDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
