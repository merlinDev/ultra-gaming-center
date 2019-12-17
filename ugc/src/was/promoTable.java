/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package was;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Waseeakr
 */
public class promoTable {

    private StringProperty name;
    private StringProperty discountPercentage;
    private StringProperty startDate;
    private StringProperty endDate;

    public promoTable(String name, int discountPercentage, Date startDate,Date endDate) {
        this.name = new SimpleStringProperty(name);
        this.discountPercentage= new SimpleStringProperty(String.valueOf(discountPercentage));
        this.startDate = new SimpleStringProperty(startDate.toString());  
        this.endDate = new SimpleStringProperty(endDate.toString());  
    }    

    public String getName() {
        System.out.println("sfsf");
        return name.get();
    }

    public String getDiscountPercentage() {
        return discountPercentage.get();
    }

    public String getStartDate() {
        return startDate.get();
    }
    public String getEndDate() {
        return endDate.get();
    }
     public void setName(String name) {
        this.name.set(name);
    }
     public void setDiscounPercentage(String discountPercentage) {
        this.discountPercentage.set(discountPercentage);
    }
     public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }
     public void setEndDate(String endDate) {
        this.endDate.set(endDate);
    }
}
