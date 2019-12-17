/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memData;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.Utility;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class SetTempPassController implements Initializable {

    @FXML
    private JFXPasswordField pass;
    @FXML
    private JFXPasswordField conPass;
    @FXML
    private JFXButton done;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    private void setPass(String tempPassID) {
        if (pass.getText().equals(conPass.getText())) {
            try {
                String savePass = new Utility().MD5(pass.getText());
                new CRUDOperationsImpl().update("user", "pass='" + savePass + "'", "idUser='" + tempPassID + "'");
                MessageBox.showInformationMessage("done", "temp. password added");
                Stage s = (Stage) done.getScene().getWindow();
                s.close();
            } catch (Exception ex) {
                Log.error(ex);
            }
        }
    }

    @FXML
    private void done(ActionEvent event) {
        setPass(MemTableController.tempPassID);
    }

}
