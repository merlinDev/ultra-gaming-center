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
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class, SidePanel.
 *
 * @author Roshana Pitigala
 */
public class SidePanelContentController implements Initializable {

    @FXML
    private Label slider_time;
    @FXML
    private AnchorPane slider_TopPanelGuest;
    @FXML
    private Label slider_tokenNumber;
    @FXML
    private Label slider_TimeSinceLoggedIn;
    @FXML
    private AnchorPane slider_TopPanelMember;
    @FXML
    private Label slider_packageNameMember;
    @FXML
    private Label slider_remainingTimeMember;
    @FXML
    private Label slider_expirationDateMember;
    @FXML
    private Label slider_gamerNameMember;
    @FXML
    private Label slider_NameMember;
    @FXML
    private Label slider_charge;
    private Stage memberCheckout, myAcc;

    private ResultSet rs;
    private DecimalFormat priceFormat = new DecimalFormat("0.00");
    private CRUDOperationsImpl db = new CRUDOperationsImpl();
    private final Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), ae -> timerTick())),
            chargeCalculator = new Timeline(new KeyFrame(Duration.seconds(30), ae -> calculateCharge()));

    private int memberPackageDuration = 0, duration = 0;//seconds
    private final Utility util = new Utility();

    private String idCharge = null;
    @FXML
    private Label remainingTimelbl;
    @FXML
    private Label validUntil;
    @FXML
    private Label slider_chargeMember;
    @FXML
    private Label chargeLbl;

    private AnchorPane anchorPane;
    @FXML
    private JFXButton rqustGame;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (user.getUserTypeId() == user.MEMBER) {
            try {
                rs = db.search("name, gamername", "user", "WHERE idUser='" + user.getIdUser() + "'");
                if (rs.first()) {
                    slider_NameMember.setText(rs.getString("name"));
                    slider_gamerNameMember.setText(rs.getString("gamername"));
                }
                rs = db.search("DATE_ADD(user_has_membership_type.member_date, INTERVAL (membership_type.duration_month) MONTH)AS `expiry date` , "
                        + "membership_type.typeName, "
                        + "membership_type.durationday ",
                        "user_has_membership_type INNER JOIN membership_type ON membership_type.idMembership_type = user_has_membership_type.membership_type_idMembership_type",
                        "WHERE user_has_membership_type.user_idUser = '" + user.getIdUser() + "'");

                if (rs.first()) {
                    slider_packageNameMember.setText(rs.getString("typeName"));
                    duration = (rs.getInt("durationday") * 60);
                    memberPackageDuration = duration;
                    slider_expirationDateMember.setText(rs.getString("expiry date"));

                    if (user.getUserTypeId() == user.MEMBER) {
                        duration = memberPackageDuration - (int) (user.getFullDuration());
                    }
                }
            } catch (Exception ex) {
                Log.error(ex);
            }

            memberDataChange();
            slider_TopPanelGuest.setVisible(false);
            slider_TopPanelMember.setVisible(true);
            rqustGame.setVisible(true);
        } else {
            try {
                rs = db.search("TIMESTAMPDIFF(SECOND, guest.startTime, NOW())AS `totalTime`, guest.token, guest.charge_idCharge AS `idCharge`",
                        "guest",
                        "WHERE guest.idguest = '" + user.getIdUser() + "'");

                if (rs.first()) {
                    slider_tokenNumber.setText(rs.getString("token"));
                    duration = rs.getInt("totalTime");
                    idCharge = rs.getString("idCharge");
                }
            } catch (Exception ex) {
                Log.error(ex);
            }

            slider_TopPanelMember.setVisible(false);
            slider_TopPanelGuest.setVisible(true);
            rqustGame.setVisible(false);
        }

        timerTick();
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

        calculateCharge();
        chargeCalculator.setCycleCount(Animation.INDEFINITE);
        chargeCalculator.play();
    }

    private void timerTick() {
        slider_time.setText(Utility.getTime());
        String[] Duration = util.timeSplitter((duration < 0) ? (duration * -1) : (duration));
        if (user.getUserTypeId() == user.MEMBER || user.getUserTypeId() == user.ADMINISTRATOR) {
            slider_remainingTimeMember.setText(Duration[0] + "hour(s) " + Duration[1] + "minute(s) " + Duration[2] + "second(s)");
            duration--;
        } else {
            slider_TimeSinceLoggedIn.setText(Duration[0] + "hour(s) " + Duration[1] + "minute(s) " + Duration[2] + "second(s)");
            duration++;
        }
        memberDataChange();
    }

    @FXML
    private void myAccInfo(ActionEvent event) {
        try {
            Parent root;
            if (user.getUserTypeId() == user.MEMBER) {
                root = FXMLLoader.load(getClass().getResource("/gamefx/editUser.fxml"));
            } else {
                root = FXMLLoader.load(getClass().getResource("/gamefx/GuestDetails.fxml"));
            }
            if (myAcc == null || !myAcc.isShowing()) {
                myAcc = new Stage();
                myAcc.setScene(new Scene(root));
                myAcc.setMaximized(false);
                myAcc.setTitle("My Account Information");
                myAcc.initStyle(StageStyle.UTILITY);
                myAcc.setResizable(false);
                myAcc.show();
            } else {
                myAcc.requestFocus();
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void calculateCharge() {
        if (user.getUserTypeId() == user.GUEST) {
            slider_charge.setText(util.calculateGuestChargeFromGuestId(user.getIdUser()) + " LKR");
        } else if (user.getUserTypeId() == user.MEMBER) {
            slider_chargeMember.setText(util.calculateOverTimeCharge(user.getIdUser()) + " LKR");
        }
    }

    private void memberDataChange() {
        if (duration < 0 && validUntil.isVisible()) {
            validUntil.setVisible(false);
            slider_expirationDateMember.setVisible(false);

            slider_chargeMember.setVisible(true);
            chargeLbl.setVisible(true);

            remainingTimelbl.setText("Over time");
        } else if (duration >= 0 && !validUntil.isVisible()) {
            validUntil.setVisible(true);
            slider_expirationDateMember.setVisible(true);

            slider_chargeMember.setVisible(false);
            chargeLbl.setVisible(false);

            remainingTimelbl.setText("Remaining for today");
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        user.logout();
    }

    @FXML
    private void requestGame(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gamerequest/RequestGames.fxml"));
            if (memberCheckout == null || !memberCheckout.isShowing()) {
                memberCheckout = new Stage();
                memberCheckout.setScene(new Scene(root));
                memberCheckout.setMaximized(false);
                memberCheckout.setTitle("Request a Game");
                memberCheckout.initStyle(StageStyle.UTILITY);
                memberCheckout.setResizable(false);
                memberCheckout.show();
            } else {
                memberCheckout.requestFocus();
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    /**
     * @param anchorPane the anchorPane to set
     */
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }
}
