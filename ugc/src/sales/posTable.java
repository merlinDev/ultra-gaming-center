/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Waseeakr
 */
public class posTable {

    private StringProperty id;
    private StringProperty name;
    private StringProperty qty;
    private StringProperty uPrice;
    private StringProperty total;

    public posTable(String id,String name, int qty, double uPrice,double total) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.qty= new SimpleStringProperty(qty+"");
        this.uPrice = new SimpleStringProperty(uPrice+"");  
        this.total = new SimpleStringProperty(total+"");  
    }    

    public String getName() {
        return name.get();
    }
    public String getId() {
        return id.get();
    }

    public String getQty() {
        return qty.get();
    }

    public String getUPrice() {
        return uPrice.get();
    }
    public String getTotal() {
        return total.get();
    }
     public void setName(String name) {
        this.name.set(name);
    }
     public void setId(String id){
        this.id.set(id);
    }
     public void setQty(String qty) {
        this.qty.set(qty);
    }
     public void setUprice(String uprice) {
        this.uPrice.set(uprice);
    }
     public void setTotal(String total) {
        this.total.set(total);
    }
}
