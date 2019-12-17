/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Waseeakr
 */
public class gameincomeTable {
    
    private StringProperty incomeDate;
    private StringProperty incomePrice;

    public gameincomeTable( Date incomeDate, String incometot) {
        this.incomeDate = new SimpleStringProperty(incomeDate.toString());
        this.incomePrice = new SimpleStringProperty(incometot);
    }
    
    public String getIncomeDate() {
        return incomeDate.get();
    }

    public String getIncomePrice() {
        return incomePrice.get();
    }

    public void setIncomeDate(String sellingDate) {
        this.incomeDate.set(sellingDate);
    }

    public void setIncomePrice(String invoicetot) {
        this.incomePrice.set(invoicetot);
    }
}
