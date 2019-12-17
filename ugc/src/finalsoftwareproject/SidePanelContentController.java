package finalsoftwareproject;

import gamefx.GameCenter;
import gamefx.Log;
import gamefx.Utility;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SidePanelContentController implements Initializable {

    private Stage memberCheckout, guestInvoice, guestToken;

    private AnchorPane anchorPane,
            gameManagement,
            gameRequests,
            lossProfit,
            machineManagement,
            memberManagement,
            pos,
            promotion,
            settings,
            stockManagement,
            supplierManagement,
            viewMachine;
    @FXML
    private ImageView sideImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        File file = new File(new Utility().getTempDir() + "downloaded.jpg");
        Image image = new Image(file.toURI().toString());
        sideImage.setImage(image);
    }

    @FXML
    private void setpos(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (pos == null) {
                pos = FXMLLoader.load(getClass().getResource("/sales/POS.fxml"));
            }
            animator(pos);
            AnchorPane.setTopAnchor(pos, 0.0);
            AnchorPane.setBottomAnchor(pos, 0.0);
            AnchorPane.setLeftAnchor(pos, 0.0);
            AnchorPane.setRightAnchor(pos, 0.0);
            anchorPane.getChildren().add(pos);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setToken(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MachineDetails/token.fxml"));
            if (guestToken == null || !guestToken.isShowing()) {
                guestToken = new Stage();
                guestToken.setScene(new Scene(root));
                guestToken.setMaximized(false);
                guestToken.setTitle("Generate Guest Token");
                guestToken.initStyle(StageStyle.UTILITY);
                guestToken.setResizable(false);
                guestToken.show();
            } else {
                guestToken.requestFocus();
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setGuestInvoice(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sales/guest_invoice.fxml"));
            if (guestInvoice == null || !guestInvoice.isShowing()) {
                guestInvoice = new Stage();
                guestInvoice.setScene(new Scene(root));
                guestInvoice.setMaximized(false);
                guestInvoice.setTitle("Guest Invoice");
                guestInvoice.initStyle(StageStyle.UTILITY);
                guestInvoice.setResizable(false);
                guestInvoice.show();
            } else {
                guestInvoice.requestFocus();
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setStock(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (stockManagement == null) {
                stockManagement = FXMLLoader.load(getClass().getResource("/gamefx/Item.fxml"));
            }
            animator(stockManagement);
            AnchorPane.setTopAnchor(stockManagement, 0.0);
            AnchorPane.setBottomAnchor(stockManagement, 0.0);
            AnchorPane.setLeftAnchor(stockManagement, 0.0);
            AnchorPane.setRightAnchor(stockManagement, 0.0);
            anchorPane.getChildren().add(stockManagement);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setAddMachine(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (machineManagement == null) {
                machineManagement = FXMLLoader.load(getClass().getResource("/MachineDetails/machineFront.fxml"));
            }
            animator(machineManagement);
            AnchorPane.setTopAnchor(machineManagement, 0.0);
            AnchorPane.setBottomAnchor(machineManagement, 0.0);
            AnchorPane.setLeftAnchor(machineManagement, 0.0);
            AnchorPane.setRightAnchor(machineManagement, 0.0);
            anchorPane.getChildren().add(machineManagement);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setMemberManagement(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (memberManagement == null) {
                memberManagement = FXMLLoader.load(getClass().getResource("/memData/member.fxml"));
            }
            animator(memberManagement);
            AnchorPane.setTopAnchor(memberManagement, 0.0);
            AnchorPane.setBottomAnchor(memberManagement, 0.0);
            AnchorPane.setLeftAnchor(memberManagement, 0.0);
            AnchorPane.setRightAnchor(memberManagement, 0.0);
            anchorPane.getChildren().add(memberManagement);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setGameRequests(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (gameRequests == null) {
                gameRequests = FXMLLoader.load(getClass().getResource("/gamerequest/GameRequests.fxml"));
            }
            animator(gameRequests);
            AnchorPane.setTopAnchor(gameRequests, 0.0);
            AnchorPane.setBottomAnchor(gameRequests, 0.0);
            AnchorPane.setLeftAnchor(gameRequests, 0.0);
            AnchorPane.setRightAnchor(gameRequests, 0.0);
            anchorPane.getChildren().add(gameRequests);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setProfit(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (lossProfit == null) {
                lossProfit = FXMLLoader.load(getClass().getResource("/was/profit.fxml"));
            }
            animator(lossProfit);
            AnchorPane.setTopAnchor(lossProfit, 0.0);
            AnchorPane.setBottomAnchor(lossProfit, 0.0);
            AnchorPane.setLeftAnchor(lossProfit, 0.0);
            AnchorPane.setRightAnchor(lossProfit, 0.0);
            anchorPane.getChildren().add(lossProfit);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setPromotions(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (promotion == null) {
                promotion = FXMLLoader.load(getClass().getResource("/MachineDetails/promotions.fxml"));
            }
            animator(promotion);
            AnchorPane.setTopAnchor(promotion, 0.0);
            AnchorPane.setBottomAnchor(promotion, 0.0);
            AnchorPane.setLeftAnchor(promotion, 0.0);
            AnchorPane.setRightAnchor(promotion, 0.0);
            anchorPane.getChildren().add(promotion);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    /**
     * @return the anchorPane
     */
    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    /**
     * @param anchorPane the anchorPane to set
     */
    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
        setHome(null);
    }

    @FXML
    private void setMemberCheckout(ActionEvent event) {
        //SUEDWindow
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/accounts/MemberOTCheck.fxml"));
            if (memberCheckout == null || !memberCheckout.isShowing()) {
                memberCheckout = new Stage();
                memberCheckout.setScene(new Scene(root));
                memberCheckout.setMaximized(false);
                memberCheckout.setTitle("Member Checkout");
                memberCheckout.initStyle(StageStyle.UTILITY);
                memberCheckout.setResizable(false);
                memberCheckout.show();
            } else {
                memberCheckout.requestFocus();
            }

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setGameManagement(ActionEvent event) {
        //SUEDWindow
        try {
            anchorPane.getChildren().clear();
            if (gameManagement == null) {
                gameManagement = FXMLLoader.load(getClass().getResource("/GameManagement/SUEDWindow.fxml"));
            }
            animator(gameManagement);
            AnchorPane.setTopAnchor(gameManagement, 0.0);
            AnchorPane.setBottomAnchor(gameManagement, 0.0);
            AnchorPane.setLeftAnchor(gameManagement, 0.0);
            AnchorPane.setRightAnchor(gameManagement, 0.0);
            anchorPane.getChildren().add(gameManagement);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setSettings(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (settings == null) {
                settings = FXMLLoader.load(getClass().getResource("/properties/SettingsGUI.fxml"));
            }
            animator(settings);
            AnchorPane.setTopAnchor(settings, 0.0);
            AnchorPane.setBottomAnchor(settings, 0.0);
            AnchorPane.setLeftAnchor(settings, 0.0);
            AnchorPane.setRightAnchor(settings, 0.0);
            anchorPane.getChildren().add(settings);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setSupplier(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (supplierManagement == null) {
                supplierManagement = FXMLLoader.load(getClass().getResource("/gamefx/Supplier.fxml"));
            }
            animator(supplierManagement);
            AnchorPane.setTopAnchor(supplierManagement, 0.0);
            AnchorPane.setBottomAnchor(supplierManagement, 0.0);
            AnchorPane.setLeftAnchor(supplierManagement, 0.0);
            AnchorPane.setRightAnchor(supplierManagement, 0.0);
            anchorPane.getChildren().add(supplierManagement);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    public void animator(Node node) {
        node.setScaleX(0);
        node.setScaleY(0);
        //Creating scale Transition 
        ScaleTransition scaleTransition = new ScaleTransition();

        //Setting the duration for the transition 
        scaleTransition.setDuration(Duration.millis(250));

        //Setting the node for the transition 
        scaleTransition.setNode(node);

        //Setting the dimensions for scaling 
        scaleTransition.setByY(1);
        scaleTransition.setByX(1);

        //Setting the cycle count for the translation 
        scaleTransition.setCycleCount(1);

        //Setting auto reverse value to true 
        scaleTransition.setAutoReverse(false);

        //Playing the animation 
        scaleTransition.play();
    }

    @FXML
    private void setHome(ActionEvent event) {
        try {
            anchorPane.getChildren().clear();
            if (viewMachine == null) {
                FXMLLoader fx = new FXMLLoader();
                viewMachine = fx.load(getClass().getResource("/MachineDetails/viewMachine.fxml").openStream());
                GameCenter.setViewMachineController(fx.getController());
            }
            animator(viewMachine);
            AnchorPane.setTopAnchor(viewMachine, 0.0);
            AnchorPane.setBottomAnchor(viewMachine, 0.0);
            AnchorPane.setLeftAnchor(viewMachine, 0.0);
            AnchorPane.setRightAnchor(viewMachine, 0.0);
            anchorPane.getChildren().add(viewMachine);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

}
