/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameManagement;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Smart
 */
public class GameStatus {

    private final SimpleStringProperty name;
    private final SimpleStringProperty status;
     private final SimpleStringProperty idGames;

    public GameStatus(String Gamesid,String gameName, String statusType) {
        this.idGames=new SimpleStringProperty(Gamesid);
        this.name = new SimpleStringProperty(gameName);
        this.status = new SimpleStringProperty(statusType);
        
    }

    public String getIdGames(){
         return idGames.get();                
}
    public void setIdGames(String Gamesid){
        idGames.set(Gamesid);
    }
    public String getName() {
        return name.get();
    }

    public void setName(String gameName) {
        name.set(gameName);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String statusType) {
        status.set(statusType);
    }
    
}
