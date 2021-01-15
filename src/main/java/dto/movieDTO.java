/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.LikedMovie;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patrick
 */
public class movieDTO {
    
    String id;
    String title;
    String overview;
    String poster_path;
    String release_date;
    
    String character;
    List<movieDTO> results = new ArrayList();

    public movieDTO(LikedMovie m) {
        this.id = m.getId();
        this.title = m.getTitle();
        this.overview = m.getOverview();
        this.poster_path = m.getPoster_path();
        this.release_date = m.getRelease_date();
    }
    
    
    
    
}
