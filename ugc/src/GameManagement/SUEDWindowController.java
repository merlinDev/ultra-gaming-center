/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameManagement;

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
import com.jfoenix.controls.JFXCheckBox;
import gamefx.MessageBox;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableColumn<gameMachine, String> type;
    private ImageView imv;
    private JFXTextField diskType;
    String ID;
    @FXML
    private ListView<String> listId;
    @FXML
    private JFXButton upb;
    @FXML
    private JFXCheckBox play;
    @FXML
    private AnchorPane PanelAvailability;
    @FXML
    private JFXTextField EditImageURL;
    @FXML
    private JFXCheckBox sell;
    @FXML
    private JFXCheckBox editimageurlCB;
    @FXML
    private JFXCheckBox EditAll;
    String sellplay;
    @FXML
    private MenuItem namefortable;
    @FXML
    private MenuButton sortby;
    String s;
    @FXML
    private TableView<gameMachine> table;
    @FXML
    private TableColumn<gameMachine, String> MachineNO;
    @FXML
    private TableColumn<gameMachine, String> Availability;
    @FXML
    private TableColumn<gameMachine, String> inuse;
    @FXML
    private JFXTextField typegame;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CRUDOperationsImpl crudoi = new CRUDOperationsImpl();
        load();
        EditImageURL.setEditable(false);
        name.setEditable(false);
        description.setEditable(false);

        // suggestions();
    }

    @FXML
    private void Disable(ActionEvent event) {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            ResultSet rt = crudo.search("*", "games", "where idGames='" + gmeid.getText() + "'");
            if (rt.next()) {

                crudo.update("games", "status='" + "deactive" + "'", "idGames='" + gmeid.getText() + "'");
                Search.clear();
                gmeid.clear();
                name.clear();
                description.clear();
                typegame.clear();
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
            if (editimageurlCB.isSelected() || EditAll.isSelected()) {
                if (editimageurlCB.isSelected()) {
                    new CRUDOperationsImpl().update("games", "background='" + EditImageURL.getText() + "'", " idGames='" + ID + "'");
                    MessageBox.showInformationMessage("UpDated", "Image URL Updated Successfully");
                    editimageurlCB.setSelected(false);

                } else {
                    System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
                    CRUDOperations crudo = new CRUDOperationsImpl();
                    if ((name.getText().equals("")) || (description.getText().equals(""))) {
                        MessageBox.showErrorMessage("ERROR", "No Details");
                    } else {
                        String f = name.getText().replace("'", " ").replace("-", " ");
                        crudo.update("games", "name='" + f + "',description='" + description.getText() + "',background='" + EditImageURL.getText() + "',sellplay='" + sellplay + "' ", "idGames='" + ID + "'");
                        MessageBox.showInformationMessage("UpDated", "UpDated Succcesfully");
                        EditAll.setSelected(false);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void lol(MouseEvent event) {
        //suggestions();
    }

    @FXML
    private void clear(MouseEvent event) {

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
    ObservableList<gameMachine> list;

    @FXML
    private void Table(MouseEvent event) {
        try {
            if (s.equals("2")) {
                String s = listId.getSelectionModel().getSelectedItem();
                CRUDOperationsImpl crudo = new CRUDOperationsImpl();
                ResultSet rs1 = crudo.search("*", "games", "where name='" + s + "'");
                if (rs1.next()) {
                    String l = rs1.getString("idGames");
                    ResultSet rs = crudo.search("*", "machine", "WHERE idMachine IN (SELECT machine_idMachine FROM machine_has_games WHERE games_idGames='" + l + "')");
                    list = FXCollections.observableArrayList();
                    while (rs.next()) {
                        list.add(new gameMachine(rs.getString("idMachine"), rs.getString("availability"), rs.getString("inuse"), rs.getString("type")));
                    }
                    MachineNO.setCellValueFactory(new PropertyValueFactory<>("idMachine"));
                    Availability.setCellValueFactory(new PropertyValueFactory<>("availability"));
                    inuse.setCellValueFactory(new PropertyValueFactory<>("inuse"));
                    type.setCellValueFactory(new PropertyValueFactory<>("type"));
                    table.setItems(null);
                    table.setItems(list);
                }
            } else {
                System.out.println("Table");
                String s = listId.getSelectionModel().getSelectedItem();
                CRUDOperations crudo = new CRUDOperationsImpl();
                ResultSet rs = crudo.search("*", "games", "where name='" + s + "'");
                if (rs.next()) {
                    ID = rs.getString("idGames");
                    gmeid.setText(rs.getString("idGames"));
                    name.setText(rs.getString("name"));
                    description.setText(rs.getString("description"));
                    EditImageURL.setText(rs.getString("background"));
                    String k = rs.getString("gameType_idGameType");
                    ResultSet rs2 = crudo.search("*", "Gametype", "where idGameType='" + k + "'");
                    if (rs2.next()) {
                        typegame.setText(rs2.getString("typeName"));
                        System.out.println(rs2.getString("typeName"));
                    }
                    int i = 0;
                    ResultSet rs3 = crudo.search("qty", "stock", "where  games_idGames='" + rs.getString("idGames") + "'");
                    while (rs3.next()) {
                        i += rs3.getInt("qty");

                    }
                    qty.setText(i + "");
                    sellplay = rs.getString("sellplay");
                    if (sellplay.equals("sell")) {
                        sell.setSelected(true);
                        play.setSelected(false);
                    } else if (sellplay.equals("play")) {
                        play.setSelected(true);
                        sell.setSelected(false);
                    } else {
                        sell.setSelected(true);
                        play.setSelected(true);
                    }
                    sell.setDisable(true);
                    play.setDisable(true);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Add(ActionEvent event) {
        try {
            Parent rt = FXMLLoader.load(getClass().getResource("addgames.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setScene(s);
            st.setTitle("Add New Game");
            st.initStyle(StageStyle.DECORATED);
            st.setResizable(false);
            st.show();
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
            st.initStyle(StageStyle.DECORATED);
            st.setResizable(false);
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
            if (Search.getText().equals("")) {
                listId.setItems(null);
                table.setItems(null);
            } else if (s.equals("1")) {
                CRUDOperations crudo = new CRUDOperationsImpl();
                ResultSet rs = crudo.search("*", "games", "where name like'" + Search.getText() + "%' && status='" + "active" + "' ");
                ArrayList<String> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
                ObservableList<String> e = FXCollections.observableArrayList(list);
                listId.setItems(e);
            } else {
                CRUDOperations crudo = new CRUDOperationsImpl();
                ResultSet rs = crudo.search("*", "games", "where name like '" + Search.getText() + "%' && status='" + "active" + "'");
                ArrayList<String> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
                ObservableList<String> t = FXCollections.observableArrayList(list);
                listId.setItems(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void playCheckbox(ActionEvent event) {
        try {
            if (play.isSelected() && !(sell.isSelected())) {
                sellplay = "play";
                System.out.println("play only in play only seleced");
            } else if (play.isSelected() && sell.isSelected()) {
                sellplay = "both";
                System.out.println("sell and play in sell selected");
            } else if (sell.isSelected() && !(play.isSelected())) {
                sellplay = "sell";
                System.out.println("sell only in sell selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void load() {
        try {
            PanelAvailability.setVisible(false);
            s = "1";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ToNormalWindow(ActionEvent event) {
        try {
            background.setVisible(true);
            PanelAvailability.setVisible(false);
            s = "1";
        } catch (Exception e) {
        }
    }

    @FXML
    private void ToTableWindow(ActionEvent event) {
        try {
            background.setVisible(false);
            PanelAvailability.setVisible(true);
            s = "2";
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void EidtImageURL(ActionEvent event) {
        try {
            if (editimageurlCB.isSelected()) {
                EditImageURL.setEditable(true);
                System.out.println("edit krnna puluwan");
            } else {
                EditImageURL.setEditable(false);
                System.out.println("edit krnna ba");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void EditAll(ActionEvent event) {
        try {
            if (EditAll.isSelected()) {
                name.setEditable(true);
                description.setEditable(true);
                sell.setDisable(false);
                play.setDisable(false);
            } else {
                name.setEditable(false);
                description.setEditable(false);
                sell.setDisable(true);
                play.setDisable(true);
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void sellcheckbox(ActionEvent event) {
        try {
            if (play.isSelected() && !(sell.isSelected())) {
                sellplay = "play";
                System.out.println("play only in play only seleced");
            } else if (play.isSelected() && sell.isSelected()) {
                sellplay = "both";
                System.out.println("sell and play in sell selected");
            } else if (sell.isSelected() && !(play.isSelected())) {
                sellplay = "sell";
                System.out.println("sell only in sell selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddtoMachineGames(ActionEvent event) {
        try {
            Parent rt = FXMLLoader.load(getClass().getResource("addGamestoMachine.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setScene(s);
            st.setTitle("Set Games to Machine");
            st.initStyle(StageStyle.DECORATED);
            st.setMaximized(false);
            st.setResizable(false);
            st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields(MouseEvent event) {

        try {
            gmeid.setText("");
            name.setText("");
            typegame.setText("");
            description.setText("");
            EditImageURL.setText("");
            play.setSelected(false);
            sell.setSelected(false);
            editimageurlCB.setSelected(false);
            EditAll.setSelected(false);
            qty.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setPath(ActionEvent event) {
        try {
            Parent rt = FXMLLoader.load(getClass().getResource("/gamer/setGamePath.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setScene(s);
            st.setTitle("Set Game path to Machine");
            st.initStyle(StageStyle.DECORATED);
            st.setMaximized(false);
            st.setResizable(false);
            st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
