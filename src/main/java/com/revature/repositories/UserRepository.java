package com.revature.repositories;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	 * Persists a new user
	 * @param user
	 * @return
	 * @throws PSQLException
	 */
	public User createUser(User user) throws PSQLException{
		SessionFactory sf = emf.unwrap(SessionFactory.class);
		
		try(Session session = sf.openSession()) {
			int id = (int) session.save(user);
			user.setUserID(id);
			return user;
		}
	}
	
	
}