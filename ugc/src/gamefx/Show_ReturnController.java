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
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class Show_ReturnController implements Initializable {

    @FXML
    private JFXTextField barcodetex;
    @FXML
    private TableView<Show_Return_Details> show_return;
    @FXML
    private TableColumn<?, ?> gamecol;
    @FXML
    private TableColumn<?, ?> suppcol;
    @FXML
    private TableColumn<?, ?> qtycol;
    @FXML
    private TableColumn<?, ?> sataescol;
    @FXML
    private JFXButton havetoreturn;

    @FXML
    private JFXButton returned;
    @FXML
    private JFXTextField supptex;
    @FXML
    private JFXTextField gametex;
    @FXML
    private TableColumn<?, ?> barcol;
    @FXML
    private TableColumn<?, ?> senddcol;
    @FXML
    private TableColumn<?, ?> resolvoeddcol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        havetoreturn.setVisible(false);
        returned.setVisible(false);

        loadtable();
    }
    private ObservableList<Show_Return_Details> data = FXCollections.observableArrayList();
    CRUDOperations crudo = new CRUDOperationsImpl();

    @FXML
    private void valibartex(KeyEvent event) {
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
    private void bartexkr(KeyEvent event) {
    }
    int eid;
    String supp;
    String bar;
    String qty;

    @FXML
    private void show_returnclick(MouseEvent event) {
        if (show_return.getSelectionModel().getSelectedItem() != null) {
            Show_Return_Details details1 = show_return.getSelectionModel().getSelectedItem();
            barcodetex.setText(details1.getBar());
            bar = details1.getBar();
            gametex.setText(details1.getGame());
            supptex.setText(details1.getSupp());
            supp = details1.getSupp();
            eid = Integer.parseInt(details1.getEid());
            if (details1.getStates().equals("Not send")) {
                havetoreturn.setVisible(true);
                returned.setVisible(false);

            } else if (details1.getStates().equals("Send")) {
                havetoreturn.setVisible(false);
                returned.setVisible(true);

            } else {
                havetoreturn.setVisible(false);
                returned.setVisible(false);

            }
            qty = details1.getQty();

        } else {

        }
    }

    @FXML
    private void havetoreturnA(ActionEvent event) {
        String[] arr = new String[2];
        arr[0] = "yes";
        arr[1] = "no";
        String desition = MessageBox.showConfirmationMessage("Warning", "Are you Sure", arr);
        if (desition.equals("yes")) {
            try {
                String suppid = null;
                ResultSet rs = crudo.search("idsupplier", "supplier", "where Company_name='" + supp + "'");
                if (rs.next()) {
                    suppid = rs.getString("idsupplier");
                }
                crudo.save("supplierreturn(Supplier_idSupplier,stock_idStock,qty)", "'" + suppid + "','" + bar + "','" + qty + "'");
                crudo.delete("exchangeInvoice", "idExchangeInvoice='" + eid + "'");
//                String il = crudo.getLastId("supplierreturn", "idSupplierReturn");
//                Log.info("Return Item " + il + " Send to Supplier by " + user.getIdUser());
                havetoreturn.setVisible(false);
                loadtable();
                barcodetex.setText(null);
                supptex.setText(null);
                gametex.setText(null);
                System.out.println(eid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void returnedA(ActionEvent event) {
        String[] arr = new String[2];
        arr[0] = "yes";
        arr[1] = "no";
        String desition = MessageBox.showConfirmationMessage("Warning", "Are you Sure", arr);
        if (desition.equals("yes")) {
            try {
                String suppid = null;
                ResultSet rs = crudo.search("idsupplier", "supplier", "where Company_name='" + supp + "'");
                if (rs.next()) {
                    suppid = rs.getString("idsupplier");
                }
                crudo.update("supplierreturn", "resolvedDT= NOW()", " idsupplierreturn='" + eid + "'");
                Log.info("Return Item " + eid + " Resolved From supplier by user--" + user.getIdUser());
                ResultSet se = crudo.search("*", "stock", "where idstock='" + bar + "'");
                if (se.next()) {
                    int qty1 = se.getInt("qty") + Integer.parseInt(qty);
                    crudo.update("stock", "qty='" + qty1 + "'", " idstock='" + bar + "'");
                }
                returned.setVisible(false);
                loadtable();
                System.out.println(eid);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void valisupptex(KeyEvent event) {
        supptex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM  ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void supptexA(ActionEvent event) {
    }

    @FXML
    private void supptexkr(KeyEvent event) {
    }

    @FXML
    private void valigamrtex(KeyEvent event) {
        gametex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void gametexA(ActionEvent event) {
    }

    @FXML
    private void gametexkr(KeyEvent event) {
    }

    private void loadtable() {
        try {
            data.clear();
            ResultSet se = crudo.search("exchangeinvoice.`idExchangeInvoice` AS exchangeinvoice_idExchangeInvoice,\n"
                    + "     exchangeinvoice.`stock_has_gameinvoiceid` AS exchangeinvoice_stock_has_gameinvoiceid,\n"
                    + "     exchangeinvoice.`qty` AS exchangeinvoice_qty,\n"
                    + "     stock_has_gameinvoice.`stock_has_gameinvoiceid` AS stock_has_gameinvoice_stock_has_gameinvoiceid,\n"
                    + "     stock.`idStock` AS stock_idStock,\n"
                    + "     stock.`games_idGames` AS stock_games_idGames,\n"
                    + "     stock.`grnItem_grnItemId` AS stock_grnItem_grnItemId,\n"
                    + "     games.`idGames` AS games_idGames,\n"
                    + "     games.`name` AS games_name,\n"
                    + "     stock_has_gameinvoice.`gameinvoice_idgameinvoice` AS stock_has_gameinvoice_gameinvoice_idgameinvoice,\n"
                    + "     stock_has_gameinvoice.`stock_idStock` AS stock_has_gameinvoice_stock_idStock,\n"
                    + "     grnitem.`grnItemId` AS grnitem_grnItemId,\n"
                    + "     grnitem.`grn_idGrn` AS grnitem_grn_idGrn,\n"
                    + "     grn.`idGrn` AS grn_idGrn,\n"
                    + "     grn.`Supplier_idSupplier` AS grn_Supplier_idSupplier,\n"
                    + "     supplier.`idSupplier` AS supplier_idSupplier,\n"
                    + "     supplier.`Company_name` AS supplier_Company_name", "`stock_has_gameinvoice` stock_has_gameinvoice INNER JOIN `exchangeinvoice` exchangeinvoice ON stock_has_gameinvoice.`stock_has_gameinvoiceid` = exchangeinvoice.`stock_has_gameinvoiceid`\n"
                    + "     INNER JOIN `stock` stock ON stock_has_gameinvoice.`stock_idStock` = stock.`idStock`\n"
                    + "     INNER JOIN `games` games ON stock.`games_idGames` = games.`idGames`\n"
                    + "     INNER JOIN `grnitem` grnitem ON stock.`grnItem_grnItemId` = grnitem.`grnItemId`\n"
                    + "     INNER JOIN `grn` grn ON grnitem.`grn_idGrn` = grn.`idGrn`\n"
                    + "     INNER JOIN `supplier` supplier ON grn.`Supplier_idSupplier` = supplier.`idSupplier`", " ");
            while (se.next()) {
                String s = "Not send";
                Label l = new Label(s);
                data.add(new Show_Return_Details(se.getString("stock_has_gameinvoice_stock_idStock"), se.getString("games_name"), se.getString("exchangeinvoice_qty"), se.getString("supplier_Company_name"), s, se.getString("exchangeinvoice_idExchangeInvoice"),null,null));
                barcol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                gamecol.setCellValueFactory(new PropertyValueFactory<>("game"));
                qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
                suppcol.setCellValueFactory(new PropertyValueFactory<>("supp"));
                sataescol.setCellValueFactory(new PropertyValueFactory<>("states"));
                show_return.setItems(null);
                show_return.setItems(data);
            }
            ResultSet rs = crudo.search("supplierreturn.`idSupplierReturn` AS supplierreturn_idSupplierReturn,\n"
                    + "     supplierreturn.`Supplier_idSupplier` AS supplierreturn_Supplier_idSupplier,\n"
                    + "     supplierreturn.`stock_idStock` AS supplierreturn_stock_idStock,\n"
                    + "     supplierreturn.`qty` AS supplierreturn_qty,\n"
                    + "     supplierreturn.`resolvedDT` AS supplierreturn_resolvedDT,\n"
                    + "     supplierreturn.`returnDT` AS supplierreturn_returnDT,\n"
                    + "     stock.`idStock` AS stock_idStock,\n"
                    + "     stock.`games_idGames` AS stock_games_idGames,\n"
                    + "     stock.`grnItem_grnItemId` AS stock_grnItem_grnItemId,\n"
                    + "     games.`idGames` AS games_idGames,\n"
                    + "     games.`name` AS games_name,\n"
                    + "     supplier.`idSupplier` AS supplier_idSupplier,\n"
                    + "     supplier.`name` AS supplier_name,\n"
                    + "     supplier.`Company_name` AS supplier_Company_name", "`stock` stock INNER JOIN `supplierreturn` supplierreturn ON stock.`idStock` = supplierreturn.`stock_idStock`\n"
                    + "     INNER JOIN `games` games ON stock.`games_idGames` = games.`idGames`\n"
                    + "     INNER JOIN `supplier` supplier ON supplierreturn.`Supplier_idSupplier` = supplier.`idSupplier`", "");
            while (rs.next()) {
                String s = null;
                if (rs.getString("supplierreturn_resolvedDT")==null) {
                    s = "Send";
                } else {
                    s = "Resolved";
                }

                data.add(new Show_Return_Details(rs.getString("supplierreturn_stock_idStock"), rs.getString("games_name"), rs.getString("supplierreturn_qty"), rs.getString("supplier_Company_name"), s, rs.getString("supplierreturn_idSupplierReturn"), rs.getString("supplierreturn_returnDT"), rs.getString("supplierreturn_resolvedDT")));
                barcol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                gamecol.setCellValueFactory(new PropertyValueFactory<>("game"));
                qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
                suppcol.setCellValueFactory(new PropertyValueFactory<>("supp"));
                sataescol.setCellValueFactory(new PropertyValueFactory<>("states"));
                senddcol.setCellValueFactory(new PropertyValueFactory<>("sendd"));
                resolvoeddcol.setCellValueFactory(new PropertyValueFactory<>("resolved"));
                show_return.setItems(null);
                show_return.setItems(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
