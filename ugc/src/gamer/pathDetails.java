/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nipun
 */
public class pathDetails {
    StringProperty game;
    StringProperty path;
    
    public pathDetails(String game,String path){
        this.game=new SimpleStringProperty(game);
        this.path=new SimpleStringProperty(path);
    }
    
    public String getGame(){
        return game.get();
    }
    public String getPath(){
        return path.get();
    }
    
    public void setGame(String game){
        this.game.set(game);
    }
    public void setPath(String path){
        this.game.set(path);
    }
}
