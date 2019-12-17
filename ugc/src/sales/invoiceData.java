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
public class invoiceData {
    private StringProperty id;
    private StringProperty total;
    private StringProperty paid;
    private StringProperty date;

    public invoiceData(String id,String total, String paid, String date) {
        this.id = new SimpleStringProperty(id);
        this.total = new SimpleStringProperty(total);
        this.paid= new SimpleStringProperty(paid);
        this.date = new SimpleStringProperty(date);  
    }    

    public String getTotal() {
        return total.get();
    }
    public String getId() {
        return id.get();
    }

    public String getPaid() {
        return paid.get();
    }

    public String getDate() {
        return date.get();
    }
     public void setTotal(String name) {
        this.total.set(name);
    }
     public void setId(String id){
        this.id.set(id);
    }
     public void setPaid(String qty) {
        this.paid.set(qty);
    }
     public void setDate(String uprice) {
        this.date.set(uprice);
    } 
}
