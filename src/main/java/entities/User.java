package entities;

import dto.characterDTO;
import dto.movieDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

@Entity
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_name", length = 25)
  private String userName;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "user_pass")
  private String userPass;
  
  @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToMany(cascade = CascadeType.PERSIST)
  private List<Role> roleList = new ArrayList<>();
  
  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "movies_liked", 
  joinColumns = @JoinColumn(name = "user_name", referencedColumnName = "user_name"), 
  inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
  private List<LikedMovie> likedMovies = new ArrayList<>();
  
    @ManyToMany(cascade = CascadeType.ALL) 
      @JoinTable(name = "actors_liked", 
  joinColumns = @JoinColumn(name = "user_name", referencedColumnName = "user_name"), 
  inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "id"))
    private List<LikedActor> likesActors = new ArrayList(); 

  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
        rolesAsStrings.add(role.getRoleName());
      });
    return rolesAsStrings;
  }

  public User() {}

  
   public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw, this.userPass);
    }

  public User(String userName, String userPass) {
    this.userName = userName;
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());
  }


  
  public void addLikedMovie(LikedMovie m){
      
      likedMovies.add(m);
  }
  
   public void removeLikedMovie(LikedMovie m){
            
  }
  
   public List<movieDTO> getLikedMovies(){
        List<movieDTO> movies = new ArrayList();
       for (LikedMovie m : this.likedMovies){
           movies.add(new movieDTO(m));
       }
      return movies ;
  }
   
   
  public void addActor(LikedActor a){

      likesActors.add(a);
  }
  public List<characterDTO> getActors(){
        List<characterDTO> actors = new ArrayList();
       for (LikedActor a : this.likesActors){
           actors.add(new characterDTO(a));
       }
      
      return actors;
  }
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPass() {
    return this.userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = BCrypt.hashpw(userPass, BCrypt.gensalt());;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    roleList.add(userRole);
  }

}
