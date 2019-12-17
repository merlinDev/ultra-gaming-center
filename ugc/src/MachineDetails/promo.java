package MachineDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PASINDHU
 */
public class promo {
    private String promoid;
    private String from;
    private String to;
    private String promocode;
    private String disprice;

    public promo(String promoid, String from, String to,String promocode, String disprice) {
        this.promoid =promoid;
        this.from = from;
        this.to = to;
        this.promocode = promocode;
        this.disprice = disprice;
   
    }
    public String getPromoid() {
        return promoid;
    }

    public void setPromoid(String promoid) {
        this.promoid = promoid;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPromocode() {
        return promocode;
    }

    public void setPromocode(String promocode) {
        this.promocode = promocode;
    }

    public String getDisprice() {
        return disprice;
    }

    public void setDisprice(String disprice) {
        this.disprice = disprice;
    }
}
