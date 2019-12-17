/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.cyclotech.repository.DB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.PrintReport;
import gamefx.Utility;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 * FXML Controller class
 *
 * @author PASINDHU
 */
public class TokenController implements Initializable {

    private CRUDOperations crud;

    @FXML
    private JFXTextField tokenId;
    @FXML
    private JFXButton tokGenButton;
    @FXML
    private ComboBox<?> selectPack;

    private ObservableList data;

    String idCharge;
    @FXML
    private JFXTextField guestId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new CRUDOperationsImpl();
        packages();
        loadTokenID();
        loadIdGuest();
    }

    @FXML
    private void genToken(ActionEvent event) {
        String a = selectPack.getSelectionModel().getSelectedItem().toString().replace("[", "").replace("]", "").replace("hour(s)", "").replace("minute(s)", "").split(" - ")[0];
        System.out.println(a);
        String[] b = a.split(" ");
        Integer b1 = Integer.parseInt(b[0]) * 60;
        Integer b2 = Integer.parseInt(b[1]) + b1;
        Integer b3 = b2 * 60000;
        System.out.println("" + b2);
        System.out.println("" + b1);
        String due = b3.toString();
        System.out.println(due);
//      Integer b = Integer.parseInt(a) * 60000;
//      System.out.println("" + b);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(date);

        try {
            ResultSet rs = crud.search("*", "charge", "where duration like '" + due + "'");
            while (rs.next()) {
                String myid = rs.getString("idCharge");
                System.out.println(rs.getString("charge"));
                crud.save("guest(idguest,charge_idCharge,token,issuedDateTime)", "'" + guestId.getText() + "','" + myid + "','" + tokenId.getText() + "','" + currentTime + "'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        genInvoice();
        loadTokenID();
        loadIdGuest();
        packages();
    }

    @FXML
    private void packageSelection(ActionEvent event) {

    }

    private void packages() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = crud.search("*", "charge", "");
            while (rs.next()) {
                List<String> l = new ArrayList();
                long pc = rs.getLong("duration") / 1000;
                String dur[] = new Utility().timeSplitter(pc);
                l.add(dur[0] + "hour(s) " + dur[1] + "minute(s) - Rs." + rs.getString("charge"));
                data.add(l);
                selectPack.setItems(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadTokenID() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        tokenId.setText(saltStr);
    }

    private void loadIdGuest() {
        try {
            ResultSet rs = crud.search("COUNT(idguest) as x", "guest", "");
            if (rs.first()) {
                int x = Integer.parseInt(rs.getString("x"));
                ++x;
                guestId.setText("" + x);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genInvoice() {
        String id = null;
        try {
            ResultSet rs = crud.search("idguest", "guest", "where token='" + tokenId.getText() + "'");
            while (rs.next()) {
                id = rs.getString("idguest");
            }

            Map<String, Object> m = new HashMap<>();
            m.put("idguest", guestId.getText());
            new PrintReport(m, "token.jrxml").start();
        } catch (Exception e) {
            Log.error(e);
        }
    }

}
