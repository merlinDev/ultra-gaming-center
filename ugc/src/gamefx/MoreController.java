/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class MoreController implements Initializable {

    String l;
    @FXML
    private TableView<GameStatus> table;
    @FXML
    private TableColumn<GameStatus, String> game;
    @FXML
    private TableColumn<GameStatus, String> status;
    String s;
    @FXML
    private JFXButton button;
    @FXML
    private TableColumn<?, ?> gmeids;
    @FXML
    private AnchorPane panel;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadData();
    }

    private ObservableList<GameStatus> d = FXCollections.observableArrayList();

    public void LoadData() {
        try {
            game.setText("name");
            status.setText("status");
            CRUDOperations crudo = new CRUDOperationsImpl();
            String s = "deactive";
            ResultSet rs = crudo.search("name, status , idGames", "gamecenter.games", "where status='deactive'");
            
            table.setItems(null);
            while (rs.next()) {
                d.add(new GameStatus( rs.getString("idGames"),rs.getString("name"),rs.getString("status")));
            }
            gmeids.setCellValueFactory(new PropertyValueFactory<>("idGames"));
            game.setCellValueFactory(new PropertyValueFactory<>("name"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            table.setItems(d);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tableEdit(MouseEvent event) {
        try {
            String name = table.getSelectionModel().getSelectedItem().getName();
       CRUDOperations crudo=new CRUDOperationsImpl();
       ResultSet rs=crudo.search("idGames","games", "where name='"+name+"'");
       
            if(rs.next()) {   
                String s=rs.getString("idGames");
                crudo.update("games", "status='active'","idGames='"+s+"'");
                MessageBox.showInformationMessage("Updated","'"+name+"'");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void button(ActionEvent event) {
        try{
            String name = table.getSelectionModel().getSelectedItem().getName();
       CRUDOperations crudo=new CRUDOperationsImpl();
       ResultSet rs=crudo.search("idGames","games", "where name='"+name+"'");
       
            if(rs.next()) {   
                String s=rs.getString("idGames");
                crudo.update("games", "status='active'","idGames='"+s+"'");
                MessageBox.showInformationMessage("Updated","'"+name+"',Activated Successfully");
                Stage t=(Stage)table.getScene().getWindow();
                t.close();
        }
        }catch (Exception e) {
        }
    }

    /*@FXML
    private void cancel(MouseEvent event) {
       

    }*/

    private void cancel(ActionEvent event) {
         
    }

    @FXML
    private void windowclose(ActionEvent event) {
        Stage s=(Stage) cancel.getScene().getWindow();
        s.close();
    }


}
