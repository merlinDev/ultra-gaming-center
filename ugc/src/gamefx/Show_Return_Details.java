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
public class Show_Return_Details {
     private StringProperty bar;
    private StringProperty game;
    private StringProperty qty;
    private StringProperty supp;
    private StringProperty states;
      private StringProperty sendd;
      private StringProperty resolved;
private StringProperty eid;
    public Show_Return_Details(String bar, String game, String qty, String supp, String states,String eid,String sendd,String resolved) {
        this.bar =new SimpleStringProperty(bar); 
        this.game =new SimpleStringProperty(game); 
        this.qty = new SimpleStringProperty(qty); 
        this.supp = new SimpleStringProperty(supp); 
        this.states = new SimpleStringProperty(states); 
        this.eid = new SimpleStringProperty(eid); 
           this.sendd = new SimpleStringProperty(sendd); 
            this.resolved = new SimpleStringProperty(resolved);    
        
    }
      public String getBar() {
        return bar.get();
    }
      public String getSupp() {
        return supp.get();
    }


    public String getGame() {
        return game.get();
    }
 public String getQty() {
        return qty.get();
    }
 public String getStates() {
        return states.get();
    }
public String getEid() {
        return eid.get();
    }
public String getSendd() {
        return sendd.get();
    }
public String getResolved() {
        return resolved.get();
    }

  
    public void setResolved(String resolved) {
        this.resolved.set(resolved);
    }


  
    public void setSendd(String sendd) {
        this.sendd.set(sendd);
    }

  
    public void setBar(String bar) {
        this.bar.set(bar);
    }

    public void setQty(String qty) {
        this.qty.set(qty);
    }
 public void setSupp(String supp) {
        this.supp.set(supp);
    }
  
    public void setGame(String game) {
        this.game.set(game);
    }
    public void setStates(String states) {
        this.states.set(states);
    }
    public void setEid(String eid) {
        this.eid.set(eid);
    }
    
    
}
