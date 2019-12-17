package memData;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nipun
 */
public class memberDetails {
    //member
    private StringProperty id;
    private StringProperty name;
    private StringProperty email;
    private StringProperty username;
    private StringProperty status;
    private StringProperty nic;
    private StringProperty contact;
    
    //membership
    private StringProperty idType;
    private StringProperty typeName;
    private StringProperty durationDay;
    private StringProperty durationMonth;
    private StringProperty typePrice;
    private StringProperty overTime;
    
    // overtime
    private StringProperty idCharge;
    private StringProperty charge;
    private StringProperty durationCharge;
       
    //member cons
    public memberDetails(String id,String name,String nic,String email,String username,String contact,String status){
        this.id=new SimpleStringProperty(id);
        this.name=new SimpleStringProperty(name);
        this.email=new SimpleStringProperty(email);
        this.username=new SimpleStringProperty(username);
        this.status=new SimpleStringProperty(status);
        this.nic=new SimpleStringProperty(nic);
        this.contact=new SimpleStringProperty(contact);
           
    }
    
    // memship con
    public memberDetails(String idType,String typeName,String durationDay,String durationMonth,String typePrice,String overTime){
        this.idType=new SimpleStringProperty(idType);
        this.typeName=new SimpleStringProperty(typeName);
        this.durationDay=new SimpleStringProperty(durationDay);
        this.durationMonth=new SimpleStringProperty(durationMonth);
        this.typePrice=new SimpleStringProperty(typePrice);
        this.overTime=new SimpleStringProperty(overTime);
    }
    
    // overtime
    public memberDetails(String idCharge,String charge,String durationCharge){
        this.idCharge=new SimpleStringProperty(idCharge);
        this.charge=new SimpleStringProperty(charge);
        this.durationCharge=new SimpleStringProperty(durationCharge);
    }
    
    
    //getters
        
        // members
    public String getId(){
        return id.get();
    }
    public String getName(){
        return name.get();
    }
    public String getNic(){
        return nic.get();
    }
    public String getEmail(){
        return email.get();
    }
    public String getUsername(){
        return username.get();
    }
    public String getStatus(){
        return status.get();
    }
    public String getContact(){
        return contact.get();
    }
   
        // memtype
    public String getIdType(){
        return idType.get();
    }
    public String getTypeName(){
        return typeName.get();
    }
    public String getDurationDay(){
        return durationDay.get();
    }
    public String getDurationMonth(){
        return durationMonth.get();
    }
    public String getTypePrice(){
        return typePrice.get();
    }
     public String getOverTime(){
        return overTime.get();
    }
    
     
     // overtime
     public String getIdCharge(){
         return idCharge.get();
     }
     public String getCharge(){
         return charge.get();
     }
     public String getDurationCharge(){
         return durationCharge.get();
     }
    
    // setters
    public void setId(String id){
        this.id.set(id);
    }
    public void setName(String name){
        this.name.set(name);
    }
    public void setNic(String nic){
        this.nic.set(nic);
    }
    public void setEmail(String email){
        this.email.set(email);
    }
    public void setUsername(String username){
        this.username.set(username);
    }
    public void setContact(String contact){
        this.contact.set(contact);
    }
    
        //memType
    public void setIdtype(String idType){
        this.idType.set(idType);
    }
    public void setTypeName(String typeName){
        this.typeName.set(typeName);
    }
    public void setDurationDay(String durationDay){
        this.durationDay.set(durationDay);
    }
    public void setDurationMonth(String durationMonth){
        this.durationMonth.set(durationMonth);
    }
    public void setTypePrice(String typePrice){
        this.typePrice.set(typePrice);
    }
    public void setOverTime(String overTime){
        this.overTime.set(overTime);
    }
    
    //overtime
    public void setIdCharge(String idCharge){
        this.idCharge.set(idCharge);
    }
    public void setCharge(String charge){
        this.charge.set(charge);
    }
    public void set(String durationCharge){
        this.durationCharge.set(durationCharge);
    }
}
