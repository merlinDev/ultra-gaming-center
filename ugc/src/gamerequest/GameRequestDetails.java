/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamerequest;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Isu
 */
public class GameRequestDetails {
    private StringProperty customernic;
    private StringProperty gamename;
    private StringProperty descrip;

    public GameRequestDetails(String s, String s1, String s2) {
        this.customernic = new SimpleStringProperty(s);
        this.gamename = new SimpleStringProperty(s1);
        this.descrip = new SimpleStringProperty(s2);
    }
    
    

    /**
     * @return the customernic
     */
    public String getCustomernic() {
        return customernic.get();
    }

    /**
     * @param customernic the customernic to set
     */
    public void setCustomernic(String s3) {
        this.customernic.set(s3);
    }

    /**
     * @return the gamename
     */
    public String getGamename() {
        return gamename.get();
    }

    /**
     * @param gamename the gamename to set
     */
    public void setGamename(String s4) {
        this.gamename.set(s4);
    }

    /**
     * @return the descrip
     */
    public String getDescrip() {
        return descrip.get();
    }

    /**
     * @param descrip the descrip to set
     */
    public void setDescrip(String s5) {
        this.descrip.set(s5);
    }
    
}
