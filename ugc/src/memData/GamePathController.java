package memData;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class GamePathController implements Initializable {

    @FXML
    private JFXTextField gameID;
    @FXML
    private JFXTextField gameName;
    @FXML
    private JFXComboBox<String> gamCat;
    private JFXButton setIcon;
    @FXML
    private JFXComboBox<String> setRatings;
    @FXML
    private JFXButton addGame;
    @FXML
    private JFXTextField gamePath;
    @FXML
    private JFXButton browse;
    @FXML
    private JFXTextArea desc;
            
    private static String path;
    
    @FXML
    private JFXCheckBox sell;
    @FXML
    private JFXCheckBox play;
    @FXML
    private JFXTextField iconURL;
    @FXML
    private JFXTextField backgroundURL;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gameID();

        sell.setSelected(true);
        play.setSelected(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gamePath.requestFocus();
            }
        });
        try {
            
            ResultSet rs=new CRUDOperationsImpl().search("idGameType,typeName", "gameType", ""); 
            while(rs.next()){
                System.out.println(rs.getString("typeName"));
               gamCat.getItems().add(rs.getString("idGameType")+":"+rs.getString("typeName"));
            }
            rs=new CRUDOperationsImpl().search("*", "rating","");
            while(rs.next()){
                System.out.println(rs.getString("idRating")+":"+rs.getString("rating"));
                setRatings.getItems().add(rs.getString("idRating")+" : "+rs.getString("rating"));
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }    

    @FXML
    private void addGame(ActionEvent event) {
       
        try {
                    String cat=gamCat.getSelectionModel().getSelectedItem().split(":")[0];
                    String rating=setRatings.getSelectionModel().getSelectedItem().replace(" ", "").split(":")[0];
                    System.out.println(cat);
                    System.out.println(rating);
                    System.out.println("printed");
                    if (gamePath.getText().equals("") || gameName.getText().equals("") || iconURL.getText().equals("") || backgroundURL.getText().equals("")) {
                        System.out.println("message error");
                        MessageBox.showErrorMessage("error", "some details missing");
                    }else{
                          path=gamePath.getText().replace("\\", "/");
                          new CRUDOperationsImpl().save("games(name,gametype_idGameType,description,status,rating_idRating,gamePath,icon,background)",
                          "'"+gameName.getText()+"','"+Integer.parseInt(cat)+"','"+desc.getText()+"','active','"+Integer.parseInt(rating)+"','"+path+"','"+iconURL.getText()+"','"+backgroundURL.getText()+"'");
                          
                          MessageBox.showInformationMessage("done!", "game added");
                          gameName.setText(null);
                          desc.setText(null);
                          gamePath.setText(null);
                          //showIcon.setText(null);
                          //showBG.setText(null);
                          cat="";
                          iconURL.setText(null);
                          backgroundURL.setText(null);
                          gamCat.getSelectionModel().clearSelection();
                          setRatings.getSelectionModel().clearSelection();
                          rating="";
                          gameID();
                          
                    } 
        }catch(NullPointerException e){
            System.out.println("lllllllllll");
            e.printStackTrace();
        }
         catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setPath(ActionEvent event) {
        try {
            FileChooser dir=new FileChooser();
            dir.setTitle("select game path");
            File path=dir.showOpenDialog(browse.getScene().getWindow());
            System.out.println(path);
            AnchorPane a=new AnchorPane();
            gamePath.setText(path.toString());
        }catch(NullPointerException e){
         e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void setIcon(ActionEvent event) {
//        try {
//        FileChooser f=new FileChooser();
//        icon = f.showOpenDialog(setIcon.getScene().getWindow()).toString();
//        System.out.println(icon);
//        String replace = icon.replace("\\", "/");
//        String[] split = replace.split("/");
//        System.out.println(split[split.length-1]);
//        
//        showIcon.setText(split[split.length-1]);
//        }catch(NullPointerException e){
//            System.out.println("lol");
//        }
//        catch (Exception e) {
//         e.printStackTrace();
//        }
//    }

//    private void setBG(ActionEvent event) {
//        try {
//        FileChooser f=new FileChooser();
//        bgImage = f.showOpenDialog(setIcon.getScene().getWindow()).toString();
//        String replace = bgImage.replace("\\", "/");
//        String[] split = replace.split("/");
//        System.out.println(split[split.length-1]);
//        
//        showBG.setText(split[split.length-1]);
//        }catch(NullPointerException e){
//            System.out.println("lol");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    private void sellPlay(ActionEvent event) {
        if (!gamePath.getText().equals("")) {
            if (sell.isSelected() && !play.isSelected()) {
            path="";
            System.out.println(path);
            gamePath.setDisable(true);
        }else if(play.isSelected()){
            path=gamePath.getText().replace("\\", "/");
            gamePath.setDisable(false);
        }
        }
    }

    private void gameID() {
        try {
            ResultSet rs = new CRUDOperationsImpl().search("count(idGames) as x", "games", "");
            int x;
            while(rs.next()){
                x=Integer.parseInt(rs.getString("x"))+1;
                System.out.println(x);
                gameID.setText("GAME:"+x);
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
    
}
