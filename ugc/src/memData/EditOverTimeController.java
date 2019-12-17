package memData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class EditOverTimeController implements Initializable {

    @FXML
    private AnchorPane mainP;
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField duration;
    @FXML
    private JFXTextField charge;
    @FXML
    private JFXButton save;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        id();
    }    

    private void id() {
        id.setText(OverTimeTableController.getID);
        duration.setText(OverTimeTableController.getDU+"");
        charge.setText(OverTimeTableController.getCHARGE);
    }

    @FXML
    private void save(ActionEvent event) {
         try {
            int due= Integer.parseInt(duration.getText());
        if(due>1440){
             MessageBox.showErrorMessage("time input is wrong", "please check your inputs");
        }else{
           
            int mili=Integer.parseInt(duration.getText())*60000;
            
            ResultSet rs=new CRUDOperationsImpl().search("*", "charge", "where duration='"+mili+"' and charge='"+charge.getText()+"'");
            if (rs.next()) {
                MessageBox.showErrorMessage("error", "a simmiler type of charge plan is already in");
            }else{
                    
            new CRUDOperationsImpl().update("charge", "duration='"+mili+"',charge='"+charge.getText()+"'", "idCharge='"+id.getText()+"'");
            MessageBox.showInformationMessage("done", "charge plan updated");
            Log.info("charge plan ID: "+id.getText()+" updated");
           // OverTimeTableController.table();
            Stage stage=(Stage) save.getScene().getWindow();
            stage.close();
            }
            
        } 
        }catch(NumberFormatException e){
            MessageBox.showErrorMessage("error", "please check your inputs");
        }
         
         catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
        
    }

    @FXML
    private void val(KeyEvent event) {
        if (Character.isLetter(event.getCharacter().toCharArray()[0])) {
            event.consume();
        }
    }
    
}
