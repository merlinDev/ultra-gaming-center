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
public class ItemShowDetails {
    private StringProperty bar;
   private StringProperty game;
    private StringProperty type;
    private StringProperty supp;
    private StringProperty qty;
    private StringProperty bp;
    private StringProperty sp;  
    private StringProperty date;  
    public ItemShowDetails(String bar, String game, String type, String supp,String qty, String bp, String sp,String date) {
        this.bar = new SimpleStringProperty(bar);
        this.supp = new SimpleStringProperty(supp);
        this.game = new SimpleStringProperty(game);
        this.type = new SimpleStringProperty(type);
        this.qty = new SimpleStringProperty(qty);
        this.sp = new SimpleStringProperty(sp);
        this.bp = new SimpleStringProperty(bp);
        this.date = new SimpleStringProperty(date);
    }

    public String getBar() {
        return bar.get();
    }

    public void setBar(String bar) {
        this.bar.set(bar);
    }

    public String getGame() {
        return game.get();
    }

    public void setGame(String game) {
        this.game.set(game);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getSupp() {
        return supp.get();
    }

    public void setSupp(String supp) {
        this.supp.set(supp);
    }

    public String getQty() {
        return qty.get();
    }

    public void setQty(String qty) {
        this.qty.set(qty);
    }

    public String getBp() {
        return bp.get();
    }

    public void setBp(String bp) {
        this.bp.set(bp);
    }

    public String getSp() {
        return sp.get();
    }

    public void setSp(String sp) {
        this.sp.set(sp);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }
    
}
