/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import accounts.user;
import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.PrintReport;
import gamefx.Return_InvoiceController;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

/**
 * FXML Controller class
 *
 * @author Roshana Pitigala
 */
public class POSController implements Initializable {

    private ResultSet rs;
    private CRUDOperationsImpl db;
    @FXML
    private JFXTextField invoiceDate;
    @FXML
    private JFXTextField invoiceCashier;
    @FXML
    private JFXTextField itemId;
    @FXML
    private JFXTextField itemName;
    @FXML
    private JFXTextField Qty;
    @FXML
    private JFXTextField unitPrice;
    @FXML
    private JFXButton stockInvoice;
    String gameinvoiceid;
    String tokenid;
    @FXML
    private TableColumn<?, ?> Name;
    @FXML
    private TableColumn<?, ?> qty;
    @FXML
    private TableColumn<?, ?> uPrice;
    @FXML
    private TableColumn<?, ?> total;
    @FXML
    private TableView<posTable> loadItems;
    @FXML
    private JFXTextField paid;
    @FXML
    private TableColumn<?, ?> id;
    private JFXTextField tokenId;
    private JFXTextField Duration;
    private JFXComboBox<String> Packages;
    private JFXTextField Charge;
    private JFXTextField paid1;
    @FXML
    private JFXTextField balance;
    @FXML
    private JFXTextField subTotal;
    @FXML
    private JFXButton clear;
    @FXML
    private JFXButton Return;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(url + "url");
        System.out.println(rb + "was");
        db = new CRUDOperationsImpl();
        invoiceInfoLoad();
        //loadSave(true);
        //packages();
    }

    private void invoiceInfoLoad() {
        String dates = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        LocalDate date = LocalDate.now();
        //System.out.println(date);
        invoiceDate.setText(date.toString());

        String uid = accounts.user.getIdUser();
        try {
            ResultSet rs = db.search("name", "user", "where idUser='" + uid + "'");
            while (rs.next()) {
                String uname = rs.getString("name");
                invoiceCashier.setText(uname);
            }
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }

    }

    private void stockInvoice(MouseEvent event) {
        gettingData();

    }

    private void loadSave(boolean b) {
        boolean val = checkLastValue();
        boolean a = b;
        try {
            db.getLastId("idgameinvoice", "gameinvoice");
            if (b) {
                db.save("gameinvoice(date,user_idUser)", "'" + invoiceDate.getText() + "','" + accounts.user.getIdUser() + "'");
            } else {

            }
        } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void loadDetails(KeyEvent event) {
        String code = event.toString();
        System.out.println(event);
        System.out.println(code);
        if (event.getCode().equals(KeyCode.ENTER)) {

            try {
                //loadSave(true);
                ResultSet rs = db.search("stock.games_idGames,stock.sellingPrice,games.name",
                        "games join stock on stock.games_idGames=games.idGames", "where idStock='" + itemId.getText() + "'");
                while (rs.next()) {
                    itemName.setText(rs.getString("games.name"));
                    String gameId = rs.getString("stock.games_idGames");
                    unitPrice.setText(rs.getString("stock.sellingPrice"));
                }
                rs = new CRUDOperationsImpl().search("qty", "stock", "where idStock='" + itemId.getText() + "'");
                while (rs.next()) {
                    int qty = rs.getInt("qty");
                    if (qty != 0) {
                        Qty.requestFocus();

                    } else {
                        MessageBox.showErrorMessage("error", "not enough qty for this stock");
                    }
                }

            } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
                Logger.getLogger(POSController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.lang.NullPointerException ex) {
                if (Qty.getText().isEmpty()) {
                    Alert al1 = new Alert(Alert.AlertType.WARNING, "Invaid Item Number", ButtonType.OK);
                    al1.showAndWait();
                    if (al1.getResult() == ButtonType.OK) {
                        al1.close();
                    }
                }
            }
        } else {
            System.out.println("out");
        }
    }
    public static ObservableList<posTable> data = FXCollections.observableArrayList();

    private void gettingData() {
//        //loadSave(true);
//        if (!balance.equals(null)) {
//            CRUDOperationsImpl db = new CRUDOperationsImpl();
//            posTable posTable = loadItems.getSelectionModel().getSelectedItem();
//            int rowcount = loadItems.getSelectionModel().getTableView().getItems().size();
//            double tot = 0;
//            double quantitiy = 0;
//            //loadSave("stock");
//            //qun;
//            ResultSet rs1 = null;
//            try {
//                String upid = itemId.getText();
//                String id = db.getLastId("idgameinvoice", "gameinvoice");
//                ResultSet rs = db.search("stock_has_gameinvoice.qty,stock_has_gameinvoice.stock_idStock,stock.sellingPrice", "stock inner join stock_has_gameinvoice on stock_has_gameinvoice.stock_idStock=stock.idStock", "where gameinvoice_idgameinvoice='" + id + "'");
//                //System.out.println(rs.next()+"wwwwwwwwwwww");
//                while (rs.next()) {
//                    double qun = Double.parseDouble(rs.getString("stock_has_gameinvoice.qty"));
//                    System.out.println(qun + "ewewewe");
//                    String sid = rs.getString("stock_has_gameinvoice.stock_idStock");
//                    double sprice = Double.parseDouble(rs.getString("stock.sellingPrice"));
//                    System.out.println(sprice + "wqwqwq");
//                    tot += qun * sprice;
//                    System.out.println(tot + "qqq");
//                    System.out.println(tot + "waseem");
//                    int q = Integer.parseInt(rs.getString("stock_has_gameinvoice.qty"));
//                    db.update("stock", "qty=qty-" + q + "", " idStock='" + sid + "'");
//                }
//
//                /*for (int i = 0; i <= rowcount; i++) {
//                System.out.println(rowcount);
//                String qty = posTable.getQty();
//            }*/
//                //db.save("gameinvoice", "'"+invoiceNo.getText()+"','"+total+"','"+payment.getText()+"'");
//                String a = db.getLastId("idgameinvoice", "gameinvoice");
//                db.update("gameinvoice", "total='" + tot + "',paid='" + paid.getText() + "'", " idgameinvoice='" + a + "'");
//                stockupdate();
//                clear1();
//                Alert al1 = new Alert(Alert.AlertType.INFORMATION, "Game selling invoice added", ButtonType.OK);
//                al1.showAndWait();
//                if (al1.getResult() == ButtonType.OK) {
//                    al1.close();
//                }
//                
//                System.out.println("save");
//
//            } catch (SQLException ex) {
//                Logger.getLogger(POSController.class
//                        .getName()).log(Level.SEVERE, null, ex);
//
//            } catch (ClassNotFoundException ex) {
//                Logger.getLogger(POSController.class
//                        .getName()).log(Level.SEVERE, null, ex);
//
//            } catch (URISyntaxException ex) {
//                Logger.getLogger(POSController.class
//                        .getName()).log(Level.SEVERE, null, ex);
//
//            } catch (IOException ex) {
//                Logger.getLogger(POSController.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }
//            try {
//                    report();
//                } catch (JRException ex) {
//                    Logger.getLogger(POSController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//        } else {
//            balValidate1();
//        }
    }

    private void stockupdate() {
        CRUDOperations db = new CRUDOperationsImpl();

    }

    @FXML
    private void qtyValidate(KeyEvent event) {
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
        }
    }

    @FXML
    private void paymentValidate(KeyEvent event) {
        if (!"0123456789.".contains(event.getCharacter())) {
            event.consume();
        }
    }

    private void tokenPayValidate(KeyEvent event) {
        if (!"0123456789.".contains(event.getCharacter())) {
            event.consume();
        }
    }

    @FXML
    private void loadAll(MouseEvent event) {

        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("showInvoice.fxml"))));
            stage.show();

//        ObservableList<posTable> data3 = FXCollections.observableArrayList();
//        LocalDate today = LocalDate.now();
////        String total = null;
////        String id = null;
////        String qty = null;
////        String price = null;
////        String name = null;
//        try {
//            boolean a = checkLastValue();
//            ResultSet rs = db.search("idgameinvoice", "gameinvoice", "where date='" + today + "'");
//            ResultSet r = null;
//            while (rs.next()) {
//                int i = 0;
//                //total[i++] = rs.getString("total");
//                String gameid = rs.getString("idgameinvoice");
//                r = db.search("gameinvoice.total,stock_has_gameinvoice.stock_idStock,stock_has_gameinvoice.qty,stock.games_idGames,stock.sellingPrice,games.name", "gameinvoice INNER join stock_has_gameinvoice on gameinvoice.idgameinvoice=stock_has_gameinvoice.gameinvoice_idgameinvoice INNER JOIN stock on stock_has_gameinvoice.stock_idStock=stock.idStock INNER join games on stock.games_idgames=games.idGames ", "where idgameinvoice='" + gameid + "'");
//                //rs1 = db.search("stock_idStock,qty", "stock_has_gameinvoice", "where gameinvoice_idgameinvoice='" + gameid + "'");
//            }
//            while (r.next()) {
//                String total = r.getString("gameinvoice.total");
//                String id = r.getString("stock_has_gameinvoice.stock_idStock");
//                String qty = r.getString("stock_has_gameinvoice.qty");
//                String price = r.getString("stock.sellingPrice");
//                String name = r.getString("games.name");
//                data3.add(new posTable(id, name, Integer.parseInt(qty), Double.parseDouble(price), Double.parseDouble(total)));
//                //data.add(new posTable(itemName.getText(),2,10,20));
//
//                this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
//                Name.setCellValueFactory(new PropertyValueFactory<>("name"));
//                this.qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
//                uPrice.setCellValueFactory(new PropertyValueFactory<>("uPrice"));
//                this.total.setCellValueFactory(new PropertyValueFactory<>("total"));
//                //loadItems.setItems(null);
//                loadItems.setItems(data3);
//                //clear();
//                this.itemId.requestFocus();
//                loadSave(true);
//
//            }
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(POSController.class
//                    .getName()).log(Level.SEVERE, null, ex);
//
//        } catch (SQLException ex) {
//            Logger.getLogger(POSController.class
//                    .getName()).log(Level.SEVERE, null, ex);
//
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(POSController.class
//                    .getName()).log(Level.SEVERE, null, ex);
//
//        } catch (IOException ex) {
//            Logger.getLogger(POSController.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
        } catch (IOException ex) {
            Logger.getLogger(POSController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void balancefix(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            if (Double.parseDouble(paid.getText()) >= Double.parseDouble(subTotal.getText())) {
                double cal = Double.parseDouble(paid.getText()) - Double.parseDouble(subTotal.getText());
                DecimalFormat df = new DecimalFormat("#.###");
                String cal1 = df.format(cal);
                balance.setText(cal1 + "");

            } else {
                MessageBox.showWarningMessage("Warning", "Payment not Enough...!");

            }
        }
    }

      public void loadSub() {
        if(!loadItems.getItems().isEmpty()){
            System.out.println("fuck");
        double total = 0;
         System.out.println(loadItems.getItems().size());
        for (int i = 0; i < loadItems.getItems().size(); i++) {
            System.out.println(loadItems.getItems().size());
            double tot = Double.parseDouble(loadItems.getItems().get(i).getTotal());
            total += tot;

        }
        subTotal.setText(total + "");
        }else{
            
                                         this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
                                Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                                this.qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                                uPrice.setCellValueFactory(new PropertyValueFactory<>("uPrice"));
                                this.total.setCellValueFactory(new PropertyValueFactory<>("total"));

                                loadItems.setItems(null);
                                loadItems.setItems(data);  
                                 System.out.println("fuck");
        double total = 0;
         System.out.println(loadItems.getItems().size());
        for (int i = 0; i < loadItems.getItems().size(); i++) {
            System.out.println(loadItems.getItems().size());
            double tot = Double.parseDouble(loadItems.getItems().get(i).getTotal());
            total += tot;

        }
        subTotal.setText(total + "");
                                
        }
        
    }

    @FXML
    private void clear(MouseEvent event) {
        clear1();
    }

    private void clear1() {
        itemId.setText(null);
        Qty.setText(null);
        itemName.setText(null);
        unitPrice.setText(null);
        subTotal.setText(null);
        paid.setText(null);
        balance.setText(null);
        loadItems.getItems().clear();
    }

    @FXML
    private void balValidate(KeyEvent event) {
        if (!"0123456789.".contains(event.getCharacter())) {
            event.consume();
        }
    }

    @FXML
    private void subValidate(KeyEvent event) {
        if (!"0123456789.".contains(event.getCharacter())) {
            event.consume();
        }
    }

    private boolean checkLastValue() {
        try {
            //boolean b=false;
            String lastid = db.getLastId("idgameinvoice", "gameinvoice");
            ResultSet rs = db.search("idgameinvoice", "gameinvoice", "where total is null and paid Is null");
            while (rs.next()) {
                //b=true;
                System.out.println("inside the rs");
                String id = rs.getString("idgameinvoice");
                db.delete("stock_has_gameinvoice", "gameinvoice_idgameinvoice='" + id + "'");
                db.delete("gameinvoice", " idgameinvoice='" + id + "'");

            }
        } catch (Exception ex) {
            Log.error(ex);

        }
        return true;
    }

    private void balValidate1() {
        if (Double.parseDouble(paid.getText()) >= Double.parseDouble(subTotal.getText())) {
            double cal = Double.parseDouble(paid.getText()) - Double.parseDouble(subTotal.getText());
            DecimalFormat df = new DecimalFormat("#.##");
            String cal1 = df.format(cal);
            balance.setText(cal1 + "");
            gettingData();
        } else {
            MessageBox.showWarningMessage("Warninig", "Payment not Enough...!");

        }

    }

    private void report() throws JRException {
        String id = null;
        try {
            id = db.getLastId("idgameinvoice", "gameinvoice");
            ResultSet rs=db.search("date", "gameinvoice", "where idgameinvoice='"+id+"'");
            String date = null;
            while(rs.next()){
                date=rs.getString("date");
            }
            String date1=date.split(" ")[0];
            String time=date.split(" ")[1];
            System.out.println(id);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("gameInvId", id);
            params.put("date", date1);
            params.put("time", time);

            new PrintReport(params, "InvoicePOS.jrxml").start();

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void addTableData(ActionEvent event) {
        if(Qty.getText()==null){
            MessageBox.showErrorMessage("error", "wrong quantity input");
        }else{
        if (Integer.parseInt(itemId.getText()) < 0) {
            MessageBox.showErrorMessage("error", "wrong item input");
        } else {

            try {

                ResultSet sr = db.search("qty", "stock", "where idStock='" + this.itemId.getText() + "'");
                double d = 0;
                while (sr.next()) {

                    d = Double.parseDouble(sr.getString("qty"));
                }
                boolean ch=checkTable();
                if (d < Double.parseDouble(Qty.getText())) {
                    MessageBox.showWarningMessage("Warning", "Not enough quantity");
                } else {
                    if(ch){
                    MessageBox.showWarningMessage("Warning", "Not enough quantity");
                    }else{
                    try {

                        ResultSet rs = new CRUDOperationsImpl().search("stock.*,games.idGames,games.name",
                                "stock inner join games on stock.games_idGames=games.idGames",
                                "where stock.idStock='" + this.itemId.getText() + "' and (games.sellplay='sell' or games.sellplay='both')");
                        //methana wenas wenda onea
                        boolean resultOK = false;
                        while (rs.next()) {
                            resultOK = true;
                            if (loadItems.getItems().isEmpty()) {
                                System.out.println("first row");
                                String id = rs.getString("stock.idStock");
                                String name = rs.getString("games.name");
                                int qty = Integer.parseInt(Qty.getText());
                                double uprice = rs.getDouble("stock.sellingPrice");
                                double total = qty * uprice;
                                data.add(new posTable(id, name, qty, uprice, total));

                                this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
                                Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                                this.qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                                uPrice.setCellValueFactory(new PropertyValueFactory<>("uPrice"));
                                this.total.setCellValueFactory(new PropertyValueFactory<>("total"));

                                loadItems.setItems(null);
                                loadItems.setItems(data);
                            }else{
                                 boolean b = false;
                            int row = 0;
                            for (int i = 0; i < loadItems.getItems().size(); i++) {
                                if (loadItems.getItems().get(i).getId().equals(itemId.getText()) && Integer.parseInt(loadItems.getItems().get(i).getQty())>=0) {
                                    b = true;
                                    row = i;
                                    break;
                                }
                                b = false;
                            }

                            if (b) {
                                int pastQty = Integer.parseInt(loadItems.getItems().get(row).getQty());
                                loadItems.getItems().remove(row);
                                String id = rs.getString("stock.idStock");
                                String name = rs.getString("games.name");
                                int qty = Integer.parseInt(Qty.getText());
                                double uprice = rs.getDouble("stock.sellingPrice");
                                double total = (qty + pastQty) * uprice;
                                data.add(new posTable(id, name, qty + pastQty, uprice, total));

                                this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
                                Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                                this.qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                                uPrice.setCellValueFactory(new PropertyValueFactory<>("uPrice"));
                                this.total.setCellValueFactory(new PropertyValueFactory<>("total"));

                                loadItems.setItems(null);
                                loadItems.setItems(data);

                            } else {
                                String id = rs.getString("stock.idStock");
                                String name = rs.getString("games.name");
                                int qty = Integer.parseInt(Qty.getText());
                                double uprice = rs.getDouble("stock.sellingPrice");
                                double total = qty * uprice;
                                data.add(new posTable(id, name, qty, uprice, total));

                                this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
                                Name.setCellValueFactory(new PropertyValueFactory<>("name"));
                                this.qty.setCellValueFactory(new PropertyValueFactory<>("qty"));
                                uPrice.setCellValueFactory(new PropertyValueFactory<>("uPrice"));
                                this.total.setCellValueFactory(new PropertyValueFactory<>("total"));

                                loadItems.setItems(null);
                                loadItems.setItems(data);
                            }
                            }
                           

                            loadSub();
                            itemId.setText(null);
                            Qty.setText(null);
                            itemName.setText(null);
                            unitPrice.setText(null);
                            itemId.requestFocus();

                        }
                        if (!resultOK) {
                            MessageBox.showWarningMessage("Error", "There is no stock in games");
                        }

                    } catch (Exception ex) {
                        Log.error(ex);
                    }
                    }
                }
            } catch (Exception ex) {
                Log.error(ex);
            }
        }
    }

    }

    @FXML
    private void stockInvoice(ActionEvent event) {
        if(paid.getText().equals("")){
         MessageBox.showWarningMessage("Warning", "Payment not valid");
        }else{
              try {
            stockReduce();
            addgameInvoice();
            stockHas();

            MessageBox.showInformationMessage("Success", "invoice added");

            report();

            clear1();
        } catch (Exception ex) {
            Log.error(ex);
        }
        }
      
    }

    private void stockReduce() {
        for (int i = 0; i < loadItems.getItems().size(); i++) {
            try {
                String idStock = loadItems.getItems().get(i).getId();

                ResultSet rs = new CRUDOperationsImpl().search("qty", "stock", "where idStock='" + idStock + "'");
                while (rs.next()) {
                    int all = rs.getInt("qty");
                    int tableQty = Integer.parseInt(loadItems.getItems().get(i).getQty());
                    int net = all - tableQty;
                    new CRUDOperationsImpl().update("stock", "qty='" + net + "'", "idStock='" + idStock + "'");
                    System.out.println("done updating stock");
                }

            } catch (Exception ex) {
                Log.error(ex);
            }
        }
    }

    private void addgameInvoice() {
        try {
            double sub = Double.parseDouble(subTotal.getText());
            double paid = Double.parseDouble(this.paid.getText());
            new CRUDOperationsImpl().save("gameinvoice(total,paid,date,user_idUser)", "'" + sub + "','" + paid + "',"
                    + "now(),'" + user.getIdUser() + "'");
            System.out.println("invoice updated");
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void stockHas() {
        for (int i = 0; i < loadItems.getItems().size(); i++) {

            try {
                String idStock = loadItems.getItems().get(i).getId();
                String qty = loadItems.getItems().get(i).getQty();
                String invoiceID = new CRUDOperationsImpl().getLastId("idgameinvoice", "gameinvoice");

                new CRUDOperationsImpl().save("stock_has_gameinvoice(stock_idStock,gameinvoice_idgameinvoice,qty)",
                        "'" + idStock + "','" + invoiceID + "','" + qty + "'");
                System.out.println("done update has");
            } catch (Exception ex) {
                Log.error(ex);
            }
        }
    }

    @FXML
    private void itemValidate(KeyEvent event) {
        if (!"0123456789".contains(event.getCharacter())) {
            event.consume();
        }
    }

    @FXML
      private void ReturnA(ActionEvent event) {
                try {
                    FXMLLoader load=new FXMLLoader();
            Parent root = load.load(getClass().getResource("/gamefx/Return_Invoice.fxml").openStream());
                    Return_InvoiceController rc=load.getController();
                    rc.setController(this);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Grn Details");
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkTable() {
        boolean b=false;
        try {
            ResultSet sr = db.search("qty", "stock", "where idStock='" + this.itemId.getText() + "'");
            double d = 0;
            while (sr.next()) {
                
                d = Double.parseDouble(sr.getString("qty"));
            }
            String q;
            for (int i = 0; i < loadItems.getItems().size(); i++) {
               if(loadItems.getItems().get(i).getId().equals(itemId.getText())){
                   q=loadItems.getItems().get(i).getQty();
                   double s1=Double.parseDouble(q)+Double.parseDouble(Qty.getText());
                   if(d<s1){
                       //MessageBox.showWarningMessage("warning", "quantity not enough in check");
                       b=true;
                   }
               } 
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
        return b;
    
    }
  
}
