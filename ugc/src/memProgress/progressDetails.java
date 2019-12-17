/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memProgress;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nipun
 */
public class progressDetails {
    StringProperty game;
    StringProperty duration;
    
    public progressDetails(String game,String duration){
        this.game=new SimpleStringProperty(game);
        this.duration=new SimpleStringProperty(duration);
    }
    
    public String getGame(){
        return game.get();
    }
    public String getDuration(){
        return duration.get();
    }
    
    public void setGame(String game){
        this.game.set(game);
    }
    public void setDuration(String duration){
        this.duration.set(duration);
    }
}
