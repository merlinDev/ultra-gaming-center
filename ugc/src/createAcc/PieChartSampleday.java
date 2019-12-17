/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createAcc;

/**
 *
 * @author Waseeakr
 */
import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

public class PieChartSampleday extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Income and Expenses in last Day");
        stage.setWidth(500);
        stage.setHeight(500);
        double[] a1 = gettingData();
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Game Invoice\n" + a1[0], a1[0]),
                        new PieChart.Data("GRN\n " + a1[1], a1[1]),
                        new PieChart.Data("Gaming Total\n" + a1[2], a1[2]));

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Income and Expenses in last Day");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

    public double[] lastday() {
        double gameInvoiceTot = 0;
        double grnTot = 0;
        double gameTot = 0;

        Calendar prevday = Calendar.getInstance();
        //Calendar yesterday = new Calendar() {};
        prevday.add(Calendar.DATE, -1);
        // prevYear.add(Calendar.YEAR, -1);
        Date year = prevday.getTime();
        System.out.println(year + "qqqqqqqqqaaaaaaaaaaaaa");
        String s = new SimpleDateFormat("yyyy-MM-dd").format(year);
        String fd = s + " " + "00:" + "00:" + "00";
        String ld = s + " " + "23:" + "59:" + "59";
        System.out.println(fd);
        System.out.println(ld);

        CRUDOperations db = new CRUDOperationsImpl();
        ResultSet rs1;

        try {
            rs1 = db.search("sum(total)", "gameinvoice", "where  date between'" + fd + "' and '" + ld + "'");
            while (rs1.next()) {
                gameInvoiceTot = rs1.getDouble("sum(total)");
                System.out.println(gameInvoiceTot + "waseemssssssssssssss");

            }
            ResultSet rs = db.search("sum(total)", "invoice", "where  date between'" + fd + "' and '" + ld + "'");
            while (rs.next()) {
                gameTot = rs.getDouble("sum(total)");
                //gamingTotal.setText(gameTot + "rrrrrrrrrrrrrr");

            }
            ResultSet rs2;

            rs2 = db.search("sum(price)", "grn", "where  date between'" + fd + "' and '" + ld + "'");
            while (rs2.next()) {
                grnTot = rs2.getDouble("sum(price)");
                System.out.println(grnTot + "waseem");
                //grnTotal.setText(grnTot + "lllllllllllll");
            }
        } catch (ClassNotFoundException ex) {

        } catch (SQLException ex) {

        } catch (URISyntaxException ex) {

        } catch (IOException ex) {

        }
        double d[] = new double[3];
        d[0] = gameInvoiceTot;
        d[1] = grnTot;
        d[2] = gameTot;

        return d;
    }

    public double[] gettingData() {
        double[] a1 = lastday();
        return a1;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
