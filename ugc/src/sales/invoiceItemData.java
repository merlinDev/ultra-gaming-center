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
public class invoiceItemData {

    private StringProperty id;
    private StringProperty name;
    private StringProperty sellingprice;
    private StringProperty quantity;

    public invoiceItemData(String id, String name, String sellingprice, String quantity) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.sellingprice = new SimpleStringProperty(sellingprice);
        this.quantity = new SimpleStringProperty(quantity);
    }

    public String getName() {
        return name.get();
    }

    public String getId() {
        return id.get();
    }

    public String getSellingprice() {
        return sellingprice.get();
    }

    public String getQuantity() {
        return quantity.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public void setSellingprice(String qty) {
        this.sellingprice.set(qty);
    }

    public void setQuantity(String uprice) {
        
        this.quantity.set(uprice);
    }
}
