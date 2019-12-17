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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
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
public class SupplierController implements Initializable {

    @FXML
    private JFXTextField addname;
    @FXML
    private JFXTextField cname;
    @FXML
    private JFXTextField tp;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField address;
    @FXML
    private TableView<supplierDeatails> supp_table;
    @FXML
    private TableColumn<?, ?> suppcol;
    @FXML
    private TableColumn<?, ?> cnamecol;
    @FXML
    private TableColumn<?, ?> tpcol;
    @FXML
    private TableColumn<?, ?> emailcol;
    @FXML
    private TableColumn<?, ?> addresscol;
    @FXML
    private JFXTextField nametex;
    @FXML
    private JFXTextField cnametex;
    @FXML
    private JFXTextField tptex;
    @FXML
    private JFXTextField emailtex;
    @FXML
    private JFXTextField addresstex;
    @FXML
    private JFXButton updatebtn;
    @FXML
    private JFXButton clearbtn;
    @FXML
    private TableView<topsuppDetails> topsupptable;
    @FXML
    private TableColumn<?, ?> spcol;
    @FXML
    private TableColumn<?, ?> suppliercol;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton addclear;
    @FXML
    private JFXButton moredetails;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       loadsupp();
       loadtopsupp();
    }    
CRUDOperations crudo = new CRUDOperationsImpl();
    @FXML
    private void addnameA(ActionEvent event) {
        cname.requestFocus();
    }

    @FXML
    private void cnameA(ActionEvent event) {
        tp.requestFocus();
    }

    @FXML
    private void tpA(ActionEvent event) {
        email.requestFocus();
    }

    @FXML
    private void emailA(ActionEvent event) {
        address.requestFocus();
    }

    @FXML
    private void addressA(ActionEvent event) {
    }

    static int id;
    @FXML
    private void supp_table_click(MouseEvent event)  {
          if (supp_table.getSelectionModel().getSelectedItem() != null ) {
          supplierDeatails details1 = supp_table.getSelectionModel().getSelectedItem();
          nametex.setText(details1.getName());
            cnametex.setText(details1.getCname());
            tptex.setText(details1.getTp());
            emailtex.setText(details1.getEm());
            addresstex.setText(details1.getAddress());
              try { 
                  ResultSet rs=crudo.search("*","supplier"," where name='"+details1.getName()+"' && Company_name='"+details1.getCname()+"'");  
                  if(rs.next()){
                      id=rs.getInt("idsupplier");
                  }
              } catch (Exception e) {
              }
            
          }else{
          clearup();
          }
    }

    @FXML
    private void nametexA(ActionEvent event) {
       cnametex.requestFocus();
    }

    @FXML
    private void cnametexA(ActionEvent event) {
        tptex.requestFocus();
    }

    @FXML
    private void tptexA(ActionEvent event) {
          emailtex.requestFocus();
    }

    @FXML
    private void emailtexA(ActionEvent event) {
          addresstex.requestFocus();
    }

    @FXML
    private void addresstexA(ActionEvent event) {
    }

    @FXML
    private void updatebtnA(ActionEvent event) {
        try {
             if (((nametex.getText()!=null) && !(nametex.getText().equals("")))) {
               if (((cnametex.getText()!=null) && !(cnametex.getText().equals("")))) {
               if (((tptex.getText()!=null) && !(tptex.getText().equals("")))) {
               if (((emailtex.getText()!=null) && !(emailtex.getText().equals("")))) {
               if (((addresstex.getText()!=null) && !(addresstex.getText().equals("")))) {
                   ResultSet rs=crudo.search("*","supplier"," where idsupplier='"+id+"'");
                   if(rs.next()){
                   crudo.update("supplier", " name='"+nametex.getText()+"', Company_name='"+cnametex.getText()+"', TP='"+tptex.getText()+"', Address='"+addresstex.getText()+"', Email='"+emailtex.getText()+"'", "idsupplier='"+id+"'");
                   Log.info("Supplier " +nametex.getText()+ " Updete  by user--" + user.getIdUser());
                   }
                   
             loadsupp();
             clearup();
             } else {
               MessageBox.showWarningMessage("Warning", "ADDRESS NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "E-MAIL NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "TELE-PHONE NO NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "COMPANY NAME NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "SUPPLIER NAME NOT ENTERED");
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void clearbtnA(ActionEvent event) {
         clearup();
    }
 private ObservableList<supplierDeatails> data = FXCollections.observableArrayList();
  private ObservableList<topsuppDetails> data1 = FXCollections.observableArrayList();
    private void loadsupp() {
        try {
            data.clear();
            ResultSet rs=crudo.search("*", "supplier", "");
            while (rs.next()) { 
                   data.add(new supplierDeatails(rs.getString("name"), rs.getString("Company_name"), rs.getString("TP"),  rs.getString("Email"),rs.getString("Address")));                  
            }
             suppcol.setCellValueFactory(new PropertyValueFactory<>("name"));
            cnamecol.setCellValueFactory(new PropertyValueFactory<>("cname"));
            tpcol.setCellValueFactory(new PropertyValueFactory<>("tp"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("em"));
            addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        
            supp_table.setItems(null);
            supp_table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadtopsupp() {
       try {
           NumberFormat formatter = new DecimalFormat("#0.00");
           
           double totqty=0;
         double stockqty=0;
         double soldqty=0;
            data1.clear();
            double[][] dd;
             
              int i=0;
                ResultSet q=crudo.search("*", "supplier", "");{i++;}
                dd=new double[i][2];
                
                for (int j = 0; j <=i; j++) {
               ResultSet rs=crudo.search("*", "supplier", "");
            while (rs.next()) { 
              ResultSet rs1=crudo.search("*", "grn"," where supplier_idsupplier='"+rs.getString("idsupplier")+"'");
            while (rs1.next()) { 
                ResultSet rs2=crudo.search("*", "grnitem"," where grn_idgrn='"+rs1.getString("idgrn")+"'");
            while (rs2.next()) { 
                totqty=totqty+rs2.getInt("QTY");
             
                    ResultSet rs3=crudo.search("*", "stock"," where grnitem_grnitemid='"+rs2.getString("grnitemid")+"'");
            while (rs3.next()) { 
                stockqty= stockqty+rs3.getInt("QTY");
              
            } 
            } }
             soldqty=totqty-stockqty;
             double i1=0;
              double i2=0;
             if(totqty!=0){
             i1=((soldqty/totqty)*100);
               i2=((stockqty/totqty)*100);
                System.out.println(rs.getString("idsupplier")+" "+("("+stockqty+"/"+totqty+")"+"*"+"100"));
             }
      
              String dx=formatter.format(i2);
              i2=Double.valueOf(dx);
         String dx1=formatter.format(i1);
        i1=Double.valueOf(dx1);
        dd[j][0]=i1;
         data1.add(new topsuppDetails(rs.getString("name"),dd[j][0]+"%" ));  
           System.out.println(rs.getString("idsupplier")+" "+dd[j][0]);
            soldqty=0;
            totqty=0;
            stockqty=0;
            i--;
            }  
           }
//           double temp;
//           for (int j = 0; j < dd.length; j++) {
//               if(dd[j][0]>=dd[j+1][0]){
//               }else{
//               temp=dd[j][0];
//               dd[j][0]=dd[j+1][0];
//               dd[j+1][0]=temp;
//               }
//               
//           }
           suppliercol.setCellValueFactory(new PropertyValueFactory<>("name"));
           spcol.setCellValueFactory(new PropertyValueFactory<>("pre"));
           
            topsupptable.setItems(null);
            topsupptable.setItems(data1);
        } catch (Exception e) {
            e.printStackTrace();
           
        }
    }

    @FXML
    private void valiaddname(KeyEvent event) {
        addname.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valicname(KeyEvent event) {
        cname.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valitp(KeyEvent event) {
        tp.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"+1234567890".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valiemail(KeyEvent event) {
        email.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM@.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void valiaddress(KeyEvent event) {
        address.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM/,- ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void saveA(ActionEvent event) {
        try {
             if (((addname.getText()!=null) && !(addname.getText().equals("")))) {
               if (((cname.getText()!=null) && !(cname.getText().equals("")))) {
               if (((tp.getText()!=null) && !(tp.getText().equals("")))) {
               if (((email.getText()!=null) && !(email.getText().equals("")))) {
               if (((address.getText()!=null) && !(address.getText().equals("")))) {
             crudo.save("supplier(name,Company_name, TP, Address, Email)", " '"+addname.getText()+"','"+cname.getText()+"','"+tp.getText()+"','"+address.getText()+"' ,'"+email.getText()+"' ");
            Log.info("Supplier " +cname.getText()+ " ADD  by user--" + user.getIdUser());
             loadsupp();
             } else {
               MessageBox.showWarningMessage("Warning", "ADDRESS NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "E-MAIL NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "TELE-PHONE NO NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "COMPANY NAME NOT ENTERED");
               }
             } else {
               MessageBox.showWarningMessage("Warning", "SUPPLIER NAME NOT ENTERED");
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearup() {
   loadsupp();
   nametex.setText(null);
            cnametex.setText(null);
            tptex.setText(null);
            emailtex.setText(null);
            addresstex.setText(null);
            id=0;
    }

    @FXML
    private void addclearA(ActionEvent event) {
        addname.setText(null);
            cname.setText(null);
            tp.setText(null);
            email.setText(null);
            address.setText(null);
    }

    @FXML
    private void valinametex(KeyEvent event) {
        nametex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void nametexkr(KeyEvent event) {
           try {
            if ((nametex.getText().equals(null)) || !(nametex.getText().equals(""))) { 
            data.clear();
            ResultSet rs=crudo.search("*", "supplier", " where name like '"+nametex.getText()+"%'");
            while (rs.next()) { 
                  data.add(new supplierDeatails(rs.getString("name"), rs.getString("Company_name"), rs.getString("TP"),  rs.getString("Email"),rs.getString("Address")));                  
            }
             suppcol.setCellValueFactory(new PropertyValueFactory<>("name"));
            cnamecol.setCellValueFactory(new PropertyValueFactory<>("cname"));
            tpcol.setCellValueFactory(new PropertyValueFactory<>("tp"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("em"));
            addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
            supp_table.setItems(null);
            supp_table.setItems(data);
            } else {
                System.err.println("vnfevjribirn");
                loadsupp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void valicnametex(KeyEvent event) {
          cnametex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void cnametexkr(KeyEvent event) {
         try {
            if ((cnametex.getText().equals(null)) || !(cnametex.getText().equals(""))) { 
            data.clear();
            ResultSet rs=crudo.search("*", "supplier", " where Company_name like '"+cnametex.getText()+"%'");
            while (rs.next()) { 
                   data.add(new supplierDeatails(rs.getString("name"), rs.getString("Company_name"), rs.getString("TP"),  rs.getString("Email"),rs.getString("Address")));                  
            }
             suppcol.setCellValueFactory(new PropertyValueFactory<>("name"));
            cnamecol.setCellValueFactory(new PropertyValueFactory<>("cname"));
            tpcol.setCellValueFactory(new PropertyValueFactory<>("tp"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("em"));
            addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
            supp_table.setItems(null);
            supp_table.setItems(data);
            } else {
                System.err.println("vnfevjribirn");
                loadsupp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void valitptex(KeyEvent event) {
         tptex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"+1234567890".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void tptexkr(KeyEvent event) {
           try {
            if ((tptex.getText().equals(null)) || !(tptex.getText().equals(""))) { 
            data.clear();
            ResultSet rs=crudo.search("*", "supplier", " where TP like '"+tptex.getText()+"%'");
            while (rs.next()) { 
                    data.add(new supplierDeatails(rs.getString("name"), rs.getString("Company_name"), rs.getString("TP"),  rs.getString("Email"),rs.getString("Address")));                  
           }
             suppcol.setCellValueFactory(new PropertyValueFactory<>("name"));
            cnamecol.setCellValueFactory(new PropertyValueFactory<>("cname"));
            tpcol.setCellValueFactory(new PropertyValueFactory<>("tp"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("em"));
            addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
            supp_table.setItems(null);
            supp_table.setItems(data);
            } else {
                System.err.println("vnfevjribirn");
                loadsupp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    @FXML
    private void valiemailtex(KeyEvent event) {
         emailtex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM@.".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void emailtexkr(KeyEvent event) {
           try {
            if ((emailtex.getText().equals(null)) || !(emailtex.getText().equals(""))) { 
            data.clear();
            ResultSet rs=crudo.search("*", "supplier", " where Email like '"+emailtex.getText()+"%'");
            while (rs.next()) { 
                   data.add(new supplierDeatails(rs.getString("name"), rs.getString("Company_name"), rs.getString("TP"),  rs.getString("Email"),rs.getString("Address")));                  
            }
             suppcol.setCellValueFactory(new PropertyValueFactory<>("name"));
            cnamecol.setCellValueFactory(new PropertyValueFactory<>("cname"));
            tpcol.setCellValueFactory(new PropertyValueFactory<>("tp"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("em"));
            addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
            supp_table.setItems(null);
            supp_table.setItems(data);
            } else {
                System.err.println("vnfevjribirn");
                loadsupp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void valiaddresstex(KeyEvent event) {
        addresstex.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM/,- ".contains(event.getCharacter())) {
                    event.consume();
                }
            }
        });
    }

    @FXML
    private void addresstexkr(KeyEvent event)  {
              try {
            if ((addresstex.getText().equals(null)) || !(addresstex.getText().equals(""))) { 
            data.clear();
            ResultSet rs=crudo.search("*", "supplier", " where Address like '"+addresstex.getText()+"%'");
            while (rs.next()) { 
                   data.add(new supplierDeatails(rs.getString("name"), rs.getString("Company_name"), rs.getString("TP"),  rs.getString("Email"),rs.getString("Address")));                  
            }
             suppcol.setCellValueFactory(new PropertyValueFactory<>("name"));
            cnamecol.setCellValueFactory(new PropertyValueFactory<>("cname"));
            tpcol.setCellValueFactory(new PropertyValueFactory<>("tp"));
            emailcol.setCellValueFactory(new PropertyValueFactory<>("em"));
            addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
            supp_table.setItems(null);
            supp_table.setItems(data);
            } else {
                System.err.println("vnfevjribirn");
                loadsupp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static int suppid;
    @FXML
    private void moredetailsA(ActionEvent event) {
       try {
            if (id!=0) {
                String grnNO = null;
                ResultSet rs = crudo.search("idsupplier", "supplier", " where idsupplier='" + id + "'");
                if (rs.next()) {
                   id= rs.getInt("idsupplier");
                     System.out.println("in else");
                Parent root = FXMLLoader.load(getClass().getResource("Supplier_Moredetails.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Supplier_Moredetails");
                stage.setScene(scene);
                stage.show();
                }
            } else {
                MessageBox.showWarningMessage("Warrning", "NO SUPPLIER SELECTED");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
