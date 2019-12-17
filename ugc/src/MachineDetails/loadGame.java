/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;

/**
 *
 * @author PASINDHU
 */
public class loadGame {

    private String machineId;
    private String usertype;
    private String userid;
    private String logtime;
    private String packageTime;
    private String duration;

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLogtime() {
        return logtime;
    }

    public void setLogtime(String logtime) {
        this.logtime = logtime;
    }

    public String getPackageTime() {
        return packageTime;
    }

    public void setPackageTime(String packageTime) {
        this.packageTime = packageTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public loadGame(String machineId, String usertype, String userid, String logtime, String packageTime, String duration) {
        this.machineId = machineId;
        this.usertype = usertype;
        this.userid = userid;
        this.logtime = logtime;
        this.packageTime = packageTime;
        this.duration = duration;
    }



}
