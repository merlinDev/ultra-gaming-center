/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import gamefx.Log;
import gamefx.MessageBox;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author PASINDHU
 */
public class MachineController implements Initializable {

    private ResultSet rs;
    private CRUDOperations crud;

    @FXML
    private JFXTextField machineNo;
    @FXML
    private JFXTextField IpAddress;
    @FXML
    private JFXTextField machineName;
    @FXML
    private JFXButton savemachine;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXToggleButton Availabilty;
    @FXML
    private JFXButton UpdateMAchine;
    @FXML
    private JFXButton Refresh;
    @FXML
    private Label AddMachine;
    @FXML
    private TableColumn<?, ?> Name;
    @FXML
    private TableColumn<?, ?> MachineNo;
    @FXML
    private TableColumn<?, ?> ipaddress;
    @FXML
    private TableView<machineDetails> table;
    @FXML
    private JFXTextField searchMachine;
    @FXML
    private Pane machineDetails;

    private ObservableList<machineDetails> data;

    @FXML
    private TableColumn<?, ?> vesi;
    @FXML
    private TableColumn<?, ?> type;
    @FXML
    private JFXButton machineTypes;
    @FXML
    private AnchorPane machineType;
    @FXML
    private JFXComboBox pcType;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
        crud = new CRUDOperationsImpl();
        loadMachineId();
        getMachineDetails();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                IpAddress.requestFocus();
                //To change body of generated methods, choose Tools | Templates.
            }
        });
        comboset();
        //MessageBox.showInformationMessage("Data Saved", "New Machine Saved");
        //System.out.println
        //(MessageBox.showMessage(MessageBox.MessageType.INFORMATION,
        //"Data Saved", null, "New Machine Saved", new String[]{MessageBox.OK_BUTTON,
        //MessageBox.CANCEL_BUTTON, "Gehan"}, false));
        //skjdlk
    }

    @FXML
    private void save(ActionEvent event) {
        String no = machineNo.getText();
        String ip = IpAddress.getText();
        String name = machineName.getText();
        String avail = "1";
        String Inuse = "2";
        int idSpecs = 1;
        String type = pcType.getSelectionModel().getSelectedItem().toString();
        try {
            System.out.println(type);
            if (ip.equals("") | name.equals("")) {
                if (MessageBox.showMessage(MessageBox.MessageType.ERROR, "Error", null, "Empty Fields", new String[]{MessageBox.OK_BUTTON}, false).equals(MessageBox.OK_BUTTON)) {
                    IpAddress.requestFocus();
                    clearcom();
                }
            } else {
                if (MessageBox.showMessage(MessageBox.MessageType.CONFIRMATION, " ", null, "Are You Sure??", new String[]{MessageBox.OK_BUTTON, MessageBox.CANCEL_BUTTON}, false).equals(MessageBox.OK_BUTTON)) {
                    if (type.equals("member")) {
                        type.valueOf(pcType);
                    } else {
                        type.valueOf(pcType);
                    }
                    crud.save("machine", "'" + no + "','" + ip + "','" + name + "','" + avail + "','" + Inuse + "','" + type + "'");
                    MessageBox.showInformationMessage("Data Saved", "New Machine Saved");
                } else {
                    clear(event);
                }
                clear(event);
                loadMachineId();
                refresh(event);
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void clear(ActionEvent event) {
        machineNo.setText("");
        IpAddress.setText("");
        machineName.setText("");
        loadMachineId();
        Availabilty.setVisible(false);
        IpAddress.requestFocus();
        UpdateMAchine.setVisible(false);
        savemachine.setVisible(true);
        clearcom();
    }

    @FXML
    private void UpdateKranwa(ActionEvent event) {
        try {
            String abc = machineNo.getText();
            String avail = "";
            String useStatus = "";
            
            String type = pcType.getSelectionModel().getSelectedItem().toString();
            if (Availabilty.isSelected()) {
                avail = "a";
                useStatus = "2";
            } else {
                avail = "n/a";
                useStatus = "1";
            }
            
            
            if (type.equals("member")) {
                type.valueOf(pcType);
            } else {
                type.valueOf(pcType);
            }
            
            

                if (MessageBox.showMessage(MessageBox.MessageType.CONFIRMATION, " ", null, "Are You Sure??", new String[]{MessageBox.OK_BUTTON, MessageBox.CANCEL_BUTTON}, false).equals(MessageBox.OK_BUTTON)) {     
                    crud.update("machine", "ipAddress='" + IpAddress.getText() + "',name='" + machineName.getText() + "',"
                            + "availability='" + avail + "',inUse='"+useStatus+"', type='" + type + "'", "idMachine ='" + abc + "'");

                    MessageBox.showInformationMessage("Data Updated", "Machine No" + machineNo.getText() + " " + "Data Update");

                } else {
                    clear(event);
                }

                clear(event);
                refresh(event);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void refresh(ActionEvent event) {
        table.setItems(null);
        clear(event);
        getMachineDetails();
        searchMachine.setText(null);
    }

    @FXML
    private void searchit(ActionEvent event) {
        data = FXCollections.observableArrayList();
        try {
            if (!(searchMachine.getText() == null)) {
                rs = crud.search("idMachine,ipAddress,name,availability,type", "machine", "where idMachine like'" + searchMachine.getText() + "%' or ipAddress like'" + searchMachine.getText() + "%' or name like'" + searchMachine.getText() + "%' or availability like'" + searchMachine.getText() + "%' or type like'" + searchMachine.getText() + "%'");

                while (rs.next()) {
                    System.out.println("searching....");
                    data.add(new machineDetails(rs.getString("idMachine"), rs.getString("ipAddress"), rs.getString("name"), rs.getString("availability").replace("a", "Available").replace("n/", "Not "), rs.getString("type").replace("g", "G").replace("me", "Me")));
                }
            }
            MachineNo.setCellValueFactory(new PropertyValueFactory<>("idMachine"));
            ipaddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
            Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            vesi.setCellValueFactory(new PropertyValueFactory<>("availability"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));

            table.setItems(null);
            table.setItems(data);
        } catch (Exception ex) {
            Log.error(ex);
        }

    }

    private void loadMachineId() {
        try {
            rs = crud.search("COUNT(idMachine) as x", "machine", "");
            if (rs.first()) {
                int x = Integer.parseInt(rs.getString("x"));
                ++x;
                machineNo.setText("" + x);

            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void getMachineDetails() {

        data = FXCollections.observableArrayList();

        try {
            rs = crud.search("*", "machine", "");

            while (rs.next()) {
                machineDetails m = new machineDetails(rs.getString("idMachine"), rs.getString("ipAddress"), rs.getString("name"), rs.getString("availability").replace("a", "Available").replace("n/", "Not "), rs.getString("type").replace("g", "G").replace("me", "Me"));
                data.add(m);
                MachineNo.setCellValueFactory(new PropertyValueFactory<>("idMachine"));
                ipaddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
                Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                vesi.setCellValueFactory(new PropertyValueFactory<>("availability"));
                type.setCellValueFactory(new PropertyValueFactory<>("type"));

            }

        } catch (Exception ex) {
            Log.error(ex);
        }

        table.setItems(null);
        table.setItems(data);
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2) {
            try {
                machineDetails abc = table.getSelectionModel().getSelectedItem();

                machineNo.setText(abc.getIdMachine());
                machineName.setText(abc.getName());
                IpAddress.setText(abc.getIpAddress());
                Availabilty.setText(abc.getAvailability());
                AvalabilityUpdate(event);
                savemachine.setVisible(false);
                UpdateMAchine.setVisible(true);
                Availabilty.setVisible(true);
                IpAddress.requestFocus();

            } catch (Exception ex) {
            ex.printStackTrace();
            } 
        }
    }

    @FXML
    private void ipAddressTO(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            machineName.requestFocus();
        }
    }

    private void vgaTo(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            pcType.requestFocus();
        }
    }

    private void printReports(ActionEvent event) {
        try {
            Stage a = new Stage(StageStyle.UNDECORATED);
            Scene sc = new Scene(new FXMLLoader().load(getClass().getResource("print.fxml")));
            a.setScene(sc);
            a.show();
        } catch (Exception ex) {
            Log.error(ex);
        }

    }

    @FXML
    private void valiIp(KeyEvent event) {
        int a = IpAddress.getText().length();
        if (!("0123456789.".contains(event.getCharacter())) || a > 16) {
            event.consume();
        }
    }

    @FXML
    private void nameVali(KeyEvent event) {
        int a = machineName.getText().length();
        if ("0123456789".contains(event.getCharacter()) || a > 10) {
            event.consume();
        }
    }

    @FXML
    private void viewMachineTpes(ActionEvent event) {
        try {
            Stage stage = new Stage(StageStyle.UNDECORATED);
            Scene sc = new Scene(new FXMLLoader().load(getClass().getResource("machineType.fxml")));
            stage.setScene(sc);
            stage.show();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void comboset() {
        List list = new ArrayList();
        list.add("Member");
        list.add("Guest");
        ObservableList ol = FXCollections.observableArrayList(list);
        pcType.setItems(null);
        pcType.setItems(ol);
        pcType.setValue("Member");
    }

    private void clearcom() {
        pcType.setValue(null);
        pcType.setValue("Member");
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void AvalabilityUpdate(MouseEvent event) {
        String a = Availabilty.getText();
        if (a.equals("Available")) {
            Availabilty.setSelected(true);
        } else {
            Availabilty.setSelected(false);
        }
    }

    @FXML
    private void setMachineDetails(ActionEvent event) {
        if (Availabilty.isSelected()) {
            Availabilty.setText("Available");
        } else {
            Availabilty.setText("Not Available");
        }
    }

//    private void test() {
//        //MessageBox.showErrorMessage("Data Saved", "New Machine Saved");
//        MessageBox.showMessage(MessageBox.MessageType.ERROR, "happy", " ", "Error", new String[]{MessageBox.OK_BUTTON}, true);
//    }

    @FXML
    private void nameTo(KeyEvent event) {
    }
}
