package facades;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.actorCreditsDTO;
import dto.characterDTO;
import dto.movieCreditsDTO;
import dto.movieDTO;
import entities.LikedActor;
import entities.LikedMovie;
import entities.User;
import errorhandling.API_Exception;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.HttpUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Patrick
 */
public class MovieFacade {
    
    private static EntityManagerFactory emf;
      private static MovieFacade instance;
      private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

      private String api_key = "api_key=013d6759032518232f3a5134d78ec33f";
   
    public MovieFacade(){}
      /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }
    
    
    public String getPopularMovies(String pageNr) throws IOException {
           
          String movieJson = HttpUtils.fetchData("https://api.themoviedb.org/3/movie/popular?"+ api_key + "&language=en-US&page=" + pageNr);
          
          movieDTO movies = GSON.fromJson(movieJson, movieDTO.class);
        
        return GSON.toJson(movies);
    }
    
    
     public String getPopularActors(String pageNr) throws IOException {
           
          String characterJson = HttpUtils.fetchData("https://api.themoviedb.org/3/person/popular?"+ api_key + "&language=en-US&page=" + pageNr);
          
          characterDTO characters = GSON.fromJson(characterJson, characterDTO.class);
        
        return GSON.toJson(characters);
    }
    
     
      public String getActorCredits(String actorID) throws IOException {
           
          String creditsJson = HttpUtils.fetchData("https://api.themoviedb.org/3/person/"+actorID+"/movie_credits?" + api_key + "&language=en-US");
          
          actorCreditsDTO credits = GSON.fromJson(creditsJson, actorCreditsDTO.class);
        
        return GSON.toJson(credits);
    }
      
       public String getMovieCredits(String movieID) throws IOException {
           
          String creditsJson = HttpUtils.fetchData("https://api.themoviedb.org/3/movie/"+movieID+"/movie_credits?" + api_key + "&language=en-US");
          
          movieCreditsDTO credits = GSON.fromJson(creditsJson, movieCreditsDTO.class);
        
        return GSON.toJson(credits);
    }
       
       
       
       public String saveMovie(LikedMovie LikedMovie, String username) throws API_Exception {
           
            EntityManager em = emf.createEntityManager();
           
            try {
                em.getTransaction().begin();
              User user = em.find(User.class, username);
                
                user.addLikedMovie(LikedMovie);
                
              em.getTransaction().commit();
                
                
            } catch (Exception e){
                throw new API_Exception(e.getMessage());
            }
           
           
           return "SAVED";
       }
       
       
       
       public String saveActor(LikedActor actor, String username) throws API_Exception {
            EntityManager em = emf.createEntityManager();
           
            try {
                em.getTransaction().begin();
              User user = em.find(User.class, username);
                
                user.addActor(actor);
                
              em.getTransaction().commit();
                
                
            } catch (Exception e){
                throw new API_Exception(e.getMessage());
            }
           
           
           
           return GSON.toJson(actor);
       }
    
       
          
       public String getLikedMovies(String username) throws API_Exception{
            EntityManager em = emf.createEntityManager();
           User user;
            try {
               user = em.find(User.class, username);
                
                
            } catch (Exception e){
                throw new API_Exception(e.getMessage());
            }
            
          
           return GSON.toJson(user.getLikedMovies());
       }
       
       public String getLikedActors(String username) throws API_Exception{
            EntityManager em = emf.createEntityManager();
           User user;
           
            List<characterDTO> likedActors = new ArrayList<>();
      
            try {
               user = em.find(User.class, username);     
                likedActors = user.getActors();
            } catch (Exception e){
                throw new API_Exception(e.getMessage());
            }
               
           return GSON.toJson(likedActors);
       }
}
