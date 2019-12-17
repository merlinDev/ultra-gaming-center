package memData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.Utility;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class MemberController implements Initializable {

    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField nic;
    @FXML
    private JFXTextField cont;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField tmpPass;
    @FXML
    private JFXButton save;
    @FXML
    private JFXTextField memID;
    @FXML
    private JFXComboBox<String> memType;
    @FXML
    private JFXTextField typeName;
    @FXML
    private JFXTextField duDay;
    @FXML
    private JFXTextField typePrice;
    @FXML
    private JFXTextField duMonth;
    @FXML
    private JFXComboBox<String> overCharge;
    @FXML
    private JFXButton typeSave;
    @FXML
    private JFXTextField duration;
    @FXML
    private JFXTextField charge;
    @FXML
    private JFXButton add;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXButton typeClear;
    @FXML
    private JFXTextField ingameName;
    @FXML
    private JFXButton showDetails;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update();
    }

    @FXML
    private void save(ActionEvent event) {

        if (!email.getText().equals("") || !cont.getText().equals("")) {
            if (name.getText().equals("") || nic.getText().equals("") || username.getText().equals("") || tmpPass.getText().equals("")) {
                System.out.println("check inputs");
                MessageBox.showErrorMessage("empty field(s) found", "please check your input");
            } else {
                try {
                    ResultSet rs = new CRUDOperationsImpl().search("*", "membership_type", "where status='1'");
                    if (rs.next()) {
                        saveMEM();
                    } else {
                        System.out.println("no Membership type..");
                        MessageBox.showErrorMessage("no membership assigned", "please check your membership type list");
                    }

                } catch (Exception ex) {
                    Log.error(ex);
                }
            }
        } else {
            System.out.println("email or phone");
            MessageBox.showErrorMessage("empty fields", "either email or phone no. required");
        }
    }

    private void saveMEM() {

        boolean validateEmail = new Utility().validateEmail(email.getText());
        boolean validatePhoneNumber = new Utility().validatePhoneNumber(cont.getText().replace(" ", ""));
        boolean NIC = new Utility().NICValidate(nic.getText().replace(" ", ""));

        if (validateEmail == false) {
            MessageBox.showErrorMessage("input error", "check email input");
        } else if (validatePhoneNumber == false) {
            MessageBox.showErrorMessage("input error", "check contact number input");
        } else if (NIC == false) {
            MessageBox.showErrorMessage("input error", "check nic input");
        } else {
            try {
                String pass = new Utility().MD5(tmpPass.getText());
                new CRUDOperationsImpl().save("user(name,username,nic,email,phone,pass,usertype_idUserType,status,gamerName)",
                        "'" + name.getText() + "','" + username.getText() + "','" + nic.getText() + "','" + email.getText() + "','" + cont.getText() + "','" + pass + "','2','active','" + ingameName.getText() + "'");
                System.out.println("saved");
                String typeID = memType.getSelectionModel().getSelectedItem().split(":")[0];
                String memberID = new CRUDOperationsImpl().getLastId("idUser", "user");
                System.out.println("mem ID : " + typeID);
                System.out.println("type ID : " + memberID);
                new CRUDOperationsImpl().save("user_has_membership_type(user_idUser,membership_type_idMembership_type,member_date)", "'" + memberID + "','" + typeID + "',CURDATE()");
                MessageBox.showInformationMessage("done!", "member added");
                Log.info("user ID: " + new CRUDOperationsImpl().getLastId("idUser", "user") + ", name: " + name.getText() + " registered");
                int x = Integer.parseInt(memID.getText().replace(" ", "").split(":")[1]);
                x++;
                memID.setText("MEM: " + x);
                clear(null);
                update();
            } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
                Log.error(ex);
            }
        }

    }

    @FXML
    private void add_overtime(ActionEvent event) {
        int mili = Integer.parseInt(duration.getText()) * 60 * 1000;
        try {
            ResultSet rs = new CRUDOperationsImpl().search("*", "charge", "where duration='" + mili + "' and charge='" + charge.getText() + "'");
            if (rs.next()) {
                System.out.println("in resultSet");
                MessageBox.showErrorMessage("error", "a simmiler type of charge plan is already in");
            } else {

                String regex = "[0-9]+";
                if (duration.getText().equals("") || charge.getText().equals("")) {
                    System.out.println("empty fucks");
                    MessageBox.showErrorMessage("empty field(s) found", "please check your inputs");
                } else if (!duration.getText().matches(regex) || !charge.getText().matches(regex)) {
                    MessageBox.showErrorMessage("error", "check input");
                } else if (Integer.parseInt(duration.getText()) > 1440) {
                    MessageBox.showErrorMessage("time input is wrong", "please check your inputs");
                } else {
                    System.out.println(mili + "");
                    try {
                        new CRUDOperationsImpl().save("charge(duration,charge)", "'" + mili + "','" + charge.getText() + "'");
                        System.out.println("overtime added");
                        Log.info("overtime plan ID: " + new CRUDOperationsImpl().getLastId("idCharge", "charge") + " added");
                        MessageBox.showInformationMessage("done!", "new charge plan added");
                        update();
                        duration.setText(null);
                        charge.setText(null);

                    } catch (Exception ex) {
                        Log.error(ex);
                    }
                }

            }

        } catch (Exception ex) {
            Log.error(ex);
        }

    }

    @FXML
    private void clear(ActionEvent event) {
        name.setText(null);
        nic.setText(null);
        email.setText(null);
        cont.setText(null);
        username.setText(null);
        tmpPass.setText(null);
        ingameName.setText(null);
        memType.getSelectionModel().clearSelection();
    }

    @FXML
    private void type_cancel(ActionEvent event) {
        typeName.setText(null);
        typePrice.setText(null);
        duDay.setText(null);
        duMonth.setText(null);
        overCharge.getSelectionModel().clearSelection();
    }

    @FXML
    private void saveType(ActionEvent event) {
        int day = 0;
        int month = 0;
        try {
            day = Integer.parseInt(duDay.getText());
            month = Integer.parseInt(duMonth.getText());

            String regex = "[0-9]+";
            if (day > 1440 || month > 12) {
                MessageBox.showErrorMessage("error", "check duration timeline");
            } else if (!typePrice.getText().matches(regex)) {
                MessageBox.showErrorMessage("error", "check price input");
            } else {

                String overPrice = overCharge.getSelectionModel().getSelectedItem();
                System.out.println(overPrice);
                overPrice = overPrice.split(":")[0];
                System.out.println(overPrice);
                if (typeName.getText().equals("") || typePrice.getText().equals("") || duDay.getText().equals("") || duMonth.getText().equals("") || overPrice == null) {
                    MessageBox.showErrorMessage("empty field(s)", "please check your inputs");
                } else {
                    try {
                        new CRUDOperationsImpl().save("membership_type(typeName,durationday,duration_month,price,status,charge_idCharge)",
                                "'" + typeName.getText() + "','" + duDay.getText() + "','" + duMonth.getText() + "','" + typePrice.getText() + "','1','" + overPrice + "'");
                        MessageBox.showInformationMessage("done!", "membership type addeds");
                        Log.info("membership type ID: " + new CRUDOperationsImpl().getLastId("idMembership_type", "membership_type") + " added");
                        update();
                        typeName.setText(null);
                        typePrice.setText(null);
                        duDay.setText(null);
                        duMonth.setText(null);
                        overCharge.getSelectionModel().clearSelection();
                    } catch (Exception ex) {
                        Log.error(ex);
                    }
                }

            }

        } catch (NumberFormatException e) {
            MessageBox.showErrorMessage("wrong input field(s)", "please check your inputs");
        }

    }

    private void update() {
        try {
            ResultSet rs = new CRUDOperationsImpl().search("count(idUser) as x", "user", "");
            int x;
            while (rs.next()) {
                x = Integer.parseInt(rs.getString("x")) + 1;
                memID.setText("MEM : " + x);
            }
            rs = new CRUDOperationsImpl().search("idMembership_type,typeName", "membership_type", "");
            memType.getItems().clear();
            while (rs.next()) {
                memType.getItems().add(rs.getString("idMembership_type") + ":" + rs.getString("typeName"));
            }
            rs = new CRUDOperationsImpl().search("*", "charge", "");
            overCharge.getItems().clear();
            while (rs.next()) {
                int charge = Integer.parseInt(rs.getString("duration")) / 60000;
                overCharge.getItems().add(rs.getString("idCharge") + ":" + charge + " mins, Rs." + rs.getString("charge") + "");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void characterCheck(KeyEvent event) {
        if (!Character.isLetter(event.getCharacter().toCharArray()[0]) && !Character.isSpaceChar(event.getCharacter().toCharArray()[0])) {
            event.consume();
        }

    }

    @FXML
    private void usernameCheck(KeyEvent event) {
        if (Character.isSpaceChar(event.getCharacter().toCharArray()[0])) {
            event.consume();
        }
    }

    @FXML
    private void duDayCheck(KeyEvent event) {
        if (Character.isLetter(event.getCharacter().toCharArray()[0])) {
            event.consume();
        }
    }

    @FXML
    private void duMonthCheck(KeyEvent event) {

    }

    @FXML
    private void showDetails(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("showMembers.fxml"))));
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
