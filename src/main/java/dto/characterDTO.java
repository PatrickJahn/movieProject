/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.LikedActor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrick
 */
public class characterDTO {
    
    List<characterDTO> results = new ArrayList();
    
    String name;
    Long id;
    String profile_path;
 
    
    public characterDTO(LikedActor actor){
        this.name = actor.getName();
        this.id = actor.getId();
        this.profile_path = actor.getProfile_path();
    }
    
}
