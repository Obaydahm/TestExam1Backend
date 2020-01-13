/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Address;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import javax.persistence.NoResultException;

/**
 *
 * @author Obaydah Mohamad
 */
public interface PersonFacadeInterface {
    public Person getPersonByID(String id) throws NotFoundException;
    public Person getPersonByEmail(String email) throws NoResultException;
    public Person getPersonByPhone(String phone) throws NoResultException;
    public Person getPersonsByHobby(Hobby h) throws NoResultException;
    public Person addPerson(Person p);
    public Person deletePerson(String id) throws NotFoundException;
}
