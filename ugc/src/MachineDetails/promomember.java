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
public class promomember {
     private String promocode;
    private String name;
    private String gamername;
    private String useddate;

    public promomember(String promocode, String name, String gamername, String useddate) {
        this.promocode = promocode;
        this.name = name;
        this.gamername = gamername;
        this.useddate = useddate;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGamername() {
        return gamername;
    }

    public void setGamername(String gamername) {
        this.gamername = gamername;
    }

    public String getUseddate() {
        return useddate;
    }

    public void setUseddate(String useddate) {
        this.useddate = useddate;
    }

    
}
