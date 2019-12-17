/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameManagement;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import gamefx.MessageBox;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class AddnewGame2Controller implements Initializable {

    @FXML
    private JFXTextField gameid;
    @FXML
    private JFXTextField image;
    @FXML
    private JFXCheckBox sell;
    @FXML
    private JFXCheckBox play;
    @FXML
    private JFXTextArea descripiton;
    @FXML
    private JFXTextField gamename;
    @FXML
    private JFXComboBox<String> gametype;
    static String t;
    @FXML
    private Label gamelabel;
    @FXML
    private JFXListView<String> listView;

    private CRUDOperationsImpl db = new CRUDOperationsImpl();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sell.setSelected(false);
        play.setSelected(false);
        listView.setVisible(false);
        Load();
        Id();
    }

    @FXML
    private void Cancel(ActionEvent event) {
        System.out.println("close");
        Stage s = (Stage) gameid.getScene().getWindow();
        s.close();
    }

    @FXML
    private void Save(ActionEvent event) {
        try {
            if (gamename.getText().equals("") || gametype.getSelectionModel().getSelectedItem().equals("") || image.getText().equals("") || descripiton.getText().equals("")) {
                MessageBox.showErrorMessage("Error!!!", "No Details Added!");
            } else {
                String s = gamename.getText().replace("'", " ").replace("-", " ");
                String f = gametype.getSelectionModel().getSelectedItem();
                ResultSet rs = db.search("'OK'", "games", "WHERE name='" + gamename.getText() + "'");
                if (rs.first()) {
                    MessageBox.showErrorMessage("Game already exists.", "This game already exists. Use game update window to update details.");
                    initialize(null, null);
                    gamename.setText("");
                    descripiton.setText("");
                    image.setText("");
                    Load();
                    return;
                }
                rs = new CRUDOperationsImpl().search("idGameType", "gametype", " where typename='" + f + "'");
                if (rs.next()) {
                    System.out.println(t);
                    String l = rs.getString("idGameType");
                    new CRUDOperationsImpl().save("games(name,gametype_idGameType,description,status,background,sellplay)", "'" + s + "','" + l + "','" + descripiton.getText() + "','" + "active" + "','" + image.getText() + "','" + t + "' ");
                    MessageBox.showInformationMessage("Saved!", "'" + gamename.getText() + "'Added successfully ");
                    gamename.setText(null);
                    image.setText(null);
                    descripiton.setText(null);
                    sell.setSelected(false);
                    play.setSelected(false);
                    Load();
                    Id();
                    listView.setVisible(false);
                }
            }

        } catch (Exception e) {
            MessageBox.showErrorMessage("ERROR!!!", "Some details are missing");
            e.printStackTrace();
        }
    }

    @FXML
    private void SearchGameName(KeyEvent event) {
        try {
            if (gamename.getText().equals("")) {
                listView.setVisible(false);
            } else {
                listView.setVisible(true);
                ResultSet rs = new CRUDOperationsImpl().search("*", "games", "where name like'" + gamename.getText() + "%' ");
                ArrayList<String> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
                ObservableList<String> e = FXCollections.observableArrayList(list);
                listView.setItems(e);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddnewType(ActionEvent event) {
        try {
            listView.setVisible(false);
            System.out.println("addnewtype");
            Parent rt = FXMLLoader.load(getClass().getResource("AddnewType.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setScene(s);
            st.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Load() {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            ResultSet rs = crudo.search("typeName", "gametype", " ");
            List<String> list = new ArrayList();
            while (rs.next()) {
                list.add(rs.getString("typeName"));
            }
            ObservableList<String> t = FXCollections.observableList(list);
            gametype.setItems(t);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void sellselected(ActionEvent event) {
        try {
            listView.setVisible(false);
            if (!(sell.isSelected()) && play.isSelected()) {
                t = "play";
                System.out.println("play only in sell selected");
            } else if (sell.isSelected() && !(play.isSelected())) {
                t = "sell";
                System.out.println("sell only in sell selected");
            } else if (sell.isSelected() && play.isSelected()) {
                t = "both";
                System.out.println("sell and play in sell selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void playonlyselected(ActionEvent event) {
        try {
            listView.setVisible(false);
            if (play.isSelected() && !(sell.isSelected())) {
                t = "play";
                System.out.println("play only in play only seleced");
            } else if (play.isSelected() && sell.isSelected()) {
                t = "both";
                System.out.println("sell and play in sell selected");
            } else if (sell.isSelected() && !(play.isSelected())) {
                t = "sell";
                System.out.println("sell only in sell selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Id() {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            ResultSet rs2 = crudo.search("count(idGames) as x ", "games", "");
            while (rs2.next()) {
                int x = Integer.parseInt(rs2.getString("x")) + 1;
                gameid.setText("UGC" + x);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void imageView(KeyEvent event) {
    }

    /*  private void refresh(ActionEvent event) {
        try {
            Load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    private void typeNameLoad(MouseEvent event) {
        try {
            listView.setVisible(false);
            Load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void image3(MouseEvent event) {
        listView.setVisible(false);
    }

    @FXML
    private void image(MouseEvent event) {
        listView.setVisible(false);
    }

    @FXML
    private void image2(MouseEvent event) {
        listView.setVisible(false);
    }

}
