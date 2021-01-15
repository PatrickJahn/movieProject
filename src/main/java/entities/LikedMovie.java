package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;


@Entity
@NamedQuery(name = "LikedMovie.deleteAllRows", query = "DELETE from LikedMovie")
public class LikedMovie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
            
    private String title;
    private String overview;
    private String poster_path;
    private String release_date;
    
    
    
    @ManyToMany(mappedBy = "likedMovies")
    private List<User> users = new ArrayList();
    

    
    public LikedMovie() {}

    public LikedMovie(String id, String title, String overview, String poster_path, String release_date) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }
    

    
       
   
          
   
}
