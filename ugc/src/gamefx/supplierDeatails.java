/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Buddhika
 */
public class supplierDeatails {
    private StringProperty name;
    private StringProperty cname;
    private StringProperty tp;
    private StringProperty em;
    private StringProperty address ;

    public supplierDeatails(String name, String cname, String tp, String em, String address) {
        this.name = new SimpleStringProperty(name);
        this.cname = new SimpleStringProperty(cname);
        this.tp = new SimpleStringProperty(tp);
        this.em = new SimpleStringProperty(em);
        this.address = new SimpleStringProperty(address);
    }
     public String getName() {
        return name.get();
         
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCname() {
        return cname.get();
    }

    public void setCname(String cname) {
        this.cname.set(cname);
    }

    public String getTp() {
        return tp.get();
    }

    public void setp(String tp) {
        this.tp.set(tp);
    }

    public String getEm() {
        return em.get();
    }

    public void setEm(String em) {
        this.em.set(em);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
         
    }
}
