/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import entities.Hobby;
import entities.Person;
import errorhandling.NotFoundException;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author Obaydah Mohamad
 */
public interface PersonFacadeInterface {
    public List<PersonDTO> getPersonByID(String id) throws NotFoundException;
    public List<PersonDTO> getPersonByEmail(String email) throws NoResultException;
    public List<PersonDTO> getPersonByPhone(String phone) throws NoResultException;
    public List<PersonDTO> getPersonsByHobby(Hobby h) throws NoResultException;
    public PersonDTO addPerson(Person p);
    public PersonDTO deletePerson(String id) throws NotFoundException;
}
