/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts;

import gamefx.Utility;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.cyclotech.repository.DB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import connection.Command;
import connection.Speaker;
import gamefx.GameCenter;
import gamefx.Log;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import gamefx.MessageBox;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Random;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Roshana Pitigala
 */
public class LoginController implements Initializable {

    private ResultSet rs;
    private CRUDOperationsImpl db;
    private Boolean login = false, isSqlModeChanged = false;
    private String Id, forgotPassCode, sqlModes;
    private int FPRCattempts = 3, loginAttempts = 2;
    private final String[] alpha = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private final Random codeRandomizer = new Random();
    private final Utility util = new Utility();
    private final SimpleDateFormat dbDateTimeFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

    @FXML
    private Button nextBtn;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private Label label;
    @FXML
    private Hyperlink forgotPass;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane loginStage;
    @FXML
    private AnchorPane forgotPassPane;
    @FXML
    private JFXRadioButton emailRBtn;
    @FXML
    private ToggleGroup sd;
    @FXML
    private JFXRadioButton smsRBtn;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField phone;
    @FXML
    private Hyperlink notYou;
    @FXML
    private Button forgotPassNextBtn;
    @FXML
    private Label forgotPassLabel;
    @FXML
    private Button RCcontinueBtn;
    @FXML
    private AnchorPane resetCodePane;
    @FXML
    private JFXTextField resetCode;
    @FXML
    private JFXPasswordField newPassword;
    @FXML
    private JFXPasswordField confirmPassword;
    @FXML
    private Label strength;
    @FXML
    private AnchorPane changePasswordPane;
    @FXML
    private ImageView background;
    @FXML
    public Label time;
    @FXML
    public Label date;
    @FXML
    private Label subTitle;
    @FXML
    private Label title;

    private final ChangeListener<Boolean> emailFocusLost = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue) {
                email.setText(email.getText().toLowerCase());
                if (!util.validateEmail(email.getText())) {
                    email.setStyle("-fx-text-fill: #ff9999;");
                } else {
                    email.setStyle("-fx-text-fill: #ffffff;");
                }
            }
        }
    },
            phoneFocusLost = new ChangeListener<Boolean>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (!newValue && phone.getText().length() > 0 && !util.validatePhoneNumber(phone.getText())) {
                phone.setStyle("-fx-text-fill: #ff9999;");
            } else {
                phone.setStyle("-fx-text-fill: #ffffff;");
            }
        }
    };
    @FXML
    private JFXButton powerBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Utility.setDateTime(date, time);
        db = new CRUDOperationsImpl();
        login = false;
        Id = null;
        smsRBtn.setSelectedColor(Color.WHITE);
        smsRBtn.setUnSelectedColor(Color.GREY);
        emailRBtn.setSelectedColor(Color.WHITE);
        emailRBtn.setUnSelectedColor(Color.GREY);
        email.focusedProperty().addListener(emailFocusLost);
        phone.focusedProperty().addListener(phoneFocusLost);
        int screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
        loginStage.setLayoutX((screenWidth - 600) / 2f);
        background.setPreserveRatio(false);
        background.setFitWidth(screenWidth);
        setFont();
        loginStage.setStyle("-fx-background-image: url('/res/glass.png');"
                + " -fx-background-repeat: stretch;"
                + " -fx-background-size: 600 362;"
                + " -fx-background-position: center center;");

        showPane(loginPane);
    }

    private void setFont() {
        title.setFont(Font.loadFont(getClass().getResource("/res/SF-UI-Display-Thin.ttf").toString(), 43));
        subTitle.setFont(Font.loadFont(getClass().getResource("/res/SF-UI-Display-Thin.ttf").toString(), 29));
    }

    @FXML
    private void emailRBtnSelected(ActionEvent event) {
        email.setDisable(false);
        phone.setDisable(true);
    }

    @FXML
    private void smsRBtnSelected(ActionEvent event) {
        phone.setDisable(false);
        email.setDisable(true);
    }

    @FXML
    private void OnMouseExit(MouseEvent event) {
        nextBtn.setStyle("-fx-background-color: #0cb754;");
        nextBtn.setStyle("-fx-background-color: #0cb754;");
    }

    @FXML
    private void OnMouseEnter(MouseEvent event) {
        nextBtn.setStyle("-fx-background-color: #09873e;");
        nextBtn.setStyle("-fx-background-color: #09873e;");
    }

    @FXML
    private void loginActionPerformed(ActionEvent event) {
        if (!login) {
            if (id.getText().equals("")) {
                id.setStyle("-fx-prompt-text-fill: #FF6E6E; -fx-text-fill: #FFFFFF;");
                return;
            } else {
                id.setStyle("-fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");
            }
        } else if (pass.getText().equals("")) {
            pass.setStyle("-fx-prompt-text-fill: #FF6E6E; -fx-text-fill: #FFFFFF;");
            return;
        } else {
            pass.setStyle("-fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");
        }
        try {
            if (!login) {
                Id = null;
                rs = db.search("idguest, startTime, endTime, machine_idMachine",
                        "guest LEFT JOIN invoice ON guest.idguest = invoice.guest_idguest",
                        "WHERE token='" + id.getText() + "'"
                        + " AND issuedDateTime >= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                        + " AND issuedDateTime <= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')"
                        + " AND invoice.guest_idguest IS NULL");
                if (rs.first()) {
                    Id = rs.getString("idguest");
                    boolean started = (rs.getString("startTime") != null),
                            ended = (rs.getString("endTime") != null);
                    String machine = rs.getString("machine_idMachine"),
                            Started = (started) ? ("'" + rs.getString("startTime") + "'") : ("NULL");

                    rs = db.search("idMachine", "machine", "WHERE ipAddress='" + util.getIpAddress() + "' AND type != 'member'");
                    if (rs.first()) {
                        if (!ended && started && !machine.equals(rs.getString("idMachine"))) {
                            MessageBox.showErrorMessage("Duplicate Sessions", "This token id is being used in another computer. Logout that session first.");
                        } else if (!util.isMachineAvailable()) {
                            MessageBox.showErrorMessage("System is under maintenance.",
                                    "Either this computer is down for maintainance, or haven't registered yet. Please find your self another computer. Sorry for the inconvenience caused.");
                            GameCenter.changeStage("accounts/login.fxml", true, true);
                        } else {
                            user.setIdUser(Id);
                            user.setUserType(user.GUEST);
                            user.setMachineId(rs.getString("idMachine"));

                            rs = db.search("(duration/1000)AS `totalSeconds`",
                                    "charge", "WHERE idCharge IN(SELECT charge_idCharge FROM guest WHERE idguest='" + user.getIdUser() + "')");
                            if (rs.first()) {
                                user.setRemainingTime(rs.getLong("totalSeconds") - user.getFullDuration());
                            }

                            if (!started) {
                                db.update("guest", "startTime = NOW()", "idguest = '" + Id + "'");
                            }
                            db.update("guest", "endTime=NULL, machine_idMachine='" + user.getMachineId() + "'", "idguest='" + user.getIdUser() + "'");
                            db.update("machine", "inUse='inuse'", "idMachine='" + user.getMachineId() + "'");
                            user.setLoggedInDateTime(dbDateTimeFormat.format(Utility.getDate()));
                            Speaker.send(DB.getIpAddress(), Command.USER_SESSION_DATA_LOGIN + ";"
                                    + Command.encodeUserSessionData(user.getMachineId(),
                                            user.getUserType(),
                                            user.getIdUser(),
                                            user.getLoggedInDateTime(),
                                            user.getPackageTime(),
                                            user.getFullDuration()));

                            //Login Complete as GUEST
                            Log.info(user.getUserType() + " logged in.");
                            GameCenter.changeStage("gamer/Gamer.fxml", true, true);
                            //*****
                        }
                    } else {
                        MessageBox.showErrorMessage("Insufficient Privileges!", "This computer is for the members. Please find yourself a comfortable place in the Guests Zone.");
                    }

                } else {
                    Id = id.getText();
                    rs = db.search("idUser", "user", "WHERE nic='" + Id + "' OR username='" + Id + "' OR email='" + Id + "'");
                    if (rs.first()) {
                        Id = rs.getString("idUser");
                        rs = db.search("status", "user", "WHERE idUser='" + Id + "'");
                        rs.first();
                        if (rs.getString("status").equals("active")) {
                            id.setVisible(false);
                            pass.setVisible(true);
                            forgotPass.setVisible(true);
                            label.setText("Enter your password");
                            nextBtn.setText("Login");
                            login = true;
                        } else if (rs.getString("status").equals("inactive")) {
                            MessageBox.showErrorMessage("Account Deactivated", "Your account has been deactivated. Please contact the administrator.");
                        } else {
                            MessageBox.showErrorMessage("Account Blocked", "Your account has been blocked temporarily. Please contact the administrator.");
                        }
                    } else {
                        MessageBox.showErrorMessage("Invalid ID", "Invalid Token ID or Username.");
                    }
                }
                id.setText("");
            } else {
                rs = db.search("name, userType_idUserType", "user", "WHERE pass='" + util.MD5(pass.getText()) + "' AND idUser='" + Id + "'");
                if (rs.first()) {
                    user.setIdUser(Id);
                    user.setUserType(rs.getInt("userType_idUserType"));

                    if (user.getUserTypeId() == user.ADMINISTRATOR) {
                        if (!isExplorerRunning()) {
                            Runtime.getRuntime().exec("userinit");
                        }
                        user.setLoggedInDateTime(dbDateTimeFormat.format(Utility.getDate()));
                        db.update("machine", "inuse = 'inuse'", "ipAddress='" + Utility.getIpAddress() + "'");
                        //Login Complete as ADMIN
                        Log.info(user.getUserType() + " logged in.");
                        gamefx.GameCenter.changeStage("finalsoftwareproject/AdminHome.fxml", true, true);
                        //*****

                    } else if (!util.isMachineAvailable()) {
                        MessageBox.showErrorMessage("System is under maintenance.",
                                "Either this computer is down for maintainance, or haven't registered yet. Please find your self another computer. Sorry for the inconvenience caused.");
                        GameCenter.changeStage("accounts/login.fxml", true, true);
                    } else {
                        rs = db.search("(TIMESTAMPDIFF(MONTH, member_date, NOW()) < duration_month)AS `membership`",
                                "user_has_membership_type JOIN membership_type ON idMembership_type = membership_type_idMembership_type",
                                "WHERE user_iduser='" + user.getIdUser() + "'");
                        if (!(rs.next() && rs.getBoolean("membership"))) {
                            MessageBox.showErrorMessage("Invalid Membership", "Your membership has either expired or invalid. Please contact the administrator.");
                            user.logout();
                            return;
                        }

                        user.setMachineId(util.getMachineId());
                        rs = db.search("play_session_id", "play_session", "WHERE endTime IS NULL");
                        while (rs.next()) {
                            db.update("play_session", "endTime = NOW()",
                                    "play_session_id='" + rs.getString(1) + "'");
                        }

                        //setSqlMode(true);
                        int totalMins = (int) (user.getFullDuration() / 60);
                        rs = db.search("durationday,"
                                + " (charge.duration / 60000) AS `extraMin`,"
                                + " charge.charge",
                                "user_has_membership_type"
                                + " INNER JOIN membership_type ON user_has_membership_type.membership_type_idMembership_type = membership_type.idMembership_type"
                                + " INNER JOIN charge ON membership_type.charge_idCharge = charge.idCharge",
                                "WHERE user_idUser = '" + user.getIdUser() + "'");
                        //setSqlMode(false);
                        if (rs.first()) {
                            String per = (rs.getInt("extraMin") >= 60) ? ("hour") : ("minute");
                            if (totalMins > 0 && totalMins >= rs.getInt("durationday")) {
                                if (MessageBox.showConfirmationMessage("Daily time limit reached.",
                                        "The allocated time limit for today has been reached. You will have to pay LKR " + rs.getInt("charge") + " per " + per + " from now on.\nDo you wish to continue?",
                                        new String[]{MessageBox.YES_BUTTON, MessageBox.NO_BUTTON}).equals(MessageBox.NO_BUTTON)) {
                                    user.logout();
                                    GameCenter.changeStage("accounts/login.fxml", true, true);
                                    return;
                                }
                            } else if (totalMins > 0 && totalMins >= (rs.getInt("durationday") - 20)) {
                                MessageBox.showWarningMessage("Daily time limit is about to reach.",
                                        "The allocated time limit for today will be reached in another " + (rs.getInt("durationday") - totalMins) + "minute(s). You will have to pay LKR " + rs.getInt("charge") + " per " + per + " after that.");
                            }
                        }

                        user.setRemainingTime((rs.getLong("durationDay") * 60) - user.getFullDuration());

                        db.save("play_session (startTime, machine_idMachine, user_IdUser)",
                                "NOW(),"
                                + " '" + user.getMachineId() + "',"
                                + " '" + user.getIdUser() + "'");
                        user.setUserPlaySessionId(db.getLastId("play_session_id", "play_session"));
                        user.setLoggedInDateTime(dbDateTimeFormat.format(Utility.getDate()));
                        db.update("machine", "inuse = 'inuse'", "ipAddress='" + Utility.getIpAddress() + "'");
                        Speaker.send(DB.getIpAddress(), Command.USER_SESSION_DATA_LOGIN + ";"
                                + Command.encodeUserSessionData(user.getMachineId(),
                                        user.getUserType(),
                                        user.getIdUser(),
                                        user.getLoggedInDateTime(),
                                        user.getPackageTime() * 60,
                                        (user.getFullDuration())));

                        //Login Complete as MEMBER
                        Log.info(user.getUserType() + " logged in.", "Play Session Id : " + user.getUserPlaySessionId());
                        GameCenter.changeStage("gamer/Gamer.fxml", true, true);
                        //*****
                    }
                } else if ((loginAttempts--) <= 0) {
                    id.setText("");
                    id.setVisible(true);
                    pass.setVisible(false);
                    forgotPass.setVisible(false);
                    label.setText("Enter your Token ID, Username, NIC or Email");
                    nextBtn.setText("Next");
                    login = false;
                    MessageBox.showErrorMessage("Incorrect Password", "Please enter your correct username and try again.");
                    id.requestFocus();

                } else {
                    MessageBox.showErrorMessage("Incorrect Password", "Please try again.");
                }
                pass.setText("");
            }
            user.setMachineId(util.getMachineId());
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void forgotPassActionPerformed(ActionEvent event) {
        try {
            rs = db.search("name, email, phone", "gamecenter.user", "WHERE idUser='" + Id + "'");
            if (rs.next()) {
                boolean emailAvailable = true, phoneAvailable = true;
                if (!util.validateEmail(rs.getString("email"))) {
                    emailAvailable = false;
                }
                if (!util.validatePhoneNumber(rs.getString("phone"))) {
                    phoneAvailable = false;
                }

                if (!emailAvailable && !phoneAvailable) {
                    MessageBox.showErrorMessage("Password reset failed", "There is no sufficient information on this account to reset the password. Please contact the system administrator.");
                    return;
                } else if (!emailAvailable) {
                    smsRBtn.setDisable(false);
                    smsRBtn.setSelected(true);
                    emailRBtn.setDisable(true);
                    smsRBtnSelected(null);
                } else if (!phoneAvailable) {
                    emailRBtn.setDisable(false);
                    emailRBtn.setSelected(true);
                    smsRBtn.setDisable(true);
                    emailRBtnSelected(null);
                }

                forgotPassLabel.setText(rs.getString("name") + ", how would you like to reset your password?");
                showPane(forgotPassPane);
            } else {
                MessageBox.showErrorMessage("Invalid User ID", "No valid User ID found. Please try again.");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void phoneKeyReleased(KeyEvent event) {
        if (!util.validatePhoneNumber(phone.getText())) {
            phone.setStyle("-fx-text-fill: #ff9999;");
        } else {
            phone.setStyle("-fx-text-fill: #ffffff;");
        }
    }

    @FXML
    private void phoneTyped(KeyEvent event) {
        if (phone.getText().length() > 0 && !Character.isDigit(event.getCharacter().toCharArray()[0]) || phone.getText().length() >= 13) {
            event.consume();
        }
    }

    @FXML
    private void notYouActionPerformed(ActionEvent event) {
        forgotPassCode = null;
        id.setVisible(true);
        pass.setVisible(false);
        forgotPass.setVisible(false);
        label.setText("Enter your Token ID, Username, NIC or Email");
        nextBtn.setText("Next");
        login = false;
        showPane(loginPane);
    }

    @FXML
    private void forgotPassNextActionPerformed(ActionEvent event) {
        //Validation
        if (emailRBtn.isSelected() && !util.validateEmail(email.getText())) {
            emailFocusLost.changed(null, Boolean.FALSE, Boolean.FALSE);
            MessageBox.showErrorMessage("Invalid email address", "Please enter your valid email address.");
            return;
        } else if (smsRBtn.isSelected() && !util.validatePhoneNumber(phone.getText())) {
            phoneFocusLost.changed(null, Boolean.FALSE, Boolean.FALSE);
            MessageBox.showErrorMessage("Invalid phone number", "Please enter your valid phone number.");
            return;
        }
        //end Validation

        try {
            rs = db.search("email, phone", "gamecenter.user", "WHERE idUser='" + Id + "'");
            if (!rs.first()) {
                MessageBox.showErrorMessage("Invalid information", "Insufficient information on this account. Please contact the system administrator to reset your password.");
            }
            if (emailRBtn.isSelected()) {
                if (rs.getString("email").toLowerCase().equals(email.getText().toLowerCase())) {
                    forgotPassContinue(1);
                } else {
                    MessageBox.showErrorMessage("Invalid email address", "Incorrect email. Please re-enter your email address to validate.");
                }
            } else if (smsRBtn.isSelected()) {
                if (rs.getString("phone").equals(phone.getText())) {
                    forgotPassContinue(2);
                } else {
                    MessageBox.showErrorMessage("Invalid phone number", "Incorrect phone number. Please re-enter your phone number to validate.");

                }
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void showPane(AnchorPane paneName) {
        if (paneName == loginPane) {
            id.setText("");
            pass.setText("");
            loginPane.setVisible(true);
            forgotPassPane.setVisible(false);
            resetCodePane.setVisible(false);
            changePasswordPane.setVisible(false);
        } else if (paneName == forgotPassPane) {
            email.setText("");
            phone.setText("");
            forgotPassPane.setVisible(true);
            loginPane.setVisible(false);
            resetCodePane.setVisible(false);
            changePasswordPane.setVisible(false);
        } else if (paneName == resetCodePane) {
            resetCode.setText("");
            resetCodePane.setVisible(true);
            loginPane.setVisible(false);
            forgotPassPane.setVisible(false);
            changePasswordPane.setVisible(false);
        } else if (paneName == changePasswordPane) {
            newPassword.setText("");
            confirmPassword.setText("");
            changePasswordPane.setVisible(true);
            resetCodePane.setVisible(false);
            loginPane.setVisible(false);
            forgotPassPane.setVisible(false);
        }
    }

    private String genarateCode() {
        String code[], genaratedCode = "";
        int strings, finalStrings, position;

        code = new String[8];
        strings = codeRandomizer.nextInt(6);
        while (strings < 3) {
            strings = codeRandomizer.nextInt(6);
        }
        finalStrings = strings;

        while (strings > 0) {
            position = codeRandomizer.nextInt(code.length);
            while (code[position] != null) {
                position = codeRandomizer.nextInt(code.length);
            }
            code[position] = alpha[codeRandomizer.nextInt(alpha.length)].toUpperCase();
            strings--;
        }

        for (int numbers = (code.length - finalStrings); numbers > 0; numbers--) {
            position = codeRandomizer.nextInt(code.length);
            while (code[position] != null) {
                position = codeRandomizer.nextInt(code.length);
            }
            code[position] = Integer.toString(codeRandomizer.nextInt(10));
        }

        for (position = 0; position < code.length; position++) {
            if (code[position] == null) {
                code[position] = Integer.toString(codeRandomizer.nextInt(10));
            }
        }

        for (String x : code) {
            genaratedCode += x;
        }

        return genaratedCode;
    }

    private void forgotPassContinue(int type) {
        String tempFPC = genarateCode();
        forgotPassCode = util.MD5(tempFPC);
        tempFPC = "Your UGC forgot password reset code is " + tempFPC + ".";
        FPRCattempts = 3;
        if (type == 1) {
            //Email FPC
            boolean failed = true;
            if (util.sendEmail(email.getText().trim(), "UGC Forgot Password Reset Code", tempFPC)) {
                failed = false;
            }

            if (failed) {
                MessageBox.showErrorMessage("Sending Failed", "Failed to send email to the given address. Please try again.");
                forgotPassCode = null;
                return;
            }
        } else if (type == 2) {
            //SMS FPC
            boolean failed = true;
            if (util.sendSMS(phone.getText().trim(), tempFPC)) {
                failed = false;
            }
            if (failed) {
                MessageBox.showErrorMessage("Sending Failed", "Failed to send SMS to the given number. Please try again.");
                forgotPassCode = null;
                return;
            }
        }
        showPane(resetCodePane);
    }

    @FXML
    private void RCcontinueActionPerformed(ActionEvent event) {
        if (FPRCattempts > 0 && forgotPassCode.equals(util.MD5(resetCode.getText()))) {
            showPane(changePasswordPane);
        } else {
            MessageBox.showErrorMessage("Incorrect Reset Code", "Incorrect forgot password reset code.\n"
                    + ((--FPRCattempts) > 0 ? FPRCattempts + " attempt(s) left." : "Failed to reset the password."));
            if (FPRCattempts <= 0) {
                forgotPassCode = null;
                id.setVisible(true);
                pass.setVisible(false);
                forgotPass.setVisible(false);
                label.setText("Enter your Token ID, Username, NIC or Email");
                nextBtn.setText("Next");
                login = false;
                loginAttempts = 2;
                showPane(loginPane);

            }
        }
        resetCode.setText("");
    }

    @FXML
    private void resetCodeTyped(KeyEvent event) {
        if (!Character.isLetterOrDigit(event.getCharacter().toCharArray()[0])) {
            event.consume();
        }
    }

    @FXML
    private void newPasswordActionPerformed(ActionEvent event) {
        confirmPassword.requestFocus();
    }

    @FXML
    private void newPasswordKeyReleased(KeyEvent event) {
        if (newPassword.getText().length() > 0) {
            int passStrength = util.passwordStrength(newPassword.getText(), 8);
            if (passStrength >= 85) {
                strength.setStyle("-fx-text-fill: #80FF5B");
                strength.setText("STRONG");
            } else if (passStrength >= 60) {
                strength.setStyle("-fx-text-fill: #FFE647");
                strength.setText("MEDIUM");
            } else if (passStrength >= 20) {
                strength.setStyle("-fx-text-fill: #FF6E6E");
                strength.setText("WEAK");
            } else {
                strength.setStyle("-fx-text-fill: #FF5050");
                strength.setText("TOO WEAK");
            }
            strength.setVisible(true);
        } else {
            strength.setVisible(false);
        }
        confirmPasswordKeyReleased(null);
    }

    @FXML
    private void changePasswordActionPerformed(ActionEvent event) {
        if (newPassword.getText().length() < 8) {
            MessageBox.showErrorMessage("Password too weak", "Password must contain atleast 8 characters.");
        } else if (!newPassword.getText().equals(confirmPassword.getText())) {
            confirmPasswordKeyReleased(null);
            MessageBox.showErrorMessage("Passwords mismatch", "The passwords you entered does not match.");
        } else {
            try {
                db.update("gamecenter.user", "pass='" + util.MD5(newPassword.getText()) + "'", "idUser='" + Id + "'");
                MessageBox.showInformationMessage("Password Changed", "Your password has been changed successfully.");
            } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
                Log.error(ex);
                MessageBox.showErrorMessage("Process Failed", "Process failed due to a system error. Please try again later or contact the system administrator.");
            }
            notYouActionPerformed(null);
        }
        newPassword.setText("");
        confirmPassword.setText("");
    }

    @FXML
    private void confirmPasswordKeyReleased(KeyEvent event) {
        if (newPassword.getText().equals(confirmPassword.getText())) {
            confirmPassword.setStyle("-fx-text-fill: #FFFFFF");
        } else {
            confirmPassword.setStyle("-fx-text-fill: #FF6E6E");
        }
    }

    private void setSqlMode(boolean clearAll) {
        try {
            Statement s = DB.getConnection().createStatement();
            ResultSet r;
            if (clearAll) {
                r = s.executeQuery("SELECT @@sql_mode");
                r.first();
                sqlModes = r.getString(1);
                s.executeUpdate("SET sql_mode = ''");
                isSqlModeChanged = true;
                r.close();
            } else if (isSqlModeChanged && sqlModes != null) {
                s.executeUpdate("SET sql_mode = '" + sqlModes + "'");
            }
            s.close();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void powerBtnActionPerformed(ActionEvent event) {
        String stdwn = "Shut down", restrt = "Restart", comd = MessageBox.showConfirmationMessage("What would you like to do?", "Select an option to continue.", new String[]{stdwn, restrt, "Cancel"});
        if (comd.equals(stdwn)) {
            try {
                Command.systemShutdown(false);
            } catch (Exception ex) {
                Log.error(ex);
            }
        } else if (comd.equals(restrt)) {
            try {
                Command.systemRestart(false);
            } catch (Exception ex) {
                Log.error(ex);
            }
        }
    }

    @FXML
    private void powerBtnMouseExit(MouseEvent event) {
        powerBtn.setOpacity(0.5);
    }

    @FXML
    private void powerBtnMouseEnter(MouseEvent event) {
        powerBtn.setOpacity(1);
    }

    private boolean isExplorerRunning() {
        try {
            String line;
            String pidInfo = "";

            Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");

            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = input.readLine()) != null) {
                pidInfo += line + "\r\n";
            }

            input.close();
            if (pidInfo.contains("explorer")) {
                return true;
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        return false;
    }
}
