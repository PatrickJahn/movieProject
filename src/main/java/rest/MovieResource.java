/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.LikedActor;
import entities.LikedMovie;
import errorhandling.API_Exception;
import facades.MovieFacade;
import java.io.IOException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author Patrick
 */
@Path("movie")
public class MovieResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   private static final MovieFacade FACADE =  MovieFacade.getMovieFacade(EMF);
    
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    /**
     * Creates a new instance of MovieResource
     */
    public MovieResource() {
    }

   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movies/{pagenr}")
    public String getPopularMovies(@PathParam("pagenr") String pageNr) throws IOException {
        
        return FACADE.getPopularMovies(pageNr);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("actors/{pagenr}")
    public String getPopularActors(@PathParam("pagenr") String pageNr) throws IOException {
        
        return FACADE.getPopularMovies(pageNr);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("actorinfo/{actorID}")
    public String getActorsInfo(@PathParam("actorID") String actorID) throws IOException {

        return FACADE.getActorCredits(actorID);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("movieinfo/{movieID}")
    public String getMovieInfo(@PathParam("movieID") String movieID) throws IOException {
     
        return FACADE.getMovieCredits(movieID);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("likedmovies")
    public String getLikedMovies() throws IOException, API_Exception {
      String name = securityContext.getUserPrincipal().getName();
        return FACADE.getLikedMovies(name);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("likedactors")
    public String getLikedActors() throws IOException, API_Exception {
      String name = securityContext.getUserPrincipal().getName();
        return FACADE.getLikedActors(name);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("saveactor")
    public String saveActor( String actor) throws API_Exception {
       
        LikedActor a = GSON.fromJson(actor, LikedActor.class);
        String name = securityContext.getUserPrincipal().getName();
        
        return FACADE.saveActor(a, name);
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("savemovie")
    public String saveMovie(String movie) throws API_Exception {
        LikedMovie m = GSON.fromJson(movie, LikedMovie.class);
        
       String name = securityContext.getUserPrincipal().getName();
    
        return FACADE.saveMovie(m, name);
    }
}
