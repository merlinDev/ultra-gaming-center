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
package gamefx;

import accounts.user;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * Logger class where all the exceptions are logged.
 *
 * Version 1.17.09.14
 *
 * @author Roshana Pitigala
 */
public class Log {

    private Log() {
    }

    private static Logger Log;
    private static final boolean printLog = false;//Change this to false to stop error Log SOUTs.

    private static void setLogger() {
        // <editor-fold defaultstate="collapsed" desc="setLogger">
        String name = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String file = new Utility().getTempDir() + "Log\\" + name + ".log";
        checkLog(file);
        try {
            PatternLayout layout = new PatternLayout("%-3p %-10d %n %m %n%n");
            RollingFileAppender apender = new RollingFileAppender(layout, file);
            apender.setName(name);
            apender.setMaxFileSize("5MB");
            apender.activateOptions();
            Logger.getRootLogger().addAppender(apender);

            Log = Logger.getLogger(name);
        } catch (Exception e) {
        }
        // </editor-fold>
    }

    private static void checkLog(String file) {
        // <editor-fold defaultstate="collapsed" desc="checklog">
        try {
            File log = new File(file);
            if (!log.exists()) {
                new File(new Utility().getTempDir() + "Log").mkdirs();
                log.createNewFile();
            }
        } catch (Exception e) {
        }
        // </editor-fold>
    }

    /**
     * Logs error messages.
     *
     * @param ex Exception object.
     */
    public static void error(Throwable ex) {
        // <editor-fold defaultstate="collapsed" desc="error">
        if (Log == null) {
            setLogger();
        }

        String message = ex.toString();
        StackTraceElement stackTrace[] = ex.getStackTrace();

        if (printLog) {
            System.err.println("*****LOG START*****");
            System.err.println(message);
            for (int x = 0; x < stackTrace.length; x++) {
                System.err.println(stackTrace[x]);
            }
            System.err.println("*****LOG FINISH*****");
        }

        message += "\r\n";
        for (int x = 0; ((x < stackTrace.length) && (x < 10)); x++) {
            message += stackTrace[x] + "\r\n";
        }

        Log.error(message);
        // </editor-fold>
    }

    /**
     * Logs information messages.
     *
     * @param message Enter Message(s). Enter separate strings to break the
     * line.
     */
    public static void info(String... message) {
        // <editor-fold defaultstate="collapsed" desc="info">
        if (Log == null) {
            setLogger();
        }
        String msgPrint;
        msgPrint = "User ID : " + user.getIdUser() + " (" + user.getUserType() + ")\r\n";
        msgPrint += "Machine ID : " + user.getMachineId() + " (" + Utility.getIpAddress() + ")\r\n";

        try {
            for (String msg : message) {
                msgPrint += msg + "\r\n";
            }
        } catch (Throwable e) {
            error(e);
            msgPrint += "An error occured while trying to fetch data...\r\n"
                    + e.toString() + "\r\nCheck Error Log for more details.";
        }

        if (printLog) {
            System.out.println("*****INFO LOG START*****");
            System.out.println(msgPrint);
            System.out.println("*****INFO LOG FINISH*****");
        }

        Log.info(msgPrint);
    }
    // </editor-fold>
}
