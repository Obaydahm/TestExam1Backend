/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.PersonDTO;
import entities.Address;
import entities.Person;
import facades.PersonFacadeImpl;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Obaydah Mohamad
 */
@Path("person")
public class PersonResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final PersonFacadeImpl FACADE = PersonFacadeImpl.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getperson/id/{id}")
    public String getPersonById(@PathParam("id") String id) {
        try {
            return GSON.toJson(FACADE.getPersonByID(id));
        } catch (Exception e) {
            return "{ \"status\": \"" + e.getMessage() + "\"}";
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getperson/email/{email}")
    public String getPersonByEmail(@PathParam("email") String email) {
        try {
            return GSON.toJson(FACADE.getPersonByEmail(email));
        } catch (Exception e) {
            return "{ \"status\": \"" + e.getMessage() + "\"}";
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getperson/phone/{phone}")
    public String getPersonByPhone(@PathParam("phone") String phone) {
        try {
            return GSON.toJson(FACADE.getPersonByPhone(phone));
        } catch (Exception e) {
            return "{ \"status\": \"" + e.getMessage() + "\"}";
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addperson")
    public String addPerson(String jsonString) {
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String firstname = json.get("firstname").getAsString();
        String lastname = json.get("lastname").getAsString();
        String email = json.get("email").getAsString();
        String phone = json.get("phone").getAsString();
        String street = json.get("address").getAsJsonObject().get("street").getAsString();
        String city = json.get("address").getAsJsonObject().get("city").getAsString();
        int zip = json.get("address").getAsJsonObject().get("zip").getAsInt();
        Person p;
        PersonDTO pDTO;
        Address a;
        try {
            p = new Person(firstname, lastname, email, phone);
            p.setAddress(new Address(street, city, zip));
            pDTO = FACADE.addPerson(p);
             return "{ \"status\": \"" +pDTO.getFn()+ " " + pDTO.getLn() + " has been added!\"}";
        } catch (Exception e) {
            return "{ \"status\": \"" + e.getMessage() + "\"}";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteperson/{id}")
    public String deletePerson(@PathParam("id") String id){
        try{
            FACADE.deletePerson(id);
            return "{ \"status\": \"The person has been deleted!\"}";
        }catch(Exception e){
            return "{ \"status\": \""+e.getMessage()+"\"}";
        }
    }
}
