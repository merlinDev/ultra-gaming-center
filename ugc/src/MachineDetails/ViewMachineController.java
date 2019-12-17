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
package MachineDetails;

import com.cyclotech.repository.CRUDOperationsImpl;
import connection.Command;
import connection.Speaker;
import gamefx.Log;
import gamefx.MessageBox;
import gamefx.Utility;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author RoshSoft Co.
 */
public class ViewMachineController implements Initializable {

    private Stage cmd;
    private ObservableList<viewMachineTbl> data = FXCollections.observableArrayList();
    private viewMachineTbl selected;
    @FXML
    private TableView<viewMachineTbl> gameFront;

    private final Timeline timeline = new Timeline(new KeyFrame(
            Duration.seconds(1),
            ae -> refresh()));
    @FXML
    private TableColumn<?, ?> machineIdCol;
    @FXML
    private TableColumn<?, ?> userTypeCol;
    @FXML
    private TableColumn<?, ?> userIdCol;
    @FXML
    private TableColumn<?, ?> loginTimeCol;
    @FXML
    private TableColumn<?, ?> packageTimeCol;
    @FXML
    private TableColumn<?, ?> durationCol;
    @FXML
    private TableColumn<?, ?> overTimeCol;
    @FXML
    private TableColumn<?, ?> selectedCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        machineIdCol.setCellValueFactory(new PropertyValueFactory<>("machineId"));
        userTypeCol.setCellValueFactory(new PropertyValueFactory<>("userType"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        loginTimeCol.setCellValueFactory(new PropertyValueFactory<>("loginTime"));
        packageTimeCol.setCellValueFactory(new PropertyValueFactory<>("packageTime"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        overTimeCol.setCellValueFactory(new PropertyValueFactory<>("overTime"));
        selectedCol.setCellValueFactory(new PropertyValueFactory<>("selected"));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    public void refreshTable(ActionEvent event) {
        data = FXCollections.observableArrayList();
        CRUDOperationsImpl db = new CRUDOperationsImpl();
        try {
            ResultSet rs = db.search("ipAddress", "machine", "WHERE inuse='inuse' AND (type='member' OR type='guest')");
            while (rs.next()) {
                try {
                    String respond = Speaker.send(rs.getString("ipAddress"), Command.REQUEST_USER_SESSION_DATA);
                    if (!respond.equals(Command.ERROR)) {
                        if (respond.equals(Command.SYSTEM_NOT_IN_USE)) {
                            db.update("machine", "inuse = 'available'", "ipAddress='" + rs.getString("ipAddress") + "'");
                        } else {
                            String sessionData[] = respond.split(";");
                            addNewRecord(sessionData[0],
                                    sessionData[1],
                                    sessionData[2],
                                    sessionData[3],
                                    Long.parseLong(sessionData[4]),
                                    Long.parseLong(sessionData[5]));
                        }
                    }
                } catch (Throwable e) {
                }
            }
        } catch (Exception ex) {
            Log.error(ex);
        }

        gameFront.setItems(null);
        gameFront.setItems(data);
        gameFront.refresh();
    }

    public void addNewRecord(String machineid, String userType, String userId, String LoginTime, long packagetime, long duration) {
        data.add(new viewMachineTbl(machineid, userType, userId, LoginTime, packagetime, duration));
    }

    public void removeRecord(String userId) {
        for (viewMachineTbl d : data) {
            if (d.getUserId().equals(userId)) {
                data.remove(d);
                return;
            }
        }
    }

    private void refresh() {
        if (data != null && data.size() > 0) {
            for (viewMachineTbl dt : data) {
                dt.durationTick();
            }
            gameFront.setItems(null);
            gameFront.setItems(data);
            gameFront.refresh();
        }
    }
    int x = 0;

    @FXML
    private void logout(ActionEvent event) {
        int count = 0, successCount = 0;
        boolean notOTmachines = false;
        Vector<String> machines = new Vector();
        for (viewMachineTbl dt : data) {
            if (dt.getSelectedStatus()) {
                count++;
                machines.add(dt.getMachineId());
                if (!notOTmachines && !dt.isOverTime()) {
                    notOTmachines = true;
                }
            }
        }

        if (count <= 0) {
            MessageBox.showErrorMessage("No machines selected", "Select machines that you want to logout.");
            return;
        }

        if (notOTmachines
                && MessageBox.showMessage(MessageBox.MessageType.WARNING, "Confirm Action", "Confirm your action.",
                        "There are users who have not played passed the package time in the selected list. Do you wish to proceed?", new String[]{MessageBox.YES_BUTTON, MessageBox.NO_BUTTON}, true).equals(MessageBox.NO_BUTTON)) {
            return;
        }

        if (MessageBox.showConfirmationMessage("Confirm your action.",
                "Do you wish to logout selected " + machines.size() + " machine(s)?", new String[]{MessageBox.YES_BUTTON, MessageBox.NO_BUTTON}).equals(MessageBox.NO_BUTTON)) {
            return;
        }

        try {
            for (String machine : machines) {
                if (Speaker.sendToMachine(machine, Command.LOGOUT).equals(Command.OK)) {
                    successCount++;
                }
            }
        } catch (Throwable e) {
        }

        MessageBox.showInformationMessage("Command(s) sent.", "Logout commands sent to " + successCount + " machine(s) successfully. " + (count - successCount) + " failed.");
    }

    @FXML
    private void playWarning(ActionEvent event) {
        int count = 0, successCount = 0;
        boolean notOTmachines = false;
        Vector<String> machines = new Vector();
        for (viewMachineTbl dt : data) {
            if (dt.getSelectedStatus()) {
                count++;
                machines.add(dt.getMachineId());
                if (!notOTmachines && !dt.isOverTime()) {
                    notOTmachines = true;
                }
            }
        }

        if (count <= 0) {
            MessageBox.showErrorMessage("No machines selected", "Select machines that you want to play warning sound.");
            return;
        }

        if (notOTmachines
                && MessageBox.showMessage(MessageBox.MessageType.WARNING, "Confirm Action", "Confirm your action.",
                        "There are users who have not played passed the package time in the selected list. Do you wish to proceed?", new String[]{MessageBox.YES_BUTTON, MessageBox.NO_BUTTON}, true).equals(MessageBox.NO_BUTTON)) {
            return;
        }

        if (MessageBox.showConfirmationMessage("Confirm your action.",
                "Do you wish to play warning sound on " + machines.size() + " machine(s)?", new String[]{MessageBox.YES_BUTTON, MessageBox.NO_BUTTON}).equals(MessageBox.NO_BUTTON)) {
            return;
        }

        try {
            for (String machine : machines) {
                if (Speaker.sendToMachine(machine, Command.PLAY_WARNING_SOUND).equals(Command.OK)) {
                    successCount++;
                }
            }
        } catch (Throwable e) {
        }

        MessageBox.showInformationMessage("Command(s) sent.", "Commands sent to " + successCount + " machine(s) successfully. " + (count - successCount) + " failed.");
    }

    @FXML
    private void opencmd(ActionEvent event) {
        if (selected != null) {
            try {
                gamefx.CmdController.setIPAddress(new Utility().getIpAddress(selected.getMachineId()));
                Parent root = FXMLLoader.load(getClass().getResource("/gamefx/Cmd.fxml"));
                if (cmd == null || !cmd.isShowing()) {
                    cmd = new Stage();
                    cmd.setScene(new Scene(root));
                    cmd.setMaximized(false);
                    cmd.setTitle("Windows Command Processor");
                    cmd.initStyle(StageStyle.UTILITY);
                    cmd.setResizable(false);
                    cmd.show();
                } else {
                    cmd.requestFocus();
                }
            } catch (Exception ex) {
                Log.error(ex);
            }
        }
    }

    @FXML
    private void gameFrontMC(MouseEvent event) {
        selected = gameFront.getSelectionModel().getSelectedItem();
    }

}
