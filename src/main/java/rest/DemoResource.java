package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.User;
import errorhandling.API_Exception;
import facades.RemoteServerFacade;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 * @author Patrick
 */
@Path("info")
public class DemoResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final RemoteServerFacade remoteFACADE =  RemoteServerFacade.getRemoteServerFacade(EMF);
       private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response  getInfoForAll() {
        return Response.ok()
               .header("Access-Control-Allow-Origin", "*")
               .header("Access-Control-Allow-Credentials", "true")
               .header("Access-Control-Allow-Headers","origin, content-type, accept, authorization")
               .header("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS, HEAD")
               .entity( "{\"msg\":\"Hello anonymous\"}").build();
   
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u", User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
         String thisuser = securityContext.getUserPrincipal().getName();
        
                   return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
   
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        
             return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("newuser")
    public String createNewUser(String user) throws AuthenticationException, API_Exception{
        
        try {
         User userToAdd = GSON.fromJson(user, User.class);
          User addedUser = FACADE.addNewUser(userToAdd);
        } catch (Exception e){
           throw new API_Exception(e.getMessage());
        }
        return "{\"message\": \"Brugeren +  er nu oprettet"+"\"}";
        
    }
    
    

 
}   