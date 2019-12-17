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
public class GrnDetails {

    private StringProperty gno;
    private StringProperty price;
    private StringProperty dat;
     private StringProperty supp;
    public GrnDetails(String gno, String price,String dat,String supp) {
        this.gno= new SimpleStringProperty(gno);     
        this.price = new SimpleStringProperty(price);
        this.dat = new SimpleStringProperty(dat);
         this.supp = new SimpleStringProperty(supp);
    }

   
    public String getGno() {
        return gno.get();
    }
      public String getSupp() {
        return supp.get();
    }


    public String getPrice() {
        return price.get();
    }
 public String getDat() {
        return dat.get();
    }

  
    public void setGno(String gno) {
        this.gno.set(gno);
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
 public void setSupp(String supp) {
        this.supp.set(supp);
    }
  
    public void setDat(String dat) {
        this.dat.set(dat);
    }
    

   
}
