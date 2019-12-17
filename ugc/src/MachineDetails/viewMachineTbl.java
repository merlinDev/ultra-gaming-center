/*
 *	Java Institute For Advanced Technology
 *	Final Project
 *	
 *	*************************************
 *
 *	The MIT License
 *
 *	Copyright © 2017 CYCLOTECH.
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *	THE SOFTWARE.
 */
package MachineDetails;

import com.jfoenix.controls.JFXCheckBox;
import gamefx.Utility;
import java.awt.TrayIcon;

/**
 *
 * @author RoshSoft Co.
 */
public class viewMachineTbl {

    /**
     * Columns
     */
    private String machineId, userType, userId, loginTime, packageTime, duration, overTime;
    private final Utility util;
    private long durationSec, overTimeSec, packageTimeSec;
    private JFXCheckBox selected = new JFXCheckBox();

    public viewMachineTbl(String machineId, String usertype, String userid, String logintime, long packageTime, long duration) {
        util = new Utility();
        this.machineId = machineId;
        this.userType = usertype;
        this.userId = userid;
        this.loginTime = logintime;
        this.packageTimeSec = packageTime;
        setPackageTime();
        this.durationSec = duration;
        this.overTimeSec = -1;
        setDuration();
        setOverTime();
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getPackageTime() {
        return packageTime;
    }

    public void setPackageTime() {
        String ptime[] = util.timeSplitter(packageTimeSec);
        this.packageTime = ptime[0] + "hour(s) " + ptime[1] + "minute(s) " + ptime[2] + "second(s)";
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration() {
        String time[] = util.timeSplitter(durationSec);
        this.duration = time[0] + " : " + time[1] + " : " + time[2];
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime() {
        if (overTimeSec == 0) {
            util.displayTrayNotification("User Overtime Warning",
                    "User Id : " + getUserId() + "\nPackage Duration : " + getPackageTime() + "Login Time : " + getLoginTime(),
                    TrayIcon.MessageType.WARNING);
        } else if (overTimeSec > 0) {
            String time[] = util.timeSplitter(overTimeSec);
            this.overTime = time[0] + " : " + time[1] + " : " + time[2];
        } else {
            overTime = "N/A";
        }
    }

    public void durationTick() {
        durationSec++;
        setDuration();
        overTimeSec = durationSec - packageTimeSec;
        setOverTime();
    }

    public boolean isOverTime() {
        return (overTimeSec > 0);
    }

    public void setSelectedStatus(boolean value) {
        this.selected.setSelected(value);
    }

    public boolean getSelectedStatus() {
        return selected.isSelected();
    }

    public JFXCheckBox getSelected() {
        return selected;
    }

}
