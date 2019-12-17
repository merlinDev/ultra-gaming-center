/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameManagement;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class AddGamestoMachineController implements Initializable {

    @FXML
    private ListView<String> gamelist;
    @FXML
    private TableView<tabletable> table;
    @FXML
    private TableColumn<tabletable, String> IPaddress;
    @FXML
    private TableColumn<tabletable, String> name;
    @FXML
    private TableColumn<tabletable, String> type;
    @FXML
    private JFXTextField GameID;
    @FXML
    private JFXTextField GameName;
    @FXML
    private JFXTextField seaGame;

    @FXML
    private MenuButton sortby;
    @FXML
    private ListView<String> pclist;
    @FXML
    private JFXTextField seachpc;

    @FXML
    private TableColumn<tabletable, String> Machineid;
    @FXML
    private ContextMenu contextMenu;

    ObservableList<tabletable> l;
    @FXML
    private TableColumn<tabletable, CheckBox> selectMachines;

    String id;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        l = FXCollections.observableArrayList();
        gamelist.setVisible(false);
        // Load();
        //sortby();
        pclist.setVisible(false);
        // TODO
    }

    /* private void SearchGame(ActionEvent event) {
        try {
            if (true) {
                
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       
    }*/
    @FXML
    private void add(ActionEvent event) {

        for (int j = 0; j < table.getItems().size(); j++) {
            if (table.getItems().get(j).getSelectMachineStatus()) {
                String machineId = table.getItems().get(j).getMachineid();
                try {
                    ResultSet rs1 = new CRUDOperationsImpl().search("machine_idMachine, games_idGames", "machine_has_games", "where machine_idMachine='" + machineId + "' and games_idGames='" + id + "'");
                    if (rs1.next()) {
                        MessageBox.showErrorMessage("Error", "Machine No: " + machineId + "" + " " + "" + "Already have" + "" + " " + "" + GameName.getText() + " ");
                    } else {
                        System.out.println(table.getItems().get(j).getMachineid() + " - " + table.getItems().get(j).getSelectMachineStatus());
                        CRUDOperationsImpl crudo = new CRUDOperationsImpl();
                        crudo.save("machine_has_games(machine_idMachine,games_idGames)", "'" + machineId + "','" + id + "'");
                        MessageBox.showInformationMessage("Added!", " Successfully Added to Machine:" + machineId + "");

                    }
                } catch (Exception ex) {
                    Log.error(ex);
                }
            }
        }
        table.setItems(null);
        pclist.setVisible(false);

    }


    /* private void SearchGame(KeyEvent event) {
         try {
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    private void getSelected(MouseEvent event) {
        try {

            String l = gamelist.getSelectionModel().getSelectedItem();
            CRUDOperationsImpl crudo = new CRUDOperationsImpl();
            ResultSet rs = crudo.search("idGames,name", "games", "where name='" + l + "'");
            if (rs.next()) {
                id = rs.getString("idGames");

                GameName.setText(rs.getString("name"));
                GameID.setText(rs.getString("idGames"));
                gamelist.setVisible(false);
                gamelist.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Searchpc(KeyEvent event) {
        try {
            pclist.setVisible(false);
            if (!(seachpc.getText().equals(""))) {
                pclist.setVisible(true);
                CRUDOperationsImpl crudo = new CRUDOperationsImpl();
                ResultSet rs = crudo.search("*", "machine", "where idMachine like'" + seachpc.getText() + "%' and availability='" + "a" + "' ");
                ArrayList<String> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(rs.getString("idMachine"));
                }
                ObservableList<String> k = FXCollections.observableArrayList(list);
                pclist.setItems(k);

            } else {
                pclist.setVisible(false);
                System.out.println("values");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addtotable(MouseEvent event) {

        try {
            gamelist.setVisible(false);
            String d = pclist.getSelectionModel().getSelectedItem();

            CRUDOperationsImpl crudo = new CRUDOperationsImpl();
            ResultSet rs = crudo.search("*", "machine", "where idMachine='" + d + "'");
            boolean b = true;
            for (int i = 0; i <table.getItems().size(); i++) {
                if (table.getItems().get(i).getMachineid().equals(pclist.getSelectionModel().getSelectedItem())) {
                    b = false;
                    MessageBox.showWarningMessage("Warning", "Machine already added to the table");
                }
            }
            if (b) {
                while (rs.next()) {
                    l.add(new tabletable(rs.getString("name"), rs.getString("IPAddress"), rs.getString("type"), rs.getString("idMachine")));
                }

                Machineid.setCellValueFactory(new PropertyValueFactory<>("machineid"));
                IPaddress.setCellValueFactory(new PropertyValueFactory<>("IPaddress"));
                name.setCellValueFactory(new PropertyValueFactory<>("Name"));
                type.setCellValueFactory(new PropertyValueFactory<>("type"));
                selectMachines.setCellValueFactory(new PropertyValueFactory<>("selectMachine"));
                table.setItems(null);
                table.setItems(l);
                System.out.println("lol");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Load() {
        try {
            CRUDOperationsImpl crudoi = new CRUDOperationsImpl();
            ResultSet rs = crudoi.search("*", "machine", "where availability='" + "a" + "'");
            ObservableList<tabletable> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(new tabletable(rs.getString("IPAddress"), rs.getString("name"), rs.getString("type"), rs.getString("idMachine")));
            }
            Machineid.setCellValueFactory(new PropertyValueFactory<>("machineid"));
            IPaddress.setCellValueFactory(new PropertyValueFactory<>("IPaddress"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            selectMachines.setCellValueFactory(new PropertyValueFactory<>("selectMachine"));
            table.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortby() {
        try {
            pclist.setVisible(false);
            gamelist.setVisible(false);
            MenuButton l = new MenuButton();
            ObservableList<CheckMenuItem> list = FXCollections.observableArrayList();
            CheckMenuItem item = new CheckMenuItem("Member");
            CheckMenuItem item1 = new CheckMenuItem("Guest");
            list.addAll(item, item1);
            sortby.getItems().addAll(list);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addtoall(ActionEvent event) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sort(MouseEvent event) {
        try {
            if (sortby.getText().equals("Member")) {
                System.out.println("member");
            } else {
                System.out.println("guest");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void member(ActionEvent event) {
        try {
            gamelist.setVisible(false);
            CRUDOperationsImpl crudo = new CRUDOperationsImpl();
            ResultSet rs = crudo.search("*", "machine", "where type='" + "member" + "' and availability='" + "a" + "'");
            ObservableList<tabletable> list = FXCollections.observableArrayList();
            while (rs.next()) {

                list.add(new tabletable(rs.getString("IPAddress"), rs.getString("name"), rs.getString("type"), rs.getString("idMachine")));
                System.out.println("member Details tynwa");
            }
            Machineid.setCellValueFactory(new PropertyValueFactory<>("machineid"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            IPaddress.setCellValueFactory(new PropertyValueFactory<>("IPAdrress"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            selectMachines.setCellValueFactory(new PropertyValueFactory<>("selectMachine"));
            table.setItems(null);
            table.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void guest(ActionEvent event) {
        try {
            gamelist.setVisible(false);
            CRUDOperationsImpl crudo = new CRUDOperationsImpl();
            ResultSet rs = crudo.search("*", "machine", "where type='" + "guest" + "' and availability='" + "a" + "'");
            ObservableList<tabletable> list = FXCollections.observableArrayList();
            while (rs.next()) {
                list.add(new tabletable(rs.getString("IPAddress"), rs.getString("name"), rs.getString("type"), rs.getString("idMachine")));
                System.out.println("guest details tynwa");;
            }
            IPaddress.setCellValueFactory(new PropertyValueFactory<>("IPAddress"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            type.setCellValueFactory(new PropertyValueFactory<>("type"));
            Machineid.setCellValueFactory(new PropertyValueFactory<>("machineid"));
            selectMachines.setCellValueFactory(new PropertyValueFactory<>("selectMachine"));
            table.setItems(null);
            table.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void select(ActionEvent event) {
        try {
            int s = table.getSelectionModel().getSelectedIndex();
            if (s >= 0) {
                table.getItems().remove(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void NewSearch(KeyEvent event) {
        try {
            if (!(seaGame.getText().equals(""))) {
                gamelist.setVisible(true);
                CRUDOperationsImpl crudo = new CRUDOperationsImpl();
                ResultSet rs = crudo.search("name", "games", "where name like '" + seaGame.getText() + "%'&& status='" + "active" + "'");
                ArrayList<String> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(rs.getString("name"));
                }
                ObservableList<String> o = FXCollections.observableArrayList(list);
                gamelist.setItems(o);
            } else {
                gamelist.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void cleartable(ActionEvent event) {
        try {
            table.setItems(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
