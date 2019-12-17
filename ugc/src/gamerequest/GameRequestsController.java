/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamerequest;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author Isu
 */
public class GameRequestsController implements Initializable {

    @FXML
    private JFXTextField search;
    @FXML
    private Label numreq;
    @FXML
    private TableView<GameRequestDetails> table;
    @FXML
    private TableColumn<?, ?> nic;
    @FXML
    private TableColumn<?, ?> gname;
    @FXML
    private TableColumn<?, ?> description;

    /**
     * Initializes the controller class.
     */
    private ObservableList<GameRequestDetails> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        loadDataToTable();

    }

    @FXML
    private void searchGName(KeyEvent event) {
        String sear = search.getText();
        data.clear();
        try {
            CRUDOperations crud = new CRUDOperationsImpl();
            ResultSet rs = crud.search("*", "request", "WHERE gameName LIKE '" + sear + "%'");
            while (rs.next()) {
                data.add(new GameRequestDetails(rs.getString("nic"), rs.getString("gameName"), rs.getString("description")));
                nic.setCellValueFactory(new PropertyValueFactory<>("customernic"));
                gname.setCellValueFactory(new PropertyValueFactory<>("gamename"));
                description.setCellValueFactory(new PropertyValueFactory<>("descrip"));
            }
            int i=data.size();
            numreq.setText(i+"");
            table.setItems(null);
            table.setItems(data);
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

    private void loadDataToTable() {
        String sear = search.getText();
        try {
            CRUDOperations crud = new CRUDOperationsImpl();
            ResultSet rs = crud.search("*", "request", "");
            while (rs.next()) {
                data.add(new GameRequestDetails(rs.getString("nic"), rs.getString("gameName"), rs.getString("description")));
                nic.setCellValueFactory(new PropertyValueFactory<>("customernic"));
                gname.setCellValueFactory(new PropertyValueFactory<>("gamename"));
                description.setCellValueFactory(new PropertyValueFactory<>("descrip"));
            }
            int i=data.size();
            numreq.setText(i+"");
            table.setItems(null);
            table.setItems(data);
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

}
