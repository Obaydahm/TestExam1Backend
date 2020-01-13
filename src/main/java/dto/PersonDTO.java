/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Obaydah Mohamad
 */
public class PersonDTO {
    private String fn, ln, email, phone;
    private List<HobbyDTO> hobbies;
    private AddressDTO address;

    public PersonDTO(Person p) {
        this.fn = p.getFirstname();
        this.ln = p.getLastname();
        this.email = p.getEmail();
        this.phone = p.getPhone();
        this.address = new AddressDTO(p.getAddress());
        this.hobbies = new ArrayList<>();
        for(Hobby h : p.getHobbies()){
            hobbies.add(new HobbyDTO(h));
        }
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
    }

    public String getLn() {
        return ln;
    }

    public void setLn(String ln) {
        this.ln = ln;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<HobbyDTO> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<HobbyDTO> hobbies) {
        this.hobbies = hobbies;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
    
    
    
    
}


