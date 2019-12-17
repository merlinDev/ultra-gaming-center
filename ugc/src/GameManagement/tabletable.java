/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameManagement;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Smart
 */
public class tabletable {

    private SimpleStringProperty ipaddress;
    private SimpleStringProperty Name;
    private SimpleStringProperty Type;
    private SimpleStringProperty machineid;
    private final JFXCheckBox selectMachine = new JFXCheckBox();

    public tabletable(String IPaddress, String name, String type, String machineid) {
        this.ipaddress = new SimpleStringProperty(IPaddress);
        this.Name = new SimpleStringProperty(name);
        this.Type = new SimpleStringProperty(type);
        this.machineid = new SimpleStringProperty(machineid);
    }

    public String getIPaddress() {
        return this.ipaddress.get();
    }

    public void setIPaddress(String IPaddress) {
        this.ipaddress.set(IPaddress);
    }

    public String getName() {
        return this.Name.get();
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public String getType() {
        return this.Type.get();
    }

    public void setType(String type) {
        this.Type.set(type);
    }

    public String getMachineid() {
        return this.machineid.get();
    }

    public void setMachineid(String machineid) {
        this.machineid.set(machineid);
    }

    public JFXCheckBox getSelectMachine() {
        return selectMachine;
    }
    
    public boolean getSelectMachineStatus(){
        return selectMachine.isSelected();
    }
    
    public void setSelectMachineStatus(boolean value){
        selectMachine.setSelected(value);
    }

}
