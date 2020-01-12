/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author Obaydah Mohamad
 */
public class UserCreationException extends Exception{
    public UserCreationException(String message) {
        super(message);
    }

    public UserCreationException() {
        super("Could not be Authenticated");
    } 
}
