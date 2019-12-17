/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class HomeTemplateController implements Initializable {

    @FXML
    private TableView<GrnDetails> addtable1;
    @FXML
    private JFXTextField Search;
    @FXML
    private JFXTextField grnNo1;
    private JFXDatePicker date1;
    @FXML
    private JFXTextField price1;
    @FXML
    private JFXButton Cancel1;
    @FXML
    private JFXButton more;
    static SimpleDateFormat sdf;
    static SimpleDateFormat sdf1;
    String da;

    @FXML
    private TableColumn<?, ?> grnnoc;
    @FXML
    private TableColumn<?, ?> pricec;
    @FXML
    private TableColumn<?, ?> datec;

    @FXML
    private JFXButton print1;
    @FXML
    private JFXButton printall;
    @FXML
    private JFXButton loadAll;
    @FXML
    private JFXTextField serchgrndate;
    @FXML
    private TableColumn<?, ?> Suppliercol;
    @FXML
    private JFXTextField Supplier;
    @FXML
    private JFXDatePicker stdate;
    @FXML
    private JFXDatePicker enddate;
    @FXML
    private JFXButton searchbydate;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {

        loadAllto();
    }

    private ObservableList<GrnDetails> data2;

    @FXML
    private void searchm(KeyEvent event) {
    }
    static int s;
    static int id1;
    static int i;

    private void clearse() {

        grnNo1.setText(null);
        price1.setText(null);
        Search.setText(null);
        Supplier.setText(null);
        data2.clear();
        loadAllto();
        serchgrndate.setText(null);
        stdate.setValue(null);
        enddate.setValue(null);
    }
    public static int grn = 0;

    @FXML
    private void sertofi(MouseEvent event) {
        System.out.println("jhyrihjiy");
        GrnDetails details = addtable1.getSelectionModel().getSelectedItem();
        grnNo1.setText(details.getGno());
        price1.setText(details.getPrice());
        serchgrndate.setText(details.getDat());
        Supplier.setText(details.getSupp());
        grn = Integer.parseInt(grnNo1.getText());
    }

    @FXML
    private void details(MouseEvent event) {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            String no = grnNo1.getText();
            ResultSet rs = crudo.search("*", "grn", "where idgrn='" + no + "'");
            if (rs.next()) {
                System.out.println("in if");
                HomeTemplateController.grn = Integer.parseInt(grnNo1.getText());
                System.out.println("in else");
                Parent root = FXMLLoader.load(getClass().getResource("EditeGrn.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Grn Details");
                stage.setScene(scene);
                stage.setMaximized(false);
                stage.show();

            } else {
                MessageBox.showWarningMessage("Warning", "GRN NO SELECTED");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void ssss(KeyEvent event) {
        addtable1.setItems(null);
        data2 = FXCollections.observableArrayList();
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();

            if (!(Search.getText().equals("")) && Search.getText() != null) {

                data2.clear();
                if (Character.isDigit(event.getCharacter().toCharArray()[0])) {
                    System.err.println("digit");
                    System.err.println("digit");
                    System.err.println("digit");
                } else {
                    System.err.println(" not digit");
                }

                ResultSet se = crudo.search("*", "stock", "where idstock like '" + Search.getText() + "' ");

                while (se.next()) {
                    ResultSet se1 = crudo.search("*", "grnitem", "where grnitemid like '" + se.getString("grnitem_grnitemid") + "' ");
                    while (se1.next()) {
                        java.sql.ResultSet rs = crudo.search("*", "grn", "where idgrn like'" + se1.getString("grn_idgrn") + "' ");
                        String s = "";
                        String[] da = null;
                        while (rs.next()) {
                            ResultSet sup = crudo.search("*", "supplier", "where idsupplier='" + rs.getString("supplier_idsupplier") + "'");
                            if (sup.next()) {
                                System.out.println("bgfsdgfdgfdgfdgfdgfdgh");

                                s = sup.getString("Company_name");
                            }
                            da = rs.getString("date").split(" ");
                            data2.add(new GrnDetails(rs.getString("idgrn"), rs.getString("price"), da[0], s));

                        }
                    }
                }

                java.sql.ResultSet sup1 = crudo.search("*", "supplier", "where Company_name like '" + Search.getText() + "%' || name like '" + Search.getText() + "%' ");
                while (sup1.next()) {
                    ResultSet rs1 = crudo.search("*", "grn", "where Supplier_idSupplier='" + sup1.getString("idSupplier") + "' ");
                    while (rs1.next()) {
                        System.out.println("bgfsdgfdgfdgfdgfdgfdgh");
                        String[] da1 = rs1.getString("date").split(" ");
                        data2.add(new GrnDetails(rs1.getString("idgrn"), rs1.getString("price"), da1[0], sup1.getString("Company_name")));
                    }
                }

                grnnoc.setCellValueFactory(new PropertyValueFactory<>("gno"));
                pricec.setCellValueFactory(new PropertyValueFactory<>("price"));
                datec.setCellValueFactory(new PropertyValueFactory<>("dat"));
                Suppliercol.setCellValueFactory(new PropertyValueFactory<>("supp"));

                addtable1.setItems(null);
                addtable1.setItems(data2);
            } else {
                addtable1.setItems(null);
                clearse();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void valippp(KeyEvent event) {
        price1.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void clear2(ActionEvent event) {
        clearse();
    }

    @FXML
    private void print1(ActionEvent event) throws Exception {
        try {
            PrintReport pr;
            if (grnNo1.getText() != null && !(grnNo1.getText().equals(""))) {
                int i = Integer.parseInt(grnNo1.getText());
                Date d = new Date();
                time = Calendar.getInstance();
                String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "A.M" : "P.M";
                sdf = new SimpleDateFormat("hh:mm:ss");
                String t = (sdf.format(d));
                sdf = new SimpleDateFormat("dd:MM:yyyy");
                String da = (sdf.format(d) + " " + ampmString);
                Map<String, Object> params = new HashMap<>();

                params.put("date", da);
                params.put("time", t);
                params.put("grnid", i+"");

                pr = new PrintReport(params, "GRN.jrxml");
                pr.start();
            } else {
                MessageBox.showWarningMessage("Warrning", "NO STOCK ITEM SELECTED");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
    Boolean grs = false;
    static Calendar time;
    boolean pb = false;

    @FXML
    private void printallA(ActionEvent event) {
        try {
            PrintReport pr;
            if (stdate.getValue() != null) {
                if (enddate.getValue() != null) {

                    Map<String, Object> params = new HashMap<>();

                    params.put("startDate", stdate.getValue() + "");
                    params.put("endDate", enddate.getValue() + "");

                    pr = new PrintReport(params, "ALLGrns.jrxml");
                    pr.start();
                } else {
                    MessageBox.showWarningMessage("Warrning", "CHECK YOUR INPUTS");
                }
            } else {
                MessageBox.showWarningMessage("Warrning", "CHECK YOUR INPUTS");
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

    }

    @FXML
    private void loadAllA(ActionEvent event) {
        loadAllto();
    }

    private void loadAllto() {
        try {
            CRUDOperations crudo = new CRUDOperationsImpl();
            data2 = FXCollections.observableArrayList();
            java.sql.ResultSet rs = crudo.search("*", "grn", "");
            while (rs.next()) {
                ResultSet sup = crudo.search("*", "supplier", "where idsupplier='" + rs.getString("supplier_idsupplier") + "'");
                while (sup.next()) {
                    String[] da = rs.getString("date").split(" ");
                    data2.add(new GrnDetails(rs.getString("idgrn"), rs.getString("price"), da[0], sup.getString("Company_name")));
                }
            }
            grnnoc.setCellValueFactory(new PropertyValueFactory<>("gno"));
            pricec.setCellValueFactory(new PropertyValueFactory<>("price"));
            datec.setCellValueFactory(new PropertyValueFactory<>("dat"));
            Suppliercol.setCellValueFactory(new PropertyValueFactory<>("supp"));
            addtable1.setItems(null);
            addtable1.setItems(data2);
        } catch (Exception ex) {
            Log.error(ex);
        }

    }

    @FXML
    private void searchbydateA(ActionEvent event) {
        CRUDOperations crudo = new CRUDOperationsImpl();
        try {
            if (stdate.getValue() != null) {
                if (enddate.getValue() != null) {
                    data2.clear();
                    ResultSet rs = crudo.search("*", "grn", " where date between '" + stdate.getValue() + "' and'" + enddate.getValue() + "' ");
                    while (rs.next()) {
                        ResultSet sup = crudo.search("*", "supplier", "where idsupplier='" + rs.getString("supplier_idsupplier") + "'");
                        while (sup.next()) {
                            String[] da = rs.getString("date").split(" ");
                            data2.add(new GrnDetails(rs.getString("idgrn"), rs.getString("price"), da[0], sup.getString("Company_name")));
                        }
                    }
                    grnnoc.setCellValueFactory(new PropertyValueFactory<>("gno"));
                    pricec.setCellValueFactory(new PropertyValueFactory<>("price"));
                    datec.setCellValueFactory(new PropertyValueFactory<>("dat"));
                    Suppliercol.setCellValueFactory(new PropertyValueFactory<>("supp"));
                    addtable1.setItems(null);
                    addtable1.setItems(data2);
                    stdate.setValue(null);
                    enddate.setValue(null);
                } else {
                    MessageBox.showWarningMessage("Warrning", "CHECK YOUR INPUTS");
                }
            } else {
                MessageBox.showWarningMessage("Warrning", "CHECK YOUR INPUTS");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
