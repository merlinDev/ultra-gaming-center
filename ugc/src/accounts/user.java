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
package accounts;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.cyclotech.repository.DB;
import connection.Command;
import connection.Speaker;
import gamefx.GameCenter;
import gamefx.Log;
import gamefx.Utility;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * User version 1.17.09.11.
 *
 * @author Roshana Pitigala
 */
public class user {

    private user() {
    }

    private static long durationSeconds = 0, remainingTime = 0;
    private static String idUser = "11", userType = null, machineId = null, playSessionId = null, loggedinDateTime = null;
    private static int userTypeId = 2, mediaPlayerPlayingTime = 0;
    private static final MediaPlayer mediaPlayer = new MediaPlayer(new Media(new user().getClass().getResource("/res/UGCwarn.wav").toString()));

    /**
     * User types as IDs.
     */
    public static final int MEMBER = 2, GUEST = 3, ADMINISTRATOR = 1;

    /**
     * Returns the type name from the database according to the given type id.
     *
     * @return the type name as String.
     * @deprecated Use getUserTypeId() to check the user type.
     */
    @Deprecated
    public static String getUserType() {
        // <editor-fold defaultstate="collapsed" desc="getUserType">
        return userType;
        //</editor-fold>
    }

    /**
     * Returns the user type as an Integer.
     *
     * @return user type id.
     */
    public static int getUserTypeId() {
        // <editor-fold defaultstate="collapsed" desc="getUserTypeId">
        return userTypeId;
        //</editor-fold>
    }

    /**
     * Sets user type related to user type id. Only for Login system.
     *
     * @param userTypeId User type id.
     * @return true if success.
     */
    public static boolean setUserType(int userTypeId) {
        // <editor-fold defaultstate="collapsed" desc="setUserType">
        user.userTypeId = userTypeId;

        CRUDOperationsImpl db = new CRUDOperationsImpl();
        ResultSet rs;
        try {
            rs = db.search("typeName", "usertype", "WHERE idUserType='" + userTypeId + "'");
            if (rs.first()) {
                userType = rs.getString("typeName");
            }
        } catch (Exception ex) {
            Log.error(ex);
            return false;
        }
        return true;
        //</editor-fold>
    }

    /**
     * Returns User Id.
     *
     * @return
     */
    public static String getIdUser() {
        // <editor-fold defaultstate="collapsed" desc="getIdUser">
        return idUser;
        //</editor-fold>
    }

    /**
     * Sets User ID. Only for Login and Logout systems.
     *
     * @param idUser User Id
     */
    public static void setIdUser(String idUser) {
        // <editor-fold defaultstate="collapsed" desc="setUserType">
        user.idUser = idUser;
        //</editor-fold>
    }

    /**
     * Call this on logout.
     *
     * @return true if logout process successful, false otherwise.
     */
    public static boolean logout() {
        // <editor-fold defaultstate="collapsed" desc="logout">
        return logout(true, false);
        // </editor-fold>
    }

    /**
     * Call this to prevent application exit and logout. USE WITH CAUTION!!!
     *
     * @param closeUGC If it's okay to exit UGC application.
     * @param remoteCall whether the call has been triggered remotely.
     * @return true if logout process successful, false otherwise.
     */
    public static boolean logout(boolean closeUGC, boolean remoteCall) {
        // <editor-fold defaultstate="collapsed" desc="logout">
        try {
            String time[] = new Utility().timeSplitter(user.getDuration());
            Log.info(user.getUserType() + " is logging out...",
                    "Duration : " + time[0] + "hour(s) " + time[1] + "minute(s) " + time[2] + "second(s)");
        } catch (Exception e) {
            Log.error(e);
        }
        try {
            CRUDOperationsImpl db = new CRUDOperationsImpl();
            db.update("guest", "endtime='" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Utility.getDate()) + "'", "idguest='" + getIdUser() + "'");
            db.update("machine", "inUse='available'", "idMachine='" + getMachineId() + "'");
            if (getUserTypeId() == MEMBER) {
                db.update("play_session",
                        "endTime='" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(Utility.getDate()) + "'",
                        "play_session_id='" + getUserPlaySessionId() + "'");
            }

            boolean sysLogout = false;
            if (getUserTypeId() == ADMINISTRATOR) {
                ResultSet rs = db.search("IF((type='member' OR type='guest'), true, false)AS `x`", "machine", "WHERE ipAddress = '" + new Utility().getIpAddress() + "'");
                if (rs.next()) {
                    sysLogout = rs.getBoolean("x");
                }
            } else if (!remoteCall) {
                Speaker.send(DB.getIpAddress(), Command.USER_SESSION_DATA_LOGOUT + ";" + user.getIdUser());
            }
            idUser = null;
            userType = null;
            userTypeId = 0;
            resetDuration();
            machineId = null;
            playSessionId = null;
            loggedinDateTime = null;
            remainingTime = 0;
            mediaPlayerPlayingTime = 0;
            mediaPlayer.stop();
            if (sysLogout && closeUGC) {
                Runtime.getRuntime().exec("cmd /c shutdown -l -f");
                System.exit(0);
            }

            GameCenter.changeStage("accounts/login.fxml", true, true);
        } catch (Exception ex) {
            Log.error(ex);
        }
        return false;
        //</editor-fold>
    }

    /**
     * Increments the logged in duration by one second. Only to be used by the
     * automated timer in Utility class.
     */
    public static void durationTick() {
        // <editor-fold defaultstate="collapsed" desc="durationTick">
        if (idUser != null) {
            durationSeconds++;
            if (remainingTime > 0) {
                remainingTime--;
            }
            if (remainingTime == 10) {
                playWarning();
            }
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING && ++mediaPlayerPlayingTime >= 12) {
                mediaPlayerPlayingTime = 0;
                mediaPlayer.stop();
            }
        }
        //</editor-fold>
    }

    /**
     * Get duration in seconds since login.
     *
     * @return Long - seconds
     */
    public static long getDuration() {
        // <editor-fold defaultstate="collapsed" desc="getDuration">
        return durationSeconds;
        // </editor-fold>
    }

    /**
     * Get Machine ID which the user is in.
     *
     * @return Machine id as String.
     * @deprecated getMachineId() in the Utility class is more reliable and
     * accurate.
     */
    @Deprecated
    public static String getMachineId() {
        // <editor-fold defaultstate="collapsed" desc="getMachineId">
        return machineId;
        // </editor-fold>
    }

    /**
     * set Machine ID which the user is in. Only to be used by Login.
     *
     * @param machineId Enter idMachine from `machine` table.
     */
    public static void setMachineId(String machineId) {
        // <editor-fold defaultstate="collapsed" desc="setMachineId">
        user.machineId = machineId;
        // </editor-fold>
    }

    /**
     * set Play Session ID for members and admins. Only to be used by Login.
     *
     * @param playSessionId Enter play_session_id from `play_session` table.
     */
    public static void setUserPlaySessionId(String playSessionId) {
        // <editor-fold defaultstate="collapsed" desc="setUserPlaySessionId">
        user.playSessionId = playSessionId;
        // </editor-fold>
    }

    /**
     * Get Play Session ID for members and admins. Only to be used by Login.
     *
     * @return play_session_id as String.
     */
    public static String getUserPlaySessionId() {
        // <editor-fold defaultstate="collapsed" desc="getUserPlaySessionId">
        return playSessionId;
        // </editor-fold>
    }

    /**
     * Sets the user duration to 0. Only to be used by the Login system.
     */
    public static void resetDuration() {
        // <editor-fold defaultstate="collapsed" desc="resetDuration">
        durationSeconds = 0;
        //</editor-fold>
    }

    /**
     * Sets remaining time of user. Only used by the login, for the warning
     * alarm.
     *
     * @param remainingTime remaining time in seconds.
     */
    public static void setRemainingTime(long remainingTime) {
        // <editor-fold defaultstate="collapsed" desc="setRemainingTime">
        user.remainingTime = remainingTime;
        // </editor-fold>
    }

    /**
     * Get remaining time of user.
     *
     * @return remaining time in seconds.
     */
    public static long getRemainingTime() {
        // <editor-fold defaultstate="collapsed" desc="getRemainingTime">
        return remainingTime;
        // </editor-fold>
    }

    /**
     * Play warning sound.
     */
    public static void playWarning() {
        // <editor-fold defaultstate="collapsed" desc="playWarning">
        try {
            mediaPlayer.setVolume(100d);
            mediaPlayer.play();
            Log.info("Warning sound played.");
        } catch (Exception ex) {
            Log.error(ex);
        }
        //</editor-fold>
    }

    /**
     * Get logged in date time in SQL format.
     *
     * @return date time as String.
     */
    public static String getLoggedInDateTime() {
        // <editor-fold defaultstate="collapsed" desc="getLoggedInDateTime">
        return loggedinDateTime;
        //</editor-fold>
    }

    /**
     * Set logged in date time in SQL format.
     *
     * @param loginDateTime date time as string.
     */
    public static void setLoggedInDateTime(String loginDateTime) {
        // <editor-fold defaultstate="collapsed" desc="setLoggedInDateTime">
        user.loggedinDateTime = loginDateTime;
        //</editor-fold>
    }

    /**
     * Get current user's full package time.
     *
     * @return package time as long.
     */
    public static long getPackageTime() {
        // <editor-fold defaultstate="collapsed" desc="getPackageTime">
        CRUDOperationsImpl db = new CRUDOperationsImpl();
        ResultSet rs;
        try {
            if (getUserTypeId() == MEMBER) {
                rs = db.search("durationday", "membership_type", "WHERE idMembership_type IN(SELECT membership_type_idMembership_type FROM user_has_membership_type WHERE user_idUser='" + getIdUser() + "')");
                rs.next();
                return Long.parseLong(rs.getString("durationday"));
            } else if (getUserTypeId() == GUEST) {
                rs = db.search("(duration/1000)AS `duration`", "charge", "WHERE idCharge IN(SELECT charge_idCharge FROM guest WHERE idguest='" + getIdUser() + "')");
                rs.next();
                return Long.parseLong(rs.getString("duration"));
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

        return 0;
        //</editor-fold>
    }

    /**
     * Returns a sum of the user's logged in duration of all valid sessions in
     * seconds.
     * <br/><br/>
     * If the user is a member, sum of all play session durations of today
     * without overtime paid sessions. If the user has paid overtime, the
     * duration for the day will be returned.<br/>
     * <br/>If the user is a guest, sum of all unbilled session durations of
     * today.
     * <br/><br/>If the user is an admin, returns 0 always.
     *
     * @return duration as seconds.
     */
    public static long getFullDuration() {
        // <editor-fold defaultstate="collapsed" desc="getFullDuration">
        CRUDOperationsImpl db = new CRUDOperationsImpl();
        ResultSet rs;
        try {
            if (getUserTypeId() == MEMBER) {
                long time = 0;
                rs = db.search("SUM(TIMESTAMPDIFF(SECOND, startTime, IFNULL(endTime, NOW()))) AS `duration`",
                        "play_session LEFT JOIN play_session_has_overtime_payments ON play_session.play_session_id = play_session_has_overtime_payments.play_session_id",
                        "WHERE user_idUser='" + getIdUser() + "'"
                        + " AND startTime >= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                        + " AND startTime <= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')"
                        + " AND play_session_has_overtime_payments.play_session_id IS NULL");
                if (rs.next()) {
                    time += rs.getLong("duration");
                }
                rs = db.search("(durationday * 60) AS `durationday`", "membership_type",
                        "WHERE idMembership_type"
                        + " IN (SELECT membership_type_idMembership_type FROM user_has_membership_type WHERE user_idUser"
                        + " IN (SELECT DISTINCT user_idUser FROM play_session INNER JOIN play_session_has_overtime_payments ON play_session.play_session_id = play_session_has_overtime_payments.play_session_id"
                        + " WHERE user_idUser='" + getIdUser() + "'"
                        + " AND startTime >= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                        + " AND startTime <= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')))");
                if (rs.first()) {
                    time += rs.getLong("durationday");
                }
                return time;
            } else if (getUserTypeId() == GUEST) {
                rs = db.search("TIMESTAMPDIFF(SECOND, guest.startTime, NOW()) AS `duration`",
                        "guest",
                        "WHERE idguest='" + getIdUser() + "'"
                        + " AND guest.startTime >= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                        + " AND guest.startTime <= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')");
                rs.next();
                return rs.getLong("duration");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

        return 0;
        //</editor-fold>
    }
}
