/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Role;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Obaydah Mohamad
 */
public class SetupUsers {

    public static void main(String[] args) {
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
        EntityManager em = emf.createEntityManager();
        User admin = new User("admin", "admin123");
        
        em.getTransaction().begin();
        Role adminRole = new Role("admin");
        admin.addRole(adminRole);
        em.persist(adminRole);
        em.persist(admin);
        em.getTransaction().commit();
        System.out.println("Created TEST admin user");
    }
}
