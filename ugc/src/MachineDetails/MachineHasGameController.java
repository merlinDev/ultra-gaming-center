/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author PASINDHU
 */
public class MachineHasGameController implements Initializable {

    private ResultSet rs;
    private CRUDOperations crud;
    private ObservableList data = FXCollections.observableArrayList();

    @FXML
    private JFXListView<?> allGames;
    @FXML
    private TableView<machineHasGames> AllMachines;
    @FXML
    private TableColumn<?, ?> machineId;
    @FXML
    private TableColumn<?, ?> machineName;
    @FXML
    private TableColumn<?, ?> machineType;
    @FXML
    private TableColumn<?, ?> ipAddress;
    @FXML
    private JFXTextField searchMachine;
    @FXML
    private JFXButton refresh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new CRUDOperationsImpl();
        loadGames();
        loadMachine();
    }

    @FXML
    private void showGames(MouseEvent event) {

        List<String> list = new ArrayList<>();
        try {
            if (event.getClickCount() == 2) {
                machineHasGames abc = AllMachines.getSelectionModel().getSelectedItem();
                String mId = abc.getMachineId().toString();
                rs = crud.search("games.name,machine_has_games.games_idGames,machine_has_games.machine_idMachine", "games,machine_has_games", "where machine_has_games.machine_idMachine  like '" + mId + "' ");
                while (rs.next()) {
                    list.add(rs.getString("gname"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList li = FXCollections.observableArrayList(list);
        allGames.setItems(null);
        allGames.setItems(li);
    }

    @FXML
    private void search(KeyEvent event) {
        try {
            if (!(searchMachine.getText() == null)) {
                rs = crud.search("*", "machine", "where ipAddress like '" + searchMachine.getText() + "%' or type like '" + searchMachine.getText() + "%' or idMachine like '" + searchMachine.getText() + "%' or name like '" + searchMachine.getText() + "%'");
                data = FXCollections.observableArrayList();
                while (rs.next()) {
                    machineHasGames mhg = new machineHasGames(rs.getString("idMachine"), rs.getString("name"), rs.getString("type"), rs.getString("ipAddress"));
                    data.add(mhg);
                    machineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
                    machineName.setCellValueFactory(new PropertyValueFactory<>("machinename"));
                    machineType.setCellValueFactory(new PropertyValueFactory<>("machineType"));
                    ipAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllMachines.setItems(null);
        AllMachines.setItems(data);
    }

    @FXML
    private void refreshAll(ActionEvent event) {
        searchMachine.setText(null);
        loadGames();
        loadMachine();
    }

    private void loadGames() {
        List<String> list = new ArrayList<>();
        try {
            rs = crud.search("*", "games", "");
            while (rs.next()) {
                list.add(rs.getString("name"));
            }
            ObservableList li = FXCollections.observableArrayList(list);
            allGames.setItems(null);
            allGames.setItems(li);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMachine() {
        try {
            rs = crud.search("*", "machine", "");
            data = FXCollections.observableArrayList();
            while (rs.next()) {
                machineHasGames mhg = new machineHasGames(rs.getString("idMachine"), rs.getString("name"), rs.getString("type"), rs.getString("ipAddress"));
                data.add(mhg);
                machineId.setCellValueFactory(new PropertyValueFactory<>("machineId"));
                machineName.setCellValueFactory(new PropertyValueFactory<>("machinename"));
                machineType.setCellValueFactory(new PropertyValueFactory<>("machineType"));
                ipAddress.setCellValueFactory(new PropertyValueFactory<>("ipAddress"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AllMachines.setItems(null);
        AllMachines.setItems(data);
    }

}
