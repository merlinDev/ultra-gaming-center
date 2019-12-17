package gamefx;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author nipun
 */
public class GamePathController implements Initializable {

    @FXML
    private JFXTextField gameID;
    @FXML
    private JFXTextField gameName;
    @FXML
    private JFXComboBox<String> gamCat;
    private JFXButton setIcon;
    private JFXComboBox<String> setRatings;
    @FXML
    private JFXButton addGame;
    @FXML
    private JFXTextField gamePath;
    @FXML
    private JFXButton browse;
    @FXML
    private JFXTextArea desc;

    private static String path;

    @FXML
    private JFXCheckBox sell;
    @FXML
    private JFXCheckBox play;
    @FXML
    private JFXTextField iconURL;
    private JFXTextField backgroundURL;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GameType();
        gameID();

        sell.setSelected(true);
        play.setSelected(true);

    }

    @FXML
    private void addGame(ActionEvent event) {

        try {
            String cat = gamCat.getSelectionModel().getSelectedItem().split(":")[0];
            if (gameName.getText().equals("") || iconURL.getText().equals("")) {
                MessageBox.showErrorMessage("error", "some details missing");
            } else {
                path = gamePath.getText().replace("\\", "/");
                String d = gameName.getText().replace("'", "");
                System.out.println(d);
                new CRUDOperationsImpl().save("games(name,gametype_idGameType,description,status,gamePath,icon,background)",
                        "'" + d + "','" + Integer.parseInt(cat) + "','" + desc.getText() + "','active','" + path + "','" + iconURL.getText() + "','" + iconURL.getText() + "'");

                MessageBox.showInformationMessage("done!", "game added");
                gameName.setText(null);
                desc.setText(null);
                gamePath.setText(null);
                cat = "";
                iconURL.setText(null);

                gamCat.getSelectionModel().clearSelection();
                Stage s = (Stage) gameName.getScene().getWindow();
                s.close();

            }
        } catch (NullPointerException e) {

            MessageBox.showWarningMessage("Error!", "Some Details missing");
            e.printStackTrace();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void setPath(ActionEvent event) {
        try {
            FileChooser dir = new FileChooser();
            dir.setTitle("select game path");
            File path = dir.showOpenDialog(browse.getScene().getWindow());
            System.out.println(path);
            AnchorPane a = new AnchorPane();
            if (path != null) {
                gamePath.setText(path.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void setIcon(ActionEvent event) {
//        try {
//        FileChooser f=new FileChooser();
//        icon = f.showOpenDialog(setIcon.getScene().getWindow()).toString();
//        System.out.println(icon);
//        String replace = icon.replace("\\", "/");
//        String[] split = replace.split("/");
//        System.out.println(split[split.length-1]);
//        
//        showIcon.setText(split[split.length-1]);
//        }catch(NullPointerException e){
//            System.out.println("lol");
//        }
//        catch (Exception e) {
//         e.printStackTrace();
//        }
//    }
//    private void setBG(ActionEvent event) {
//        try {
//        FileChooser f=new FileChooser();
//        bgImage = f.showOpenDialog(setIcon.getScene().getWindow()).toString();
//        String replace = bgImage.replace("\\", "/");
//        String[] split = replace.split("/");
//        System.out.println(split[split.length-1]);
//        
//        showBG.setText(split[split.length-1]);
//        }catch(NullPointerException e){
//            System.out.println("lol");
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    @FXML
    private void sellPlay(ActionEvent event) {
        if (!gamePath.getText().equals("")) {
            if (sell.isSelected() && !play.isSelected()) {
                path = "";
                System.out.println(path);
                gamePath.setDisable(true);
            } else if (play.isSelected()) {
                path = gamePath.getText().replace("\\", "/");
                gamePath.setDisable(false);
            }
        }
    }

    private void gameID() {
        try {
            ResultSet rs = new CRUDOperationsImpl().search("count(idGames) as x", "games", "");
            int x;
            while (rs.next()) {
                x = Integer.parseInt(rs.getString("x")) + 1;
                System.out.println(x);
                gameID.setText("GAME:" + x);
            }
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void addTypes(ActionEvent event) {
        try {
            Parent rt = FXMLLoader.load(getClass().getResource("Addtype.fxml"));
            Scene s = new Scene(rt);
            Stage st = new Stage();
            st.setScene(s);
            st.show();
            //Stage l=(Stage) addGame.getScene().getWindow();
            //l.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@FXML
    private void Refresh(MouseEvent event) {
        try {
            System.out.println("loadgames");
            CRUDOperations crudo = new CRUDOperationsImpl();
            ResultSet rs = crudo.search("typeName", "gamecenter.gameType", " ");
            List<String> a = new ArrayList<>();
            while (rs.next()) {
                a.add(rs.getString("typeName"));
            }
            ObservableList<String> ob = FXCollections.observableArrayList(a);
            gamCat.setItems(ob);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    @FXML
    private void Refresh(ActionEvent event) {
        try {
            GameType();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GameType() {
        try {
            ResultSet rs = new CRUDOperationsImpl().search("idGameType,typeName", "gameType", "");
            while (rs.next()) {
                System.out.println(rs.getString("typeName"));
                gamCat.getItems().add(rs.getString("idGameType") + ":" + rs.getString("typeName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Cancel(ActionEvent event) {
        Stage s = (Stage) gamCat.getScene().getWindow();
        s.close();
    }

}
