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
    
    public User createUser(User u, Role r) throws UserCreationException{
        EntityManager em = emf.createEntityManager();
        try{
            if(u.getUsername().isEmpty() || u.getUsername() == null) throw new UserCreationException("You must enter a username!");
            if(u.getPassword().isEmpty() || u.getPassword() == null) throw new UserCreationException("You must enter a password!"); 
            
            em.getTransaction().begin();
            em.persist(u);
            em.persist(r);
            em.getTransaction().commit();
            return u;
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
