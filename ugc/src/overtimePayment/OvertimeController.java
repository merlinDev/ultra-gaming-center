/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package overtimePayment;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import gamefx.Log;
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

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class OvertimeController implements Initializable {

    @FXML
    private TableView<overtimeData> table;
    @FXML
    private TableColumn<?, ?> date;
    @FXML
    private TableColumn<?, ?> overtime;

    ObservableList<overtimeData> data;
    @FXML
    private JFXDatePicker from;
    @FXML
    private JFXDatePicker to;

    static String dateFrom = "";
    static String dateTo = "";
    public static String userID = "";
    @FXML
    private JFXButton search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showTable();
    }

    private void showTable() {
        data = FXCollections.observableArrayList();
        try {
            ResultSet rs = new CRUDOperationsImpl().search("overtime_payments.*,play_session_has_overtime_payments.*", "play_session_has_overtime_payments inner join overtime_payments on play_session_has_overtime_payments.overtime_payments_id=overtime_payments.id inner join play_session on play_session.play_session_id=play_session_has_overtime_payments.play_session_id", "where play_session.user_idUser='" + userID + "' and (dateTime BETWEEN '" + dateFrom + "' AND '" + dateTo + "') GROUP BY overtime_payments.`dateTime`");
            while (rs.next()) {
                data.add(new overtimeData(rs.getString("dateTime").split("\\.")[0], rs.getString("total")));
            }

            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            overtime.setCellValueFactory(new PropertyValueFactory<>("overtime"));

            table.setItems(null);
            table.setItems(data);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void search(ActionEvent event) {
        try {
            dateFrom = from.getValue().toString();
            dateTo = to.getValue().toString() + " 23:59:59";
            showTable();
        } catch (Exception e) {
        }
    }

}
