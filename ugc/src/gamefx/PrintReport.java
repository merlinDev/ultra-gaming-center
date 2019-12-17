/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.cyclotech.repository.DB;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import properties.Settings;

/**
 *
 * @author Buddhika
 */
public class PrintReport extends Thread {

    Map<String, Object> para;
    String path = "C:\\CycloTech\\Reports\\";
    boolean passDBConnection = true, headerDetails = true;
    private Settings settings = new Settings();

    /**
     * DB connection and header details are passed.
     */
    public PrintReport(Map<String, Object> para, String reportFile) {
        this.para = para;
        this.path += reportFile;
    }

    /**
     * Header details are passed.
     */
    public PrintReport(Map<String, Object> para, String reportFile, boolean passDBCon) {
        this.para = para;
        this.path += reportFile;
        this.passDBConnection = passDBCon;
    }

    public PrintReport(Map<String, Object> para, String reportFile, boolean passDBCon, boolean passHeaderDetails) {
        this.para = para;
        this.path += reportFile;
        this.passDBConnection = passDBCon;
        this.headerDetails = passHeaderDetails;
    }

    @Override
    public void run() {
        try {
            System.out.println("PREPARING : " + path);
            path = path.replace("jrxml", "jasper");
            if (headerDetails) {
                System.out.println("ADDING HEADER DETAILS...");
                para.put("Address", settings.getValue(Settings.ADDRESS));
                para.put("Telephone", settings.getValue(Settings.TELEPHONE));
            }

            System.out.println("COMPILING REPORT..." + path);
            InputStream is = new FileInputStream(path);
            JasperReport jr = (JasperReport) JRLoader.loadObject(is);
            //JasperReport jr = JasperCompileManager.compileReport(path);
            JasperPrint jasp;
            System.out.println(jr);
            if (passDBConnection) {
                System.out.println("PASSING DB CONNECTION...");
                jasp = JasperFillManager.fillReport(jr, para, DB.getConnection());
            } else {
                jasp = JasperFillManager.fillReport(jr, para,new JREmptyDataSource());
            }

            System.out.println("PRINTING : " + path);
            JasperPrintManager.printReport(jasp, true);

        } catch (Exception e) {
            Log.error(e);
        }

    }

    public static boolean getScreenshot(final Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.REVERSE_LANDSCAPE, Printer.MarginType.EQUAL);
        JobSettings jobSettings = job.getJobSettings();
        return job.printPage(node);
    }
}
