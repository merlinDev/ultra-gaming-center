package memData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.Utility;
import java.io.IOException;
import java.net.ProtocolException;
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
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class OverTimeTableController implements Initializable {

    @FXML
    private TableView<memberDetails> table;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> duration;
    @FXML
    private TableColumn<?, ?> charge;

    private ObservableList<memberDetails> data;
    @FXML
    private JFXTextField search;
    @FXML
    private MenuItem edit;

    
    public static String getID;
    public static int getDU;
    public static String getCHARGE;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table();
    }

    public void table() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = new CRUDOperationsImpl().search("*", "charge", "where idCharge like '"+search.getText()+"%' order by idCharge DESC");
            while (rs.next()) {
                String[] du = new Utility().timeSplitter(rs.getLong("duration") / 1000);
                String duration=du[0]+"hs  "+du[1]+"min";
                data.add(new memberDetails(rs.getString("idCharge"),duration, rs.getString("charge")));
            }

            id.setCellValueFactory(new PropertyValueFactory<>("idCharge"));
            duration.setCellValueFactory(new PropertyValueFactory<>("charge"));
            charge.setCellValueFactory(new PropertyValueFactory<>("durationCharge"));

            table.setItems(null);
            table.setItems(data);

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {
        table();
    }

    @FXML
    private void goEdit(ActionEvent event) throws IOException {
       String dataID= (String) id.getCellData(table.getSelectionModel().getSelectedIndex());
        try {
            ResultSet rs=new CRUDOperationsImpl().search("duration", "charge", "where idCharge='"+dataID+"'");
            while (rs.next()) {                
                getDU=rs.getInt("duration")/60000;
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
       String dataCHARGE= (String) charge.getCellData(table.getSelectionModel().getSelectedIndex());
        getID=dataID;
        getCHARGE=dataCHARGE;  
        System.out.println(data);
        Stage stage=new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/memData/editOverTime.fxml"))));
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.show();
        
    }

    
}
