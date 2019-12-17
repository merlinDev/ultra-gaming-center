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
import com.cyclotech.repository.CRUDOperationsImpl;
import com.cyclotech.repository.DB;
import connection.Speaker;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javax.mail.internet.InternetAddress;

/**
 * Utility Class where you can find equipments to make your codings more faster
 * and efficient.
 *
 * Utility version 1.17.09.08
 *
 * @author Roshana Pitigala
 */
public class Utility {

    private static Label timeLabel, dateLabel;
    private static Date dateObject;
    private static Calendar cal;
    private static String time, date;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa"),
            dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
    private static int garbageCollector = 0;
    private static final Timeline timeline = new Timeline(new KeyFrame(
            Duration.seconds(1),
            ae -> timerTick()));

    public Utility() {
    }

    /**
     * Starts the timer.
     */
    public static void startTimer() {
        // <editor-fold defaultstate="collapsed" desc="startTimer">
        setDate();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        //</editor-fold>
    }

    private static void setDate() {
        // <editor-fold defaultstate="collapsed" desc="setDate">
        try {
            ResultSet rs;
            CRUDOperationsImpl db = new CRUDOperationsImpl();
            rs = db.search("YEAR(NOW())AS `YYYY`, MONTH(NOW())AS `MM`, DAY(NOW())AS `DD`, HOUR(NOW())AS `H`, MINUTE(NOW())AS `M`, SECOND(NOW())AS `S`", "(SELECT 0)as `x`", "");
            rs.first();

            cal = Calendar.getInstance();
            cal.set(rs.getInt("YYYY"), (rs.getInt("MM") - 1), rs.getInt("DD"), rs.getInt("H"), rs.getInt("M"), rs.getInt("S"));
            dateObject = cal.getTime();
        } catch (Exception ex) {
            timeline.stop();
            Log.error(ex);
        }
        //</editor-fold>
    }

    /**
     * Sets the time to the given label.
     *
     * @param timeLabel label to set text.
     */
    public static void setTime(Label timeLabel) {
        // <editor-fold defaultstate="collapsed" desc="setTime">
        Utility.timeLabel = timeLabel;
        //</editor-fold>
    }

    /**
     * Sets the date to the given label.
     *
     * @param dateLabel label to set text.
     */
    public static void setDate(Label dateLabel) {
        // <editor-fold defaultstate="collapsed" desc="setDate">
        Utility.dateLabel = dateLabel;
        //</editor-fold>
    }

    /**
     * get time according to the format - hh:mm aa
     *
     * @return time as String.
     */
    public static String getTime() {
        // <editor-fold defaultstate="collapsed" desc="getTime">
        return time;
        //</editor-fold>
    }

    /**
     * Sets the date and time to the given labels.
     *
     * @param dateLabel labels to set date.
     * @param timeLabel labels to set time.
     */
    public static void setDateTime(Label dateLabel, Label timeLabel) {
        // <editor-fold defaultstate="collapsed" desc="setDateTime">
        setTime(timeLabel);
        setDate(dateLabel);
        //</editor-fold>
    }

    private static void setDateTime() {
        // <editor-fold defaultstate="collapsed" desc="setDateTime">
        time = timeFormat.format(dateObject);
        date = dateFormat.format(dateObject);
        if (timeLabel != null) {
            timeLabel.setText(time);
        }
        if (dateLabel != null) {
            dateLabel.setText(date);
        }
        //</editor-fold>
    }

    private static void timerTick() {
        // <editor-fold defaultstate="collapsed" desc="timerTick">
        if (dateObject == null) {
            setDate();
        }
        cal.add(Calendar.SECOND, 1);
        dateObject = cal.getTime();
        user.durationTick();
        setDateTime();
        if (++garbageCollector >= 1200) {
            garbageCollector = 0;
            System.gc();
        }
        //</editor-fold>
    }

    /**
     * Converts text according to the MD5 algorithm. Char set UTF-8.
     *
     * @param md5 enter normal text.
     * @return string converted into MD5.
     */
    public String MD5(String md5) {
        // <editor-fold defaultstate="collapsed" desc="MD5">
        try {
            md5 = "cyclotech" + md5 + "2017";
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
        //</editor-fold>
    }

    /**
     * Validates a phone number according to the Sri Lankan pattern.
     *
     * @param Number enter the number.
     * @return true if number valid, false otherwise.
     */
    public boolean validatePhoneNumber(String Number) {
        // <editor-fold defaultstate="collapsed" desc="validatePhoneNumber">
        if (Number == null || Number.trim().equals("")) {
            return false;
        }

        if (Number.length() <= 2) {
            return false;
        }

        char number[] = Number.toCharArray();
        int maxLength = 0;

        if (number[0] == '0') {
            if (number[1] == '0') {
                maxLength = 13;
            } else {
                maxLength = 10;
            }
        } else if (number[0] == '+') {
            maxLength = 12;
        } else if (Character.isDigit(number[0])) {
            maxLength = 9;
        } else {
            return false;
        }

        if (number.length != maxLength) {
            return false;
        }

        for (int x = 0; x > number.length; x++) {
            if (x > 0 && !Character.isDigit(number[x])) {
                return false;
            }
        }

        return true;
        //</editor-fold>
    }

    /**
     * Validates an email using javax.mail
     *
     * @param email enter email address.
     * @return true if email valid, false otherwise.
     */
    public boolean validateEmail(String email) {
        // <editor-fold defaultstate="collapsed" desc="validateEmail">
        if (email == null || email.trim().equals("")) {
            return false;
        }
        try {
            new InternetAddress(email).validate();
            return true;
        } catch (Exception ex) {
        }
        return false;
        //</editor-fold>
    }

    /**
     * A simple yet effective algorithm to check password strength. Lower case
     * and Upper Case letters, digits and symbols will be considered. Process
     * time may differ according to the performance of the processor.
     *
     * @param password enter the password.
     * @param minimumLength minimum length of the password. Strength may be high
     * without reaching the minimum length.
     * @return a value as a percentage out of 100%.
     */
    public int passwordStrength(String password, int minimumLength) {
        // <editor-fold defaultstate="collapsed" desc="passwordStrength">
        if (password.length() <= 0) {
            return 0;
        }

        int score = 0;
        boolean lowerCase = false,
                upperCase = false,
                digits = false,
                symbols = false;
        char Pass[] = password.toCharArray();

        for (int x = 0; x < Pass.length; x++) {
            if (Character.isDigit(Pass[x])) {
                digits = true;
            } else if (Character.isLetter(Pass[x])) {
                if (Character.isLowerCase(Pass[x])) {
                    lowerCase = true;
                } else {
                    upperCase = true;
                }
            } else {
                symbols = true;
            }
            if (lowerCase && upperCase && digits && symbols) {
                break;
            }
        }

        if (Pass.length <= minimumLength) {
            int belowMinlength = (int) (((float) Pass.length / minimumLength) * 100);
            score += (((float) belowMinlength / 100) * 60);
        } else if (Pass.length > minimumLength) {
            score += 60;
            int overMinlength = (int) (((float) (Pass.length - minimumLength) / minimumLength) * 100);
            score += (((float) overMinlength / 100) * 15);
        }

        int bonus = 25;
        if (!lowerCase) {
            bonus -= 6;
        }
        if (!upperCase) {
            bonus -= 6;
        }
        if (!digits) {
            bonus -= 6;
        }
        if (!symbols) {
            bonus -= 7;
        }

        score += bonus;
        if (score > 100) {
            score = 100;
        }

        return (score);
        //</editor-fold>
    }

    /**
     * Format numbers by adding zeros to front. 8 max.
     *
     * @param number Number to be formatted.
     * @return the formatted number as a String.
     */
    public String numberFormatter(int number) {
        // <editor-fold defaultstate="collapsed" desc="numberFormatter">
        return String.format("%08d", number);
        //</editor-fold>
    }

    /**
     * Sends the given message to the given number as SMS.
     *
     * @param phoneNo Phone number. Include country code for an international
     * number.
     * @param Message Message to be sent.
     * @return True if sending successful, false otherwise.
     */
    public boolean sendSMS(String phoneNo, String Message) {
        // <editor-fold defaultstate="collapsed" desc="sendSMS">
        boolean status = false;
        int responseCode = -1;
        if (!DB.getIpAddress().equals(Utility.getIpAddress())) {
            status = Speaker.sendSMS(Message, phoneNo);
        } else {
            try {
                System.out.println(phoneNo);
                System.out.println(Message);
                DB.getConnection();
                String urlstr = "http://" + DB.getIpAddress() + ":2586/Default.aspx?phnno=" + URLEncoder.encode(phoneNo.trim(), "UTF-8") + "&message=" + URLEncoder.encode(Message, "UTF-8");
                System.out.println(urlstr);
                URL url = new URL("http://" + DB.getIpAddress() + ":2586/Default.aspx?phnno=" + URLEncoder.encode(phoneNo.trim(), "UTF-8") + "&message=" + URLEncoder.encode(Message, "UTF-8"));
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inLine;
                StringBuffer response = new StringBuffer();
                while ((inLine = in.readLine()) != null) {
                    response.append(inLine);
                }
                in.close();

                if (response.toString().trim().toLowerCase().equals("true")) {
                    status = true;
                }
            } catch (Exception ex) {
                Log.error(ex);
            }
        }

        Log.info((status ? ("Message was sent successfully to ") : ("Message sending failed to ")) + phoneNo + ".",
                "Response Code : " + responseCode);
        return status;
        //</editor-fold>
    }

    /**
     * Email the given message to the given address.
     *
     * @param emailAddress Email Address.
     * @param Header Email Header.
     * @param Message Message to be sent.
     * @return True if sending successful, false otherwise.
     */
    public boolean sendEmail(String emailAddress, String Header, String Message) {
        // <editor-fold defaultstate="collapsed" desc="sendSMS">
        Esender.emailsender(emailAddress, Header, Message);
        return true;
        //</editor-fold>
    }

    /**
     * Splits time into hours, minutes and seconds.
     *
     * @param seconds enter time in seconds.
     * @return String[] 0=hour(s), 1=minute(s), 2=second(s).
     */
    public String[] timeSplitter(long seconds) {
        // <editor-fold defaultstate="collapsed" desc="timeSplitter">
        int h = 0, m = 0;

        if (seconds >= (60 * 60)) {
            h = (int) (seconds / (60 * 60));
            seconds -= (h * 60 * 60);
        }
        if (seconds >= 60) {
            m = (int) (seconds / 60);
            seconds -= (m * 60);
        }

        return new String[]{String.format("%02d", h), String.format("%02d", m), String.format("%02d", seconds)};
        //</editor-fold>
    }

    /**
     * Returns temporary files storage path.
     *
     * @return String path as ...\ProgramData\CycloTech\UltraGaming\
     */
    public String getTempDir() {
        // <editor-fold defaultstate="collapsed" desc="getTempDir">
        try {
            String path = "";
            if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                path = System.getenv("ProgramData") + "\\CycloTech\\UltraGaming\\";
                File f = new File(path);
                f.mkdirs();

                return path;
            }
        } catch (Exception e) {
        }
        return "";
        // </editor-fold>
    }

    /**
     * Returns current machine's IP address.
     *
     * @return IP Address as String.
     */
    public static String getIpAddress() {
        // <editor-fold defaultstate="collapsed" desc="getIpAddress">
        try {
            String ip = Inet4Address.getLocalHost().toString();
            try {
                return ip.split("/")[1];
            } catch (Exception e) {
                return ip;
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        return "0.0.0.0";
        // </editor-fold>
    }

    /**
     * Returns the IP Address for the given machine id.
     *
     * @param MachineId Enter relevant machine id.
     * @return IP Address as String.
     */
    public String getIpAddress(String MachineId) {
        // <editor-fold defaultstate="collapsed" desc="getIpAddress">
        String ip = "";
        try {
            CRUDOperationsImpl db = new CRUDOperationsImpl();
            ResultSet rs = db.search("ipAddress", "machine", "WHERE idMachine='" + MachineId + "'");
            if (rs.first()) {
                ip = rs.getString("ipAddress");
            } else {
                Log.error(new Exception("No ip address found for the machine id " + MachineId + "."));
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return ip;
        // </editor-fold>
    }

    /**
     * Returns the ID of the current machine based on the IP address.
     *
     * @return ID as String.
     */
    public String getMachineId() {
        // <editor-fold defaultstate="collapsed" desc="getMachineId">
        String id = "";
        try {
            CRUDOperationsImpl db = new CRUDOperationsImpl();
            ResultSet rs = db.search("idMachine", "machine", "WHERE ipAddress='" + getIpAddress() + "'");
            if (rs.first()) {
                id = rs.getString("idMachine");
            } else {
                Log.error(new Exception("No machine id found for the ip " + getIpAddress() + "."));
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return id;
        // </editor-fold>
    }

    /**
     * Returns the availability of the current machine based on the information
     * in the database. (Maintenance status).
     *
     * @return true if available, false otherwise.
     */
    public boolean isMachineAvailable() {
        // <editor-fold defaultstate="collapsed" desc="isMachineAvailable">
        try {
            CRUDOperationsImpl db = new CRUDOperationsImpl();
            ResultSet rs = db.search("true as `x`", "machine", "WHERE idMachine='" + getMachineId() + "' AND availability='a'");
            if (rs.first() && rs.getBoolean("x")) {
                return true;
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return false;
        // </editor-fold>
    }

    /**
     * Get Date object for the DateTime from the MYSQL server.
     *
     * @return Date object.
     */
    public static Date getDate() {
        // <editor-fold defaultstate="collapsed" desc="getDate">
        if (dateObject == null) {
            setDate();
        }
        return dateObject;
        //</editor-fold>
    }

    /**
     * Get the charge of a guest, calculated according to a fixed algorithm
     * based on played time, package details and user type.
     *
     * @param TokenId Guest token id.
     * @return double charge price.
     */
    public String calculateGuestCharge(final String TokenId) {
        // <editor-fold defaultstate="collapsed" desc="calculateCharge">
        int packageDuration = 0, duration = 0;
        double packagePrice = 0, charge = 0;

        CRUDOperationsImpl db = new CRUDOperationsImpl();
        try {
            ResultSet rs = db.search("charge, (duration/60000) AS `chargeDuration`, TIMESTAMPDIFF(MINUTE, guest.startTime, IFNULL(guest.endTime, NOW()))AS `totalDuration`",
                    "guest INNER JOIN charge ON guest.charge_idCharge = charge.idCharge",
                    "WHERE guest.token='" + TokenId + "'");
            rs.first();
            packagePrice = rs.getDouble("charge");
            packageDuration = rs.getInt("chargeDuration");
            duration = rs.getInt("totalDuration");
            charge = packagePrice;

            if (packageDuration < duration) {
                long overTime = duration - packageDuration;
                if (overTime > 0) {
                    charge += (packageDuration / packagePrice) * (double) overTime;
                }
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

        return (decimalFormat.format(charge));
        // </editor-fold>
    }

    /**
     * Get the charge of a guest, calculated according to a fixed algorithm
     * based on played time, package details and user type.
     *
     * @param guestId Guest ID.
     * @return double charge price.
     */
    public String calculateGuestChargeFromGuestId(final String guestId) {
        // <editor-fold defaultstate="collapsed" desc="calculateGuestChargeFromGuestId">
        String charge = "0.00";
        try {
            CRUDOperationsImpl db = new CRUDOperationsImpl();
            ResultSet rs = db.search("token", "guest", "WHERE idguest='" + guestId + "'");
            if (rs.first()) {
                return calculateGuestCharge(rs.getString("token"));
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        return charge;
        // </editor-fold>
    }

    /**
     * Get the over time charge of a member, calculated according to a fixed
     * algorithm based on played time, package details and user type.
     *
     * @param idUser Value of idUser from user table.
     * @return double charge price.
     */
    public String calculateOverTimeCharge(final String idUser) {
        // <editor-fold defaultstate="collapsed" desc="calculateOverTimeCharge">
        int packageDuration = 0, totalDuration = 0, remainingDurationDay = 0;//minutes
        double packagePrice = 0, charge = 0;

        CRUDOperationsImpl db = new CRUDOperationsImpl();
        try {
            ResultSet rs;
            rs = db.search("SUM(TIMESTAMPDIFF(MINUTE, startTime, IFNULL(endTime, NOW()))) AS `duration`",
                    "play_session LEFT JOIN play_session_has_overtime_payments ON play_session.play_session_id = play_session_has_overtime_payments.play_session_id",
                    "WHERE user_idUser='" + idUser + "'"
                    + " AND startTime >= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                    + " AND startTime <= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')"
                    + " AND play_session_has_overtime_payments.play_session_id IS NULL");
            if (rs.next()) {
                totalDuration += rs.getInt("duration");
            }
            rs = db.search("durationday", "membership_type",
                    "WHERE idMembership_type"
                    + " IN (SELECT membership_type_idMembership_type FROM user_has_membership_type WHERE user_idUser"
                    + " IN (SELECT DISTINCT user_idUser FROM play_session INNER JOIN play_session_has_overtime_payments ON play_session.play_session_id = play_session_has_overtime_payments.play_session_id"
                    + " WHERE user_idUser='" + idUser + "'"
                    + " AND startTime >= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                    + " AND startTime <= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')))");
            if (rs.first()) {
                totalDuration += rs.getInt("durationday");
            }

            rs = db.search("durationday, (charge.duration/60000)AS `duration`, charge.charge",
                    "membership_type INNER JOIN charge ON charge_idCharge = idCharge",
                    "WHERE idMembership_type IN(SELECT membership_type_idMembership_type FROM user_has_membership_type WHERE user_idUser='" + idUser + "')");
            rs.first();
            packagePrice = rs.getDouble("charge");
            packageDuration = rs.getInt("duration");
            final int durationDay = rs.getInt("durationday");
            remainingDurationDay = totalDuration - durationDay;

            if (remainingDurationDay > 0) {
                charge = (double) remainingDurationDay * (packagePrice / packageDuration);
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

        return (decimalFormat.format(charge));
        // </editor-fold>
    }

    /**
     * Validates NIC number.
     *
     * @param NIC nic number.
     * @return true if according to the pattern, false otherwise.
     */
    public boolean NICValidate(String NIC) {
        // <editor-fold defaultstate="collapsed" desc="NICValidate">
        if (!(NIC.length() == 10 || NIC.length() == 12)) {
            return false;
        }

        int DobSex;
        NIC = NIC.toUpperCase();
        char nicC[] = NIC.toCharArray();

        if (nicC.length == 10) {
            DobSex = Integer.parseInt(NIC.substring(2, 5));
            if (DobSex > 500) {
                DobSex -= 500;
            }
            if (DobSex > 366) {
                return false;
            }

            if (!(nicC[9] == 'V' || nicC[9] == 'X')) {
                return false;
            } else {
                nicC = NIC.substring(0, 9).toCharArray();
                for (char a : nicC) {
                    if (!Character.isDigit(a)) {
                        return false;
                    }
                }
            }
        } else {
            DobSex = Integer.parseInt(NIC.substring(4, 7));
            if (DobSex > 500) {
                DobSex -= 500;
            }
            if (DobSex > 366) {
                return false;
            }

            for (char a : nicC) {
                if (Character.isLetter(a)) {
                    return false;
                }
            }

            Calendar c = Calendar.getInstance();
            c.setTime(getDate());
            c.add(Calendar.YEAR, -16);
            if (Integer.parseInt(NIC.substring(0, 4)) > Integer.parseInt(new SimpleDateFormat("yyyy").format(c.getTime()))) {
                return false;
            }
        }
        return true;
        // </editor-fold>
    }

    /**
     * Sets registry keys to run this application on windows login. ONLY FOR TO
     * BE USED ONLY BY ACTIVATION. THIS MAY HARM YOUR COMPUTER.
     */
    public void setRegistryKeys() throws Exception {
        // <editor-fold defaultstate="collapsed" desc="setRegistryKeys">
        Runtime.getRuntime().exec("cmd /c reg add \"HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Winlogon\" /t REG_SZ /v Userinit /d \"" + getPath() + "\" /f");
        Log.info("Windows registry key/value set/changed.",
                "KEY : HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\Winlogon -> Userinit",
                "VALUE : " + getPath());
        //</editor-fold>
    }

    private String getPath() throws Exception {
        // <editor-fold defaultstate="collapsed" desc="getPath">
        return System.getProperty("user.dir") + "\\ugc.exe";
        //</editor-fold>
    }

    /**
     * Displays a windows notification containing the given message.
     *
     * @param title Message title
     * @param message Message.
     * @param messageType Message type.
     */
    public void displayTrayNotification(String title, String message, MessageType messageType) {
        // <editor-fold defaultstate="collapsed" desc="displayTrayNotification">
        try {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage("logoSMBlack32.png");
            TrayIcon trayIcon = new TrayIcon(image);
            //Let the system resizes the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("Ultra Gaming Center");
            tray.add(trayIcon);
            trayIcon.displayMessage(title, message, messageType);

        } catch (Exception ex) {
            Log.error(ex);
        }
        //</editor-fold>
    }
}
