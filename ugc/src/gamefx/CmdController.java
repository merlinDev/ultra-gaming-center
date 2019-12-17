/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import connection.Command;
import connection.Speaker;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Roshana Pitigala
 */
public class CmdController implements Initializable {

    private final ArrayList<String> cmds = new ArrayList<>();
    private int cmdsPosition = 0;
    private static String ipAddress = "127.0.0.1";
    private static final Pattern IPPATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    @FXML
    private JFXTextArea scrn;
    @FXML
    private JFXTextField ip;
    @FXML
    private JFXTextField cmd;
    @FXML
    private JFXCheckBox oneway;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MessageBox.showWarningMessage("Use With Caution!", "Improper usage of Windows Command Processor may cause a system crash. Use only if you know what you are playing with.");
        ip.setText(ipAddress);
        if (ipValid(ipAddress)) {
            cmd.requestFocus();
        } else {
            ipA(null);
            ip.requestFocus();
        }
    }

    public static void setIPAddress(String IpAddress) {
        ipAddress = IpAddress;
    }

    @FXML
    private boolean ipA(ActionEvent event) {
        if (ipValid(ip.getText())) {
            ip.setStyle("-fx-text-fill: #ffffff;");
            cmd.setEditable(true);
            ipAddress = ip.getText();
            return true;
        }

        ip.setStyle("-fx-text-fill: #FF1919;");
        cmd.setEditable(false);
        return false;
    }

    @FXML
    private void cmdA(ActionEvent event) {
        if (cmds.isEmpty() || !cmds.get(0).equals(cmd.getText())) {
            cmds.add(0, cmd.getText());
        }
        cmdsPosition = -1;
        if (cmd.getText().toLowerCase().equals("cls")) {
            scrn.setText("");
        } else if (cmd.getText().toLowerCase().equals("exit")) {
            ((Stage) scrn.getScene().getWindow()).close();
        } else {
            scrn.appendText("/>" + cmd.getText() + "\r\n");
            scrn.appendText("\r\n" + Speaker.send(ipAddress, Command.CMD + ";" + cmd.getText() + ";" + oneway.isSelected()) + "\r\n");
        }
        cmd.setText("");
    }

    private boolean ipValid(String ip) {
        return IPPATTERN.matcher(ip).matches();
    }

    @FXML
    private void cmdKR(KeyEvent event) {
        if (event.getCode() == KeyCode.UP && cmds.size() > 0) {
            if (cmdsPosition < (cmds.size() - 1)) {
                cmdsPosition++;
            }
            cmd.setText(cmds.get(cmdsPosition));
        } else if (event.getCode() == KeyCode.DOWN && cmds.size() > 0) {
            if (cmdsPosition > 0) {
                cmdsPosition--;
            }
            cmd.setText(cmds.get(cmdsPosition));
        }
    }
}
