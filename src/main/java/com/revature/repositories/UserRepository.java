package com.revature.repositories;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.revature.models.User;

/**
 * Repository for the User entity, used to request and manage user data.
 * @author tiand
 */
@Repository
public class UserRepository {

	@Autowired
	EntityManagerFactory emf;

	
	/**
	 * Create and persists a new user
	 */
	public User createUser(User user) throws PSQLException{
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()) {
			int id = (int) session.save(user);
			user.setUserID(id);
			return user;
		}
	}


	/**
	 * Repository method to retrieve a user based on their unique username
	 */
	public User getUserByUsername(String username) {
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()){			
			List<?> users = session.createQuery("from User u where u.username like :username")
					.setParameter("username", username, StringType.INSTANCE).list();
			
			if(users.isEmpty()) return null;
			return (User) users.get(0);
		}
	}
	
	/**
	 * Retrieves a user based on their unique email
	 */
	public User getUserByEmail(String email) {
		System.out.println("getting user invoked");
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()){			
			List<?> users = session.createQuery("from User u where u.email = :email")
					.setParameter("email", email, StringType.INSTANCE).list();
			
			if(users.isEmpty()) return null;
			System.out.println("UserRepository.getUserEmail: got user " + ((User) users.get(0)).getUserID());
			return (User) users.get(0);
		}
	}
}