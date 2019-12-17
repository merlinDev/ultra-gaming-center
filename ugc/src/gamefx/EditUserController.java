/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import accounts.user;
import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class EditUserController implements Initializable {

    @FXML
    private JFXTextField MType;
    @FXML
    private JFXTextField Pprice;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField Uname;
    @FXML
    private JFXTextField nic;
    @FXML
    private JFXTextField Email;
    @FXML
    private JFXTextField phneNo;
    private JFXTextField Npassword;
    private JFXTextField ConPassword;
    @FXML
    private JFXTextField Gname;

    public String j = user.getIdUser();
    @FXML
    private JFXPasswordField NewPassword;
    @FXML
    private JFXPasswordField ConfirmPassword;
    @FXML
    private Label MsgLabel;
    @FXML
    private Label ConfirmPasswordMsgLabel;
    @FXML
    private AnchorPane progress;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        load();
    }

    @FXML
    private void Save(ActionEvent event) {
        try {
            System.out.println("Save Button ekta awa");
            CRUDOperations crudo = new CRUDOperationsImpl();
            crudo.update("user", "gamerName='" + Gname.getText() + "',username='" + Uname.getText() + "',email='" + Email.getText() + "',phone='" + phneNo.getText() + "', pass='" + new Utility().MD5(ConfirmPassword.getText()) + "'", "idUser='" + j + "'");
            MessageBox.showInformationMessage("Account Updating", "Account Details updated Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void Cancel(ActionEvent event) {

        Stage s = (Stage) name.getScene().getWindow();
        s.close();
    }

    private void load(){
        
            try {
                CRUDOperations crudo = new CRUDOperationsImpl();
                ResultSet rs = crudo.search("*", "user", "where idUser='" + j + "'");
                if (rs.next()) {
                    name.setText(rs.getString("name"));
                    Uname.setText(rs.getString("username"));
                    Email.setText(rs.getString("email"));
                    nic.setText(rs.getString("nic"));
                    phneNo.setText(rs.getString("phone"));
                    Gname.setText(rs.getString("gamerName"));
                    //NewPassword.setText(rs.getString("pass"));
                    ResultSet rs1 = crudo.search("typeName,price", "membership_type",
                            "where idmembership_type IN (SELECT membership_type_idMembership_type FROM user_has_membership_type WHERE user_idUser='" + j + "')");
                    if (rs1.next()) {
                        MType.setText(rs1.getString("typeName"));
                        Pprice.setText(rs1.getString("price"));
                    }
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            progress.getChildren().clear();
            Node node;
        try {
            node = (Node) (FXMLLoader.load(getClass().getResource("/memProgress/memProgress.fxml")));
            progress.getChildren().setAll(node);
        } catch (Exception ex) {
            Log.error(ex);
        }
            
        }
    

    @FXML
    private void CountCharacters(KeyEvent event) {
        try {
            if (NewPassword.getLength() < 7) {
                MsgLabel.setText("Minimum Length 8 characters");
                ConfirmPassword.setVisible(false);
            } else {
                ConfirmPassword.setVisible(true);
                MsgLabel.setText(" ");
                MsgLabel.setText("Password Length is correct");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* private void ConfirmPassword(KeyEvent event) {
        try {
           
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

 /* private void confirmpass(MouseEvent event) {
        try {
             Parent rt=FXMLLoader.load(getClass().getResource("confirmPassword.fxml"));
                  Scene s=new Scene(rt);
                  Stage st=new Stage(); 
                  st.setScene(s);
                  st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    private void OnkeyReleased(KeyEvent event) {
        try {
            String s = NewPassword.getText();
            String l = ConfirmPassword.getText();
            if (!(l.equals(s))) {
                ConfirmPasswordMsgLabel.setText("Password doesn't match");
                System.out.println("if:" + ConfirmPassword.getText() + "not matching");

            } else {
                System.out.println("Password ok");
                ConfirmPasswordMsgLabel.setText(" ");
                ConfirmPasswordMsgLabel.setText("Password is OK ");
                System.out.println("else:" + ConfirmPassword.getText() + "matching");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
