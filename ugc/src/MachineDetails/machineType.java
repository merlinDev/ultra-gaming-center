/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PASINDHU
 */
public class machineType {

   private StringProperty type;
   private StringProperty count;
   private StringProperty avail;
            
    public machineType(String type, String count, String avail) {
        this.type = new SimpleStringProperty(type); 
        this.count = new SimpleStringProperty(count); 
        this.avail = new SimpleStringProperty(avail);
    }
     public String getType(){
        return type.get();
    }
     public void setType(String type){
         this.type.set(type);
     }
     public String getCount(){
        return count.get();
    }
     public void setCount(String type){
         this.count.set(type);
     }
     public String getAvail(){
        return avail.get();
    }
     public void setAvail(String type){
         this.avail.set(type);
     }
    
}
