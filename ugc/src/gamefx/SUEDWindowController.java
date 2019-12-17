/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import com.cyclotech.repository.CRUDOperations;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class SUEDWindowController implements Initializable {
    
    
    @FXML
    private JFXButton Disable;
    @FXML
    private AnchorPane background;
    @FXML
    private JFXTextField Search;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextArea description;
    @FXML
    public JFXTextField gmeid;
    private JFXTextField price;
    @FXML
    private JFXTextField qty;
    @FXML
    private JFXTextField type;
    @FXML
    private ImageView imv;
    private JFXTextField diskType;
    int i;
    @FXML
    private ListView<String> listId;
    @FXML
    private ImageView backgroundImage;
    @FXML
    private JFXButton upb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CRUDOperationsImpl crudoi = new CRUDOperationsImpl();
        // suggestions();

    }

    @FXML
    private void Disable(ActionEvent event) {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            ResultSet rt = crudo.search("*", "games", "where idGames='" + gmeid.getText() + "'");
            if (rt.next()) {

                crudo.update("games", "status='"+"deactive"+"'", "idGames='" + gmeid.getText() + "'");
                Search.clear();
                gmeid.clear();
                name.clear();
                description.clear();
                type.clear();
                qty.clear();            
                MessageBox.showWarningMessage("Game Deactivated!!!", "Game is Deactivate by user");
                listId.setItems(null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(MouseEvent event) {
        try {
            Parent rt = FXMLLoader.load(getClass().getResource("FXML.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setTitle("New Game");
            st.setScene(s);
            st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void update(ActionEvent event) {
        try {
            System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
            CRUDOperations crudo = new CRUDOperationsImpl();
            String s = name.getText();
            String l = description.getText();
            System.out.println(l);
            System.out.println(s);
            //System.out.println(!(l==null && l.equals("")) && (s!=null && s.equals("")));
            if ((l!=null && !(l.equals(""))) ) {
                if((s!=null && !(s.equals("")))){
                System.out.println("Update una");
               crudo.update("games", "name='" + name.getText() + "', description='" + description.getText() + "'", "idGames='" + gmeid.getText() + "'");
               MessageBox.showInformationMessage("Updated", "Updated successfully");
               Search.clear();
                gmeid.clear();
                name.clear();
                description.clear();
                type.clear();
                qty.clear();
                imv.setImage(null);
                }
                
            } else {
                
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

   /* private void More(ActionEvent event) {
        try {
            Parent rt = FXMLLoader.load(getClass().getResource("more.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setTitle("Deactivated Games");
            st.setScene(s);
            st.show();
        } catch (Exception e) {
        }
    }*/

   /* public void suggestions() {

        List<String> l = new ArrayList<>();
        String[] g = new String[i];
        CRUDOperations crudo = new CRUDOperationsImpl();
       try {
            int id = Integer.parseInt(crudo.getLastId("idGames", "games"));
            for (int j = 0; j < 30; j++) {

                java.sql.ResultSet fg = crudo.search("name", "games", "where idGames='" + id +"'");
                id--;
                while (fg.next()) {
                    l.add(fg.getString("name"));
                }
                ObservableList n = FXCollections.observableArrayList(l);
                listId.setItems(null);
                listId.setItems(n);
         }
      }catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    private void lol(MouseEvent event) {
        //suggestions();
    }

    @FXML
    private void clear(MouseEvent event) {
                Search.clear();
                gmeid.clear();
                name.clear();
                description.clear();
                type.clear();
                qty.clear();
                
                price.clear();
                imv.setImage(null);
    }

    private void Menu(ActionEvent event) {
         try {
             Parent rt = FXMLLoader.load(getClass().getResource("Menu.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.initStyle(StageStyle.UNDECORATED);
            st.setScene(s);
            st.show();
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void Table(MouseEvent event) {
        try {
            System.out.println("Table");
           String s=listId.getSelectionModel().getSelectedItem();
            CRUDOperations crudo=new CRUDOperationsImpl();
            ResultSet rs=crudo.search("*","games","where name='"+s+"'");
            if (rs.next()) {
                gmeid.setText(rs.getString("idGames"));
                name.setText(rs.getString("name"));
                description.setText(rs.getString("description"));
                Image im=new Image(rs.getString("icon"));
                 imv.setImage(im);
               
               String k=rs.getString("gameType_idGameType");
                ResultSet rs2=crudo.search("*","Gametype", "where idGameType='"+k+"'");
                if (rs2.next()) {
                    type.setText(rs2.getString("typeName"));
                }
                int i=0;
                ResultSet rs3=crudo.search("qty","stock","where  games_idGames='"+rs.getString("idGames")+"'");
                while (rs3.next()) {                    
                   i+=rs3.getInt("qty");
                   
                }
                qty.setText(i+"");
             
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        try {
            Parent rt=FXMLLoader.load(getClass().getResource("gamePath.fxml"));
            Scene s=new Scene(rt);
            Stage st=new Stage();
            st.setScene(s);
            st.show();
            //Stage l=(Stage) menu.getScene().getWindow();
            // l.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    @FXML
    private void Activate(ActionEvent event) {
         try {
            
            Parent rt = FXMLLoader.load(getClass().getResource("more.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setTitle("Deactivated Games");
            st.setScene(s);
            st.show();
             //Stage l=(Stage) menu.getScene().getWindow();
             //l.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Search(KeyEvent event) {
         try {
           
            CRUDOperations crudo = new CRUDOperationsImpl();
            ResultSet qw = crudo.search("*", "games", "where name like '" + Search.getText() + "%'&& status='"+"active"+"'");
          ArrayList <String>li=new ArrayList<>(); 
            while(qw.next()) {      
              
                                                    
                    li.add(qw.getString("name"));        
                        
       }
            ObservableList<String> l=FXCollections.observableArrayList(li);
                    listId.setItems(l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }



