package com.auca.library.dao;

import com.auca.library.model.User;
import com.auca.library.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.NoResultException; 

public class UserDAO {

    public void saveUser(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(user); // Use persist for new entities
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); 
            // Handle exception properly in a production setting (e.g., log and rethrow)
        }
    }

    // Add methods for other operations (e.g., getByUsername, getByEmail) 
    // if needed for validation or authentication later. Example:
    public User getUserByUsername(String username) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM User WHERE username = :username", User.class)
                         .setParameter("username", username)
                         .getSingleResult();
        } catch (NoResultException e) { 
            // Handle the case where no user is found - return null or throw a custom exception
            return null; 
        }
    }
}