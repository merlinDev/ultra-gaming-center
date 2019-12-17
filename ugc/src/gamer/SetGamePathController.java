/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamer;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.Utility;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class SetGamePathController implements Initializable {

    @FXML
    private MenuItem setGamePath;
    @FXML
    private TableColumn<?, ?> game;
    @FXML
    private TableColumn<?, ?> path;
    @FXML
    private JFXTextField search;

    ObservableList<pathDetails> data;
    @FXML
    private TableView<pathDetails> table;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showTable();
        
    }    

    @FXML
    private void setGamePath(ActionEvent event) {
        
        try {
            String idGame=table.getSelectionModel().getSelectedItem().game.get().replace(" ", "").split(":")[0];
            System.out.println(idGame);
            
            FileChooser f=new FileChooser();
            FileChooser.ExtensionFilter exe=new FileChooser.ExtensionFilter("exe games (*.exe)", "*.exe");
            f.getExtensionFilters().add(exe);
            File filePath=f.showOpenDialog(search.getScene().getWindow());
            System.out.println(path);
            String addPath=filePath.toString().replace("\\", "/");
            new CRUDOperationsImpl().update("machine_has_games", "path='"+addPath+"'", "games_idGames='"+idGame+"'");
            System.out.println("updated");
            MessageBox.showInformationMessage("done", "game path updated");
            showTable();
        }catch(NullPointerException e){}
        
         catch (Exception ex) {
            Log.error(ex);
        }
        
        
    }


    private void showTable() {
        try {
            data=FXCollections.observableArrayList();
            String s=search.getText();
            String pcId=new Utility().getMachineId();
            ResultSet rs= new CRUDOperationsImpl().search("games.idGames,games.name,machine_has_games.path", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", "where machine_idMachine='"+pcId+"' and games.name like '"+s+"%'");
            while (rs.next()) {                
               data.add(new pathDetails(rs.getString("games.idGames")+": "+rs.getString("games.name"), rs.getString("machine_has_games.path")));
            }
               game.setCellValueFactory(new PropertyValueFactory<>("game"));
               path.setCellValueFactory(new PropertyValueFactory<>("path"));
               
               table.setItems(null);
               table.setItems(data);
               
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void search(KeyEvent event) {
        showTable();
    }
   
}
