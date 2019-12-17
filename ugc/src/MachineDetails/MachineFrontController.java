package MachineDetails;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import connection.Command;
import connection.Speaker;
import gamefx.Log;
import gamefx.MessageBox;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class private ResultSet rs; private CRUDOperations crud;
 *
 * @author PASINDHU
 */
public class MachineFrontController implements Initializable {

    private ResultSet rs;
    private CRUDOperations crud;

    private CheckBox checkBox;

    private ObservableList<machineFront> data;

    @FXML
    private JFXTextField searchMachine;
    @FXML
    private TableView<machineFront> machineDetails;
    @FXML
    private TableColumn<?, ?> machineNo;
    @FXML
    private TableColumn<?, ?> machineName;
    @FXML
    private TableColumn<?, ?> avail;
    @FXML
    private TableColumn<?, ?> state;
    @FXML
    private TableColumn<?, ?> machineType;
    private TableColumn<?, ?> stateCheck;
    @FXML
    private JFXButton shutDownAll;
    @FXML
    private JFXButton refresh;
    @FXML
    private TableColumn<?, ?> ipAddress;
    @FXML
    private JFXButton allGame;
    @FXML
    private JFXButton viewMachine;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new CRUDOperationsImpl();
        loadMacine();
    }

    @FXML
    private void searchpc(ActionEvent event) {
        data = FXCollections.observableArrayList();
        try {
            if (!(searchMachine.getText() == null)) {
                rs = crud.search("*", "machine", "where idMachine like'" + searchMachine.getText() + "%' or name like'" + searchMachine.getText() + "%' or availability like'" + searchMachine.getText() + "%' or inUse like'" + searchMachine.getText() + "%' or type like'" + searchMachine.getText() + "%'");

                while (rs.next()) {
                    System.out.println("searching....");
                    machineFront mf = new machineFront(rs.getString("idMachine"), rs.getString("ipAddress"), rs.getString("name"), rs.getString("availability").replace("a", "Available").replace("n/", "Not "), rs.getString("inUse").replace("available", "Available").replace("in", "In"), rs.getString("type").replace("g", "G").replace("mem", "Mem"));
                    data.add(mf);
                }
            }
            machineNo.setCellValueFactory(new PropertyValueFactory<>("idMachine"));
            ipAddress.setCellValueFactory(new PropertyValueFactory<>("ip"));
            machineType.setCellValueFactory(new PropertyValueFactory<>("type"));
            machineName.setCellValueFactory(new PropertyValueFactory<>("name"));
            avail.setCellValueFactory(new PropertyValueFactory<>("availability"));
            state.setCellValueFactory(new PropertyValueFactory<>("inUse"));

            machineDetails.setItems(null);
            machineDetails.setItems(data);

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void loadMacine() {
        data = FXCollections.observableArrayList();
        try {
            rs = crud.search("*", "machine", "");
            while (rs.next()) {
                machineFront mf = new machineFront(rs.getString("idMachine"), rs.getString("ipAddress"), rs.getString("name"), rs.getString("availability").replace("a", "Available").replace("n/", "Not "), rs.getString("inUse").replace("available", "Available").replace("in", "In"), rs.getString("type").replace("g", "G").replace("mem", "Mem"));
                data.add(mf);
                System.out.println(mf);
                machineNo.setCellValueFactory(new PropertyValueFactory<>("idMachine"));
                ipAddress.setCellValueFactory(new PropertyValueFactory<>("ip"));
                machineType.setCellValueFactory(new PropertyValueFactory<>("type"));
                machineName.setCellValueFactory(new PropertyValueFactory<>("name"));
                avail.setCellValueFactory(new PropertyValueFactory<>("availability"));
                state.setCellValueFactory(new PropertyValueFactory<>("inUse"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        machineDetails.setItems(null);
        machineDetails.setItems(data);
    }

    @FXML
    private void mouse(MouseEvent event) {
        if (event.getClickCount() == 2) {
            machineFront abc = machineDetails.getSelectionModel().getSelectedItem();

            String machineno = abc.getIdMachine();

            if (gamefx.MessageBox.showMessage(gamefx.MessageBox.MessageType.CONFIRMATION, "Machine Shut Down", null, "Want To Shut Down Machine??", new String[]{gamefx.MessageBox.OK_BUTTON, gamefx.MessageBox.CANCEL_BUTTON}, false).equals(gamefx.MessageBox.OK_BUTTON)) {

                try {
                    rs = crud.search("ipAddress", "machine", "WHERE idMachine='" + machineno + "'");
                    rs.first();
                    if (Speaker.send(rs.getString("ipAddress"), Command.SHUTDOWN).equals(Command.OK)) {
                        MessageBox.showInformationMessage("Command Sent", "Shutdown command sent to machine " + machineno);
                    } else {
                        MessageBox.showErrorMessage("Command Send Failed", "Failed to send shutdown command to " + machineno);
                    }
                } catch (Exception ex) {
                    Log.error(ex);
                }
                refreshAll();
            }

        }
    }

    @FXML
    private void shutDownAll(ActionEvent event) {
        if (MessageBox.showConfirmationMessage("Are you sure you want to shutdown all computers?", "Confirm Action", new String[]{"YES", "NO"}).equals("YES")) {
            data.forEach(e -> {
                try {
                    rs = crud.search("ipAddress", "machine", "WHERE idMachine='" + e.getIdMachine() + "'");
                    rs.first();
                    Speaker.send(rs.getString("ipAddress"), Command.SHUTDOWN);
                } catch (Exception ex) {
                    Log.error(ex);
                }
            });
            refreshAll();
        }
    }

    private void refreshAll() {
        machineDetails.setItems(null);
        loadMacine();
        searchMachine.setText(null);
    }

    @FXML
    private void refreshTable(ActionEvent event) {
        machineDetails.setItems(null);
        loadMacine();
        searchMachine.setText(null);
    }

    @FXML
    private void loadGames(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MachineDetails/machineHasGame.fxml"));
            Stage guestToken = new Stage();
            guestToken.setScene(new Scene(root));
            guestToken.setMaximized(false);
            guestToken.setTitle("Machine Game Details");
            guestToken.initStyle(StageStyle.UTILITY);
            guestToken.setResizable(false);
            guestToken.show();
        } catch (IOException ex) {
            Log.error(ex);
        }

    }

    @FXML
    private void viewAddMachines(ActionEvent event) {
         try {
            Parent root = FXMLLoader.load(getClass().getResource("/MachineDetails/Machine.fxml"));
            Stage guestToken = new Stage();
            guestToken.setScene(new Scene(root));
            guestToken.setMaximized(false);
            guestToken.setTitle("Machine Details");
            guestToken.initStyle(StageStyle.UTILITY);
            guestToken.setResizable(false);
            guestToken.show();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

}
