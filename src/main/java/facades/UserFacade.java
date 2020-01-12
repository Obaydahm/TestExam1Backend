/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Role;
import entities.User;
import errorhandling.AuthenticationException;
import errorhandling.UserCreationException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

/**
 *a
 * @author Obaydah Mohamad
 */
public class UserFacade {
    private static EntityManagerFactory emf;
    private static UserFacade instance;
    
    private UserFacade(){}
    
    public static UserFacade getUserFacade (EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
    
    public Long usernameValidation(String uname){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<Long> tq = em.createQuery("SELECT count(u) FROM User u WHERE u.username = :uname", Long.class).setParameter("uname", uname);
            return tq.getSingleResult();
        }finally{
            em.close();
        }
        
    }
    
    public User createUser(String username, String password, Role r) throws UserCreationException{
        EntityManager em = emf.createEntityManager();
        User u;
        try{
            
            if(username.isEmpty() || username == null) throw new UserCreationException("You must enter a username!");
            if(password.isEmpty() || password == null) throw new UserCreationException("You must enter a password!"); 
            if(usernameValidation(username) > 0) throw new UserCreationException("Username already exists!");
            
            u = new User(username, password);
            u.addRole(r);
            em.getTransaction().begin();
            em.persist(u);
            em.persist(r);
            em.getTransaction().commit();
            return u;
        }catch(Exception e){    
            em.getTransaction().rollback();
            throw new UserCreationException("An error occoured. The user was not created.");
        }finally{
            em.close();
        }
    }
    
    public User getUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = (User)em.createQuery("SELECT u FROM User u WHERE u.username = :username")
                    .setParameter("username", username).getSingleResult();
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username or password");
            }
        } finally {
            em.close();
        }
        return user;
    }
    
}
