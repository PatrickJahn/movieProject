/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.ws.rs.ext.ParamConverter.Lazy;

/**
 *
 * @author Patrick
 */
@Entity
public class LikedActor implements Serializable {

 

  
    private static final long serialVersionUID = 1L;
    
    @Id
    private Long id;

    String name;
    String profile_path;
    
       @ManyToMany(mappedBy = "likesActors")
    private List<User> users;

    public LikedActor() {
    }

    public LikedActor(Long id, String name, String profile_path) {
        this.id = id;
        this.name = name;
        this.profile_path = profile_path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
public void addUser(User u){
    this.users.add(u);
}
    
}
