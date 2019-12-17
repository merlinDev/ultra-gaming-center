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
public class expenseTable {

    private StringProperty expenseDate;
    private StringProperty grnNo;
    private StringProperty expensePrice;

    public expenseTable( String grnNo,Date expenseDate, String expensePrie) {
        this.expenseDate = new SimpleStringProperty(expenseDate.toString());
        this.grnNo = new SimpleStringProperty(grnNo);
        this.expensePrice = new SimpleStringProperty(expensePrie);  
    }    

    public String getExpenseDate() {
        System.out.println("sfsf");
        return expenseDate.get();
    }

    public String getGrnNo() {
        return grnNo.get();
    }

    public String getExpensePrice() {
        return expensePrice.get();
    }
     public void setExpenseDate(String expenseDate) {
        this.expenseDate.set(expenseDate);
    }
     public void setIncomeName(String grnNo) {
        this.grnNo.set(grnNo);
    }
     public void setIncomeprice(String expenseDate) {
        this.expenseDate.set(expenseDate);
    }
}
