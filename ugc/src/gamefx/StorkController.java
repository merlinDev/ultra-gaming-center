/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import static gamefx.HomeTemplateController.i;
import static gamefx.HomeTemplateController.time;
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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class StorkController implements Initializable {

    private JFXTextField gameprice;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private TableColumn<?, ?> namec;
    @FXML
    private TableColumn<?, ?> bpricec;
    @FXML
    private TableColumn<?, ?> qtyc;
    @FXML
    private JFXTextField gamescomb;
    @FXML
    private JFXTextField gameprice1;
    @FXML
    private JFXComboBox<String> gamescomb1;
    @FXML
    private JFXButton update;
    @FXML
    private JFXButton addtype;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private JFXListView<String> searchlist;
    @FXML
    private JFXTextField game;
    @FXML
    private JFXTextField grnno2;
    @FXML
    private JFXTextField buyp;
    @FXML
    private JFXTextField sell;
    @FXML
    private JFXTextField qty;
    @FXML
    private JFXTextField barcode;
    @FXML
    private TableView<StorkDetails> storktable;
    @FXML
    private TableColumn<?, ?> sprice;
    @FXML
    private TableColumn<?, ?> typec;
    @FXML
    private JFXTextField qty1;
    @FXML
    private JFXButton loadall;
    @FXML
    private JFXButton printall;
    @FXML
    private JFXTextField games;
    @FXML
    private JFXTextField type1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadtype();
        normalse();
        loadgamestype();

        loadtable();
        clear1();
    }

    @FXML
    private void com(MouseEvent event) {
    }

    @FXML
    private void comboOnAct(ActionEvent event) {
    }

    private void loadtype() {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            java.sql.ResultSet ss = (ResultSet) crudo.search("*", "storagemedium", "");
            List<String> list = new ArrayList<>();
            String[] game = new String[i];
            while (ss.next()) {
                list.add(ss.getString("type"));
            }

            ObservableList<String> c = FXCollections.observableArrayList(list);
            type.setItems(null);
            type.setItems(c);
        } catch (Exception ex) {
         
        } 
    }

    @FXML
    private void sea(KeyEvent event) {
 game.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
       
    }

    private void normalse() {
        System.out.println("awa");
        List<String> list = new ArrayList<>();
        String[] game1 = new String[i];
        CRUDOperations crudo = new CRUDOperationsImpl();
        try {
            int id = 0;
            ObservableList li = null;
            java.sql.ResultSet rs = crudo.search("MAX(grnitemId) AS x", "grnitem", " where status='" + 1 + "'");

            if (rs.next()) {
                System.out.println("frngrng");
                id = rs.getInt("x");
                System.err.println(id);
                if (id > 5) {
                    System.out.println("oooo");
                    int s = id - 5;
                    for (i = id; s != id; id--) {
                        list.add(id + "");
                        li = FXCollections.observableArrayList(list);
                        searchlist.setItems(null);
                        searchlist.setItems(li);
                    }
                } else {
                    System.out.println("ddfed");
                    if (id != 0) {
                        while (id != 0) {
                            list.add(id + "");
                            li = FXCollections.observableArrayList(list);
                            id--;

                        }
                        searchlist.setItems(null);
                        searchlist.setItems(li);
                    }

                }

            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void sebuy(ActionEvent event) {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            String gid = null;
            java.sql.ResultSet gs = crudo.search("idGames", "games", "where name='" + game.getText() + "' ");
            if (gs.next()) {
                gid = gs.getString("idGames");
            }
            java.sql.ResultSet rs = crudo.search("buying_price", "grnitem", "where grn_idGrn='" + grnno2.getText() + "' and games_idGames='" + gid + "'");
            while (rs.next()) {
                buyp.setText(rs.getString("buying_price"));
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void dddd(MouseEvent event) {
        game.setText(searchlist.getSelectionModel().getSelectedItem());
        loadg();
    }
    private ObservableList<StorkDetails> data2;

    @FXML
    private void toFields(MouseEvent event) {
        System.out.println("wwe");
        StorkDetails details = storktable.getSelectionModel().getSelectedItem();
        gamescomb.setText(details.getGame());
        System.out.println(details.getBp());
        type1.setText(details.getBp());
        qty1.setText(details.getQty());
        gameprice1.setText(details.getSp());
        gamescomb1.setValue(details.getType());
        //barcode.setText(null);
        barcode.setText(details.getId());
    }

    private void loadgamestype() {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            java.sql.ResultSet ss = (ResultSet) crudo.search("type", "storagemedium", "");
            List<String> list = new ArrayList<>();
            String[] game = new String[i];
            while (ss.next()) {
                list.add(ss.getString("type"));
            }
            ObservableList<String> c = FXCollections.observableArrayList(list);
            gamescomb1.setItems(null);
            gamescomb1.setItems(c);

        } catch (Exception ex) {
            Log.error(ex);
        }

    }
    public static boolean bl = false;

    @FXML
    private void upd(MouseEvent event) {

        call();

    }

    private void loadselectdetails() {
        data2 = FXCollections.observableArrayList();

        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            if (!(barcode.getText() == null)) {
                java.sql.ResultSet rs = crudo.search("idStock,qty,buyingPrice,storageMedium_idstorageMedium,games_idGames,sellingPrice", "stock", "where idStock='" + barcode.getText() + "'");
                String ses = null;
                while (rs.next()) {
                    java.sql.ResultSet rs2 = crudo.search("type", "storageMedium", "where idstorageMedium='" + rs.getString("storageMedium_idstorageMedium") + "'");
                    if (rs2.next()) {
                        ses = rs2.getString("type");
                    }
                    java.sql.ResultSet rs1 = crudo.search("name", "games", "where idGames='" + rs.getString("games_idGames") + "'");
                    if (rs1.next()) {
                        data2.add(new StorkDetails(rs.getString("idStock"), rs1.getString("name"), rs.getString("buyingPrice"), rs.getString("sellingPrice"), ses, rs.getString("qty")));
                    }

                    System.out.println("up");
                }
                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                namec.setCellValueFactory(new PropertyValueFactory<>("game"));
                bpricec.setCellValueFactory(new PropertyValueFactory<>("bp"));
                sprice.setCellValueFactory(new PropertyValueFactory<>("sp"));
                typec.setCellValueFactory(new PropertyValueFactory<>("type"));
                qtyc.setCellValueFactory(new PropertyValueFactory<>("qty"));

                storktable.setItems(null);
                storktable.setItems(data2);

                barcode.setText(null);
                gamescomb.setText(null);
                gamescomb1.setValue(null);
                qty1.setText(null);

                gameprice1.setText(null);
            } else {
                storktable.setItems(null);
            }

        } catch (Exception ex) {
         
        } 
    }

    private void loadtable() {
        refill();
    }

    private void refill() {
        data2 = FXCollections.observableArrayList();
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();

            java.sql.ResultSet rs = crudo.search("idStock,qty,buyingPrice,storageMedium_idstorageMedium,games_idGames,sellingPrice", "stock", "");
            String sds = null;
            System.out.println("fcid");
            data2.clear();
            while (rs.next()) {
                java.sql.ResultSet rs1 = crudo.search("name,gametype_idGameType", "games", "where idGames='" + rs.getString("games_idGames") + "'");
                java.sql.ResultSet rs2 = crudo.search("type", "storagemedium", "where idstorageMedium='" + rs.getString("storageMedium_idstorageMedium") + "'");
                if (rs2.next()) {
                    sds = rs2.getString("type");
                }
                if (rs1.next()) {
                    java.sql.ResultSet rs3 = crudo.search("typeName", "gametype", "where idGameType='" + rs1.getString("gametype_idGameType") + "'");
                    if (rs3.next()) {
                        data2.add(new StorkDetails(rs.getString("idStock"), rs1.getString("name"), rs3.getString("typeName"), rs.getString("sellingPrice"), sds, rs.getString("qty")));
                    }
                }

                System.out.println("first");
            }
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            namec.setCellValueFactory(new PropertyValueFactory<>("game"));
            bpricec.setCellValueFactory(new PropertyValueFactory<>("bp"));
            sprice.setCellValueFactory(new PropertyValueFactory<>("sp"));
            typec.setCellValueFactory(new PropertyValueFactory<>("type"));
            qtyc.setCellValueFactory(new PropertyValueFactory<>("qty"));

            storktable.setItems(null);
            storktable.setItems(data2);

            barcode.setText(null);
            gamescomb.setText(null);
            gamescomb1.setValue(null);
            type1.setText(null);
            qty1.setText(null);
            gameprice1.setText(null);
            System.out.println("nn");

        } catch (Exception ex) {
           
        } 

    }

    @FXML
    private void valibp(KeyEvent event) {
        buyp.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valisp(KeyEvent event) {
        sell.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valigrnno(KeyEvent event) {
        grnno2.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valiqty(KeyEvent event) {
        qty.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valibar(KeyEvent event) {
        barcode.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    private void valigbp(KeyEvent event) {
        gameprice.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valigq(KeyEvent event) {
        qty1.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valigsp(KeyEvent event) {
        gameprice1.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void addty(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("storagetype.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Grn Details");
            stage.setScene(scene);
            stage.show();
            loadtype();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clear1() {
        game.setText(null);
        grnno2.setText(null);
        buyp.setText(null);
        type.setValue(null);
        games.setText(null);
        qty.setText(null);
        sell.setText(null);
        normalse();

    }

    @FXML
    private void losdallb(MouseEvent event) {
        refill();
    }

    @FXML
    private void printallb(MouseEvent event) {
    }

    @FXML
    private void cleb(MouseEvent event) {
        clear1();
    }

    private void call() {
        try {
            StorkDetails details = storktable.getSelectionModel().getSelectedItem();
            CRUDOperations crudo = new CRUDOperationsImpl();
            String ss = null;
            java.sql.ResultSet gs = (ResultSet) crudo.search("idGames", "games", "where name='" + gamescomb.getText() + "'");
            if (gs.next()) {
                ss = gs.getString("idGames");
            }
            String sds = null;
            String sgt = null;
            java.sql.ResultSet rs = crudo.search("idstorageMedium", "storagemedium", "where type='" + gamescomb1.getValue() + "'");
            if (rs.next()) {
                sds = rs.getString("idstorageMedium");
            }
            java.sql.ResultSet rs1 = crudo.search("idGameType", "GameType", "where typeName='" + type1.getText() + "'");
            if (rs1.next()) {
                sgt = rs1.getString("idGameType");
            }
            String s = details.getId();
            crudo.update("stock", "qty='" + qty1.getText() + "',storageMedium_idstorageMedium='" + sds + "',games_idGames='" + ss + "',sellingPrice='" + gameprice1.getText() + "'", "idStock='" + barcode.getText() + "'");
            crudo.update("games", "gametype_idGameType='" + sgt + "'", "idGames='" + ss + "'");
            Notifications notificationBuilder = Notifications.create()
                    .title("Succesful")
                    .text("Stock updated Added")
                    .graphic(null)
                    .hideAfter(Duration.seconds(2))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("Click on Notification");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.showInformation();
            refill();

        } catch (Exception ex) {
            Log.error(ex);
        }
    }
    PrintReport pr;
    static SimpleDateFormat sdf;
    static SimpleDateFormat sdf1;

    @FXML
    private void printsto(ActionEvent event) throws Exception {

        try {

            Date d = new Date();
             time = Calendar.getInstance();
            String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "A.M" : "P.M";
            sdf = new SimpleDateFormat("hh:mm:ss");
            String t = (sdf.format(d) + " " + ampmString);
            sdf = new SimpleDateFormat("dd:MM:yyyy");
            String da = (sdf.format(d) + " " );
            Map<String, Object> params = new HashMap<>();

            params.put("Time", t);
            params.put("Date", da);
            pr = new PrintReport (params, "C:\\Users\\Buddhika\\Desktop\\SP\\StorkAll.jrxml");
            //pr = new printeport(params, "report\\StorkAll.jrxml");
            pr.start();
        } catch (Exception e) {
        }

    }

    private void loadg() {
        CRUDOperations crudo = new CRUDOperationsImpl();
        try {
            java.sql.ResultSet rs = crudo.search("*", "grnitem", " where grnitemId='" + game.getText() + "'  and status='" + 1 + "' ");
            if (rs.next()) {
                grnno2.setText(rs.getString("grn_idGrn"));
                buyp.setText(rs.getString("buying_price"));
                java.sql.ResultSet rs1 = crudo.search("*", "games", " where idGames='" + rs.getString("games_idGames") + "'  and status='" + "active" + "' ");
                if (rs1.next()) {
                    System.out.println("cferfergrf");
                    games.setText(rs1.getString("name"));
                }

            }
        } catch (Exception ex) {
            // Logger.getLogger(HOMEController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Savestrock(ActionEvent event) {
        String s = type.getSelectionModel().getSelectedItem();
        CRUDOperations crudo = new CRUDOperationsImpl();
        if (games.getText() != null) {
            if (qty.getText() != null) {
                if (sell.getText() != null) {
                    if (s != null) {
                        try {
                            int i = 0;
                            java.sql.ResultSet rs = crudo.search("MAX(idStock) AS x", "stock", "");
                            if (rs.next()) {
                                i = rs.getInt("x");
                            }
                            i++;
                            java.sql.ResultSet size = crudo.search("idstorageMedium", "storageMedium", " where type='" + type.getValue() + "'");
                            if (size.next()) {
                                System.err.println(i);
                                java.sql.ResultSet type = crudo.search("idGames", "games", " where name='" + games.getText() + "'");
                                if (type.next()) {
                                    System.out.println("pako");
                                    crudo.save("stock", "'" + i + "','" + qty.getText() + "','" + buyp.getText() + "','" + type.getInt("idGames") + "','" + sell.getText() + "','" + size.getInt("idstorageMedium") + "'");
                                    int sy = 0;
                                    crudo.update("grnItem", "status='" + sy + "'", " grnItemId='" + game.getText() + "'");
                                    System.out.println("saved");
                                    printlable(i);
                                }
                            }
                            clear1();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        MessageBox.showWarningMessage("Warning", "STORAGE TYPE NOT SELECTED");
                    }
                } else {
                    MessageBox.showWarningMessage("Warning", "GAME SELLING PRICE NOT ENTERED");
                }
            } else {
                MessageBox.showWarningMessage("Warning", "QTY NOT ENTERED");
            }
        } else {
            MessageBox.showWarningMessage("Warning", "GRN ITEM NOT SELECTED");
        }

    }

    @FXML
    private void searchbybar(KeyEvent event) {
        
        data2 = FXCollections.observableArrayList();

        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            if ((barcode.getText() != null) && !(barcode.getText().equals(""))) {
                System.err.println(!(barcode.getText() == null) || !(barcode.getText() == ""));
                java.sql.ResultSet rs = crudo.search("idStock,qty,buyingPrice,storageMedium_idstorageMedium,games_idGames,sellingPrice", "stock", "where idStock='" + barcode.getText() + "'");
                String asa = null;
                while (rs.next()) {
                    java.sql.ResultSet rs2 = crudo.search("type", "storageMedium", "where idstorageMedium='" + rs.getString("storageMedium_idstorageMedium") + "' ");
                    if (rs2.next()) {
                        asa = rs2.getString("type");
                    }
                    java.sql.ResultSet rs1 = crudo.search("name", "games", "where idGames='" + rs.getString("games_idGames") + "'");
                    if (rs1.next()) {
                        System.out.println("trrrrriiii");
                        data2.add(new StorkDetails(rs.getString("idStock"), rs1.getString("name"), rs.getString("buyingPrice"), rs.getString("sellingPrice"), asa, rs.getString("qty")));
                    }

                    System.out.println("svnjnv");
                }
                id.setCellValueFactory(new PropertyValueFactory<>("id"));
                namec.setCellValueFactory(new PropertyValueFactory<>("game"));
                bpricec.setCellValueFactory(new PropertyValueFactory<>("bp"));
                sprice.setCellValueFactory(new PropertyValueFactory<>("sp"));
                typec.setCellValueFactory(new PropertyValueFactory<>("type"));
                qtyc.setCellValueFactory(new PropertyValueFactory<>("qty"));

                storktable.setItems(null);
                storktable.setItems(data2);
            } else {
                System.out.println("vnfevjribirn");
                refill();
            }
        } catch (Exception ex) {
            
        } 
    }

    @FXML
    private void qty1A(ActionEvent event) {
        gameprice1.requestFocus();
    }

    @FXML
    private void qtyA(ActionEvent event) {
        sell.requestFocus();
    }

    private void printlable(int i) {
        System.out.println(new Utility().numberFormatter(i));
        try {
            PrintReport pr;

            String bb = new Utility().numberFormatter(i);
            Map<String, Object> params = new HashMap<>();

            params.put("idStork", i);
            params.put("barcodeNo", new Utility().numberFormatter(i));

            pr = new PrintReport (params, "C:\\Users\\Buddhika\\Desktop\\SP\\ItemBill.jrxml");
            pr.start();

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void searchty(KeyEvent event) {
        
                 List<String> list = new ArrayList<>();
        String[] game1 = new String[i];
        CRUDOperations crudo = new CRUDOperationsImpl();

        try {
            if (!(game.getText().equals("")) ) {
                System.out.println("bla bla");
                java.sql.ResultSet rs = crudo.search("grnitemId", "grnitem", "where grnitemId like'" + game.getText() + "%'&& status='" + 1 + "'");
                while (rs.next()) {
                    list.add(rs.getString("grnitemId"));
                    System.out.println("svnjnv");
                }
                ObservableList li = FXCollections.observableArrayList(list);
                searchlist.setItems(null);
                searchlist.setItems(li);
                searchlist.setVisible(true);
            } else {
                System.out.println("hear");
                normalse();
                // searchlist.setItems(null);
                // searchlist.setVisible(false);
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
                
    }

}
