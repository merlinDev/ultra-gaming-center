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
package connection;

import accounts.user;
import gamefx.Log;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.Timer;

/**
 *
 * @author Roshana Pitigala
 */
public final class Command {

    private static boolean endTree = true;
    private static String processResponse;
    private final static Timer processTimer = new Timer(2000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            terminateProcess();
            processTimer.stop();
        }
    });

    private Command() {
    }

    public static final String LOGOUT = "logout",
            SHUTDOWN = "shutdown",
            ERROR = "Communication failed",
            OK = "ok",
            SMS = "SMS",
            REQUEST_USER_SESSION_DATA = "RuserSdata",
            USER_SESSION_DATA_LOGIN = "userSdataLogin",
            USER_SESSION_DATA_LOGOUT = "userSdataLogout",
            UNKNOWN_COMMAND = "unknown",
            SYSTEM_NOT_IN_USE = "sysNInUse",
            CMD = "cmd",
            PLAY_WARNING_SOUND = "playWarn";

    /**
     * Logout Windows on this system.
     *
     * @param remoteCall Is the method called remotely.
     * @throws IOException If an I/O error occurs
     */
    public static void systemLogout(boolean remoteCall) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="systemLogout">
        user.logout(false, remoteCall);
        Runtime.getRuntime().exec("cmd /c shutdown -l -f");
        System.exit(0);
        //</editor-fold>
    }

    /**
     * Shutdown Windows on this system.
     *
     * @param remoteCall Is the method called remotely.
     * @throws IOException If an I/O error occurs
     */
    public static void systemShutdown(boolean remoteCall) throws Exception {
        // <editor-fold defaultstate="collapsed" desc="systemShutdown">
        user.logout(false, remoteCall);
        Runtime.getRuntime().exec("cmd /c shutdown -s -f -t 0");
        System.exit(0);
        //</editor-fold>
    }

    /**
     * Restarts Windows on this system.
     *
     * @param remoteCall Is the method called remotely.
     * @throws IOException If an I/O error occurs
     */
    public static void systemRestart(boolean remoteCall) throws IOException {
        // <editor-fold defaultstate="collapsed" desc="systemShutdown">
        user.logout(false, remoteCall);
        Runtime.getRuntime().exec("cmd /c shutdown -r -f -t 0");
        System.exit(0);
        //</editor-fold>
    }

    /**
     * Set user session data into a format and return as String.
     *
     * @param machineId Machine id.
     * @param userType user type.
     * @param userId user id.
     * @param loginTime last logged in dateTime.
     * @param packageTime full package time.
     * @param duration full duration of user.
     * @return encoded data.
     */
    public static String encodeUserSessionData(String machineId, String userType, String userId, String loginTime, long packageTime, long duration) {
        // <editor-fold defaultstate="collapsed" desc="encodeUserSessionData">
        return machineId + ";" + userType + ";" + userId + ";" + loginTime + ";" + packageTime + ";" + duration;
        //</editor-fold>
    }

    /**
     * Runs the given command in Windows Command Processor and returns the
     * response.
     *
     * @param command Command.
     * @param respond wait for a response.
     * @return response.
     */
    public static String cmd(final String command, boolean respond) {
        // <editor-fold defaultstate="collapsed" desc="cmd">
        String line;
        try {
            processResponse = "";
            if (respond) {
                processTimer.setDelay(6000);
            } else {
                processTimer.setDelay(2000);
            }
            Process p = Runtime.getRuntime().exec("cmd /c " + command);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            endTree = respond;
            processTimer.start();
            if (respond) {
                while ((line = input.readLine()) != null) {
                    processResponse += line + "\r\n";
                }
            }
        } catch (Exception ex) {
            Log.error(ex);
            return Command.ERROR;
        }
        return processResponse;
        //</editor-fold>
    }

    private static void terminateProcess() {
        // <editor-fold defaultstate="collapsed" desc="terminateProcess">
        try {
            Runtime.getRuntime().exec("cmd /c taskkill /F /IM cmd.exe" + ((endTree) ? (" /T") : ("")));
            processResponse += "Process was terminated with force to avoid system crash...\r\n"
                    + "Try again executing an 'One Way Command'.\r\n";
        } catch (Exception ex) {
            Log.error(ex);
        }
        //</editor-fold>
    }
}
