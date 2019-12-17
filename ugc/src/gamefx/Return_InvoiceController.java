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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import sales.POSController;
import sales.posTable;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class Return_InvoiceController implements Initializable {

    @FXML
    private JFXTextField invnotex;
    @FXML
    private TableView<ReturninvDetails> invtable;
    @FXML
    private TableView<ReturninvDetails> returntable;
    @FXML
    private JFXTextField barcodetex;
    @FXML
    private JFXTextField qtytex;
    @FXML
    private JFXTextField gametex;
    @FXML
    private Label iodlable;
    @FXML
    private JFXButton returnbtn;
    @FXML
    private JFXButton Save;
    @FXML
    private JFXButton clear;
    @FXML
    private TableColumn<?, ?> invbarcol;
    @FXML
    private TableColumn<?, ?> invgamecol;
    @FXML
    private TableColumn<?, ?> invqtycol;
    @FXML
    private TableColumn<?, ?> rebarcol;
    @FXML
    private TableColumn<?, ?> regamecol;
    @FXML
    private TableColumn<?, ?> reqtycol;
    private POSController controller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iodlable.setVisible(false);
    }
    CRUDOperations crudo = new CRUDOperationsImpl();
    private ObservableList<ReturninvDetails> data = FXCollections.observableArrayList();
    private ObservableList<ReturninvDetails> data1 = FXCollections.observableArrayList();

    @FXML
    private void invnotexkr(KeyEvent event) {
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @FXML
    private void invnotexA(ActionEvent event) {
        invtableload();
        invnotex.setEditable(false);
    }

    @FXML
    private void valibarcodetex(KeyEvent event) {
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
    private void valiqtytex(KeyEvent event) {
        qtytex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void qtytexA(ActionEvent event) {
        addtoretable();

    }

    @FXML
    private void valigametex(KeyEvent event) {
    }

    @FXML
    private void gametexA(ActionEvent event) {
    }

    @FXML
    private void returnbtnA(ActionEvent event) {
        addtoretable();
    }

    @FXML
    private void saveA(ActionEvent event) {
        try {
            if (returntable.getItems().size() != 0) {
                System.out.println(returntable.getItems().size());
                for (int i = 0; i < returntable.getItems().size(); i++) {
                    String barcode = returntable.getItems().get(i).getBar();
                    String qty = returntable.getItems().get(i).getQty();
                    String invid = invnotex.getText();
                    int il=0;
                    ResultSet rs = crudo.search("*", "stock_has_gameinvoice", "where stock_idstock='" + barcode + "' and gameinvoice_idgameinvoice='" + invid + "' ");
                    while (rs.next()) {
                        il++;
                        crudo.save("exchangeinvoice(stock_has_gameinvoiceid,qty)", "'" + rs.getString("stock_has_gameinvoiceid") + "','" + qty + "'");

                        crudo.update("stock_has_gameinvoice", "qty='" + (Integer.parseInt(rs.getString("qty")) - Integer.parseInt(qty)) + "'", "stock_has_gameinvoiceid='" + rs.getString("stock_has_gameinvoiceid") + "'");
                        ResultSet de = new CRUDOperationsImpl().search("stock.*,games.name", "stock inner join games on stock.games_idGames=games.idGames", "where stock.idStock='" + rs.getString("stock_idStock") + "' ");
                        while (de.next()) {
                            double total = (-1 * (Double.parseDouble(de.getString("sellingPrice")) * (Double.parseDouble(qty))));

                            sales.POSController.data.add(new posTable(de.getString("idstock"), de.getString("games.name"), -1 * Integer.parseInt(qty), Double.parseDouble(de.getString("sellingPrice")), total));

                        }
                                          
                    }
                    MessageBox.showInformationMessage("Success", "RETURN ITEM SUCCESSFULLY ADDED");
  Log.info("Return item "+il+"added for"+ invnotex.getText()+"by"+user.getIdUser());
                    invnotex.setText(null);
                    invtable.setItems(null);

                    invnotex.setEditable(true);
                    invnotex.requestFocus();
                    System.out.println("fuck inside");
                    System.out.println(returntable.getItems().size());
                }
                System.out.println("fuck away");
               
                controller.loadSub();
                Log.info("Return item for ");
                returntable.setItems(null);
            } else {
                MessageBox.showWarningMessage("Warning", "NO ITEM SELECTED");
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void clearA(ActionEvent event) {
        invnotex.setText(null);
        barcodetex.setText(null);
        qtytex.setText(null);
        gametex.setText(null);
        invtable.setItems(null);
        returntable.setItems(null);
        iodlable.setVisible(false);
        invnotex.setEditable(true);
        invnotex.requestFocus();
    }

    @FXML
    private void invtableclick(MouseEvent event) {
        if (invtable.getSelectionModel().getSelectedItem() != null) {
            ReturninvDetails details1 = invtable.getSelectionModel().getSelectedItem();
            barcodetex.setText(details1.getBar());
            gametex.setText(details1.getGame());
            qtytex.requestFocus();

        } else {

        }
    }

    private void addtoretable() {
        if (qtytex.getText() != null && !(qtytex.getText().equals(""))) {
            if (barcodetex.getText() != null && !(barcodetex.getText().equals(""))) {
                if (Integer.parseInt(barcodetex.getText()) < 0) {
                    MessageBox.showErrorMessage("error", "wron qty input");
                } else {
                    try {
                        ResultSet sr = crudo.search("qty", "stock_has_gameinvoice", "where stock_idStock='" + Integer.parseInt(barcodetex.getText()) + "' and gameinvoice_idgameinvoice='" + invnotex.getText() + "'");
                        int d = 0;
                        while (sr.next()) {
                            if(sr.getInt("qty")>0){
                            d = sr.getInt("qty");
                            }
                            
                        }
                        
                        if (d < Integer.parseInt(qtytex.getText())) {
                            MessageBox.showWarningMessage("Warning", "Not enough quantity");
                        } else {
                            try {
                                System.out.println("lklkklklkklkkl");
                                System.err.println(returntable.getItems().equals(null));
                                if (returntable.getItems().size() == 0) {
                                    System.out.println("first row");

                                    data1.add(new ReturninvDetails(barcodetex.getText(), gametex.getText(), qtytex.getText()));
                                    rebarcol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                                    regamecol.setCellValueFactory(new PropertyValueFactory<>("game"));
                                    reqtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));

                                    returntable.setItems(null);
                                    returntable.setItems(data1);
                                }
                                boolean b = false;
                                int row = 0;
                                for (int i = 0; i < returntable.getItems().size(); i++) {
                                    if (returntable.getItems().get(i).getBar().equals(barcodetex.getText())) {
                                        b = true;
                                        row = i;
                                        break;
                                    }
                                    b = false;
                                }

                                if (b) {
                                    int pastQty = Integer.parseInt(returntable.getItems().get(row).getQty());
                                    returntable.getItems().remove(row);

                                    data1.add(new ReturninvDetails(barcodetex.getText(), gametex.getText(), qtytex.getText()));
                                    rebarcol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                                    regamecol.setCellValueFactory(new PropertyValueFactory<>("game"));
                                    reqtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));

                                    returntable.setItems(null);
                                    returntable.setItems(data1);

                                } else {
                                    data1.add(new ReturninvDetails(barcodetex.getText(), gametex.getText(), qtytex.getText()));
                                    rebarcol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                                    regamecol.setCellValueFactory(new PropertyValueFactory<>("game"));
                                    reqtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));

                                    returntable.setItems(null);
                                    returntable.setItems(data1);
                                }

                                barcodetex.setText(null);
                                qtytex.setText(null);
                                gametex.setText(null);

                            } catch (Exception ex) {
                                Log.error(ex);
                            }
                        }
                    } catch (Exception ex) {
                        Log.error(ex);
                    }
                }
            } else {
                MessageBox.showWarningMessage("Warning", "ITEM NOT SELECTED");
            }
        } else {
            MessageBox.showWarningMessage("Warning", "QTY NOT ENTERED");
        }
    }

    private void invtableload() {
        if (invnotex.getText() != null && !(invnotex.getText().equals(""))) {
            data.clear();
            try {
                ResultSet rs = crudo.search("*", "gameinvoice", "where idgameinvoice='" + invnotex.getText() + "'");
                if (rs.next()) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(rs.getDate("date"));
                    c.add(Calendar.DATE, 5);
                    String cdate = sdf.format(c.getTime());
                    System.out.println(cdate);
                    ResultSet check = crudo.search("*", "gameinvoice", "where now() between '" + rs.getString("date") + "'  and '" + cdate + "' and idgameinvoice='" + rs.getString("idgameinvoice") + "'");
                    if (check.next()) {
                        iodlable.setVisible(false);
                        ResultSet se = crudo.search("stock_has_gameinvoice.`stock_idStock` AS stock_has_gameinvoice_stock_idStock,\n"
                                + "     stock_has_gameinvoice.`gameinvoice_idgameinvoice` AS stock_has_gameinvoice_gameinvoice_idgameinvoice,\n"
                                + "     stock_has_gameinvoice.`qty` AS stock_has_gameinvoice_qty,\n"
                                + "     stock.`idStock` AS stock_idStock,\n"
                                + "     stock.`grnItem_grnItemId` AS stock_grnItem_grnItemId,\n"
                                + "     stock.`sellingPrice` AS stock_sellingPrice,\n"
                                + "     stock.`games_idGames` AS stock_games_idGames,\n"
                                + "     games.`idGames` AS games_idGames,\n"
                                + "     games.`name` AS games_name,\n"
                                + "     games.`gametype_idGameType` AS games_gametype_idGameType,\n"
                                + "     games.`description` AS games_description,\n"
                                + "     games.`status` AS games_status,\n"
                                + "     games.`background` AS games_background", "`gameinvoice` gameinvoice INNER JOIN `stock_has_gameinvoice` stock_has_gameinvoice ON gameinvoice.`idgameinvoice` = stock_has_gameinvoice.`gameinvoice_idgameinvoice`\n"
                                + "     INNER JOIN `stock` stock ON stock_has_gameinvoice.`stock_idStock` = stock.`idStock`\n"
                                + "     INNER JOIN `games` games ON stock.`games_idGames` = games.`idGames`", "where idgameinvoice = '" + rs.getString("idgameinvoice") + "'");
                        while (se.next()) {
                            System.out.println("aloo");
                            if (se.getInt("stock_has_gameinvoice_qty") > 0) {
                                
                                data.add(new ReturninvDetails(se.getString("stock_idStock"), se.getString("games_name"), se.getString("stock_has_gameinvoice_qty")));
                            }
                        }
                        invbarcol.setCellValueFactory(new PropertyValueFactory<>("bar"));
                        invgamecol.setCellValueFactory(new PropertyValueFactory<>("game"));
                        invqtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));
                        System.out.println("wesi");
                        invtable.setItems(null);
                        invtable.setItems(data);
                    } else {
                        iodlable.setVisible(true);
                    }

                } else {
                    MessageBox.showWarningMessage("Warrning", "INVOICE NO NOT VALIED");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MessageBox.showWarningMessage("Warrning", "NO INVOICE NO ENTERED");
        }
    }

    /**
     * @return the controller
     */
    public POSController getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(POSController controller) {
        this.controller = controller;
    }

}
