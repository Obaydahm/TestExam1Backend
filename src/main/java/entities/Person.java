/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Obaydah Mohamad
 */
@Entity
@Table(name = "persons")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "firstname")
    @Basic(optional = false)
    @NotNull
    private String firstname;
    
    @Column(name = "lastname")
    @Basic(optional = false)
    @NotNull
    private String lastname;
    
    @Column(name = "email")
    @Basic(optional = false)
    @NotNull
    private String email;
    
    @Column(name = "phone")
    @Basic(optional = false)
    @NotNull
    private String phone;
    
    @JoinTable(
        name = "hobbies_assigned", 
        joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id")}, 
        inverseJoinColumns = {@JoinColumn(name = "hobby_id", referencedColumnName = "id")}
    )
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Hobby> hobbies;
    
    @JoinColumn(name = "address_id")
    @NotNull
    @ManyToOne
    private Address address;
    
    public Person (){}

    public Person(String firstname, String lastname, String email, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", phone=" + phone + ", hobbies=" + hobbies + ", address=" + address + '}';
    }

    
    
}
