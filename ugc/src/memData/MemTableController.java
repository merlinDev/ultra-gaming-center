package memData;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import overtimePayment.OvertimeController;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class MemTableController implements Initializable {

    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> email;
    @FXML
    private TableColumn<?, ?> contact;
    @FXML
    private TableColumn<?, ?> username;
    @FXML
    private TableColumn<?, ?> nic;
    @FXML
    private TableColumn<?, ?> status;
    @FXML
    private TableColumn<?, ?> id;

     private static ObservableList<memberDetails> data;
    // 748 337
    @FXML
    private TableView<memberDetails> memTable;
    @FXML
    private JFXTextField search;
    @FXML
    private MenuItem deactivate;
    @FXML
    private ContextMenu contex;
    @FXML
    private MenuItem Activate;
    @FXML
    private MenuItem banMemer;
     
    public static String tempPassID;
    @FXML
    private MenuItem changeMembership;
    @FXML
    private MenuItem progress;
    @FXML
    private MenuItem showDetails;
    @FXML
    private MenuItem overtimePay;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        table();
    }    

    private void table() {
        data = FXCollections.observableArrayList();
        String s=search.getText();
        try {
            ResultSet rs=new CRUDOperationsImpl().search("*", "user", " where (idUser like '"+s+"%' or name like '"+s+"%' or nic like '"+s+"%' or email like '"+s+"%' or phone like '"+s+"%' or username like '"+s+"%') and status!='blocked' and usertype_idUserType!='1' order by idUser DESC");
            while (rs.next()) {                
                data.add(new memberDetails(rs.getString("idUSer"), rs.getString("name"), rs.getString("nic"), rs.getString("email"), rs.getString("username"),rs.getString("phone"), rs.getString("status")));
            }
            
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
            email.setCellValueFactory(new PropertyValueFactory<>("email"));
            username.setCellValueFactory(new PropertyValueFactory<>("username"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
            
            memTable.setItems(null);
            memTable.setItems(data);
            
            
        }  catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {
        table();
    }

    @FXML
    private void deactivateMember(ActionEvent event) {
        try {
            String id=memTable.getSelectionModel().getSelectedItem().getId();
            new CRUDOperationsImpl().update("user","status='inactive'", "idUser='"+id+"'");
            gamefx.Log.info("member ID: "+id+" deactivated");
            
            MessageBox.showInformationMessage("done", "member account deactivated");
            table();
        } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
            gamefx.Log.error(ex);
        }
    }

    @FXML
    private void activateMember(ActionEvent event) {
        try {
            String id=memTable.getSelectionModel().getSelectedItem().getId();
            new CRUDOperationsImpl().update("user","status='active'", "idUser='"+id+"'");
            gamefx.Log.info("member ID: "+id+" activated");
            MessageBox.showInformationMessage("done", "member account activated");
            table();
        } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
            Logger.getLogger(MemTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void banMember(ActionEvent event) {
          try {
            String id=memTable.getSelectionModel().getSelectedItem().getId();
            new CRUDOperationsImpl().update("user","status='blocked'", "idUser='"+id+"'");
            gamefx.Log.info("member ID: "+id+" banned");
            MessageBox.showInformationMessage("done", "member account Banned");
            table();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    

    @FXML
    private void changeMembership(ActionEvent event) {
        try {
            String id=memTable.getSelectionModel().getSelectedItem().getId();
            System.out.println(id);
            
            ResultSet rs=new CRUDOperationsImpl().search("user.*,user_has_membership_type.*,membership_type.typeName", "user inner join user_has_membership_type on user.idUser=user_has_membership_type.user_idUser inner join membership_type on membership_type.idMembership_type=user_has_membership_type.membership_type_idMembership_type", "where user.idUser='"+id+"'");
            while (rs.next()) {                
                ChangeMembershipController.memID=id;
                ChangeMembershipController.memTypeName=rs.getString("membership_type.typeName");
                ChangeMembershipController.memberName=rs.getString("user.name");
                ChangeMembershipController.date=rs.getString("user_has_membership_type.member_date");
            }
            Stage stage=new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/memData/changeMembership.fxml"))));
                stage.setTitle("membership type");
                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.show();
                System.out.println("showed");
                
        }  catch (Exception ex) {
            Log.error(ex);
        }
        
    }

    @FXML
    private void showProgress(ActionEvent event) {
        try {
            memProgress.MemProgressController.userID=memTable.getSelectionModel().getSelectedItem().getId();
            Stage stage=new Stage();
            stage.setTitle("gaming progress");
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(new Scene(new FXMLLoader().load(getClass().getResource("/memProgress/memProgress.fxml"))));
            stage.show();
        } catch (Exception ex) {
            Log.error(ex);
        }
        
    }

    @FXML
    private void showDetails(ActionEvent event) {
        try {
            ShowMemLogController.memID=memTable.getSelectionModel().getSelectedItem().getId();
            Stage stage=new Stage();
            stage.setTitle("member progress");
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.setScene(new Scene(new FXMLLoader().load(getClass().getResource("/memData/showMemLog.fxml"))));
            stage.show();
        }  catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void overtimePayments(ActionEvent event) {
        Stage stage=new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/overtimePayment/overtime.fxml"))));
            stage.setTitle("overtime payments");
            OvertimeController.userID=memTable.getSelectionModel().getSelectedItem().getId();
            stage.show();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
    
}
