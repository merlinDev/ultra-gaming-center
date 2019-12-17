/*
 *	Java Institute For Advanced Technology
 *	Final Project
 *	
 *	*************************************
 *
 *	The MIT License
 *
 *	Copyright © 2017 CYCLOTECH.
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a copy
 *	of this software and associated documentation files (the "Software"), to deal
 *	in the Software without restriction, including without limitation the rights
 *	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *	copies of the Software, and to permit persons to whom the Software is
 *	furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM
 *	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *	THE SOFTWARE.
 */
package properties;

import com.cyclotech.repository.DB;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gamefx.MessageBox;
import gamefx.Utility;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Roshana Pitigala
 */
public class SettingsGUIController implements Initializable {

    private Utility util = new Utility();
    private Settings settings = new Settings();

    @FXML
    private JFXTextField address;
    @FXML
    private JFXTextField contactNumber;
    @FXML
    private JFXTextField contactEmail;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton esSave;
    @FXML
    private JFXPasswordField emailPass;
    @FXML
    private TitledPane gs;
    @FXML
    private Accordion main;
    @FXML
    private JFXButton esSave1;
    @FXML
    private JFXButton esSave11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadAll();
        main.setExpandedPane(gs);
    }

    @FXML
    private void addressAP(ActionEvent event) {
        contactNumber.requestFocus();
    }

    @FXML
    private void contactNumberAP(ActionEvent event) {
        contactNumberKR(null);
        contactEmail.requestFocus();
    }

    @FXML
    private void contactNumberKT(KeyEvent event) {
        if (!Character.isDigit(event.getCharacter().toCharArray()[0])) {
            event.consume();
        }
    }

    @FXML
    private void contactEmailAP(ActionEvent event) {
        gsSaveAP(null);
    }

    @FXML
    private boolean contactEmailKR(KeyEvent event) {
        if (contactEmail.getText() == null || contactEmail.getText().equals("") || !util.validateEmail(contactEmail.getText())) {
            contactEmail.setStyle("-fx-prompt-text-fill: #FF6E6E; -fx-text-fill: #FFFFFF;");
        } else {
            contactEmail.setStyle("-fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");
            return true;
        }
        return false;
    }

    @FXML
    private void gsSaveAP(ActionEvent event) {
        if (address.getText() == null || address.getText().equals("")) {
            MessageBox.showErrorMessage("Address can not be empty.", "All fields are mandatory.");
        } else if (!contactNumberKR(null)) {
            MessageBox.showErrorMessage("Enter a valid contact number.", "All fields are mandatory.");
        } else if (!contactEmailKR(null)) {
            MessageBox.showErrorMessage("Enter a valid email address.", "All fields are mandatory.");
        } else {
            settings.setValue(Settings.ADDRESS, address.getText());
            settings.setValue(Settings.CONTACT_EMAIL_ADDRESS, contactEmail.getText());
            settings.setValue(Settings.TELEPHONE, contactNumber.getText());
            MessageBox.showInformationMessage("Data Saved.", "General settings saved successfully.");
        }
    }

    @FXML
    private void emailAP(ActionEvent event) {
        emailKR(null);
        emailPass.requestFocus();
    }

    @FXML
    private boolean emailKR(KeyEvent event) {
        if (email.getText() == null || email.getText().equals("") || !util.validateEmail(email.getText())) {
            email.setStyle("-fx-prompt-text-fill: #FF6E6E; -fx-text-fill: #FFFFFF;");
        } else {
            email.setStyle("-fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");
            return true;
        }
        return false;
    }

    @FXML
    private void esSaveAP(ActionEvent event) {
        if (email.getText() == null || email.getText().equals("") || !util.validateEmail(email.getText())) {
            MessageBox.showErrorMessage("Enter a valid email address.", "All fields are mandatory.");
        } else if (emailPass.getText() == null || emailPass.getText().equals("")) {
            MessageBox.showErrorMessage("Email Password can not be empty.", "All fields are mandatory.");
        } else {
            settings.setValue(Settings.EMAIL_ADDRESS, email.getText());
            settings.setValue(Settings.EMAIL_PASSWORD, emailPass.getText());
            emailPass.setText("");
            MessageBox.showInformationMessage("Data Saved.", "Email settings saved successfully.");
        }
    }

    @FXML
    private void emailPassAP(ActionEvent event) {
        esSaveAP(null);
    }

    private void loadAll() {
        address.setText(settings.getValue(Settings.ADDRESS));
        contactEmail.setText(settings.getValue(Settings.CONTACT_EMAIL_ADDRESS));
        contactNumber.setText(settings.getValue(Settings.TELEPHONE));
        email.setText(settings.getValue(Settings.EMAIL_ADDRESS));
    }

    @FXML
    private boolean contactNumberKR(KeyEvent event) {
        if (contactNumber.getText() == null || contactNumber.getText().equals("") || !util.validatePhoneNumber(contactNumber.getText())) {
            contactNumber.setStyle("-fx-prompt-text-fill: #FF6E6E; -fx-text-fill: #FFFFFF;");
        } else {
            contactNumber.setStyle("-fx-prompt-text-fill: #FFFFFF; -fx-text-fill: #FFFFFF;");
            return true;
        }
        return false;
    }

    @FXML
    private void backaup(ActionEvent event) {
        activation.BackupHandler backupHandler = new activation.BackupHandler();
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions
                = new FileChooser.ExtensionFilter(
                        "SQL Files", "*.sql", "*.sql");

        chooser.getExtensionFilters().add(fileExtensions);
        File f = chooser.showSaveDialog(null);
        if (f != null) {
            backupHandler.backupDataWithDatabase("\"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump.exe\"", DB.getIpAddress(), "3306", "root", "1234", "gamecenter", f.getAbsolutePath());
            MessageBox.showMessage(MessageBox.MessageType.INFORMATION, "Backup Success!", "Backup Successfully", "Backup Successfully", null, true);
        }
    }

    @FXML
    private void restore(ActionEvent event) {
        activation.BackupHandler backupHandler = new activation.BackupHandler();
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter fileExtensions
                = new FileChooser.ExtensionFilter(
                        "SQL Files", "*.sql", "*.sql");

        chooser.getExtensionFilters().add(fileExtensions);
        File f = chooser.showOpenDialog(null);
        if (f != null) {
            backupHandler.restoreDatabase("root", "1234", f.getAbsolutePath());
            MessageBox.showMessage(MessageBox.MessageType.INFORMATION, "Restore Success!", "Restore Successful", "Restore Successful", null, true);
        }
    }

}
