/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import accounts.user;
import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Smart
 */
public class GuestDetailsController implements Initializable {

    @FXML
    private JFXTextField TokenID;
    @FXML
    private JFXTextField PackagePrice;
    @FXML
    private JFXTextField PackageTime;
    @FXML
    private JFXTextField StartingTime;
    @FXML
    private JFXTextField EndTime;
    private AnchorPane panel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GetDetails();
        //CalculateTime();
    }

    private void GetDetails() {
        try {
            System.out.println("Get Details called");
            CRUDOperations crudo = new CRUDOperationsImpl();
            ResultSet rs = crudo.search("*", "guest", "where idguest='" + user.getIdUser() + "'");
            if (rs.next()) {
                TokenID.setText(rs.getString("token"));
                StartingTime.setText(rs.getString("startTime"));
                EndTime.setText(rs.getString("endTime"));
                String l = rs.getString("charge_idCharge");
                ResultSet rs1 = crudo.search("duration,charge", "gamecenter.charge", "where idCharge='" + l + "'");
                if (rs1.next()) {
                    PackagePrice.setText(rs1.getString("charge"));
                    PackageTime.setText(rs1.getString("duration"));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*private void CalculateTime() {
        try {
            CRUDOperations crudo=new CRUDOperationsImpl();
            ResultSet rs=crudo.search("startTime,endTime","guest","where token='"+l1+"'");
            if (rs.next()) {
                String startTime=rs.getString("startTime");
                String endTime=rs.getString("endTime");
                SimpleDateFormat sdf=new SimpleDateFormat("HH-mm-ss");
                Date Time1=sdf.parse(startTime);
                Date Time2=sdf.parse(endTime);
                long d=Time2.getTime()-Time1.getTime();
                long l=d/1000;
                System.out.println(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }*/
}
