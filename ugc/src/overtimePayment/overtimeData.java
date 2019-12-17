/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overtimePayment;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author nipun
 */
public class overtimeData {
    StringProperty date;
    StringProperty overtime;
    
    public overtimeData(String date,String overtime){
        this.date=new SimpleStringProperty(date);
        this.overtime=new SimpleStringProperty(overtime);
    }
    
    public String getDate(){
        return this.date.get();
    }
     
    public String getOvertime(){
        return this.overtime.get();
    }
    public void setDate(String date){
        this.date.set(date);
    }
    public void setOvertime(String overtime){
        this.overtime.set(overtime);
    }
    
}
