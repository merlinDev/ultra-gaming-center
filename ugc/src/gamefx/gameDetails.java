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
public class gameDetails {
  

    private StringProperty name;
    private StringProperty id;
    private StringProperty price;
    private StringProperty qty;

    public gameDetails(String name, String price,String qty,String id) {
        this.name= new SimpleStringProperty(name);     
        this.price = new SimpleStringProperty(price);
        this.qty = new SimpleStringProperty(qty);
        this.id = new SimpleStringProperty(id);
    }

   
    public String getName() {
        return name.get();
    }
public String getId() {
        return id.get();
    }


    public String getPrice() {
        return price.get();
    }
 public String getQty() {
        return qty.get();
    }

  
    public void setNmae(String name) {
        this.name.set(name);
    }
        public void setId(String id) {
        this.id.set(id);
    }
    public void setPrice(String price) {
        this.price.set(price);
    }

  
    public void setQty(String qty) {
        this.qty.set(qty);
    }
    

   

  
}
