/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class StoragetypeController implements Initializable {

    @FXML
    private JFXButton button;
    @FXML
    private JFXTextField type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void save(MouseEvent event) {
        try {
            CRUDOperations crudo=new CRUDOperationsImpl();
          crudo.save("storagemedium(type)","'"+type.getText()+"'");
          
          Notifications notificationBuilder=Notifications.create()
                    .title("Succesful")
                    .text("Storage Type Added")
                    .graphic(null)
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Click on Notification");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
          Stage stage=(Stage) type.getScene().getWindow();
          stage.close();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
    
}
