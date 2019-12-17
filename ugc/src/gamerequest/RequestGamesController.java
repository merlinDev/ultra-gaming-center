/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamerequest;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Isu
 */
public class RequestGamesController implements Initializable {

    @FXML
    private JFXTextField gname;
    @FXML
    private JFXTextArea description;
    @FXML
    private JFXButton send;
    @FXML
    private JFXTextField nic;
    private ImageView image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        File file = new File(new Utility().getTempDir() + "downloaded.jpg");
//        Image image = new Image(file.toURI().toString());
//        this.image.setImage(image);
        // TODO
        nic.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                System.out.println("Textfield on focus");
            } else {
                //when focus lost
                if ((!nic.getText().trim().matches("[0-9]{9}([0-9]{3})?[x|X|v|V]"))) {
                    
                    Alert al2 = new Alert(Alert.AlertType.ERROR, "Invalid NIC...!!!", ButtonType.OK);
                    al2.showAndWait();
                    if (al2.getResult() == ButtonType.OK) {
                        al2.close();
                    }
                }
            }
        });
    }

    @FXML
    private void sendRequest(ActionEvent event) {
        boolean flag = true;
        try {
            String id = nic.getText();
            String gameName = gname.getText();
            String des = description.getText();
            CRUDOperations crud = new CRUDOperationsImpl();
            if (gameName == null || gameName.equals("") || id == null || id.equals("")) {
                Alert a1 = new Alert(Alert.AlertType.ERROR, "You must enter your NIC and the game name you want to request...!", ButtonType.OK);
                a1.showAndWait();
                if (a1.getResult() == ButtonType.OK) {
                    a1.close();
                }
            } else {
                ResultSet rs = crud.search("*", "request", "");
                while (rs.next()) {
                    if (rs.getString("nic").equals(id) && rs.getString("gameName").equals(gameName)) {
                        flag = false;
                        Alert a2 = new Alert(Alert.AlertType.ERROR, "You have requested this game...!", ButtonType.OK);
                        a2.showAndWait();
                        if (a2.getResult() == ButtonType.OK) {
                            a2.close();
                        }
                        break;
                    }
                }
                if (flag) {

                    crud.save("request(nic, gameName, description)", "'" + id + "','" + gameName + "', '" + des + "'");
                    Alert a = new Alert(Alert.AlertType.INFORMATION, "Your request sent...!", ButtonType.OK);
                    a.showAndWait();
                    if (a.getResult() == ButtonType.OK) {
                        a.close();
                    }
                }
                nic.getText();
                gname.setText("");
                description.setText("");

            }

        } catch (SQLException ex) {
            Log.error(ex);
        } catch (ClassNotFoundException ex) {
            Log.error(ex);
        } catch (URISyntaxException ex) {
            Log.error(ex);
        } catch (IOException ex) {
            Log.error(ex);
        }
    }

}
