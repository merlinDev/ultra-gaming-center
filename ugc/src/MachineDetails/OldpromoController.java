/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MachineDetails;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author PASINDHU
 */
public class OldpromoController implements Initializable {

    private ResultSet rs;
    private CRUDOperations crud;
    private ObservableList data;

    @FXML
    private TableView<promo> promotabel;
    @FXML
    private TableColumn<?, ?> promoid;
    @FXML
    private TableColumn<?, ?> startDate;
    @FXML
    private TableColumn<?, ?> endDate;
    @FXML
    private TableColumn<?, ?> promoCode;
    @FXML
    private TableColumn<?, ?> discountPrice;
    @FXML
    private JFXButton viewUsers;
    @FXML
    private JFXButton refresh;
    @FXML
    private JFXTextField search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new CRUDOperationsImpl();
        loadPromo();
    }

    @FXML
    private void view(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MachineDetails/promomember.fxml"));
            Stage guestToken = new Stage();
            guestToken.setScene(new Scene(root));
            guestToken.setMaximized(false);
            guestToken.setTitle("Old Promotion Used Member Details");
            guestToken.initStyle(StageStyle.UTILITY);
            guestToken.setResizable(false);
            guestToken.show();
        } catch (IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void refreshAll(ActionEvent event) {
        loadPromo();
        search.setText(null);
    }

    @FXML
    private void searchPromo(ActionEvent event) {
        data = FXCollections.observableArrayList();
        try {
            if (!(search.getText() == null)) {
                rs = crud.search("*", "promo", "where idPromo like'" + search.getText() + "%' or sdate like'" + search.getText() + "%' or edate like'" + search.getText() + "%' or PromoCode like'" + search.getText() + "%' or DisPrice like'" + search.getText() + "%'");

                while (rs.next()) {
                    System.out.println("searching....");
                    promo p = new promo(rs.getString("idPromo"), rs.getString("sdate"), rs.getString("edate"), rs.getString("PromoCode"), rs.getString("DisPrice"));
                    data.add(p);
                }
            }
            promoid.setCellValueFactory(new PropertyValueFactory<>("promoid"));
            startDate.setCellValueFactory(new PropertyValueFactory<>("from"));
            endDate.setCellValueFactory(new PropertyValueFactory<>("to"));
            promoCode.setCellValueFactory(new PropertyValueFactory<>("promocode"));
            discountPrice.setCellValueFactory(new PropertyValueFactory<>("disprice"));
           
            promotabel.setItems(null);
            promotabel.setItems(data);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadPromo() {
        data = FXCollections.observableArrayList();

        try {
            rs = crud.search("*", "promo", "");
            while (rs.next()) {
                promo p = new promo(rs.getString("idPromo"), rs.getString("sdate"), rs.getString("edate"), rs.getString("PromoCode"), rs.getString("DisPrice"));
                data.add(p);
                promoid.setCellValueFactory(new PropertyValueFactory<>("promoid"));
                startDate.setCellValueFactory(new PropertyValueFactory<>("from"));
                endDate.setCellValueFactory(new PropertyValueFactory<>("to"));
                promoCode.setCellValueFactory(new PropertyValueFactory<>("promocode"));
                discountPrice.setCellValueFactory(new PropertyValueFactory<>("disprice"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        promotabel.setItems(null);
        promotabel.setItems(data);
    }

}
