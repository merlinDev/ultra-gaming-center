/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamefx;

import MachineDetails.ViewMachineController;
import accounts.user;
import connection.Command;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class GameCenter {

    private static Stage mainStage;
    private static ViewMachineController adminHome = null;
    private static final String login = "login", activation = "activ";

    public static Stage getStage() {
        return mainStage;
    }

    public static void setAlwaysOnTop(boolean onTop) {
        mainStage.setAlwaysOnTop(onTop);
    }

    /**
     * Changes the Main Stage.
     *
     * @param location fxml file path as from root. Give
     * "Package/YourFile.fxml".<br><br>Example - accounts/login.fxml
     * @param undecorated Undecorated window if true.
     * @param maximized Full screen window if true.
     * @return True if Stage changed without errors, false otherwise. Check Log
     * for errors.
     */
    public static boolean changeStage(String location, boolean undecorated, boolean maximized) {
        //<editor-fold defaultstate="collapsed" desc="changeStage">
        Stage previous = mainStage;
        try {
            mainStage = new Stage();
            Scene s = new Scene(FXMLLoader.load(GameCenter.class.getResource("/" + location)));
            s.setFill(Color.TRANSPARENT);
            mainStage.setScene(s);
            if (undecorated) {
                mainStage.initStyle(StageStyle.UNDECORATED);
            }
            mainStage.initStyle(StageStyle.TRANSPARENT);
            //mainStage.setAlwaysOnTop(true);
            setStageClosingListner();
            if (location.equals("accounts/login.fxml")) {
                mainStage.getScene().setUserData(login);
            } else if (location.equals("activation/FXML.fxml")) {
                mainStage.getScene().setUserData(activation);
            }
            mainStage.setMaximized(maximized);
            mainStage.show();
            if (previous != null) {
                previous.close();
            }
            return true;
        } catch (Exception ex) {
            Log.error(ex);
        }
        mainStage = previous;
        return false;
        //</editor-fold>
    }

    private static void setStageClosingListner() {
        //<editor-fold defaultstate="collapsed" desc="setStageClosingListner">
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                if (mainStage.getScene().getUserData() != null && mainStage.getScene().getUserData().equals(login)) {
                    String stdwn = "Shut down", comd = MessageBox.showConfirmationMessage("Shut down the system now?", "All running programs will be terminated.", new String[]{stdwn, "Cancel"});
                    if (comd.equals(stdwn)) {
                        try {
                            Command.systemShutdown(false);
                        } catch (Exception ex) {
                            Log.error(ex);
                        }
                    }
                } else if (mainStage.getScene().getUserData() != null && mainStage.getScene().getUserData().equals(activation)) {

                } else {
                    String lgout = "Logout", comd = MessageBox.showConfirmationMessage("Do you wish to logout?", "Your session will be terminated.", new String[]{lgout, "Cancel"});
                    if (comd.equals(lgout)) {
                        user.logout();
                    }
                }
            }
        });
        //</editor-fold>
    }

    public static ViewMachineController getAdminHome() {
        if (user.getUserTypeId() == user.ADMINISTRATOR) {
            return adminHome;
        }
        return null;
    }

    public static void setViewMachineController(ViewMachineController controllerClass) {
        adminHome = controllerClass;
    }
}
