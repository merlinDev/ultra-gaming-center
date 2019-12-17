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
public class incomeTable {
    
    private StringProperty sellingDate;
    private StringProperty invoicetot;

    public incomeTable( Date sellingDate, String invoicetot) {
        this.sellingDate = new SimpleStringProperty(sellingDate.toString());
        this.invoicetot = new SimpleStringProperty(invoicetot);
    }
    
    public String getSellingDate() {
        return sellingDate.get();
    }

    public String getInvoicetot() {
        return invoicetot.get();
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate.set(sellingDate);
    }

    public void setInvoicetot(String invoicetot) {
        this.invoicetot.set(invoicetot);
    }
}
