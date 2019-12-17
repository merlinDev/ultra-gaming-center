package MachineDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author PASINDHU
 */
public class machineFront {

    private String idMachine;
    private String name;
    private String availability;
    private String inUse;
    private String type;
    private String ip;

    public machineFront(String idMachine, String ip, String name, String availability, String inUse, String type) {
        this.idMachine = idMachine;
        this.name = name;
        this.ip = ip;
        this.availability = availability;
        this.inUse = inUse;
        this.type = type;

    }

    public String getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(String idMachine) {
        this.idMachine = idMachine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getInUse() {
        return inUse;
    }

    public void setInUse(String inUse) {
        this.inUse = inUse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
