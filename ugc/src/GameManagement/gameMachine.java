/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameManagement;

/**
 *
 * @author Smart
 */
public class gameMachine {

    private String idMachine;
    private String availability;
    private String inuse;
    private String type;

    public gameMachine(String idMachine, String availability, String inuse, String type) {
        this.idMachine = idMachine;
        this.availability = availability;
        this.inuse = inuse;
        this.type = type;
    }
    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getInuse() {
        return inuse;
    }

    public void setInuse(String inuse) {
        this.inuse = inuse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(String idMachine) {
        this.idMachine = idMachine;
    }

}
