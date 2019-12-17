package memData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.jfoenix.controls.JFXButton;
import gamefx.Log;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class ShowMembersController implements Initializable {

    @FXML
    private AnchorPane  tablePane;
    @FXML
    private JFXButton members;
    @FXML
    private JFXButton memberType;
    @FXML
    private JFXButton overtime;
    @FXML
    private Label type;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            members(null);
            type.setText("members...");
        }  catch (Exception ex) {
            Log.error(ex);
        }
    }    

    @FXML
    private void members(ActionEvent event) throws IOException {
        type.setText("members...");
        tablePane.getChildren().clear();
        Node node= (Node) (FXMLLoader.load(getClass().getResource("memTable.fxml")));
        tablePane.getChildren().setAll(node);
    }

    @FXML
    private void memberType(ActionEvent event) throws IOException {
        type.setText("membership types...");
        tablePane.getChildren().clear();
        Node node= (Node) (FXMLLoader.load(getClass().getResource("memTypeTable.fxml")));
        tablePane.getChildren().setAll(node);
    }

    @FXML
    private void overtime(ActionEvent event) throws IOException {
        type.setText("charge plans...");
        tablePane.getChildren().clear();
        Node node= (Node) (FXMLLoader.load(getClass().getResource("overTimeTable.fxml")));
        tablePane.getChildren().setAll(node);
    }
    
}
