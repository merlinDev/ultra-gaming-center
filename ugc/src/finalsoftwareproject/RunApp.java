/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalsoftwareproject;

import activation.GetNetworkAddress;
import connection.Listener;
import gamefx.GameCenter;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.Utility;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Isu
 */
public class RunApp extends Application {

    public static void main(String[] args) throws Exception {
        Utility.startTimer();
        Listener.initialize();
        //new Utility().displayTrayNotification("TEST", "ERROR", TrayIcon.MessageType.ERROR);
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Read the property file in GameCenter folder in documents
        if (!netIsAvailable()) {
            MessageBox.showWarningMessage("No working internet connection", "This software might not work without a working internet connection.");
        }
        FileInputStream filein = null;
        try {
            filein = new FileInputStream(new Utility().getTempDir() + "Activation.properties");
            Properties properties = new Properties();
            properties.load(filein);
            String mac = properties.getProperty("mac");
            //GameCenter.changeStage("finalsoftwareproject/AdminHome.fxml", true, true);

            if (mac != null || GetNetworkAddress.GetAddress("mac").equals(mac)) {
                GameCenter.changeStage("accounts/login.fxml", true, true);
                //GameCenter.changeStage("MachineDetails/viewMachine.fxml", true, false);

               // GameCenter.changeStage("accounts/MemberOTCheck.fxml", true, false);
                //GameCenter.changeStage("GameManagement/addGamestoMachine.fxml", true, false);
            } else {
                MessageBox.showErrorMessage("Configuration Error", "Configuration Error");
            }
        } catch (FileNotFoundException ex) {
            GameCenter.changeStage("activation/FXML.fxml", false, false);
        } catch (Exception ex) {
            Log.error(ex);
        } finally {
            try {
                if (filein != null) {
                    filein.close();
                }
            } catch (IOException ex) {
                Log.error(ex);
            }
        }

    }

    //check internet connection
    public static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void downloadFile() {
        try {
            File file = new File(new Utility().getTempDir() + "downloaded.jpg");
            if (!file.exists()) {
                URL url = new URL("http://i.imgur.com/xlTNJ5rg.jpg");
                BufferedImage img = ImageIO.read(url);
                ImageIO.write(img, "jpg", file);
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
}
