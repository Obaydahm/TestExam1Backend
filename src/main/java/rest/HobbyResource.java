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
import entities.Hobby;
import errorhandling.HobbyCreationException;
import facades.HobbyFacadeImpl;
import java.util.List;
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
@Path("hobby")
public class HobbyResource {

    private static EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(EMF_Creator.DbSelector.DEV, EMF_Creator.Strategy.CREATE);
    private static final HobbyFacadeImpl HOBBY_FACADE = HobbyFacadeImpl.getHobbyFacade(EMF);
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
    @Path("all")
    public String allHobbies() {
        return GSON.toJson(HOBBY_FACADE.getAllHobbies());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addhobby")
    public String addHobby(String jsonString){
        JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
        String name = json.get("hobby").getAsString();
        String desc = json.get("desc").getAsString();
        
        try{
            HOBBY_FACADE.addHobby(new Hobby(name, desc));
            return "{ \"status\": \""+name+" has been created.\"}";
        }catch(HobbyCreationException e){
            return "{ \"status\": \""+e.getMessage()+"\"}";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deletehobby/{id}")
    public String deleteHobby(@PathParam("id") String id){
        try{
            HOBBY_FACADE.deleteHobby(id);
            return "{ \"status\": \"The hobby has been deleted!\"}";
        }catch(HobbyCreationException e){
            return "{ \"status\": \""+e.getMessage()+"\"}";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gethobby/id/{id}")
    public String getHobbyById(@PathParam("id") String id){
        try{
            return GSON.toJson(HOBBY_FACADE.getHobbyById(id));
        }catch(HobbyCreationException e){
            return "{ \"status\": \""+e.getMessage()+"\"}";
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("gethobby/name/{name}")
    public String getHobbyByName(@PathParam("name") String name){
        try{
            return GSON.toJson(HOBBY_FACADE.getHobbyByName(name));
        }catch(HobbyCreationException e){
            return "{ \"status\": \""+e.getMessage()+"\"}";
        }
    }
}
