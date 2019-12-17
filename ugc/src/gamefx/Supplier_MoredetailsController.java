/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class Supplier_MoredetailsController implements Initializable {

    @FXML
    private Label cname;
    @FXML
    private Label tp;
    @FXML
    private Label email;
    @FXML
    private PieChart piechart;
    @FXML
    private Label address;
    @FXML
    private Label grncout;
    @FXML
    private Label totitem;
    @FXML
    private Label totsitem;
    @FXML
    private Label psi;
    @FXML
    private Label name;
    @FXML
    private AnchorPane main;
    @FXML
    private JFXButton save;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loaddetails();
        // TODO
    }    
CRUDOperations crudo = new CRUDOperationsImpl();
    private void loaddetails() {
            try {
           NumberFormat formatter = new DecimalFormat("#0.00");
int grnco=0;
           double totiqty=0;
         double balance=0;
         double soldiqty=0;
         double reqty=0;
         int y=0;
         double c=0;
int grncount=0;
               ResultSet rs=crudo.search("*", "supplier", " where idsupplier='"+SupplierController.id+"'");
           if(rs.next()) {
                  ResultSet grsw=crudo.search("*","grn","where Supplier_idSupplier='"+SupplierController.id+"'");
                  if(grsw.first()){
               ResultSet grs=crudo.search("*","grn","where Supplier_idSupplier='"+SupplierController.id+"'");
               while(grs.next()){
                   grnco++;
                   ResultSet girs=crudo.search("*","grnitem"," where grn_idgrn='"+grs.getInt("idgrn")+"' ");
            while (girs.next()) {                
                 totiqty=totiqty+girs.getInt("QTY");
             ResultSet rs1=crudo.search("*","stock","where grnitem_grnitemid='"+girs.getInt("grnitemid")+"'");
                while (rs1.next()) {                    
                  soldiqty=soldiqty+rs1.getInt("QTY"); 
                    System.out.println("fuck-"+soldiqty+" "+rs1.getInt("QTY"));
              ResultSet re=crudo.search("exchangeinvoice.`idExchangeInvoice` AS exchangeinvoice_idExchangeInvoice,\n" +
"     exchangeinvoice.`stock_has_gameinvoiceid` AS exchangeinvoice_stock_has_gameinvoiceid,\n" +
"     exchangeinvoice.`qty` AS exchangeinvoice_qty,\n" +
"     stock.`idStock` AS stock_idStock,\n" +
"     stock_has_gameinvoice.`stock_idStock` AS stock_has_gameinvoice_stock_idStock,\n" +
"     stock_has_gameinvoice.`stock_has_gameinvoiceid` AS stock_has_gameinvoice_stock_has_gameinvoiceid","`stock` stock INNER JOIN `stock_has_gameinvoice` stock_has_gameinvoice ON stock.`idStock` = stock_has_gameinvoice.`stock_idStock`\n" +
"     INNER JOIN `exchangeinvoice` exchangeinvoice ON stock_has_gameinvoice.`stock_has_gameinvoiceid` = exchangeinvoice.`stock_has_gameinvoiceid`", "where stock_idStock='"+rs1.getString("idstock")+"'");
              while(re.next()){
              reqty+=re.getDouble("exchangeinvoice_qty");
                  System.out.println("fuck  "+reqty);
              }
              ResultSet re1=crudo.search("stock.`idStock` AS stock_idStock,\n" +
"     supplierreturn.`idSupplierReturn` AS supplierreturn_idSupplierReturn,\n" +
"     supplierreturn.`stock_idStock` AS supplierreturn_stock_idStock,\n" +
"     supplierreturn.`qty` AS supplierreturn_qty,\n" +
"     supplierreturn.`resolvedDT` AS supplierreturn_resolvedDT","`stock` stock INNER JOIN `supplierreturn` supplierreturn ON stock.`idStock` = supplierreturn.`stock_idStock`", "WHERE stock_idStock='"+rs1.getString("idstock")+"' and supplierreturn.`resolvedDT` is null");
             while(re1.next()){
             reqty+=re1.getDouble("supplierreturn_qty");
              System.out.println("fuck  "+reqty);
             }       
                }
            }
               }
         
               
               balance=totiqty-soldiqty;
              balance= balance-reqty;
            System.out.println(totiqty);
            System.out.println(soldiqty);
            System.out.println(balance);
            
             double i1=(100*(reqty/totiqty));
         double i2=(100*(balance/totiqty));
         double i3=(100*(soldiqty/totiqty));
            System.out.println("vmjvirt"+i1);
                  if(i1!=0){
     String dx2=formatter.format(i1);
        i1=Double.valueOf(dx2);
        }
            if(i2!=0){
      String dx=formatter.format(i2);
        i2=Double.valueOf(dx);
        }
               if(i3!=0){
     String dx1=formatter.format(i3);
        i3=Double.valueOf(dx1);
        }
       System.out.println("100*"+"("+reqty+"/"+totiqty+")=="+100*(totiqty/reqty));
            System.out.println("100*"+"("+balance+"/"+totiqty+")=="+100*(totiqty/balance));
            System.out.println("100*"+"("+soldiqty+"/"+totiqty+")=="+100*(totiqty/soldiqty));
           
            System.out.println(i1+"%");
            System.out.println(i2+"%");
            System.out.println(i3+"%");

           int i = 1;
        while (i == 1) {
            i = 2;
            ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                    new PieChart.Data("Sold :"+i2+"%", i2),
                    new PieChart.Data("Sork :"+i3+"%",i3 ),
                     new PieChart.Data("Return :"+i1+"%",i1 )
                  
            );
            piechart.setData(list);
             piechart.setVisible(true);
        } 

    
     c=i2;
            }
       name.setText(rs.getString("name"));
     cname.setText(rs.getString("Company_name"));
     tp.setText(rs.getString("tp"));
     email.setText(rs.getString("Email"));
     address.setText(rs.getString("Address"));
      grncout.setText(grnco+"");
   totitem.setText(totiqty+"");
    totsitem.setText(balance+"");
     psi.setText(c+"%");
           }
           

          
        } catch (Exception e) {
            e.printStackTrace();
           
        } 
   }

    @FXML
    private void saveA(ActionEvent event) {
          PrintReport.getScreenshot(main);
    }
    
}
