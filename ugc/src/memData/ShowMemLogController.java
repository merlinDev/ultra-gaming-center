/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memData;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class ShowMemLogController implements Initializable {

    @FXML
    private TableView<loginDetails> logTable;
    @FXML
    private TableColumn<?, ?> times;
    @FXML
    private TableView<loginDetails> timeTable;
    @FXML
    private TableColumn<?, ?> game;
    @FXML
    private TableColumn<?, ?> duration;
    @FXML
    private JFXDatePicker datePick;

    ObservableList<loginDetails> data;
    ObservableList<loginDetails> data2;
    
    public static String memID;
    static String date="";
    @FXML
    private TableColumn<?, ?> start;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        table();
    }    

    private void table() {
        
        try {
            data=FXCollections.observableArrayList();
            
            ResultSet rs=new CRUDOperationsImpl().search("*", "play_session", "where startTime like '"+date+"%' and user_idUser='"+memID+"'");
            while (rs.next()) {                
                data.add(new loginDetails(rs.getString("play_session_id")+" :"+rs.getDate("startTime")+" "+rs.getTime("startTime")));
            }
            times.setCellValueFactory(new PropertyValueFactory<>("date"));
            
            logTable.setItems(null);
            logTable.setItems(data);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setDate(ActionEvent event) {
         System.out.println(datePick.getValue());
         date=datePick.getValue().toString();
         table();
    }

    @FXML
    private void showGamePlay(MouseEvent event) {
        data2=FXCollections.observableArrayList();
        try {
            String session=logTable.getSelectionModel().getSelectedItem().date.get();
            session=session.replace(" ", "").split(":")[0];
            System.out.println(session);
            
            ResultSet rs=new CRUDOperationsImpl().search("games.name,play_log.*", "play_log inner join games on games.idGames=play_log.games_idGames","where play_session_play_session_id='"+session+"'");
            while (rs.next()) {      
                String[] time=new Utility().timeSplitter(rs.getLong("play_log.duration"));
                String playTime=time[0]+"hours ,"+time[1]+"mins and "+time[2]+"secs";
                data2.add(new loginDetails(rs.getString("games.name"),playTime,rs.getDate("play_log.dateTime")+" "+rs.getTime("play_log.dateTime").toString()));
            }
            game.setCellValueFactory(new PropertyValueFactory<>("game"));
            start.setCellValueFactory(new PropertyValueFactory<>("started"));
            duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
            timeTable.setItems(null);
            timeTable.setItems(data2);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
    
}
