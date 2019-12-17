/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalsoftwareproject;

import accounts.user;
import com.jfoenix.controls.JFXDrawer;
import gamefx.Log;
import gamefx.Utility;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class AdminHomeController implements Initializable {

    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane anchor2;
    @FXML
    private Label time;
    @FXML
    private Label date;
    @FXML
    private AnchorPane logout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        RunApp.downloadFile();
        logout.setEffect(new DropShadow(10, Color.GRAY));
        try {
            Utility.setDateTime(date, time);
            FXMLLoader fXMLLoader = new FXMLLoader();
            VBox box = fXMLLoader.load(getClass().getResource("SidePanelContent.fxml").openStream());
            SidePanelContentController contentController = fXMLLoader.getController();
            contentController.setAnchorPane(anchor2);
            drawer.setSidePane(box);
            drawer.open();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void lougout(ActionEvent event) {
        user.logout();
    }

}
