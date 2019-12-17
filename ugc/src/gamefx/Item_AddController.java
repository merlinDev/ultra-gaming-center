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
import com.jfoenix.controls.JFXTextField;
import static gamefx.HomeTemplateController.sdf1;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javafx.scene.control.ListView;
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
public class Item_AddController implements Initializable {

    @FXML
    private JFXTextField Itemid;
    @FXML
    private JFXTextField buyp;
    @FXML
    private JFXTextField sellp;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    @FXML
    private JFXTextField Suppliretex;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private JFXTextField totalprice;
    @FXML
    private JFXButton addtype;
    @FXML
    private JFXTextField games;
    @FXML
    private JFXTextField QTY;
    @FXML
    private TableView<ItemDetails> Iteam_Add_table;
    @FXML
    private ListView<?> gameslist;
    @FXML
    private JFXButton addgame;
    @FXML
    private ListView<?> supplist;
    @FXML
    private TableColumn<?, ?> gamecol;
    @FXML
    private TableColumn<?, ?> typecol;
    @FXML
    private TableColumn<?, ?> qtycol;
    @FXML
    private TableColumn<?, ?> bpcol;
    @FXML
    private TableColumn<?, ?> spcol;
    @FXML
    private JFXButton close;
    @FXML
    private JFXButton addtotable;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        supplist.setVisible(false);
        gameslist.setVisible(false);
        totalprice.setVisible(false);
        loadstid();
        loadStoragemediums();
        Suppliretex.requestFocus();
        // TODO
    }
    CRUDOperations crudo = new CRUDOperationsImpl();

    @FXML
    private void buypA(ActionEvent event) {
        QTY.requestFocus();
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
        sellp.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void sellpA(ActionEvent event) {
        Add();
        games.requestFocus();
    }

    @FXML
    private void Savestrock(ActionEvent event) {
        try {
            ResultSet rs=crudo.search("*","grnItem", "where grn_idgrn='"+Itemid.getText()+"'");
            while (rs.next()) {                
              ResultSet rs1=crudo.search("*","Stock", "where idStock='"+rs.getString("grnItemId")+"'");
            while (rs1.next()) {                
                    int i=rs1.getInt("idStock");
          System.out.println(new Utility().numberFormatter(i));
            PrintReport pr;
            Map<String, Object> params = new HashMap<>();
            params.put("idStork", i);
            params.put("barcodeNo", new Utility().numberFormatter(i));
            pr = new PrintReport(params, "ItemBill.jrxml");
            pr.start(); 
               Log.info("Stock item "+i+" ADD by "+user.getIdUser());
               Log.info("Grn_item "+rs.getString("grnitemid")+" ADD by "+user.getIdUser());
                 Log.info("Grn "+Itemid.getText()+" ADD by "+user.getIdUser());
            }  
            }

            System.out.println(pb);
            if (pb) {
                grs = false;
                pb = false;
                totalprice.setText(null);
                totalprice.setVisible(false);
                Suppliretex.setText(null);
                Suppliretex.setEditable(true);
                loadstid();
                loaditemids();
                Iteam_Add_table.setItems(null);
                MessageBox.showInformationMessage("Success", "item add Successful ");
             
            } else {
                MessageBox.showWarningMessage("Warrning", "item adding not Successful");
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void cancelA(ActionEvent event) {
        cl();
     
    }

    @FXML
    private void Supptexsearch(KeyEvent event) {
        List<String> list = new ArrayList<>();
        try {
            boolean b = false;
            if (!(Suppliretex.getText().equals(null) || Suppliretex.getText().equals(""))) {
                java.sql.ResultSet rs = crudo.search("Company_name", "supplier", "where idSupplier like'" + Suppliretex.getText() + "%'|| name like'" + Suppliretex.getText() + "%'|| Company_name like'" + Suppliretex.getText() + "%' ");
                while (rs.next()) {
                   
                    list.add(rs.getString("Company_name"));
                    b = true;
                }
            }
            if (b) {
                ObservableList li = FXCollections.observableArrayList(list);
                supplist.setItems(null);
                supplist.setItems(li);
                supplist.setVisible(true);
            } else {
                supplist.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void SuppliretexA(ActionEvent event) {
        games.requestFocus();
    }

    @FXML
    private void valiSupptex(KeyEvent event) {
        Suppliretex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void addty(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("storagetype.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Add Type");
            stage.setScene(scene);
            stage.show();
            loadStoragemediums();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gamesA(ActionEvent event) {
        type.requestFocus();
    }

    @FXML
    private void valigamestex(KeyEvent event) {
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
    private void valiqty(KeyEvent event) {
        QTY.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void QTYA(ActionEvent event) {
        sellp.requestFocus();
    }

    @FXML
    private void Iteam_Add_table_Click(MouseEvent event) {
    }

    @FXML
    private void gameslist_Click(MouseEvent event) {
        games.setText((String) gameslist.getSelectionModel().getSelectedItem());
        gameslist.setItems(null);
        gameslist.setVisible(false);
    }
    static int stid;
    static int grnitemid;

    private void loadstid() {
        try {
            int id;
            java.sql.ResultSet rs = crudo.search("*", "grn", "");
            if (rs.first()) {
                id = Integer.parseInt(crudo.getLastId("idGrn", "grn"));
                id++;
                Itemid.setText(id + "");
            } else {
                Itemid.setText(1 + "");
            }
            loaditemids();
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
    private void supplist_Click(MouseEvent event) {
        Suppliretex.setText((String) supplist.getSelectionModel().getSelectedItem());
        supplist.setItems(null);
        supplist.setVisible(false);
    }

    @FXML
    private void gamessearch(KeyEvent event) {
        List<String> list = new ArrayList<>();
        try {
            boolean b = false;
            if (!(games.getText().equals(null) || games.getText().equals(""))) {
                ResultSet rs = new CRUDOperationsImpl().search("*", "games", " where name like '" + games.getText() + "%' ");
                while (rs.next()) {
                    list.add(rs.getString("name"));
                    b = true;
                }  }
            if (b) {
                ObservableList li = FXCollections.observableArrayList(list);
                gameslist.setItems(null);
                gameslist.setItems(li);
                gameslist.setVisible(true);
            } else {
                gameslist.setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadStoragemediums() {
        try {
            java.sql.ResultSet ss = crudo.search("type", "storagemedium", "");
            System.out.println("aawa ooi");
            ArrayList<String> list = new ArrayList<>();
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
    static SimpleDateFormat sdf;
    static String da;
    static boolean pb = false;
    static boolean grs = false;
    private ObservableList<ItemDetails> data = FXCollections.observableArrayList();

    private void Add() {
        boolean bs=false;
        try {
            String ty = type.getSelectionModel().getSelectedItem();
            if (((Suppliretex.getText()!=null) && !(Suppliretex.getText().equals("")))) {
                if (((games.getText()!=null) && !(games.getText().equals("")))) {
                    if ((!(ty == null))) {
                        if (((QTY.getText()!=null) && !(QTY.getText().equals(""))) && !(QTY.getText().equals("0"))) {
                            if ((buyp.getText()!=null) && !(buyp.getText().equals("")) && !(buyp.getText().equals("0.0") || buyp.getText().equals("0")) && !(BigDecimal.valueOf(Double.parseDouble(buyp.getText())).scale() > 2)) {
                                if ((sellp.getText()!=null) && !(sellp.getText().equals("")) && !(sellp.getText().equals("0.0") || sellp.getText().equals("0")) && !(BigDecimal.valueOf(Double.parseDouble(sellp.getText())).scale() > 2)) {
                                    if ((Double.parseDouble(sellp.getText())) >= (Double.parseDouble(buyp.getText()))) { 
                                        bs=true;
                                        } else {
                                        String [] arr=new String[2];
                                                        arr[0]="yes";
                                                        arr[1]="no";
                                                        String desition=MessageBox.showConfirmationMessage("Warning", "Selling price is bilow the buyinig! are you sure you whant to save this", arr);
                                                        if(desition.equals("yes")){
                                                        bs=true;
                                                        }
                                    }
                                          if(bs){
                                               boolean b = false;
                                        //  gameDetails details = Iteam_Add_table.getSelectionModel().getSelectedItem();
                                        int grnid = Integer.parseInt(Itemid.getText());
                                       Date d = new Date();
                        sdf1 = new SimpleDateFormat("hh:mm:ss");
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String f = (sdf.format(d));
                        String f1 = (sdf1.format(d));
                        da = f + " " + f1;
                                        int l = Integer.parseInt(QTY.getText());
                                        double n = Double.parseDouble(buyp.getText());
                                        double m = Double.parseDouble(sellp.getText());
                                        java.sql.ResultSet gs = (ResultSet) crudo.search("idGames", "games", "where name='" + games.getText() + "'");
                                        String ds = null;
                                        if (gs.next()) {
                                            ds = gs.getString("idGames");
                                            java.sql.ResultSet suu = (ResultSet) crudo.search("idSupplier", "Supplier", "where Company_name='" + Suppliretex.getText() + "'");
                                            String su = null;
                                            if (suu.next()) {
                                                su = suu.getString("idSupplier");

                                                java.sql.ResultSet tyy = (ResultSet) crudo.search("idstorageMedium", "storageMedium", "where type='" + ty + "'");
                                                String typ = null;
                                                if (tyy.next()) {
                                                    typ = tyy.getString("idstorageMedium");
                                                }

                                                ResultSet rs = crudo.search("*", "grn", "where idgrn='" + Itemid.getText() + "'");
                                                if (rs.next()) {
                                                    java.sql.ResultSet hge = (ResultSet) crudo.search("*", "grnitem", "where grn_idGrn='" + grnid + "' and games_idGames='" + ds + "'");
                                                    if (hge.next()) {
                                                         String [] arr1=new String[2];
                                                        arr1[0]="yes";
                                                        arr1[1]="no";
                                                        String desition1=MessageBox.showConfirmationMessage("Warning", "This item Allrady added doyou whan to update it", arr1);
                                                        if(desition1.equals("yes")){
                                                          java.sql.ResultSet dd = crudo.search("*", "grn", " WHERE idGrn='" + grnid + "'");
                                                        Double ff = 0.0;
                                                        if (dd.next()) {

                                                            ff = dd.getDouble("price") - (hge.getDouble("qty") * hge.getDouble("buying_price"));
                                                            System.out.println("ff");
                                                            double gt1 = n * l;
                                                            double m1 = ff + gt1;
                                                            totalprice.setText(m1 + "");
                                                        }
                                                        //idStock`, s.`qty`, s.`buyingPrice`, s.`games_idGames`, s.`sellingPrice`, s.`storageMedium_idstorageMedium`, s.`grnItem_grnItemId`
                                                        crudo.update("grn", "price='" + Double.parseDouble(totalprice.getText()) + "'", "idgrn='" + grnid + "'");
                                                        crudo.update("grnitem", "games_idGames='" + ds + "',buying_price='" + n + "',qty='" + l + "'", "grnitemid='" + hge.getString("grnitemid") + "'");
                                                        crudo.update("Stock", "qty='" + l + "',buyingPrice='" + n + "',sellingPrice='" + m + "',storageMedium_idstorageMedium='" + typ + "'", " grnItem_grnItemId='" + hge.getString("grnitemid") + "'");
                                                     
                                                        b = true;
                                                        }
                                                      

                                                    } else {
                                                        double gt1 = n * l;
                                                        double m1 = rs.getDouble("price") + gt1;
                                                        totalprice.setText(m1 + "");
                                                        int i = 0;
                                                        java.sql.ResultSet ss1 = (ResultSet) crudo.search("MAX(grnItemId) as x", "grnitem", "");
                                                        if (ss1.next()) {
                                                            i = ss1.getInt("x");
                                                            i++;
                                                        }
                                                        crudo.save("grnitem(grnitemid,games_idGames,grn_idGrn,buying_price,qty,status)", "'" + i + "','" + ds + "','" + grnid + "','" + n + "','" + l + "','" + 1 + "'");
                                                        crudo.save("Stock(idStock,qty,buyingPrice,games_idGames,sellingPrice,storageMedium_idstorageMedium,grnItem_grnItemId)", "'" + stid + "','" + l + "','" + n + "','" + ds + "','" + m + "','" + typ + "','" + grnitemid + "'");
                                                        crudo.update("grn", "price='" + Double.parseDouble(totalprice.getText()) + "'", "idgrn='" + grnid + "'");
                                                         
                                                    }

                                                } else {
                                                    totalprice.setVisible(true);
                                                    double gt = n * l;
                                                    totalprice.setText(gt + "");
                                                    int i = 0;
                                                    java.sql.ResultSet ss1 = (ResultSet) crudo.search("MAX(grnItemId) as x", "grnitem", "");
                                                    if (ss1.next()) {
                                                        i = ss1.getInt("x");
                                                        i++;
                                                    }
                                                    System.out.println("ss");
                                                    crudo.save("grn(idGrn,price,date,Supplier_idSupplier)", " '" + grnid + "','" + Double.parseDouble(totalprice.getText()) + "','" + da + "','" + su + "'");
                                                    crudo.save("grnitem(grnitemid,games_idGames,grn_idGrn,buying_price,qty,status)", "'" + grnitemid + "','" + ds + "','" + grnid + "','" + n + "','" + l + "','" + 1 + "'");
                                                    crudo.save("Stock(idStock,qty,buyingPrice,games_idGames,sellingPrice,storageMedium_idstorageMedium,grnItem_grnItemId)", "'" + stid + "','" + l + "','" + n + "','" + ds + "','" + m + "','" + typ + "','" + grnitemid + "'");
                                                    Suppliretex.setEditable(false);
                                                  
                                                }

                                                data.clear();
                                                java.sql.ResultSet gn = crudo.search("*", "grn", "where idgrn='" + grnid + "'");
                                                while (gn.next()) {
                                                    java.sql.ResultSet gs1 = crudo.search("*", "grnItem", "where grn_idgrn='" + gn.getString("idgrn") + "'");
                                                    while (gs1.next()) {
                                                        java.sql.ResultSet gs2 = crudo.search("*", "Stock", "where grnItem_grnItemId='" + gs1.getString("grnItemId") + "'");
                                                        while (gs2.next()) {
                                                            java.sql.ResultSet gs3 = crudo.search("name", "games", "where idGames='" + gs2.getString("games_idGames") + "'");
                                                            while (gs3.next()) {
                                                                java.sql.ResultSet gs4 = crudo.search("type", "storageMedium", "where idstorageMedium='" + gs2.getString("storageMedium_idstorageMedium") + "'");

                                                                while (gs4.next()) {
                                                                    System.err.println("awoo");
                                                                    data.add(new ItemDetails(gs3.getString("name"), gs4.getString("type"), gs2.getString("qty"), gs2.getString("buyingPrice"), gs2.getString("sellingPrice")));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                gamecol.setCellValueFactory(new PropertyValueFactory<>("game"));
                                                typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
                                                qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
                                                bpcol.setCellValueFactory(new PropertyValueFactory<>("bp"));
                                                spcol.setCellValueFactory(new PropertyValueFactory<>("sp"));

                                                Iteam_Add_table.setItems(null);
                                                Iteam_Add_table.setItems(data);
                                                type.setValue(null);
                                                games.setText("");
                                                buyp.setText("");
                                                sellp.setText("");
                                                QTY.setText("0");
                                                games.requestFocus();
                                                pb = true;
                                                grs = true;
                                                loaditemids();
                                            } else {
                                                MessageBox.showWarningMessage("Warning", "SUPPLIER NOT VALIED");
                                                Suppliretex.setText("");
                                                type.setValue(null);
                                                games.setText("");
                                                buyp.setText("");
                                                sellp.setText("");
                                                QTY.setText("");
                                            }
                                        } else {
                                            MessageBox.showWarningMessage("Warning", "GAME NOT VALIED");
                                            type.setValue(null);
                                            games.setText("");
                                            buyp.setText("");
                                            sellp.setText("");
                                            QTY.setText("");
                                        }
                                          
                                          
                                          }              
                                
                                   
                                    
                                } else {
                                    MessageBox.showWarningMessage("Warning", "SELLING PRICE NOT VALIED");
                                }
                            } else {
                                MessageBox.showWarningMessage("Warning", "BUYING PRICE NOT VALIED");
                            }
                        } else {
                            MessageBox.showWarningMessage("Warning", "QTY NOT VALIED");
                        }
                    } else {
                        MessageBox.showWarningMessage("Warning", "STORAGE TYPE NOT ENTERED");
                    }
                } else {
                    MessageBox.showWarningMessage("Warning", "GAME NOT VALIED");
                }
            } else {
                MessageBox.showWarningMessage("Warning", "SUPPLIER NOT VALIED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loaditemids() {
        try {

            java.sql.ResultSet rs1 = crudo.search("*", "stock", "");
            if (rs1.first()) {
                stid = Integer.parseInt(crudo.getLastId("idStock", "stock"));
                stid++;
            } else {
                stid = 1;
            }

            java.sql.ResultSet rs2 = crudo.search("*", "grnItem", "");
            if (rs2.first()) {
                grnitemid = Integer.parseInt(crudo.getLastId("grnItemId", "grnItem"));
                grnitemid++;
            } else {
                grnitemid = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void typeclick(MouseEvent event) {
        loadStoragemediums();
    }

    @FXML
    private void typeA(ActionEvent event) {
        System.out.println("acction");
        buyp.requestFocus();
    }

    @FXML
    private void closeA(ActionEvent event) {
       cl();
        Stage stage = (Stage) Itemid.getScene().getWindow();
        stage.close();
    }

    private void cl() {
      if (grs) {
            try {
                ResultSet rs = crudo.search("*", "grnItem", " where grn_idGrn='" + Itemid.getText() + "'");
                while (rs.next()) {
                    crudo.delete("stock", " grnItem_grnItemId='" + rs.getString("grnItemid") + "' ");
                }
                crudo.delete("grnItem", " grn_idGrn='" + Itemid.getText() + "' ");
                crudo.delete("grn", " idGrn='" + Itemid.getText() + "'");
                Itemid.setText(null);
                totalprice.setText(null);
                type.setValue(null);
                buyp.setText(null);
                sellp.setText(null);
                Suppliretex.setText(null);
                data.clear();
                Itemid.forward();
                loaditemids();
                loadstid();
                totalprice.setVisible(false);
                Suppliretex.setEditable(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            grs = false;
        } else {
            Itemid.setText(null);
            totalprice.setText(null);
            type.setValue(null);
            buyp.setText(null);
            sellp.setText(null);
            Suppliretex.setText(null);
            Suppliretex.setEditable(true);
            data.clear();
            Itemid.forward();
            loaditemids();
            loadstid();
            totalprice.setVisible(false);
            grs = true;
        }
    }

    @FXML
    private void addtotableA(ActionEvent event) {
          Add();
        games.requestFocus();
    }


}
