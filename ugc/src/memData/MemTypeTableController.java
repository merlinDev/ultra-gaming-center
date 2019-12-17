package memData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class MemTypeTableController implements Initializable {

    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> day;
    @FXML
    private TableColumn<?, ?> month;
    @FXML
    private TableColumn<?, ?> fee;
    @FXML
    private TableColumn<?, ?> overtime;
    @FXML
    private TableView<memberDetails> memType;

    private ObservableList<memberDetails> data;
    @FXML
    private JFXTextField search;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table();
    }    

    private void table() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs=new CRUDOperationsImpl().search("membership_type.*,charge.idCharge,charge.duration,charge.charge", "membership_type inner join charge on charge.idCharge=membership_type.charge_idCharge", "where idMembership_type like '"+search.getText()+"%' or typeName like '"+search.getText()+"%' and membership_type.status=1 order by idMembership_type DESC");
            while (rs.next()) {    
                int duration=Integer.parseInt(rs.getString("charge.duration"))/60000;
                data.add(new memberDetails(rs.getString("idMembership_type"), rs.getString("typeName"), rs.getString("durationday"), rs.getString("duration_month"), rs.getString("price"),"duration: "+duration+" min."+", charge: Rs."+rs.getString("charge.charge")));
            }
            
            id.setCellValueFactory(new PropertyValueFactory<>("idType"));
            name.setCellValueFactory(new PropertyValueFactory<>("typeName"));
            day.setCellValueFactory(new PropertyValueFactory<>("durationDay"));
            month.setCellValueFactory(new PropertyValueFactory<>("durationMonth"));
            fee.setCellValueFactory(new PropertyValueFactory<>("typePrice"));
            overtime.setCellValueFactory(new PropertyValueFactory<>("overTime"));
            
            memType.setItems(null);
            memType.setItems(data);
            
            
        }  catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {
        table();
    }
    
    
    
}
