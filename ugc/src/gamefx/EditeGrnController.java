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
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class EditeGrnController implements Initializable {

    @FXML
    private Label grnno;
    @FXML
    private Label grnprice;
    @FXML
    private Label grndate;
    @FXML
    private TableView<gameDetails> table;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> qty;
    @FXML
    private TableColumn<?, ?> price;
    private JFXTextField pricete;
    private JFXTextField qtyte;
    private JFXComboBox<String> gamecom;
    private int i;
    private TableColumn<?, ?> itemidc;
    @FXML
    private TableColumn<?, ?> id;
    @FXML
    private Label supplier;
    @FXML
    private PieChart piechart;
    @FXML
    private JFXButton close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loaddetails();

    }
    private ObservableList<gameDetails> data = FXCollections.observableArrayList();
    int grn;

    private void loaddetails() {
        try {
            grn = HomeTemplateController.grn;
            CRUDOperations crudo = new CRUDOperationsImpl();
            java.sql.ResultSet rs = crudo.search("*", "grn", "where idgrn='" + grn + "'");
            while (rs.next()) {
                ResultSet s1 = crudo.search("Company_name", "supplier", "where idsupplier='" + rs.getString("supplier_idsupplier") + "'");
                while (s1.next()) {
                    supplier.setText(s1.getString("Company_name"));
                }
                grnno.setText(rs.getString("idgrn"));
                grnprice.setText(rs.getString("price"));
                String[] da = rs.getString("date").split(" ");
                grndate.setText(da[0]);
                loadpiechart();
            }
            data.clear();
            java.sql.ResultSet gs = crudo.search("games_idGames,buying_price,qty,grnitemid", "grnitem", "where grn_idGrn='" + grn + "'");
            while (gs.next()) {
                System.out.println("dddd");
                java.sql.ResultSet gn = crudo.search("name", "games", "where idGames='" + gs.getString("games_idGames") + "'");
                while (gn.next()) {
                    System.out.println("ff");
                    data.add(new gameDetails(gn.getString("name"), gs.getString("buying_price"), gs.getString("qty"), gs.getString("grnitemid")));
                }
            }
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            price.setCellValueFactory(new PropertyValueFactory<>("price"));
            qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
            table.setItems(null);
            table.setItems(data);
        } catch (Exception ex) {
            Log.error(ex);
        }

    }
    static String gid = null;

    @FXML
    private void tofiled(MouseEvent event) {
    }

    @FXML
    private void cl(ActionEvent event) {
        Stage stage = (Stage) grnno.getScene().getWindow();
        stage.close();
    }

    private void loadpiechart() {
        CRUDOperations crudo = new CRUDOperationsImpl();
        NumberFormat formatter = new DecimalFormat("#0.00");
        double totiqty = 0;
        double soldiqty = 0;
        double balance = 0;
        double reqty = 0;
        try {
            ResultSet rs = crudo.search("*", "grnitem", " where grn_idgrn='" + grn + "'");
            while (rs.next()) {
                totiqty = totiqty + rs.getInt("QTY");
                ResultSet rs1 = crudo.search("*", "stock", "where grnitem_grnitemid='" + rs.getInt("grnitemid") + "'");
                while (rs1.next()) {
                    soldiqty = soldiqty + rs1.getInt("QTY");
                    System.out.println("fuck-" + soldiqty + " " + rs1.getInt("QTY"));
                    ResultSet re = crudo.search("exchangeinvoice.`idExchangeInvoice` AS exchangeinvoice_idExchangeInvoice,\n"
                            + "     exchangeinvoice.`stock_has_gameinvoiceid` AS exchangeinvoice_stock_has_gameinvoiceid,\n"
                            + "     exchangeinvoice.`qty` AS exchangeinvoice_qty,\n"
                            + "     stock.`idStock` AS stock_idStock,\n"
                            + "     stock_has_gameinvoice.`stock_idStock` AS stock_has_gameinvoice_stock_idStock,\n"
                            + "     stock_has_gameinvoice.`stock_has_gameinvoiceid` AS stock_has_gameinvoice_stock_has_gameinvoiceid", "`stock` stock INNER JOIN `stock_has_gameinvoice` stock_has_gameinvoice ON stock.`idStock` = stock_has_gameinvoice.`stock_idStock`\n"
                            + "     INNER JOIN `exchangeinvoice` exchangeinvoice ON stock_has_gameinvoice.`stock_has_gameinvoiceid` = exchangeinvoice.`stock_has_gameinvoiceid`", "where stock_idStock='" + rs1.getString("idstock") + "'");
                    while (re.next()) {
                        reqty += re.getDouble("exchangeinvoice_qty");
                        System.out.println("fuck  " + reqty);
                    }
                    ResultSet re1 = crudo.search("stock.`idStock` AS stock_idStock,\n"
                            + "     supplierreturn.`idSupplierReturn` AS supplierreturn_idSupplierReturn,\n"
                            + "     supplierreturn.`stock_idStock` AS supplierreturn_stock_idStock,\n"
                            + "     supplierreturn.`qty` AS supplierreturn_qty,\n"
                            + "     supplierreturn.`resolvedDT` AS supplierreturn_resolvedDT", "`stock` stock INNER JOIN `supplierreturn` supplierreturn ON stock.`idStock` = supplierreturn.`stock_idStock`", "WHERE stock_idStock='" + rs1.getString("idstock") + "' and supplierreturn.`resolvedDT` is null");
                    while (re1.next()) {
                        reqty += re1.getDouble("supplierreturn_qty");
                        System.out.println("fuck  " + reqty);
                    }
                }
            }

            balance = totiqty - soldiqty;
            balance = balance - reqty;
            System.out.println(totiqty);
            System.out.println(soldiqty);
            System.out.println(balance);

            double i1 = (100 * (reqty / totiqty));
            double i2 = (100 * (balance / totiqty));
            double i3 = (100 * (soldiqty / totiqty));
            System.out.println("vmjvirt" + i1);
            if (i1 != 0) {
                String dx2 = formatter.format(i1);
                i1 = Double.valueOf(dx2);
            }
            if (i2 != 0) {
                String dx = formatter.format(i2);
                i2 = Double.valueOf(dx);
            }
            if (i3 != 0) {
                String dx1 = formatter.format(i3);
                i3 = Double.valueOf(dx1);
            }
            System.out.println("100*" + "(" + reqty + "/" + totiqty + ")==" + 100 * (totiqty / reqty));
            System.out.println("100*" + "(" + balance + "/" + totiqty + ")==" + 100 * (totiqty / balance));
            System.out.println("100*" + "(" + soldiqty + "/" + totiqty + ")==" + 100 * (totiqty / soldiqty));

            System.out.println(i1 + "%");
            System.out.println(i2 + "%");
            System.out.println(i3 + "%");

            int i = 1;
            while (i == 1) {
                i = 2;
                ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                        new PieChart.Data("Sold :" + i2 + "%", i2),
                        new PieChart.Data("Sork :" + i3 + "%", i3),
                        new PieChart.Data("Return :" + i1 + "%", i1)
                );
                piechart.setData(list);
                piechart.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
