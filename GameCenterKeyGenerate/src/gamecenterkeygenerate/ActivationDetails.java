/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecenterkeygenerate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Isu
 */
public class ActivationDetails {
    private StringProperty customernic;
    private StringProperty numofpc;
    private StringProperty activationkey;

    public ActivationDetails(String s, String s1, String s2) {
        this.customernic = new SimpleStringProperty(s);
        this.numofpc = new SimpleStringProperty(s1);
        this.activationkey = new SimpleStringProperty(s2);
        
        
    }

    
    
    /**
     * @return the nic
     */
    public String getCustomernic() {
        return customernic.get();
    }

    /**
     * @param nic the nic to set
     */
    public void setCustomernic(String s3) {
        this.customernic.set(s3);
    }

    /**
     * @return the numpc
     */
    public String getNumofpc() {
        return numofpc.get();
    }

    /**
     * @param numpc the numpc to set
     */
    public void setNumofpc(String s4) {
        this.numofpc.set(s4);
    }

    /**
     * @return the actkey
     */
    public String getActivationkey() {
        return activationkey.get();
    }

    /**
     * @param actkey the actkey to set
     */
    public void setActivationkey(String s5) {
        this.activationkey.set(s5);
    }
    
    
}
