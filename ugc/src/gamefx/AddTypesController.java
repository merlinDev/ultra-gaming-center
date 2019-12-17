/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class AddTypesController implements Initializable {

    @FXML
    private JFXTextField newType;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    

    @FXML
    private void save(ActionEvent event) {
        try {
            CRUDOperations crudo=new CRUDOperationsImpl();
        crudo.save("gameType(typeName)","'"+newType.getText()+"'");
            System.out.println(newType.getText());
        MessageBox.showInformationMessage("New Type Added ","New Type Added Successfully!");
        Stage s=(Stage) newType.getScene().getWindow();
        s.close();
        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage s=(Stage) newType.getScene().getWindow();
        s.close();
    }

   
    
}
