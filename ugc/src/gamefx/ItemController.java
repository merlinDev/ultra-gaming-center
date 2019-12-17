/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import accounts.user;
import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class ItemController implements Initializable {

    @FXML
    private JFXComboBox<String> type;
    @FXML
    private JFXButton addtype;
    @FXML
    private JFXTextField games;
    @FXML
    private JFXButton addgame;

    @FXML
    private JFXTextField barcodetex;
    @FXML
    private TableView<ItemShowDetails> Search_Item_table;

    @FXML
    private TableColumn<?, ?> barcodecol;
    @FXML
    private TableColumn<?, ?> gamescol;
    @FXML
    private TableColumn<?, ?> typecol;
    @FXML
    private TableColumn<?, ?> suppcol;
    @FXML
    private TableColumn<?, ?> qtycol;
    @FXML
    private TableColumn<?, ?> bpcol;
    @FXML
    private TableColumn<?, ?> spcol;
    @FXML
    private TableColumn<?, ?> datecol;
    @FXML
    private Label Availabalitylab;
    @FXML
    private JFXButton Addnewbtn;
    @FXML
    private JFXButton Clerbtn;
    @FXML
    private JFXButton PrintLable;
    @FXML
    private JFXButton GrnDetails;
    @FXML
    private JFXButton Allgrn;
    @FXML
    private JFXButton ItemUpdatebtn;
    @FXML
    private JFXListView<?> gamelist;
    @FXML
    private JFXButton returndetails;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDetails();
        Availabalitylab.setVisible(false);
        gamelist.setVisible(false);

    }
    CRUDOperations crudo = new CRUDOperationsImpl();
    private ObservableList<ItemShowDetails> data = FXCollections.observableArrayList();

    boolean buul = true;

    @FXML
    private void typeA(ActionEvent event) {
        if (buul) {
            System.err.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            try {
                if ((type.getSelectionModel().getSelectedItem().equals(null)) || !(type.getSelectionModel().getSelectedItem().equals(""))) {
                    data.clear();

                    java.sql.ResultSet r = crudo.search("idstoragemedium", "storagemedium", "where type='" + type.getSelectionModel().getSelectedItem() + "" + "'");
                    while (r.next()) {
                        java.sql.ResultSet rs = crudo.search("*", "stock", "where storageMedium_idstorageMedium ='" + r.getString("idstoragemedium") + "%'");
                        while (rs.next()) {
                            int i = rs.getInt("idStock");
                            System.out.println(new Utility().numberFormatter(i));
                            ResultSet rs1 = crudo.search("*", "grnItem", "where grnItemId='" + rs.getString("grnItem_grnItemId") + "'");
                            while (rs1.next()) {
                                ResultSet rs2 = crudo.search("*", "games", "where idGames='" + rs1.getString("games_idGames") + "'");
                                while (rs2.next()) {
                                    ResultSet rs3 = crudo.search("*", "storageMedium", "where idstorageMedium='" + rs.getString("storageMedium_idstorageMedium") + "'");
                                    while (rs3.next()) {
                                        ResultSet rs4 = crudo.search("*", "grn", "where idgrn='" + rs1.getString("grn_idgrn") + "'");
                                        while (rs4.next()) {
                                            ResultSet rs5 = crudo.search("*", "Supplier", "where idSupplier='" + rs4.getString("Supplier_idSupplier") + "'");
                                            while (rs5.next()) {

                                                data.add(new ItemShowDetails(i + "", rs2.getString("name"), rs3.getString("type"), rs5.getString("Company_name"), rs.getString("qty"), rs.getString("buyingPrice"), rs.getString("sellingPrice"), rs4.getString("date")));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    barcodecol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                    gamescol.setCellValueFactory(new PropertyValueFactory<>("game"));
                    typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
                    suppcol.setCellValueFactory(new PropertyValueFactory<>("supp"));
                    qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
                    bpcol.setCellValueFactory(new PropertyValueFactory<>("bp"));
                    spcol.setCellValueFactory(new PropertyValueFactory<>("sp"));
                    datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
                    Search_Item_table.setItems(null);
                    Search_Item_table.setItems(data);
                } else {
                    System.err.println("vnfevjribirn");
                    loadDetails();
                }
            } catch (Exception e) {
            }
        }
    }

    @FXML
    private void addty(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("storagetype.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Grn Details");
            stage.setScene(scene);
            stage.show();
            loadStoragemediums();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void valigames(KeyEvent event) {
        games.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void gamesA(ActionEvent event) {
    
    }

    @FXML
    private void gameskr(KeyEvent event) {
        List<String> list = new ArrayList<>();
        boolean b = false;
        try {
            if (!(games.getText().equals(null) || games.getText().equals(""))) {
                ResultSet rs = new CRUDOperationsImpl().search("*", "games", " where name like '" + games.getText() + "%' ");
                while (rs.next()) {
                    list.add(rs.getString("name"));
                    b = true;
                }
            }
            if (b) {
                ObservableList li = FXCollections.observableArrayList(list);
                gamelist.setItems(null);
                gamelist.setItems(li);
                gamelist.setVisible(true);
            } else {
                gamelist.setVisible(false);
            }
            if ((games.getText().equals(null)) || !(games.getText().equals(""))) {
                data.clear();
                java.sql.ResultSet r = crudo.search("idgames", "games", "where name like '" + games.getText() + "%'");
                while (r.next()) {
                    java.sql.ResultSet rs = crudo.search("*", "stock", "where games_idgames like '" + r.getString("idgames") + "%'");
                    while (rs.next()) {
                        int i = rs.getInt("idStock");
                        System.out.println(new Utility().numberFormatter(i));
                        ResultSet rs1 = crudo.search("*", "grnItem", "where grnItemId='" + rs.getString("grnItem_grnItemId") + "'");
                        while (rs1.next()) {
                            ResultSet rs2 = crudo.search("*", "games", "where idGames='" + rs1.getString("games_idGames") + "'");
                            while (rs2.next()) {
                                ResultSet rs3 = crudo.search("*", "storageMedium", "where idstorageMedium='" + rs.getString("storageMedium_idstorageMedium") + "'");
                                while (rs3.next()) {
                                    ResultSet rs4 = crudo.search("*", "grn", "where idgrn='" + rs1.getString("grn_idgrn") + "'");
                                    while (rs4.next()) {
                                        ResultSet rs5 = crudo.search("*", "Supplier", "where idSupplier='" + rs4.getString("Supplier_idSupplier") + "'");
                                        while (rs5.next()) {

                                            data.add(new ItemShowDetails(i + "", rs2.getString("name"), rs3.getString("type"), rs5.getString("Company_name"), rs.getString("qty"), rs.getString("buyingPrice"), rs.getString("sellingPrice"), rs4.getString("date")));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                barcodecol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                gamescol.setCellValueFactory(new PropertyValueFactory<>("game"));
                typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
                suppcol.setCellValueFactory(new PropertyValueFactory<>("supp"));
                qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
                bpcol.setCellValueFactory(new PropertyValueFactory<>("bp"));
                spcol.setCellValueFactory(new PropertyValueFactory<>("sp"));
                datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
                Search_Item_table.setItems(null);
                Search_Item_table.setItems(data);
            } else {
                System.err.println("vnfevjribirn");
                loadDetails();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addgameA(ActionEvent event) {
            try {
            Parent root = FXMLLoader.load(getClass().getResource("/GameManagement/SUEDWindow.fxml"));
            Stage memberCheckout = new Stage();
            memberCheckout.setScene(new Scene(root));
            memberCheckout.setMaximized(false);
            memberCheckout.setTitle("Member Checkout");
            memberCheckout.initStyle(StageStyle.UTILITY);
            memberCheckout.setResizable(false);
            memberCheckout.show();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void valibar(KeyEvent event) {
        barcodetex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void barcodetexA(ActionEvent event) {
    }

    @FXML
    private void barcodetexkr(KeyEvent event) {
        try {
            if ((barcodetex.getText() != null) || !(barcodetex.getText().equals(""))) {
                data.clear();
                java.sql.ResultSet rs = crudo.search("*", "stock", "where idStock like '" + barcodetex.getText() + "%'");
                while (rs.next()) {
                    int i = rs.getInt("idStock");
                    System.out.println(new Utility().numberFormatter(i));
                    ResultSet rs1 = crudo.search("*", "grnItem", "where grnItemId='" + rs.getString("grnItem_grnItemId") + "'");
                    while (rs1.next()) {
                        ResultSet rs2 = crudo.search("*", "games", "where idGames='" + rs1.getString("games_idGames") + "'");
                        while (rs2.next()) {
                            ResultSet rs3 = crudo.search("*", "storageMedium", "where idstorageMedium='" + rs.getString("storageMedium_idstorageMedium") + "'");
                            while (rs3.next()) {
                                ResultSet rs4 = crudo.search("*", "grn", "where idgrn='" + rs1.getString("grn_idgrn") + "'");
                                while (rs4.next()) {
                                    ResultSet rs5 = crudo.search("*", "Supplier", "where idSupplier='" + rs4.getString("Supplier_idSupplier") + "'");
                                    while (rs5.next()) {

                                        data.add(new ItemShowDetails(i + "", rs2.getString("name"), rs3.getString("type"), rs5.getString("Company_name"), rs.getString("qty"), rs.getString("buyingPrice"), rs.getString("sellingPrice"), rs4.getString("date")));
                                    }
                                }
                            }
                        }
                    }
                }
                barcodecol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                gamescol.setCellValueFactory(new PropertyValueFactory<>("game"));
                typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
                suppcol.setCellValueFactory(new PropertyValueFactory<>("supp"));
                qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
                bpcol.setCellValueFactory(new PropertyValueFactory<>("bp"));
                spcol.setCellValueFactory(new PropertyValueFactory<>("sp"));
                datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
                Search_Item_table.setItems(null);
                Search_Item_table.setItems(data);
            } else {
                System.err.println("vnfevjribirn");
                loadDetails();
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void Search_Item_tableclick(MouseEvent event) {
        if (Search_Item_table.getSelectionModel().getSelectedItem() != null) {
            ItemShowDetails details1 = Search_Item_table.getSelectionModel().getSelectedItem();
            barcodetex.setText(details1.getBar());
            games.setText(details1.getGame());

            buul = false;
            type.setValue(details1.getType());
            Availabalitylab.setVisible(true);
            if (Integer.parseInt(details1.getQty()) > 0) {
                Availabalitylab.setText("Available");
                Availabalitylab.setStyle("-fx-text-fill: rgb(94, 239, 45);");
            } else {
                Availabalitylab.setText("Unavailable");
                Availabalitylab.setStyle("-fx-text-fill: red;");
            }
        } else {
            Availabalitylab.setVisible(false);
        }
    }

    private void loadDetails() {
        try {
            data.clear();
            ResultSet rs = crudo.search("*", "stock", "");
            while (rs.next()) {
                int i = rs.getInt("idStock");
                System.out.println(new Utility().numberFormatter(i));
                ResultSet rs1 = crudo.search("*", "grnItem", "where grnItemId='" + rs.getString("grnItem_grnItemId") + "'");
                while (rs1.next()) {
                    ResultSet rs2 = crudo.search("*", "games", "where idGames='" + rs1.getString("games_idGames") + "'");
                    while (rs2.next()) {
                        ResultSet rs3 = crudo.search("*", "storageMedium", "where idstorageMedium='" + rs.getString("storageMedium_idstorageMedium") + "'");
                        while (rs3.next()) {
                            ResultSet rs4 = crudo.search("*", "grn", "where idgrn='" + rs1.getString("grn_idgrn") + "'");
                            while (rs4.next()) {
                                ResultSet rs5 = crudo.search("*", "Supplier", "where idSupplier='" + rs4.getString("Supplier_idSupplier") + "'");
                                while (rs5.next()) {
                                    data.add(new ItemShowDetails(i + "", rs2.getString("name"), rs3.getString("type"), rs5.getString("Company_name"), rs.getString("qty"), rs.getString("buyingPrice"), rs.getString("sellingPrice"), rs4.getString("date")));
                                }
                            }
                        }
                    }
                }
            }
            barcodecol.setCellValueFactory(new PropertyValueFactory<>("bar"));
            gamescol.setCellValueFactory(new PropertyValueFactory<>("game"));
            typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
            suppcol.setCellValueFactory(new PropertyValueFactory<>("supp"));
            qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
            bpcol.setCellValueFactory(new PropertyValueFactory<>("bp"));
            spcol.setCellValueFactory(new PropertyValueFactory<>("sp"));
            datecol.setCellValueFactory(new PropertyValueFactory<>("date"));
            Search_Item_table.setItems(null);
            Search_Item_table.setItems(data);

            loadStoragemediums();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void DateA(ActionEvent event) {
        try {
            //    data.add(new ItemShowDetails(i + "", rs2.getString("name"), rs3.getString("type"), rs5.getString("Company_name"), rs.getString("qty"), rs.getString("buyingPrice"), rs.getString("sellingPrice"), rs4.getString("date")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStoragemediums() {
        try {
            java.sql.ResultSet ss = crudo.search("type", "storagemedium", "");
            List<String> list = new ArrayList<>();
            while (ss.next()) {
                list.add(ss.getString("type"));
            }
            ObservableList<String> c = FXCollections.observableArrayList(list);
            type.setItems(null);
            type.setItems(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void typeclick(MouseEvent event) {
        buul = true;
        loadStoragemediums();
    }

    @FXML
    private void AddnewbtnA(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Item_Add.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Add_New_Item");
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

            loadStoragemediums();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ClerbtnA(ActionEvent event) {
        clearfelds();

    }

    @FXML
    private void PrintLableA(ActionEvent event) {
        try {
            if (barcodetex.getText() != null) {
                if (Availabalitylab.getText().equals("Available")) {
                    System.out.println("dasd");
                    ResultSet rs = crudo.search("*", "stock", "where idstock='" + barcodetex.getText() + "'");
                    if (rs.first()) {
                        int i = rs.getInt("idStock");
                        System.out.println(new Utility().numberFormatter(i));
                        PrintReport pr;
                        String bb = new Utility().numberFormatter(i);
                        Map<String, Object> params = new HashMap<>();
                        params.put("idStork", i);
                        params.put("barcodeNo", new Utility().numberFormatter(i));
                        pr = new PrintReport(params, "ItemBill.jrxml");
                        pr.start();
                    } else {
                        MessageBox.showWarningMessage("Warrning", "WRONG STOCK ITEM SELECTED");
                    }
                } else {
                    MessageBox.showWarningMessage("Warrning", "NO AVAILABLE QTY");
                }

            } else {
                MessageBox.showWarningMessage("Warrning", "NO STOCK ITEM SELECTED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void PrintAllA(ActionEvent event) {
        try {
            ResultSet rs = crudo.search("*", "stock", "");
            if (rs.next()) {
                PrintReport pr;
                Date d = new Date();
                Calendar time = Calendar.getInstance();
                String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "A.M" : "P.M";
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                String t = (sdf.format(d) + " " + ampmString);
                sdf = new SimpleDateFormat("dd:MM:yyyy");
                String da = (sdf.format(d) + " ");
                Map<String, Object> params = new HashMap<>();

                params.put("Time", t);
                params.put("Date", da);
                pr = new PrintReport(params, "StorkAll.jrxml");

                pr.start();

            } else {
                MessageBox.showWarningMessage("Warrning", "NO AVAILABLE STOCK");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    static int stid;

    @FXML
    private void GrnDetailsA(ActionEvent event) {
        try {

            if (barcodetex.getText() != null && !(barcodetex.getText().equals(""))) {
                String grnNO = null;
                ResultSet rs = crudo.search("idstock", "stock", " where idstock='" + barcodetex.getText() + "'");
                if (rs.next()) {
                    stid = rs.getInt("idstock");
                    System.out.println("in else");
                    Parent root = FXMLLoader.load(getClass().getResource("Stock_Details.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Stock_Details");
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                MessageBox.showWarningMessage("Warrning", "NO STOCK ITEM SELECTED");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearfelds() {
        loadDetails();
        barcodetex.setText(null);

        games.setText(null);

        Availabalitylab.setVisible(false);

        gamelist.setVisible(false);
        //  System.out.println(Date.getValue().toString() + " 00:00:00");

    }

    @FXML
    private void AllgrnA(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("homeTemplate.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Grn Details");
            stage.setScene(scene);
            stage.show();
            loadStoragemediums();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ItemUpdatebtnA(ActionEvent event) {
        try {
            if (barcodetex.getText() != null && !(barcodetex.getText().equals(""))) {
                if (games.getText() != null && !(games.getText().equals(""))) {
                    if ((type.getSelectionModel().getSelectedItem().equals(null)) || !(type.getSelectionModel().getSelectedItem().equals(""))) {
                        ResultSet rs = crudo.search("*", "stock", "where idstock='" + Integer.parseInt(barcodetex.getText()) + "'");
                        if (rs.next()) {
                            ResultSet ga = crudo.search("*", "games", "where name='" + games.getText() + "'");
                            if (ga.next()) {
                                ResultSet ty = crudo.search("*", "storagemedium", "where type='" + type.getSelectionModel().getSelectedItem() + "'");
                                if (ty.next()) {
                                    crudo.update("stock", "games_idGames='" + ga.getString("idgames") + "',storageMedium_idstorageMedium='" + ty.getString("idstorageMedium") + "'", "idstock='" + Integer.parseInt(barcodetex.getText()) + "'");
                                    crudo.update("grnitem", "games_idGames='" + ga.getString("idgames") + "'", " grnitemid='" + rs.getString("grnItem_grnItemId") + "'");
                                    MessageBox.showInformationMessage("Success", "STOCK SUCCESSFULLY UPDATEED");
                                    Log.info("Stock item " + Integer.parseInt(barcodetex.getText()) + " Updete by " + user.getIdUser());
                                    loadDetails();
                                }
                            } else {
                                MessageBox.showWarningMessage("Warrning", "CAN'T FIND THIS GAME");
                            }
                        } else {
                            MessageBox.showWarningMessage("Warrning", "INVALIED BARCODE");
                        }
                    } else {
                        MessageBox.showWarningMessage("Warrning", "STORAGEMEDIUM SELECTED");
                    }
                } else {
                    MessageBox.showWarningMessage("Warrning", "GAME SELECTED");
                }
            } else {
                MessageBox.showWarningMessage("Warrning", "NO STOCK ITEM SELECTED");
            }
        } catch (Exception e) {
            Log.error(e);
        }
    }

    @FXML
    private void gamelistclick(MouseEvent event) {
        games.setText((String) gamelist.getSelectionModel().getSelectedItem());
        gamelist.setItems(null);
        gamelist.setVisible(false);
    }

    @FXML
    private void returndetailsA(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Show_Return.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Return Details");
            stage.setScene(scene);
            stage.show();
            loadStoragemediums();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
