/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createAcc;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.Utility;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import was.promoTable;

/**
 * FXML Controller class
 *
 * @author Waseeakr
 */
public class HomeController implements Initializable {

    @FXML
    private JFXTextField promoName;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private JFXDatePicker promoMsgDate;
    @FXML
    private JFXTextArea promoMsg;
    @FXML
    private JFXTextField discountPercentage;
    @FXML
    private TableView<promoTable> loadPromo;
    @FXML
    private JFXRadioButton sms;
    @FXML
    private JFXRadioButton email;
    @FXML
    private TableColumn<?, ?> Name;
    @FXML
    private TableColumn<?, ?> disPercentage;
    @FXML
    private TableColumn<?, ?> startdate;
    @FXML
    private TableColumn<?, ?> enddate;
    @FXML
    private JFXButton promoDelete;
    @FXML
    private JFXButton promoSave;
    @FXML
    private JFXButton promoUpdate;
    @FXML
    private JFXButton promoClear;
    @FXML
    private ToggleGroup msgSending;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        LocalDate d = LocalDate.now();
        promoMsgDate.setValue(d);
        tableLoad();
        discountPercentage.focusedProperty().addListener((ObservableValue<? extends Boolean> observable,
                Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                System.out.println("Textfield on focus");
            } else //when focus lost
            {
                if ((!discountPercentage.getText().trim().matches("(^100(\\.0{1,2})?$)|(^([1-9]([0-9])?|0)(\\.[0-9]{1,2})?)"))) {

                    MessageBox.showWarningMessage("Error", "Invalid Discount Percentage!!");

                }
            }
        });
    }

    @FXML
    private void promoDelete(MouseEvent event) {
        //tableLoad();
        CRUDOperations db = new CRUDOperationsImpl();
        String id = null;
        try {
            promoTable promoTable = loadPromo.getSelectionModel().getSelectedItem();
            String name = promoTable.getName();
            String sdate = promoTable.getStartDate();
            String edate = promoTable.getEndDate();
            System.out.println("123");
            ResultSet rs = db.search("idPromotion", "promotion",
                    "where name='" + name + "' and startDate='" + sdate + "' and endDate='" + edate + "'");
            while (rs.next()) {
                id = rs.getString("idPromotion");
                System.out.println(id);
            }
            db.delete("promotionmsg", "promotion_idPromotion='" + id + "'");
            System.out.println("changer");
            db.delete("promotion", "idPromotion='" + id + "'");

            MessageBox.showWarningMessage("Information", "Promotion Removed");
            tableLoad();
            promoClear(event);

        } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void promoSave(MouseEvent event) {
        CRUDOperations db = new CRUDOperationsImpl();
        if (promoMsg.getText() == null | promoName.getText() == null | discountPercentage.getText() == null
                | startDate.getValue() == null | endDate.getValue() == null | promoMsgDate.getValue() == null) {
            MessageBox.showWarningMessage("Warning", "Fields are Empty");
        } else if (sms.isSelected()) {
            save1();
            sendsms();

        } else if (email.isSelected()) {
            save1();
            sendmail();
        } else {
            save1();
            //System.out.println("non");
        }
    }

    public void save1() {
        try {
            CRUDOperations db = new CRUDOperationsImpl();
            System.out.println(discountPercentage.getText());
            System.out.println(startDate.getValue());
            System.out.println(endDate.getValue());

            Alert al1 = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Add this Promotion",
                    ButtonType.OK, ButtonType.CANCEL);
            al1.showAndWait();
            if (al1.getResult() == ButtonType.CANCEL) {
                al1.close();
            }
            if (al1.getResult() == ButtonType.OK) {
                System.out.println("a");
                if (startDate.getValue().isBefore(endDate.getValue())) {
                   db.save("promotion(name,discountPercentage,startDate,endDate)", "'" + promoName.getText() + "',"
                   + "'" + discountPercentage.getText() + "','" + startDate.getValue() + "','" + endDate.getValue() + "'");
                    String s = db.getLastId("idPromotion", "promotion");
                    db.save("promotionMsg(msg,date,promotion_idPromotion)", "'" + promoMsg.getText() + "',"
                            + "'" + promoMsgDate.getValue() + "','" + s + "'");
                    MessageBox.showWarningMessage("Confirmation", "Promotion Added");

                    MouseEvent event = null;
                    promoClear(event);
                    loadPromo.setItems(null);
                    tableLoad();
                } else {
                    Alert al2 = new Alert(Alert.AlertType.WARNING, "Start date and End date are not valid", ButtonType.OK);
                    al2.showAndWait();
                    if (al2.getResult() == ButtonType.OK) {
                        al2.close();
                        MouseEvent event = null;
                        startDate.setValue(null);
                        endDate.setValue(null);
                    }

                }

            }

        } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }

    }

    @FXML
    private void promoUpdate(MouseEvent event) {
        CRUDOperations db = new CRUDOperationsImpl();
        try {
            db.update("promotion", "name='" + promoName.getText() + "',"
                    + "discountPercentage='" + discountPercentage.getText() + "',startDate='" + startDate.getValue() + "',"
                    + "endDate='" + endDate.getValue() + "'", "idPromotion='" + proid + "'");
            db.update("promotionmsg", "msg='" + promoMsg.getText() + "',date='" + promoMsgDate.getValue() + "'",
                    "promotion_idPromotion='" + proid + "'");
            MessageBox.showWarningMessage("Warning", "promotion Updated");

            tableLoad();
            promoClear(event);
        } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void settingData(MouseEvent event) {
        promoTable promoTable = loadPromo.getSelectionModel().getSelectedItem();
        promoName.setText(" ");
        promoName.setText(promoTable.getName());
        discountPercentage.setText(promoTable.getDiscountPercentage());
        startDate.setValue(dateSetter(promoTable.getStartDate()));
        startDate.setValue(dateSetter(promoTable.getStartDate()));
        endDate.setValue(dateSetter(promoTable.getEndDate()));
        CRUDOperations db = new CRUDOperationsImpl();
        try {
            ResultSet rs = db.search("idPromotion", "promotion", "where name='" + promoTable.getName() + "'"
             + " and startDate='" + promoTable.getStartDate() + "' and endDate='" + promoTable.getEndDate() + "'");
            while (rs.next()) {
                proid = rs.getString("idPromotion");
            }
            ResultSet rs1 = db.search("msg,date", "promotionmsg", "where promotion_idPromotion='" + proid + "'");
            while (rs1.next()) {
                promoMsg.setText(rs1.getString("msg"));
                promoMsgDate.setValue(dateSetter(rs1.getDate("date").toString()));
            }        
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    private LocalDate dateSetter(String dateString) {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh-mm-ss");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //datePicker.setValue(LOCAL_DATE("2016-05-01"));
        LocalDate localDate = LocalDate.parse(dateString, format);
        return localDate;
    }

    @FXML
    private void promoClear(MouseEvent event) {
        //promoNo.setText(null);
        promoName.setText(null);
        discountPercentage.setText(null);
        startDate.setValue(null);
        endDate.setValue(null);
        promoMsg.setText(null);
        promoMsgDate.setValue(null);
        promoMsg.setText(null);
        promoMsgDate.setValue(null);
        sms.setSelected(false);
        email.setSelected(false);
    }
//private ObservableList<promoTable>;
    String proid;

    private void tableLoad() {
        ObservableList<promoTable> data = null;
        data = FXCollections.observableArrayList();
        CRUDOperations db = new CRUDOperationsImpl();
        try {
            ResultSet rs1 = db.search("name,discountPercentage,startDate,endDate", "promotion", "");
            while (rs1.next()) {
                data.add(new promoTable(rs1.getString("name"), rs1.getInt("discountPercentage"), rs1.getDate("startDate"),
                        rs1.getDate("endDate")));
            }
            Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            disPercentage.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));
            startdate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            enddate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            loadPromo.setItems(null);
            loadPromo.setItems(data);
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    private void msgSetter() {
        CRUDOperations db = new CRUDOperationsImpl();
        String id;
        try {
            System.out.println(promoName.getText() + "waseem");
            ResultSet rs = db.search("idPromotion", "promotion", " where name='" + promoName.getText() + "' and"
                    + " startDate='" + startDate.getValue() + "' and endDate='" + endDate.getValue() + "'");
            while (rs.next()) {
                id = rs.getString("idPromotion");
                System.out.println(id);
            }
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    private void sendsms() {
        try {
            CRUDOperations db = new CRUDOperationsImpl();
            ResultSet rs = db.search("phone", "user", "");
            while (rs.next()) {
                String sms = rs.getString("phone");

                new Utility().sendSMS(sms, promoMsg.getText());
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void sendmail() {
        try {
            CRUDOperations db = new CRUDOperationsImpl();
            ResultSet rs = db.search("email", "user", "");
            while (rs.next()) {
                String mail = rs.getString("email");

                new Utility().sendSMS(mail, promoMsg.getText());
            }
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void disValidate(KeyEvent event) {
        if(!"0123456789".contains(event.getCharacter())){
            event.consume();
            }
    }
}
