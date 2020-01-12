/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Hobby;
import errorhandling.HobbyCreationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Obaydah Mohamad
 */
public class HobbyFacadeImpl implements HobbyFacadeInterface {

    private static EntityManagerFactory emf;
    private static HobbyFacadeImpl instance;

    private HobbyFacadeImpl() {
    }

    public static HobbyFacadeImpl getHobbyFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HobbyFacadeImpl();
        }
        return instance;
    }

    @Override
    public List<Hobby> getAllHobbies() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Hobby> tq = em.createQuery("SELECT h FROM Hobby h", Hobby.class);
            return tq.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Hobby addHobby(Hobby h) throws HobbyCreationException {
        EntityManager em = emf.createEntityManager();
        try {
            if (h.getName().isEmpty() || h.getName() == null) {
                throw new HobbyCreationException("You must enter a hobby name!");
            }
            em.getTransaction().begin();
            em.persist(h);
            em.getTransaction().commit();
            return h;
        } catch (Exception e) {
            throw new HobbyCreationException("An error occoured. The hobby was not created.");
        } finally {
            em.close();
        }
    }

    @Override
    public Hobby deleteHobby(String id) throws HobbyCreationException{
        EntityManager em = emf.createEntityManager();
        if(id == null) throw new HobbyCreationException("You must enter an ID!");
        try {
            
            try{
                Long.parseLong(id);
            }catch(NumberFormatException e){
                throw new HobbyCreationException("The ID must be numeric!");
            }
            
            em.getTransaction().begin();
            Hobby h = em.find(Hobby.class, Long.valueOf(id));
            em.remove(h);
            em.getTransaction().commit();
            return h;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new HobbyCreationException("An error occoured. The hobby was not deleted.");
        } finally {
            em.close();
        }
    }

    @Override
    public Hobby getHobbyById(String id) throws HobbyCreationException {
        EntityManager em = emf.createEntityManager();
        try {           
            try{
                Long.parseLong(id);
            }catch(NumberFormatException e){
                throw new HobbyCreationException("The ID must be numeric!");
            }           
            Hobby h = em.find(Hobby.class, Long.valueOf(id));
            if(h == null) throw new HobbyCreationException("Hobby with ID " + id + " doesn't exist!");
            return h;
        } finally {
            em.close();
        }
    }

    @Override
    public Hobby getHobbyByName(String name) throws HobbyCreationException {
        EntityManager em = emf.createEntityManager();
        if(name.isEmpty() || name == null) throw new HobbyCreationException("You must enter a hobby!");
        try {
            return em.createQuery("SELECT h FROM Hobby h WHERE h.name = :name", Hobby.class)
                    .setParameter("name", name).getSingleResult();
        }catch(NoResultException e){    
            throw new HobbyCreationException(name + " is not registrered as hobby!");
        } finally {
            em.close();
        }
    }

}
