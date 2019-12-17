/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memProgress;

import accounts.user;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.Utility;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class MemProgressController implements Initializable {

    @FXML
    private TableView<progressDetails> table;
    @FXML
    private TableColumn<?, ?> game;
    @FXML
    private TableColumn<?, ?> duration;
    @FXML
    private JFXTextField search;
    
    ObservableList<progressDetails> data;
    @FXML
    private Label playerName;
    
    public static String userID;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addName();
        addToTable();
    }    

    private void addToTable() {
        data=FXCollections.observableArrayList();
        try {
            if (user.getUserTypeId()!=user.ADMINISTRATOR) {
                System.out.println("true");
                userID=user.getIdUser();
            }
            
            ResultSet rs=new CRUDOperationsImpl().search("sum(play_log.`duration`) as duration,user.`name` , games.`name`", "`play_session` play_session INNER JOIN `play_log` play_log ON play_session.`play_session_id` = play_log.`play_session_play_session_id` INNER JOIN `user` user ON play_session.`user_idUser` = user.`idUser` INNER JOIN `games` games ON play_log.`games_idGames` = games.`idGames`", "where gamecenter.user.`idUser`='"+userID+"' and games.name like '"+search.getText()+"%' group by games.idGames order by duration DESC");
            while (rs.next()) {      
                String[] time=new Utility().timeSplitter(Long.parseLong(rs.getString("duration")));
                String hh=time[0];
                String mm=time[1];
                String ss=time[2];
                String durationTime=hh+" Hours, "+mm+"mins, "+ss+"sec";
                data.add(new progressDetails(rs.getString("games.name"), durationTime));
            }
            game.setCellValueFactory(new PropertyValueFactory<>("game"));
            duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
            
            table.setItems(null);
            table.setItems(data);
        }  catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {
        addToTable();
    }

    private void addName() {
        
        if (user.getUserTypeId()!=user.ADMINISTRATOR) {
            userID=user.getIdUser();
        }
        try {
            ResultSet rs=new CRUDOperationsImpl().search("username", "user", "where idUser='"+userID+"'");
            while (rs.next()) {                
                playerName.setText(rs.getString("username"));
            }
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Logger.getLogger(MemProgressController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
