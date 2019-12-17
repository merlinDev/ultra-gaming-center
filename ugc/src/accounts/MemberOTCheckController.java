/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accounts;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.Utility;
import gamefx.PrintReport;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Roshana Pitigala
 */
public class MemberOTCheckController implements Initializable {

    @FXML
    private JFXTextField userId;
    @FXML
    private JFXTextField startTime;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField charge;
    @FXML
    private JFXTextField payment;
    @FXML
    private JFXTextField balance;
    @FXML
    private JFXTextField duration;

    private final CRUDOperationsImpl db = new CRUDOperationsImpl();
    private ResultSet rs;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private DecimalFormat priceFormat = new DecimalFormat("0.00");
    private double Charge = 0, NetTotal = 0, Discount = 0;
    private String playSessionIds[];
    @FXML
    private JFXTextField promoCode;
    @FXML
    private JFXTextField disprice;
    @FXML
    private JFXTextField netTotal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void save(ActionEvent event) {
        paymentActionPerformed(event);
    }

    @FXML
    private void cancel(ActionEvent event) {
        ((Stage) userId.getScene().getWindow()).close();
    }

    @FXML
    private void userIdActionPerformed(ActionEvent event) {

        int id = 0;
        try {
            String sTime = "";
            rs = db.search("`name`, SUM(TIMESTAMPDIFF(SECOND, startTime, IFNULL(endtime, NOW()))) AS `duration`, startTime, play_session.play_session_id",
                    "play_session JOIN `user` ON play_session.user_idUser = `user`.idUser"
                    + " LEFT JOIN play_session_has_overtime_payments ON play_session.play_session_id = play_session_has_overtime_payments.play_session_id",
                    "WHERE `user`.idUser='" + userId.getText() + "'"
                    + " AND (startTime >= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                    + " AND (startTime <= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')))"
                    + " AND play_session_has_overtime_payments.play_session_id IS NULL"
                    + " GROUP BY `name`, startTime, play_session_id ORDER BY `duration` DESC");
            if (rs.last()) {
                playSessionIds = new String[rs.getRow()];
                rs.first();
                userId.setStyle("-fx-text-fill: #ffffff;");
                name.setText(rs.getString("name"));
                playSessionIds[id] = rs.getString("play_session_id");
                String[] dura = new Utility().timeSplitter(rs.getLong("duration"));
                sTime = rs.getString("startTime");
                duration.setText(dura[0] + "hour(s) " + dura[1] + "minute(s) " + dura[2] + "second(s)");
                Charge = Double.parseDouble(new Utility().calculateOverTimeCharge(userId.getText()));
                charge.setText(priceFormat.format(Charge) + " LKR");
                while (rs.next()) {
                    playSessionIds[++id] = rs.getString("play_session_id");
                    try {
                        Date s1 = dateFormat.parse(sTime);
                        Date s2 = dateFormat.parse(rs.getString("startTime"));
                        if (s1.before(s2)) {
                            sTime = rs.getString("startTime");
                        }
                    } catch (Exception e) {
                    }
                }

                startTime.setText(sTime.split("\\.")[0]);
                promoCode.setEditable(true);
                promoCodeA(null);
                payment.requestFocus();
            } else {
                userId.setStyle("-fx-text-fill: #FF1919;");
                name.setText("");
                startTime.setText("");
                charge.setText("");
                payment.setText("");
                balance.setText("");
                duration.setText("");
                promoCode.setText("");
                promoCode.setEditable(false);
                disprice.setText("");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private boolean promoValid() {
        return !promoCode.getText().equals("") && !promoCode.getStyle().trim().toLowerCase().contains("-fx-text-fill: #ff1919;");
    }

    @FXML
    private void paymentActionPerformed(ActionEvent event) {
        total();
        if (!paymentKeyRelased(null)) {
            return;
        }
        try {
            double Balance = 0;
            if (!payment.getText().equals("")) {
                Balance = (Double.parseDouble(payment.getText()) - NetTotal);
            }
            balance.setText(new DecimalFormat("0.00").format(Balance) + " LKR");
            payment.setStyle("-fx-text-fill: #ffffff;");
            if (event == null) {
                return;
            }
            if (Balance >= 0 && !payment.getText().equals("") && MessageBox.showConfirmationMessage("Confirm your action", "Are you sure that you want to finalize this invoice?", new String[]{"YES", "NO"}).equals("YES")) {
                if (promoValid()) {
                    db.save("overtime_payments (`total`,`promoCode`)", "'" + Charge + "', '" + promoCode.getText() + "'");
                    db.update("promomember", "UsedDate = NOW()",
                            "user_idUser='" + userId.getText() + "'"
                            + " AND Promo_idPromo IN (SELECT idPromo FROM promo WHERE PromoCode='" + promoCode.getText() + "')");
                } else {
                    db.save("overtime_payments (`total`)", "'" + Charge + "'");
                }
                String id = db.getLastId("Id", "overtime_payments");
                for (String sessionId : playSessionIds) {
                    db.save("play_session_has_overtime_payments (`play_session_id`, `overtime_payments_id`)", "'" + sessionId + "','" + id + "'");
                }
                MessageBox.showInformationMessage("Data Saved", "Data saved successfully.");
                HashMap<String, Object> mp = new HashMap<>();
                mp.put("name", name.getText());
                mp.put("opID", db.getLastId("id", "overtime_payments"));
                mp.put("startDT", startTime.getText());
                mp.put("duration", duration.getText());
                mp.put("charge", charge.getText());
                mp.put("payment", payment.getText());
                mp.put("balance", balance.getText());
                new PrintReport(mp, "memberOTC.jrxml",false,true).start();
                cancel(null);
            }
        } catch (Exception e) {
            Log.error(e);
            payment.setStyle("-fx-text-fill: #FF1919;");
        }
    }

    @FXML
    private boolean paymentKeyRelased(KeyEvent event) {
        try {
            balance.setText(new DecimalFormat("0.00").format((Double.parseDouble(payment.getText()) - NetTotal)) + " LKR");
            payment.setStyle("-fx-text-fill: #ffffff;");
            return true;
        } catch (Exception e) {
            payment.setStyle("-fx-text-fill: #FF1919;");
        }
        return false;
    }

    @FXML
    private void promoCodeA(ActionEvent event) {
        if (promoCode.getText().equals("")) {
            promoCode.setStyle("-fx-text-fill: #FFFFFF;");
            disprice.setText("0.00 LKR");
            paymentActionPerformed(null);
            return;
        }

        try {
            rs = db.search("promo.DisPrice",
                    "promo INNER JOIN promomember ON promo.idPromo = promomember.Promo_idPromo",
                    "WHERE promomember.user_idUser='" + userId.getText() + "'"
                    + " AND promomember.UsedDate IS NULL"
                    + " AND promo.PromoCode = '" + promoCode.getText() + "'"
                    + " AND sdate <= DATE_FORMAT(NOW() ,'%Y-%m-%d 00:00:00')"
                    + " AND edate >= DATE_FORMAT(NOW() ,'%Y-%m-%d 23:59:59')");
            if (rs.first()) {
                promoCode.setStyle("-fx-text-fill: #ffffff;");
                Discount = rs.getDouble("DisPrice");
                disprice.setText(new DecimalFormat("0.00").format(Discount) + " LKR");
                promoCode.setEditable(false);
            } else {
                promoCode.setStyle("-fx-text-fill: #FF1919;");
                Discount = 0;
                disprice.setText("0.00 LKR");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        paymentActionPerformed(null);
    }

    private void total() {
        NetTotal = Charge - Discount;
        netTotal.setText(new DecimalFormat("0.00").format(NetTotal) + " LKR");
    }

    @FXML
    private void paymentKT(KeyEvent event) {
        if (Character.isLetter(event.getCharacter().toCharArray()[0]) || Character.isSpaceChar(event.getCharacter().toCharArray()[0])) {
            event.consume();
        }
    }

}
