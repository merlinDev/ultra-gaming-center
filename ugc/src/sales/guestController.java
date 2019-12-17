/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.PrintReport;
import gamefx.Utility;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Waseeakr
 */
public class guestController implements Initializable {

    CRUDOperations db;
    @FXML
    private JFXTextField tokenId;
    @FXML
    private JFXTextField Duration;
    @FXML
    private JFXTextField Packages;
    @FXML
    private JFXButton guestInvoice;
    @FXML
    private JFXTextField Charge;
    @FXML
    private JFXTextField paid1;

    String gameinvoiceid;
    String tokenid;
    @FXML
    private JFXTextField invoiceDate;
    @FXML
    private JFXTextField invoiceCashier;
    @FXML
    private JFXButton clear;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new CRUDOperationsImpl();
        invoiceInfoLoad();
        //loadSave();
        //packages();
    }

    @FXML
    private void guestInvoice(MouseEvent event) {
        String payment = paid1.getText();
        double payment1 = Double.parseDouble(paid1.getText());
        if (payment == null) {
            MessageBox.showWarningMessage("Warning", "Enter a payment!");
        } else if (payment1 >= Double.parseDouble(Charge.getText())) {

            try {
                ResultSet d1 = db.search("idguest,charge_idCharge", "guest", "where token='" + tokenId.getText() + "'");
                String guestid = null;
                String chargeid = null;
                while (d1.next()) {
                    guestid = d1.getString("idguest");
                    chargeid = d1.getString("charge_idCharge");
                }

                db.save("invoice(date,user_idUser,charge_idCharge,guest_idguest,total,paid)", "" +" now()" + ",'" + accounts.user.getIdUser() + "','" + chargeid + "','" + guestid + "','" + Charge.getText() + "','" + paid1.getText() + "'");

                MessageBox.showInformationMessage("Successful", "Invoice is Added..!");

                double balance = (Double.parseDouble(paid1.getText()) - Double.parseDouble(Charge.getText()));
                DecimalFormat df = new DecimalFormat("#.##");
                String df1 = df.format(balance);
                System.out.println(df1 + "report");
                report1(df1);

            } catch (Exception ex) {
                Log.error(ex);
            }
        } else {
            MessageBox.showWarningMessage("Warning", "payment is not enough!");
        }
    }

    @FXML
    private void tokenPayValidate(KeyEvent event) {
        if (!"0123456789.".contains(event.getCharacter())) {
            event.consume();
        }
    }

    public String id() {
        String dates = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        dates = new Date().getTime() + "";
        System.out.println(dates);
        LocalDate date = LocalDate.now();
        return dates;
    }

    private void packages() {
        try {
            ResultSet rs = db.search("duration", "charge", "");
            List<String> l = new ArrayList();
            while (rs.next()) {
                l.add(rs.getString("duration"));
                ObservableList<String> a = FXCollections.observableArrayList(l);
                Packages.setText(a + "");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

    }

    private void invoiceInfoLoad() {
        String dates = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        LocalDate date = LocalDate.now();
        //System.out.println(date);
        invoiceDate.setText(date.toString());

        String uid = accounts.user.getIdUser();
        try {
            ResultSet rs = db.search("name", "user", "where idUser='" + uid + "'");
            while (rs.next()) {
                String uname = rs.getString("name");
                invoiceCashier.setText(uname);
            }
        } catch (ClassNotFoundException ex) {
            Log.error(ex);
        } catch (SQLException ex) {
            Log.error(ex);
        } catch (URISyntaxException ex) {
            Log.error(ex);
        } catch (IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void clear(MouseEvent event) {
        tokenId.setText(null);
        Packages.setText("");
        Duration.setText(null);
        Charge.setText(null);
        paid1.setText(null);

    }

    private void report1(String s) {
        String id = null;
        try {

            ResultSet rs = db.search("invoice.`idInvoice`,invoice.`date`", "`guest` guest INNER JOIN `invoice` invoice ON guest.`idguest` = invoice.`guest_idguest`", "where guest.token='" + tokenId.getText() + "'");
            String idinv = null;
            String date = null;
            while (rs.next()) {
                idinv = rs.getString("idInvoice");
                date = rs.getString("date");
            }
            String date1 = date.split(" ")[0];
            String time = date.split(" ")[1];
            Map<String, Object> params = new HashMap<>();
            params.put("invoiceID", idinv);
            params.put("date", date1);
            params.put("time", time);

            new PrintReport(params, "InvoiceGuest.jasper").start();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void loadCharge(ActionEvent event) throws Exception {

        String a = tokenId.getText();
        boolean b = checkguestid();
        if (b) {
            if (a == null) {
                MessageBox.showWarningMessage("Warning", "Enter Valid Token Number!");
            } else {
                setPackages();
                setDuration();
                setCharge();
            }
        } else {
            MessageBox.showWarningMessage("Warninig", "Already invoice issued for this token no");
        }

    }

    private boolean checkguestid() {
        boolean b = false;
        String token = tokenId.getText();

        try {
            ResultSet rs = db.search("idguest", "guest", "where token='" + token + "'");
            String idguest = null;
            while (rs.next()) {
                idguest = rs.getString("idguest");
            }
            ResultSet rs1 = db.search("idInvoice", "invoice", "where guest_idguest='" + idguest + "'");
            String idInvoice = null;

            if (rs1.next()) {
                System.out.println("inside if");

            } else {
                System.out.println("inside else");
                b = true;
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        return b;
    }

    private void setPackages() {
        try {
            ResultSet r = db.search(" `duration`", "charge Join guest on charge.idCharge=guest.charge_idCharge", "where token='" + tokenId.getText() + "'");
            while (r.next()) {
                long duration = r.getLong("duration") / 1000;

                String dur[] = new Utility().timeSplitter(duration);
                Packages.setText(dur[0] + "hour(s) " + dur[1] + "minute(s)");
                System.out.println("package eka" + Packages.getText());
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

    }

    private void setDuration() {

        long between = getbetweentime();
        long milvalue = between * 60 * 1000;
        System.out.println(milvalue + "milvaue");
        System.out.println(between * 60 * 60);
        String dur[] = new Utility().timeSplitter(milvalue / 1000);
        Duration.setText(dur[0] + "hour(s) " + dur[1] + "minute(s)");

    }

    private long getbetweentime() {
        long between = 0;
        try {
            ResultSet rs = db.search("startTime,endTime", "guest", "where token='" + tokenId.getText() + "'");

            while (rs.next()) {
                String startTime = rs.getString("startTime");
                String endTime = rs.getString("endTime");

                String ss = rs.getString("startTime").split("\\.")[0];

                String ee = rs.getString("endTime").split("\\.")[0];

                String st = ss.split(" ")[1];
                String et = ee.split(" ")[1];
                System.out.println(st);
                System.out.println(et);
                LocalTime st2 = LocalTime.parse(st);
                LocalTime et2 = LocalTime.parse(et);
                between = MINUTES.between(st2, et2);
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
        return between;
    }

    private void setCharge() {

        try {
            ResultSet rs = db.search("charge_idCharge", "guest", "where token='" + tokenId.getText() + "'");
            String idcharge = null;
            while (rs.next()) {
                idcharge = rs.getString("charge_idCharge");
            }
            long between = getbetweentime();
            Charge.setText(new Utility().calculateGuestCharge(tokenId.getText()));
        } catch (Exception ex) {
            Log.error(ex);
        }

    }
}
