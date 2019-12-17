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
public class machineHasGames {
    private String machineId;
    private String machinename;
    private String machineType;
    private String ipAddress;

    public machineHasGames(String machineId, String machinename, String machineType, String ipAddress) {
        this.machineId = machineId;
        this.machinename = machinename;
        this.machineType = machineType;
        this.ipAddress = ipAddress;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachinename() {
        return machinename;
    }

    public void setMachinename(String machinename) {
        this.machinename = machinename;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    
}
