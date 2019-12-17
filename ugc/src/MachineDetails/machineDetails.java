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
public class machineDetails {
    private StringProperty idMachine;
    private StringProperty ipAddress;
    private StringProperty name;
    private StringProperty availability;
    private StringProperty type;
    
    public machineDetails(String idMachine, String ipAddress, String name, String availability, String type) {
        this.idMachine = new SimpleStringProperty(idMachine); 
        this.ipAddress = new SimpleStringProperty(ipAddress); 
        this.name = new SimpleStringProperty(name);
        this.availability = new SimpleStringProperty(availability);
        this.type = new SimpleStringProperty(type);
    }
    public String getIdMachine(){
        return idMachine.get();
    }
    public String getIpAddress(){
        return ipAddress.get();
    }
    public String getName(){
        return name.get();
    }
    public String getAvailability(){
        return availability.get();
    }
    public  String getType(){
        return type.get();
    }
     public void setIdMachine(String idMachine){
         this.idMachine.set(idMachine);
     }
     public void setIpAddress(String ipAddress){
         this.ipAddress.set(ipAddress);
     }
     public void setName(String name){
         this.name.set(name);
     }
     public void setAvailability(String availability){
         this.availability.set(availability);
     }
     public void setType(String type){
         this.type.set(type);
     }
}
