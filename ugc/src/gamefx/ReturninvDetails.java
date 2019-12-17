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
public class ReturninvDetails {
     private StringProperty bar;
    private StringProperty game;
    private StringProperty qty;

    public ReturninvDetails(String bar, String game, String qty) {
        this.bar =new SimpleStringProperty(bar); 
        this.game =new SimpleStringProperty(game); 
        this.qty = new SimpleStringProperty(qty); 
    }
      public String getBar() {
        return bar.get();
    }
      public String getGame() {
        return game.get();
    }


    public String getQty() {
        return qty.get();
    }
     public void setBar(String bar) {
        this.bar.set(bar);
    }

    public void setGame(String game) {
        this.game.set(game);
    }
 public void setQty(String qty) {
        this.qty.set(qty);
    }
}
