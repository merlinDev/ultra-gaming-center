/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memData;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nipun
 */
public class loginDetails {
    StringProperty date;
    StringProperty game;
    StringProperty duration;
    StringProperty started;
    
    public loginDetails(String date){
        this.date=new SimpleStringProperty(date);
    }
    public loginDetails(String game,String duration,String started){
        this.game=new SimpleStringProperty(game);
        this.duration=new SimpleStringProperty(duration);
        this.started=new SimpleStringProperty(started);
    }
    
    public String getDate(){
     return this.date.get();
    }
    public String getGame(){
     return this.game.get();
    }
    public String getDuration(){
     return this.duration.get();
    }
    public String getStarted(){
     return this.started.get();
    }
    
    
    public void setDate(String date){
        this.date.set(date);
    }
    public void setGame(String game){
        this.game.set(game);
    }
    public void setDuration(String duration){
        this.duration.set(duration);
    }
    public void setStarted(String started){
        this.started.set(started);
    }
}
