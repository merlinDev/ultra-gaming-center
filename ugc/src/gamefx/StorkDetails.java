/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Buddhika
 */
public class StorkDetails {
    private StringProperty id;
   private StringProperty game;
    private StringProperty bp;
    private StringProperty sp;
    private StringProperty type;
    private StringProperty qty;

    public StorkDetails(String id, String game,String bp,String sp, String type,String qty) {
        this.id= new SimpleStringProperty(id);     
        this.game = new SimpleStringProperty(game);
        this.bp = new SimpleStringProperty(bp);
         this.sp= new SimpleStringProperty(sp);     
        this.type = new SimpleStringProperty(type);
        this.qty = new SimpleStringProperty(qty);
    }

   
    public String getId() {
        return id.get();
    }


    public String getGame() {
        return game.get();
    }
 public String getBp() {
        return bp.get();
    }
 public String getSp() {
        return sp.get();
    }


    public String getType() {
        return type.get();
    }
 public String getQty() {
        return qty.get();   
    }
 
 
   public void setId(String id) {
        this.id.set(id);
    }

    public void setGame(String game) {
        this.game.set(game);
    }

  
    public void setBp(String bp) {
        this.bp.set(bp);
    }
    public void setSp(String sp) {
        this.sp.set(sp);
    }

    public void setType(String type) {
        this.type.set(type);
    }

  
    public void setQty(String qty) {
        this.qty.set(qty);
    }
    

   
}
