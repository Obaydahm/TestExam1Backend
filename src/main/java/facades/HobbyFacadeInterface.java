/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Hobby;
import errorhandling.HobbyCreationException;
import java.util.List;

/**
 *
 * @author Obaydah Mohamad
 */
public interface HobbyFacadeInterface {
    public List<Hobby> getAllHobbies();
    public Hobby getHobbyById(String id) throws HobbyCreationException;
    public Hobby getHobbyByName(String name) throws HobbyCreationException;
    public Hobby addHobby(Hobby h) throws HobbyCreationException;
    public Hobby deleteHobby(String id) throws HobbyCreationException;
}
