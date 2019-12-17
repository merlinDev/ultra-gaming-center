package MachineDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PASINDHU
 */
public class loadUSers {
    private String userid;
    private String name;
    private String gamername;

    public loadUSers(String userid, String name, String gamername) {
        this.userid = userid;
        this.name = name;
        this.gamername =gamername;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

}