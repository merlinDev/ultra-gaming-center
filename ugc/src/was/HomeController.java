package was;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Waseeakr
 */
public class HomeController implements Initializable {

    //@FXML
    //private ObservableList<expenseesTable> data1;
    @FXML
    private TableView<expenseTable> expenses;
    @FXML
    private TableView<incomeTable> income;
    @FXML
    private JFXDatePicker startDate;
    @FXML
    private JFXTextField invoiceTotal;
    @FXML
    private JFXTextField profit;
    @FXML
    private JFXDatePicker endDate;
    @FXML
    private TableColumn<?, ?> incomeDate;
    private TableColumn<?, ?> incomeName;
    @FXML
    private TableColumn<?, ?> incomePrice;
    @FXML
    private TableColumn<?, ?> gameinv;
    @FXML
    private TableColumn<?, ?> expenseDate;
    @FXML
    private TableColumn<?, ?> grnNo;
    @FXML
    private TableColumn<?, ?> expensePrice;
    @FXML
    private TableColumn<?, ?> sellingDate;
    @FXML
    private JFXTextField grnTotal;
    @FXML
    private JFXTextField gameinvoiceTotal;
    @FXML
    private JFXTextField gamingTotal;
    @FXML
    private TableView<gameincomeTable> gamingincome;

    LocalDate strtdate = null;
    LocalDate endate = null;
    @FXML
    private JFXButton search;
    @FXML
    private JFXButton reload;
    @FXML
    private JFXButton lastYear;
    @FXML
    private JFXButton lastMonth;
    @FXML
    private JFXButton lastDay;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //double gameSelling[] = lastMonth();
        //  double gameSelling[] = lastyear();
//        double gameSelling1[] = lastMonth();
        tableloading();

    }
    String currentdate;
    String beginDate;
    String sdate = null;
    String edate = null;

    private void tableloading() {

        System.out.println(strtdate + "wwwwwwwwwwwwwwwwwwwww");

        currentdate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        //beginDate = begin();
        LocalDate date = LocalDate.now();
        System.out.println(strtdate);
        System.out.println(endate);
        startDate.setValue(date.with(TemporalAdjusters.firstDayOfMonth()));
        endDate.setValue(date);
        strtdate = startDate.getValue();
        //String s=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(strtdate);
        System.out.println(strtdate + "rrrrrrrrrrrrrrrrrr");
        endate = endDate.getValue();
        CRUDOperations db = new CRUDOperationsImpl();

        System.out.println(endate.isAfter(strtdate));

        if (strtdate.equals(endate)) {
            load1();
        } else if (strtdate.isBefore(endate)) {
            load1();
        } else {
            Alert al2 = new Alert(Alert.AlertType.WARNING, "Start date and End date is invalid", ButtonType.OK);
            al2.showAndWait();
            if (al2.getResult() == ButtonType.OK) {
                al2.close();
            }
        }

    }

    private void load1() {
        ObservableList<incomeTable> data;
        data = FXCollections.observableArrayList();
        CRUDOperations db = new CRUDOperationsImpl();
        expensesearch();
        data = FXCollections.observableArrayList();
        sdate = strtdate.toString() + " " + "00:" + "00:" + "00";
        edate = endate.toString() + " " + "23:" + "59:" + "59";
        try {
            profitcal();
            //DefaultTableModel dtm=(DefaultTableModel)income.getta
            //TableView<> tab=new TableView<>;
            // incomeName.set
            ResultSet rs1 = db.search("date,total", "invoice", "where date between '" + sdate + "' and'" + edate + "'");
            ResultSet rs = db.search("total,date", "gameinvoice", "where date between'" + sdate + "' and '" + edate + "'");
            ObservableList<gameincomeTable> data2;
            data2 = FXCollections.observableArrayList();
            while (rs1.next()) {
                DecimalFormat d = new DecimalFormat("#.##");
                double dd = Double.parseDouble(rs1.getString("total"));
                String da = d.format(dd);
                data2.add(new was.gameincomeTable(rs1.getDate("date"), da));
            }

            while (rs.next()) {
                //String s = rs1.getString("invoice.date");
                DecimalFormat d = new DecimalFormat("#.##");
                double dd = Double.parseDouble(rs.getString("total"));
                String da = d.format(dd);
                data.add(new incomeTable(rs.getDate("date"), da));
            }

            sellingDate.setCellValueFactory(new PropertyValueFactory<>("sellingDate"));
            gameinv.setCellValueFactory(new PropertyValueFactory<>("invoicetot"));
            income.setItems(null);
            income.setItems(data);

            incomeDate.setCellValueFactory(new PropertyValueFactory<>("incomeDate"));
            incomePrice.setCellValueFactory(new PropertyValueFactory<>("incomePrice"));
            gamingincome.setItems(null);
            gamingincome.setItems(data2);
        } catch (Exception ex) {
            Log.error(ex);
        }

        profitcal();
    }

    private long begin() {
        Calendar calendar = getCalendarForNow();
        calendar.set(Calendar.YEAR, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTimeInMillis();
    }

    private Calendar getCalendarForNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar;
    }

    private void profitcal() {
        CRUDOperations db = new CRUDOperationsImpl();
        double gamingTot = 0;
        double gameInvoiceTot = 0;
        double grnTot = 0;
        double invoiceTot;
        double profit;
        String sdate = strtdate.toString() + " " + "00:" + "00:" + "00";
        String edate = endate.toString() + " " + "23:" + "59:" + "59";
        try {
            ResultSet rs = db.search("sum(total)", "invoice", "where  date between'" + sdate + "' and '" + edate + "'");
            while (rs.next()) {
                gamingTot = rs.getDouble("sum(total)");
                DecimalFormat d = new DecimalFormat("#.##");
                gamingTotal.setText(d.format(gamingTot) + "");

            }
            ResultSet rs1 = db.search("sum(total)", "gameinvoice", "where  date between'" + sdate + "' and '" + edate + "'");
            while (rs1.next()) {
                gameInvoiceTot = rs1.getDouble("sum(total)");
                System.out.println(gameInvoiceTot + "waseem");
                DecimalFormat d = new DecimalFormat("#.##");
                gameinvoiceTotal.setText(d.format(gameInvoiceTot) + "");

            }
            ResultSet rs2 = db.search("sum(price)", "grn", "where  date between'" + sdate + "' and '" + edate + "'");
            while (rs2.next()) {
                grnTot = rs2.getDouble("sum(price)");
                System.out.println(grnTot + "waseem");
                DecimalFormat d = new DecimalFormat("#.##");
                grnTotal.setText(d.format(grnTot) + "");

            }
            invoiceTot = gamingTot + gameInvoiceTot;
            DecimalFormat d = new DecimalFormat("#.##");
            invoiceTotal.setText(d.format(invoiceTot) + "");
            profit = invoiceTot - grnTot;
            if (profit >= 0) {
                this.profit.setText(d.format(profit) + "");
            } else {
                int x = (int) Math.abs(invoiceTot - grnTot) * -1;
                this.profit.setText("Lost" + d.format(profit));
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void expensesearch() {
        ObservableList<was.expenseTable> data1;
        data1 = FXCollections.observableArrayList();
        System.out.println(strtdate);
        System.out.println(endate.isAfter(strtdate));
        if (strtdate.isBefore(endate)) {
            System.out.println("after if");
            try {
                strtdate = startDate.getValue();
                endate = endDate.getValue();
                CRUDOperations db = new CRUDOperationsImpl();
                String sdate = strtdate.toString() + " " + "00:" + "00:" + "00";
                String edate = endate.toString() + " " + "23:" + "59:" + "59";
                ResultSet rs = db.search("idGrn, date, price", "grn", "where date between'" + sdate + "' and '" + edate + "'");
                while (rs.next()) {
                    DecimalFormat d = new DecimalFormat("#.##");
                    double dd = Double.parseDouble(rs.getString("price"));
                    String da = d.format(dd);
                    data1.add(new expenseTable(rs.getString("idGrn"), rs.getDate("date"), da));

                }
                expenseDate.setCellValueFactory(new PropertyValueFactory<>("expenseDate"));
                grnNo.setCellValueFactory(new PropertyValueFactory<>("grnNo"));
                expensePrice.setCellValueFactory(new PropertyValueFactory<>("expensePrice"));
                //expenses.setItems(null);
                expenses.setItems(data1);

            } catch (Exception ex) {
                Log.error(ex);
            }
        } else {

        }
    }

    @FXML
    private void searchLoader(MouseEvent event) {
        CRUDOperations db = new CRUDOperationsImpl();
        if (startDate.getValue().equals(endDate.getValue())) {
            System.out.println("matched");
            load2();

        } else if (startDate.getValue().isBefore(endDate.getValue())) {
            System.out.println("is before");
            load2();
        } else {
            Alert al2 = new Alert(Alert.AlertType.WARNING, "Start date and End date is invalid", ButtonType.OK);
            System.out.println("else");
            al2.showAndWait();
            if (al2.getResult() == ButtonType.OK) {
                al2.close();
            }

        }
    }

    @FXML
    private void reload(MouseEvent event) {
        tableloading();
    }

    private void tableloading1() {

    }

    public void load2() {
        strtdate = startDate.getValue();
        endate = endDate.getValue();
        String edate = endate.toString() + " " + "23:" + "59:" + "59";
        System.out.println(edate + "palaa");
        System.out.println(endate + "wasssss");
        currentdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        LocalDate date = LocalDate.now();
        String dates = startDate.getValue().toString() + " " + "00:" + "00:" + "00";

        ObservableList<incomeTable> data;
        data = FXCollections.observableArrayList();
        CRUDOperations db = new CRUDOperationsImpl();
        System.out.println("after if in search");
        data = FXCollections.observableArrayList();
        try {
            expensesearch();
            System.out.println("after if in esx");
            // profitcal();
            //DefaultTableModel dtm=(DefaultTableModel)income.getta
            //TableView<> tab=new TableView<>;
            // incomeName.set
            ResultSet rs1 = db.search("date,total", "invoice", "where  date between '" + dates + "' and '" + edate + "'");
            //ResultSet rs1 = db.search("date,total", "invoice", "where  date between '" + st + "' and'" + et + "'");
            ResultSet rs = db.search("total,date", "gameinvoice", "where date between '" + dates + "'and '" + edate + "'");
            //ResultSet rs = db.search("total,date", "gameinvoice", "where date between '" + st+ "'and '" + et + "'");
            ObservableList<gameincomeTable> data2;
            data2 = FXCollections.observableArrayList();

            while (rs1.next()) {
                DecimalFormat d = new DecimalFormat("#.##");
                double dd = Double.parseDouble(rs1.getString("total"));
                String da = d.format(dd);
                data2.add(new gameincomeTable(rs1.getDate("date"), da));
            }

            while (rs.next()) {
                DecimalFormat d = new DecimalFormat("#.##");
                double dd = Double.parseDouble(rs.getString("total"));
                String da = d.format(dd);
                //String s = rs1.getString("invoice.date");
                data.add(new incomeTable(rs.getDate("date"), da));
            }

            sellingDate.setCellValueFactory(new PropertyValueFactory<>("sellingDate"));
            gameinv.setCellValueFactory(new PropertyValueFactory<>("invoicetot"));
            income.setItems(null);
            income.setItems(data);

            incomeDate.setCellValueFactory(new PropertyValueFactory<>("incomeDate"));
            incomePrice.setCellValueFactory(new PropertyValueFactory<>("incomePrice"));
            gamingincome.setItems(null);
            gamingincome.setItems(data2);
        } catch (Exception ex) {
            Log.error(ex);
        }

        profitcal();

    }

    /*  public double[] lastMonth() {
        double gameInvoiceTot = 0;
        double grnTot = 0;
        double gameTot = 0;
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.set(Calendar.DATE, 1);
        aCalendar.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDateOfPreviousMonth = aCalendar.getTime();
        String ld = new SimpleDateFormat("yyyy-MM-dd").format(lastDateOfPreviousMonth) + " " + "23:" + "59:" + "59";
        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        String fd = new SimpleDateFormat("yyyy-MM-dd").format(firstDateOfPreviousMonth) + " " + "00:" + "00:" + "00";
        System.out.println(lastDateOfPreviousMonth + "llllllll");
        System.out.println(firstDateOfPreviousMonth + "llllllllll");
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
                // gamingTotal.setText(gameTot + "");

            }
            ResultSet rs2;

            rs2 = db.search("sum(price)", "grn", "where  date between'" + fd + "' and '" + ld + "'");
            while (rs2.next()) {
                grnTot = rs2.getDouble("sum(price)");
                System.out.println(grnTot + "waseemmmmmmmmmmmmmmm");
                //grnTotal.setText(grnTot + "");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);

        }
        double d[] = new double[3];
        d[0] = gameInvoiceTot;
        d[1] = grnTot;
        d[2] = gameTot;
        return d;
    }*/
//    public double[] lastyear() {
//        double gameInvoiceTot = 0;
//        double grnTot = 0;
//        double gameTot = 0;
//
//        Calendar prevYear = Calendar.getInstance();
//        prevYear.add(Calendar.YEAR, -1);
//        int year = prevYear.get(Calendar.YEAR);
//        System.out.println(year);
//        String fd = year + "-01" + "-01" + " " + "00:" + "00:" + "00";
//        String ld = year + "-12" + "-31" + " " + "23:" + "59:" + "59";
//
//        CRUDOperations db = new CRUDOperationsImpl();
//        ResultSet rs1;
//
//        try {
//            rs1 = db.search("sum(total)", "gameinvoice", "where  date between'" + fd + "' and '" + ld + "'");
//            while (rs1.next()) {
//                gameInvoiceTot = rs1.getDouble("sum(total)");
//                System.out.println(gameInvoiceTot + "waseemssssssssssssss");
//
//            }
//            ResultSet rs = db.search("sum(total)", "invoice", "where  date between'" + fd + "' and '" + ld + "'");
//            while (rs.next()) {
//                gameTot = rs.getDouble("sum(total)");
//                //gamingTotal.setText(gameTot + "rrrrrrrrrrrrrr");
//
//            }
//            ResultSet rs2;
//
//            rs2 = db.search("sum(price)", "grn", "where  date between'" + fd + "' and '" + ld + "'");
//            while (rs2.next()) {
//                grnTot = rs2.getDouble("sum(price)");
//                System.out.println(grnTot + "waseem");
//                //grnTotal.setText(grnTot + "lllllllllllll");
//            }
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        double d[] = new double[3];
//        d[0] = gameInvoiceTot;
//        d[1] = grnTot;
//        d[2] = gameTot;
//
//        return d;
//    }
    @FXML
    private void yearCal(MouseEvent event) {
        createAcc.PieChartSampleYear a = new createAcc.PieChartSampleYear();
        Stage s = new Stage();
        a.start(s);
    }

    @FXML
    private void monthCal(MouseEvent event) {
        createAcc.PieChartSampleMonth a = new createAcc.PieChartSampleMonth();
        Stage s = new Stage();
        a.start(s);

    }

    @FXML
    private void dayCal(MouseEvent event) {
        createAcc.PieChartSampleday a = new createAcc.PieChartSampleday();
        Stage s = new Stage();
        a.start(s);
    }
}
