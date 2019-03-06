package com.revature.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.dtos.InvitationStatusDTO;
import com.revature.models.InviteStatus;
import com.revature.models.Project;
import com.revature.models.User;
import com.revature.models.UserProject;
import com.revature.models.UserRole;

/**
 * Repository Bean to handle database operations relating to Projects.
 */
@Repository
public class ProjectRepository {
	@Autowired
	EntityManagerFactory emf;
	
	/**
	 * Creates a project and saves it into the database.
	 */
	public Project createProject(Project project) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()) {
			int id = (int) session.save(project);
			project.setProjectID(id);
			return project;
		}
	}

	/**
	 * Get the project related to the passed ID.
	 */
	public Project getProject(int id) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		Project project = new Project();
		
		try(Session session = sf.openSession()) {
			project = session.get(Project.class, id);
			return project;
		}
	}

	/**
	 * Gets a project based off of the project id that was passed
	 * Create a new userProject (invitation to the project)
	 * assign the project and user to that userProject
	 */
	public Project inviteUser(User user, int projectID) {
		Project project = getProject(projectID);
		UserProject userProject = new UserProject();
		userProject.setProject(project);
		userProject.setUser(user);
		
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try (Session session = sf.openSession()) {
			List<?> ups = session.createQuery("select up.project from UserProject up where up.project.projectID = :id").setParameter("id", project.getProjectID()).list();
			int upsLength = ups.size();
			
			System.out.println("Test 1");
			
			List<?> userProjectInvites = session.createQuery("select up.inviteStatus from UserProject up where up.project.projectID = :id AND up.user.userID = :uid")
					.setParameter("id", project.getProjectID()).setParameter("uid", user.getUserID()).list();
			int upiLength = userProjectInvites.size();
			
			System.out.println("upi = " + userProjectInvites.get(0));
			System.out.println("upiLength = " + upiLength);
		
//			if (project.getUserProjects().get(0) == null) {
			if (upsLength == 0) {
				userProject.setRole(UserRole.OWNER);
				userProject.setInviteStatus(InviteStatus.ACCEPTED);
				project.setUserProjects(new ArrayList<UserProject>());
			} else {
				if(upiLength == 0) {
					userProject.setRole(UserRole.TEAM_MEMBER);
					userProject.setInviteStatus(InviteStatus.PENDING);
					int id = (int) session.save(userProject);
					userProject.setuPID(id);
					Project persistentProject = session.get(Project.class, project.getProjectID());
					List<UserProject> list = persistentProject.getUserProjects();
					list.add(userProject);
					persistentProject.setUserProjects(list);
					session.saveOrUpdate(persistentProject);
					return persistentProject;
				} else if(userProjectInvites.get(0).toString() == "DECLINED") {
					Transaction tx = session.beginTransaction();
					
					UserProject up = new UserProject();
					List<?> declinedUPS = session.createQuery("from UserProject up where up.project.projectID = :pid AND up.user.userID = :uid")
								.setParameter("pid", project.getProjectID())
								.setParameter("uid", user.getUserID()).list();
					up = (UserProject) declinedUPS.get(0);
					up.setInviteStatus(InviteStatus.PENDING);
					
					System.out.println("");
					System.out.println("");
					System.out.println("New Invite Status: " + up.getInviteStatus());
					System.out.println("ID: " + up.getuPID());
					System.out.println("");
					System.out.println("");
					
					
					session.merge(up);
					session.flush();
					tx.commit();
					return null;
				} else {
					return null;
				}
			}
		}
		return project;
	}
		
	/**
	 * Returns a list of projects that are related to the passed in user ID.
	 */
	@SuppressWarnings("unchecked")
	public List<Project> getProjectsByUserId(int id) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try (Session session = sf.openSession()){
			Transaction tx = session.beginTransaction();
			List<?> projectIds = session.createQuery("select up.project.projectID from UserProject up where up.user.userID = :id")
					.setParameter("id", id).list();
			if (projectIds.size() == 0) {
				tx.commit();
				return new ArrayList<Project>();
			}
			
			List<?> projects = session.createQuery("from Project p where p.projectID in (:pIds)")
					.setParameter("pIds", projectIds).list();
			tx.commit();
			return (List<Project>) projects;
		}
	}

	/**
	 * Returns a list of all UserProject joint entities where the invitation status is PENDING
	 * and the related user has the passed ID.
	 */
	public List<UserProject> viewInvitations(int uID) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			
			@SuppressWarnings("unchecked")
			List<UserProject> ups = session.createQuery("from UserProject up where up.user.userID = :id AND up.inviteStatus = :status")
					.setParameter("id", uID)
					.setParameter("status", InviteStatus.PENDING).list();
			if(ups.size() == 0) {
				System.out.println("ups.size() == 0");
				tx.commit();
				return null;
			}
			
			tx.commit();
			return ups;
		}
	}

	/**
	 * Retrieves the UserProject defined in the passed DTO and updates the invitation status to the
	 * specified one in the passed DTO. The change is merged into the database.
	 */
	public void processInvitation(InvitationStatusDTO invStat) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try (Session session = sf.openSession()) {
			Transaction tx = session.beginTransaction();
			
			UserProject up = new UserProject();
			List<?> pendingUPS = session.createQuery("from UserProject up where up.uPID = :upid")
						.setParameter("upid", invStat.getUpid()).list();
			up = (UserProject) pendingUPS.get(0);
			
			if (invStat.getStatus().equals(InviteStatus.ACCEPTED.toString())) {
				up.setInviteStatus(InviteStatus.ACCEPTED);
			} else if (invStat.getStatus().equals(InviteStatus.DECLINED.toString())) {
				up.setInviteStatus(InviteStatus.DECLINED);
			} else {
				System.out.println("Soemthing went wrong.");
			}
			
			session.merge(up);
			session.flush();
			tx.commit();
		}
	}
}
