/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import gamefx.Log;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author PASINDHU
 */
public class MachineTypeController implements Initializable {
    private ResultSet rs;
    private CRUDOperations crud;
    
    private ObservableList<machineType> data;
    
    @FXML
    private TableView<machineType> table2;
    @FXML
    private TableColumn<?, ?> machineType;
    @FXML
    private TableColumn<?, ?> machineCount;
    @FXML
    private TableColumn<?, ?> availCount;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new CRUDOperationsImpl();
        loadall();
        // TODO
    }    

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    private void loadall() {
        data = FXCollections.observableArrayList();
        
        try {
            rs = crud.search("distinct(type) , count(*)as count , count(if(availability = 'a',1, Null)) 'avail'", "gamecenter.machine","group by type ");
          
            while (rs.next()) {
               machineType m = new machineType(rs.getString("type"),rs.getString("count"),rs.getString("avail"));
               data.add(m);
               System.out.println(m);
               machineType.setCellValueFactory(new PropertyValueFactory<>("type"));
               machineCount.setCellValueFactory(new PropertyValueFactory<>("count"));
               availCount.setCellValueFactory(new PropertyValueFactory<>("avail"));
            }
        }  catch (Exception ex) {
            Log.error(ex);
        }
        
        table2.setItems(null);
        table2.setItems(data);
    }
    
}
