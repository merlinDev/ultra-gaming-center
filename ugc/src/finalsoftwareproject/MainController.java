/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalsoftwareproject;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import gamefx.Log;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author niroisu
 */
public class MainController implements Initializable {

    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    HamburgerBackArrowBasicTransition transition;
    @FXML
    private AnchorPane anchor;
    @FXML
    private AnchorPane anchor2;
    double drawerWidth;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            // TODO
            drawerWidth = drawer.getWidth();
            try {
                FXMLLoader fXMLLoader = new FXMLLoader();
                VBox box = fXMLLoader.load(getClass().getResource("SidePanelContent.fxml").openStream());
                SidePanelContentController contentController = fXMLLoader.getController();
                contentController.setAnchorPane(anchor2);
                drawer.setSidePane(box);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            transition = new HamburgerBackArrowBasicTransition(hamburger);
            transition.setRate(-1);
            System.out.println(anchor.getPrefWidth());
            AnchorPane newLoadedPane = FXMLLoader.load(getClass().getResource("/MachineDetails/machineFront.fxml"));
            AnchorPane.setTopAnchor(newLoadedPane, 0.0);
            AnchorPane.setBottomAnchor(newLoadedPane, 0.0);
            AnchorPane.setLeftAnchor(newLoadedPane, 0.0);
            AnchorPane.setRightAnchor(newLoadedPane, 0.0);

            anchor2.getChildren().add(newLoadedPane);

        } catch (IOException ex) {
            Log.error(ex);
        }

    }

    @FXML
    private void hamburgerAction(MouseEvent event) {
        transition.setRate(transition.getRate() * -1);
        transition.play();

        if (drawer.isShown()) {
            drawer.close();
            drawer.setVisible(false);
            drawer.setPrefWidth(0.0);
        } else {
            drawer.open();
            drawer.setPrefWidth(drawerWidth);
            drawer.setVisible(true);
        }
    }

}
