/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memData;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import gamefx.Log;
import gamefx.MessageBox;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class ChangeMembershipController implements Initializable {
    
    @FXML
    private Label memName;
    @FXML
    private Label curMemType;
    @FXML
    private Label expDate;
    @FXML
    private JFXComboBox<String> memberships;
    @FXML
    private JFXButton save;

    public static String memID;
    public static String memTypeName;
    public static String memberName;
    public static String date;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        memName.setText(memberName);
        curMemType.setText(memTypeName);
        expDate.setText(date);
        try {
            ResultSet rs=new CRUDOperationsImpl().search("*", "membership_type", "where status='1'");
            memberships.getItems().clear();
            while(rs.next()){
                memberships.getItems().add(rs.getString("idMembership_type")+": "+rs.getString("typeName"));
            }
            
        } catch (Exception ex) {
            Log.error(ex);
        }
    }    

    @FXML
    private void save(ActionEvent event) {
        try {
            String typeId=memberships.getSelectionModel().getSelectedItem().replace(" ", "").split(":")[0];
            new CRUDOperationsImpl().update("user_has_membership_type", "membership_type_idmembership_type='"+typeId+"', member_date=CURDATE()", "user_idUser='"+memID+"'");
            MessageBox.showInformationMessage("done", "membership updated");
            
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
    
}
