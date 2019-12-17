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
public class topsuppDetails {
    private StringProperty name;
    private StringProperty pre;

    public topsuppDetails(String name, String pre) {
         this.name = new SimpleStringProperty(name);
        this.pre = new SimpleStringProperty(pre);
    }
  public String getName() {
        return name.get();
         
    }

    public void setName(String name) {
        this.name.set(name);
    }
    public String getPre() {
        return pre.get();
         
    }

    public void setPre(String pre) {
        this.pre.set(pre);
    }
}
