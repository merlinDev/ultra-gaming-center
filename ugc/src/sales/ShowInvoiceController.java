/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sales;

import com.cyclotech.repository.CRUDOperationsImpl;
import gamefx.Log;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Waseeakr
 */
public class ShowInvoiceController implements Initializable {

    //private TableView<invoiceData> loadItems;
    @FXML
    private TableColumn<?, ?> id;
   
    @FXML
    private TableColumn<?, ?> total;
    @FXML
    private TableColumn<?, ?> paid;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> id2;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> sellingPrice;
    
    @FXML
    private TableView<invoiceData> loadInvoice;
    @FXML
    private TableView<invoiceItemData> loadInvItems;
    @FXML
    private TableColumn<?, ?> quantity;
    @FXML
    private Label lab;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lab.setVisible(false);
        gameInvoiceDetails();
        
    } 
    ObservableList<invoiceData> data = FXCollections.observableArrayList();
    ObservableList<invoiceItemData> data1 = FXCollections.observableArrayList();
    public void gameInvoiceDetails() {
       GetInvoiceData(); 
    }

    private void GetInvoiceData() {
        try {
       ResultSet rs=new CRUDOperationsImpl().search("idgameinvoice,total,paid,date", "gameinvoice","order By date DESC");
            
            while(rs.next()){
                
                String id=rs.getString("idgameinvoice");
                String total=rs.getString("total");
                String paid=rs.getString("paid");
                String date=rs.getString("date");
                data.add(new invoiceData(id, total,paid,date));
                             
                             this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
                             this.total.setCellValueFactory(new PropertyValueFactory<>("total"));
                             this.paid.setCellValueFactory(new PropertyValueFactory<>("paid"));
                             this.date.setCellValueFactory(new PropertyValueFactory<>("date"));
                             
                             
                             loadInvoice.setItems(null);
                             loadInvoice.setItems(data);
            
            }
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void loadItems(ActionEvent event) {
        getSelectedValue();
    }

    private void getSelectedValue() {
       if (loadInvoice.getSelectionModel().getSelectedItem() != null ) {
        int index=loadInvoice.getSelectionModel().getSelectedIndex();
        String id=loadInvoice.getItems().get(index).getId();
        data1.clear();
        try {
         
             boolean b=true;
            ResultSet rs=new CRUDOperationsImpl().search("games.name ,stock.sellingPrice ,"
            + "stock_has_gameinvoice.stock_has_gameinvoiceid,stock_has_gameinvoice.qty", 
            "  games games INNER JOIN stock stock ON games.idGames = stock.games_idGames INNER JOIN "
            + "stock_has_gameinvoice  stock_has_gameinvoice ON stock.idStock= stock_has_gameinvoice.stock_idStock",
            "where stock_has_gameinvoice.gameinvoice_idgameinvoice='"+id+"'");
            while(rs.next()){
                
           if(rs.getInt("stock_has_gameinvoice.qty")<0 ){
                lab.setVisible(true);
                b=false;
                }
           if(b){
               lab.setVisible(false);
           }
                String name=rs.getString("games.name");
                String sellingPrice=rs.getString("stock.sellingPrice");
                String invid=rs.getString("stock_has_gameinvoice.stock_has_gameinvoiceid");
                String qty=rs.getString("stock_has_gameinvoice.qty");
               
                data1.add(new invoiceItemData(invid,name,sellingPrice,qty));
                             
                             this.id2.setCellValueFactory(new PropertyValueFactory<>("id"));
                             this.name.setCellValueFactory(new PropertyValueFactory<>("name"));
                             this.sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingprice"));
                             this.quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                             
                             
                             loadInvItems.setItems(null);
                             loadInvItems.setItems(data1);
            }
        } catch (Exception ex) {
            Log.error(ex);
        }}
    }
    
    
    
}
