/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import entities.Address;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author Obaydah Mohamad
 */
public class PersonFacadeImpl implements PersonFacadeInterface {

    private static EntityManagerFactory emf;
    private static PersonFacadeImpl instance;

    private PersonFacadeImpl() {
    }

    public static PersonFacadeImpl getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacadeImpl();
        }
        return instance;
    }

    @Override
    public List<PersonDTO> getPersonByID(String id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        List<PersonDTO> persons = new ArrayList<>();
        try {
            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The ID must be numeric!");
            }
            Person p = em.find(Person.class, Long.valueOf(id));
            if (p == null) {
                throw new NotFoundException("Person with ID " + id + " doesn't exist!");
            }
            persons.add(new PersonDTO(p));
            return persons;
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getPersonByEmail(String email) throws NoResultException {
        EntityManager em = emf.createEntityManager();
        if (email.isEmpty() || email == null) {
            throw new IllegalArgumentException("You must enter a email!");
        }
        try {
            return em.createQuery("SELECT new dto.PersonDTO(p) FROM Person p WHERE p.email = :email", PersonDTO.class)
                    .setParameter("email", email).getResultList();
        } catch (NoResultException e) {
            throw new NoResultException(email + " is not assigned to any persons!");
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getPersonByPhone(String phone) throws NoResultException {
        EntityManager em = emf.createEntityManager();
        if (phone.isEmpty() || phone == null) {
            throw new IllegalArgumentException("You must enter a phonenumber!");
        }
        try {
            Integer.parseInt(phone);
        } catch (NumberFormatException e) {
            throw new NoResultException("The phonenumber must be numeric!");
        }
        try {
            return em.createQuery("SELECT new dto.PersonDTO(p) FROM Person p WHERE p.phone = :phone", PersonDTO.class)
                    .setParameter("phone", phone).getResultList();
        } catch (NoResultException e) {
            throw new NoResultException(phone + " is not assigned to any persons!");
        } finally {
            em.close();
        }
    }

    @Override
    public List<PersonDTO> getPersonsByHobby(Hobby h) throws NoResultException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Address addressChecker(Address a) throws Exception{
        EntityManager em = emf.createEntityManager();
        try{
            if (a.getStreet().isEmpty() || a.getStreet() == null) throw new IllegalArgumentException("You must enter a street!");
            if (a.getCity().isEmpty() || a.getCity() == null) throw new IllegalArgumentException("You must enter a city!");
            Address addressFetched = em.createQuery("SELECT a FROM Address a WHERE a.street = :street AND a.city = :city AND a.zip = :zip", Address.class)
                                    .setParameter("street", a.getStreet())
                                    .setParameter("city", a.getCity())
                                    .setParameter("zip", a.getZip())
                                    .getSingleResult();
            return addressFetched;
        }catch(Exception e){
            return null;
        }finally{
            em.close();
        }
    }

    @Override
    public PersonDTO addPerson(Person p) {
        EntityManager em = emf.createEntityManager();
        boolean addressExists = true;
        try {
            Address addressChecked = addressChecker(p.getAddress());
            
            if (p.getFirstname().isEmpty() || p.getFirstname() == null) throw new IllegalArgumentException("You must enter a firstname!");
            if (p.getLastname().isEmpty() || p.getLastname() == null) throw new IllegalArgumentException("You must enter a lastname!");
            if (p.getPhone().isEmpty() || p.getPhone() == null) throw new IllegalArgumentException("You must enter a phonenumber!");
            if (p.getEmail().isEmpty() || p.getEmail() == null) throw new IllegalArgumentException("You must enter a email!");
            if (p.getAddress().getStreet().isEmpty() || p.getAddress().getStreet() == null) throw new IllegalArgumentException("You must enter a street!");
            if (p.getAddress().getCity().isEmpty() || p.getAddress().getCity() == null) throw new IllegalArgumentException("You must enter a city!");
            
            em.getTransaction().begin();
            em.persist(p);
            if(addressChecked != null){
                p.setAddress(addressChecked);
            }else{
                em.persist(p.getAddress());
            }
            em.getTransaction().commit();
            
            System.out.println(p);
            return new PersonDTO(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("An error occoured. The person was not created.");
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO deletePerson(String id) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        if (id.isEmpty() || id == null) {
            throw new IllegalArgumentException("You must enter an ID!");
        }
        try {

            try {
                Long.parseLong(id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("The ID must be numeric!");
            }

            em.getTransaction().begin();
            Person p = em.find(Person.class, Long.valueOf(id));
            em.remove(p);
            em.getTransaction().commit();
            return new PersonDTO(p);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new IllegalArgumentException("An error occoured. The hobby was not deleted.");
        } finally {
            em.close();
        }
    }
}
