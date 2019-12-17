/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.Utility;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author PASINDHU
 */
public class PromotionsController implements Initializable {

    private ResultSet rs;
    private CRUDOperations crud;
    private ObservableList<loadUSers> data;
    LocalDate start = null;
    LocalDate end = null;

    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXDatePicker enddate;
    @FXML
    private JFXComboBox<String> topUser;
    @FXML
    private JFXTextField promocode;
    @FXML
    private JFXTextField DiscountPrice;
    @FXML
    private JFXTextArea description;
    @FXML
    private TableView<loadUSers> selectMember;
    @FXML
    private TableColumn<?, ?> userid;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private JFXCheckBox sms;
    @FXML
    private JFXCheckBox email;
    @FXML
    private JFXButton send;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXButton viewOld;
    @FXML
    private TableColumn<?, ?> gamerName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new CRUDOperationsImpl();
        genPromoCode();
        addTop();
        loadUser();
    }

    @FXML
    private void selectTop(ActionEvent event) {
        String a = topUser.getSelectionModel().getSelectedItem().toString();
        try {
            rs = crud.search("ps.user_idUser,u.name,u.gamername, count(user_idUser)as `count`", "play_session ps,user u", "where idUser=user_idUser GROUP BY user_idUser ORDER BY `count` DESC limit " + a + "");
            data = FXCollections.observableArrayList();
            while (rs.next()) {
                loadUSers lu = new loadUSers(rs.getString("user_idUser"), rs.getString("name"), rs.getString("gamername"));
                data.add(lu);
                userid.setCellValueFactory(new PropertyValueFactory<>("userid"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                gamerName.setCellValueFactory(new PropertyValueFactory<>("gamername"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectMember.setItems(null);
        selectMember.setItems(data);
    }

    @FXML
    private void sendAndSavePromo(ActionEvent event) {
        try {
            start = startDate.getValue();
            end = enddate.getValue();
            String sdate = start.toString();
            String edate = end.toString();
            String top = topUser.getSelectionModel().getSelectedItem().toString();
            String promo = promocode.getText();
            String discount = DiscountPrice.getText();
            String txt = description.getText();
            String method;
            if (sms.isSelected()) {
                method = "sms";
            } else {
                method = "Email";
            }

            crud.save("promo(sdate,edate,PromoCode,Message,DisPrice)", "'" + sdate + "','" + edate + "','" + promo + "','" + txt + "','" + discount + "'");
            data.forEach(e -> {
                
                try {
                    String user = e.getUserid();
                    System.out.println(userid);
                    rs = crud.search("*", "promo", "order by idPromo desc limit 1");
                    while (rs.next()) {
                        crud.save("promomember(Promo_idPromo,user_idUser)", "'" + rs.getString("idPromo") + "','" + user + "'");
                    }
                } catch (Exception ex) {
                    Log.error(ex);
                }
            });
            gamefx.MessageBox.showInformationMessage("Data Saved", "New Promotion Send");
            data.forEach(e -> {
                String abc = e.getUserid();
                if (sms.isSelected()) {
                    try {
                        rs = crud.search("*", "user", "where idUser = '" + abc + "'");
                        while (rs.next()) {
                            new Utility().sendSMS(rs.getString("phone"), txt + ".Your Promotion Code is" + promo + ".Valid from" + sdate + "toDate" + edate);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        rs = crud.search("*", "user", "where idUser = '" + abc + "'");
                        while (rs.next()) {
//                        new Utility().sendSMS(rs.getString("phone"), txt + ".Your Promotion Code is" + promo + ".Valid from" + sdate + "toDate" + edate);
                            new Utility().sendEmail(edate, edate, edate);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
            System.out.println(top + "/" + promo + "/" + "/" + discount + "/" + txt + "/" + method + "/" + start + "/" + end);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFeild(ActionEvent event) {
//        topUser.setItems(data);
        DiscountPrice.setText(null);
        description.setText(null);
        genPromoCode();
        startDate.setPromptText("Set Start Date");
        enddate.setPromptText("Set End Date");
        sms.setSelected(true);
        email.setSelected(false);
        loadUser();
    }

    @FXML
    private void oldPromos(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MachineDetails/oldpromo.fxml"));
            Stage guestToken = new Stage();
            guestToken.setScene(new Scene(root));
            guestToken.setMaximized(false);
            guestToken.setTitle("Old Promotion Details");
            guestToken.initStyle(StageStyle.UTILITY);
            guestToken.setResizable(false);
            guestToken.show();
        } catch (IOException ex) {
            Log.error(ex);
        }
    }

    private void genPromoCode() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        promocode.setText(saltStr);
    }

    private void addTop() {
        data = FXCollections.observableArrayList();
        try {
            for (int i = 1; i < 11; i++) {
                topUser.getItems().addAll("" + i);
                topUser.setEditable(true);
            }
            System.out.println(topUser.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeText(KeyEvent event) {
        int a = DiscountPrice.getText().length();
        if (!("0123456789".contains(event.getCharacter()) || a > 4)) {
            event.consume();
        }
    }

    private void loadUser() {
        data = FXCollections.observableArrayList();

        try {
            rs = crud.search("*", "user", "");

            while (rs.next()) {
                loadUSers lu = new loadUSers(rs.getString("idUser"), rs.getString("name"), rs.getString("gamerName"));
                data.add(lu);
                userid.setCellValueFactory(new PropertyValueFactory<>("userid"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                gamerName.setCellValueFactory(new PropertyValueFactory<>("gamername"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectMember.setItems(null);
        selectMember.setItems(data);
    }
}
