/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import GameManagement.*;
import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.MessageBox;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class AddnewTypeController implements Initializable {

    @FXML
    private JFXTextField newType;
    @FXML
    private ListView<String> showtype;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showtype.setVisible(false);
        // TODO
    }

    @FXML
    private void save(ActionEvent event) {
         try {
            CRUDOperations crudo=new CRUDOperationsImpl();
            ResultSet rs= crudo.search("*","gameType","where typeName='"+newType.getText()+"'");
             if (rs.first()) {
                 MessageBox.showErrorMessage("Type already Exists","Type already Exist");
                 Stage s=(Stage) newType.getScene().getWindow();
                s.close();
             }else{
             crudo.save("storagemedium(type)","'"+newType.getText()+"'");
            System.out.println(newType.getText());
        MessageBox.showInformationMessage("New Type Added ","New Type Added Successfully!");
        Stage s=(Stage) newType.getScene().getWindow();
        s.close();
             }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        try {
            Stage s=(Stage) newType.getScene().getWindow();
        s.close();
        } catch (Exception e) {
            e.printStackTrace();;
        }
    }

    @FXML
    private void showtype(KeyEvent event) {
        try {
            if (newType.getText().equals("")) {
                showtype.setVisible(false);
            } else {
                showtype.setVisible(true);
                CRUDOperations crudo=new CRUDOperationsImpl();
        ResultSet rs=crudo.search("*","storagemedium","where type like'"+newType.getText()+"%' ");
            ArrayList<String> list=new ArrayList<>();
            while (rs.next()) {
                list.add(rs.getString("type"));

            }
            ObservableList<String> l= FXCollections.observableArrayList(list);
            showtype.setItems(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 }

}
