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
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author PASINDHU
 */
public class PromomemberController implements Initializable {

    private ResultSet rs;
    private CRUDOperations crud;
    private ObservableList data;

    @FXML
    private TableColumn<?, ?> promoCode;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> username;
    @FXML
    private TableColumn<?, ?> usedDate;
    @FXML
    private JFXTextField searchPromoMember;
    @FXML
    private JFXButton refresh;
    @FXML
    private TableView<?> promoMember;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crud = new CRUDOperationsImpl();
        loadAll();
    }

    @FXML
    private void searchMember(KeyEvent event) {
        try {
            if (!(searchPromoMember.getText() == null)) {
                rs = crud.search("p.PromoCode, pm.Promo_idPromo,u.name,u.username, pm.UsedDate", "promo p,promomember pm, user u ", "where p.idPromo=pm.Promo_idPromo and pm.user_idUser = u.idUser and Promo_idPromo like'" + searchPromoMember.getText() + "%' or UsedDate like'" + searchPromoMember.getText() + "%' or PromoCode like'" + searchPromoMember.getText() + "%' or username like'" + searchPromoMember.getText() + "%'");
                while (rs.next()) {
                    promomember pm = new promomember(rs.getString("PromoCode"), rs.getString("name"), rs.getString("username"), rs.getString("UsedDate"));
                    data.add(pm);
                    promoCode.setCellValueFactory(new PropertyValueFactory<>("promocode"));
                    name.setCellValueFactory(new PropertyValueFactory<>("name"));
                    username.setCellValueFactory(new PropertyValueFactory<>("gamername"));
                    usedDate.setCellValueFactory(new PropertyValueFactory<>("useddate"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        promoMember.setItems(null);
        promoMember.setItems(data);
    }

    @FXML
    private void refreshAll(ActionEvent event) {
        searchPromoMember.setText(null);
        loadAll();
    }

    private void loadAll() {
        try {
            rs = crud.search("p.PromoCode, pm.Promo_idPromo,u.name,u.username, pm.UsedDate", "promo p,promomember pm, user u ", "where p.idPromo=pm.Promo_idPromo and pm.user_idUser = u.idUser");
            while (rs.next()) {
                data = FXCollections.observableArrayList();
                promomember pm = new promomember(rs.getString("PromoCode"), rs.getString("name"), rs.getString("username"), rs.getString("UsedDate"));
                data.add(pm);
                promoCode.setCellValueFactory(new PropertyValueFactory<>("promocode"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                username.setCellValueFactory(new PropertyValueFactory<>("gamername"));
                usedDate.setCellValueFactory(new PropertyValueFactory<>("useddate"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        promoMember.setItems(null);
        promoMember.setItems(data);
    }

}
