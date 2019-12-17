/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamer;

import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import gamefx.Log;
import gamefx.Utility;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Buddhika
 */
public class GamerController implements Initializable {

    AnchorPane mainPane = new AnchorPane();
    static JFXButton[] playb;
    static JFXButton[] Detailsb;
    static ImageView[] image;
    static AnchorPane[] pane;
    static Label[] lab;
    static Label[] name;
    static AnchorPane[] subpane;
    //static AnchorPane[][] rowpane;
    @FXML
    private ScrollPane sco;
    @FXML
    private JFXButton gamesbtn;
    @FXML
    private JFXTextField search;
    @FXML
    private JFXButton homebutton;
    @FXML
    private JFXListView<?> serchresultlist;
    @FXML
    private ComboBox<String> Categorycom;
    @FXML
    private JFXButton sort;
    @FXML
    private AnchorPane Deatails;
    @FXML
    private ImageView backimiage;
    @FXML
    private ImageView minimage;
    @FXML
    private Label DetailsDescription;
    @FXML
    private Label Detailsname;
    @FXML
    private JFXButton detailsplay;
    @FXML
    private JFXDrawer Drower;
    @FXML
    private JFXHamburger hamburger;
    HamburgerBackArrowBasicTransition transition;
    @FXML
    private JFXButton Mostplayed;
    @FXML
    private Label ip;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Drower.setVisible(false);
        Deatails.setVisible(false);
        serchresultlist.setVisible(false);
        homebutton.setVisible(false);
        VBox box;

        System.out.println("machine id : " + new Utility().getMachineId());

        try {
            box = FXMLLoader.load(getClass().getResource("/gamefx/SidePanelContent.fxml"));
            Drower.setSidePane(box);
        }  catch (Exception ex) {
            Log.error(ex);
        }
        transition = new HamburgerBackArrowBasicTransition(hamburger);
        transition.setRate(-1);
        getAllGames();
        loadcombo();
        ip.setText(new Utility().getIpAddress());
    }

    @FXML
    private void gamesbtnAction(ActionEvent event) {
        if (search.getText() != null && !(search.getText().equals(""))) {
            serchgame(search.getText());
        } else {
            getAllGames();
        }
    }

    private void getAllGames() {
        try {
            int[] i;
            int count = 0;
            mainPane.getChildren().clear();
            ResultSet rs1 = new CRUDOperationsImpl().search("count(id) as `x`", "machine_has_games", "where machine_has_games.machine_idMachine='" + new Utility().getMachineId() + "'");
            if (rs1.first()) {
                count = rs1.getInt("x");
            }
            if (count != 0) {
                i = new int[count];

                ResultSet rs = new CRUDOperationsImpl().search("games.*,machine_has_games.*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", "where machine_has_games.machine_idMachine='" + new Utility().getMachineId() + "' and (sellplay='play' or sellplay='both') order by idGames DESC ");
                int o = 0;
                while (rs.next()) {
                    i[o] = Integer.parseInt(rs.getString("games.idGames"));
                    o++;
                }
                getGames(i);
            } else {
                Label l = new Label("No results for " + search.getText());
                l.setLayoutX(500);
                l.setLayoutY(150);
                l.setMinSize(300, 50);
                l.setFont(Font.font(40));
                l.setStyle("-fx-text-fill: green;");
                mainPane.getChildren().addAll(l);
            }
            search.setText(null);
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void getGames(int[] idlist) {
        try {
            image = new ImageView[idlist.length];
            playb = new JFXButton[idlist.length];
            Detailsb = new JFXButton[idlist.length];
            subpane = new AnchorPane[idlist.length];
            pane = new AnchorPane[idlist.length];
            name = new Label[idlist.length];
            int count = (idlist.length / 4);
            if (idlist.length % 4 != 0) {
                count++;
            }
            int u = idlist.length;
            int l = idlist.length - 1;
            boolean b = true;
            int X = 20;
            int Y = 50;
            long v = System.nanoTime();
            for (int i = 0; i < count; i++) {
                X = 20;
                for (int j = 0; j < 4; j++) {
                    ResultSet rs1;
                    if (u <= 0) {
                        rs1 = new CRUDOperationsImpl().search("games.*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", "where games.idGames is null");
                    } else if (b) {
                        rs1 = new CRUDOperationsImpl().search("*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", "where games.idGames='" + idlist[0] + "'  && (sellplay='play' or sellplay='both')");
                        b = false;
                    } else {
                        rs1 = new CRUDOperationsImpl().search("*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", "where games.idGames='" + idlist[idlist.length - l] + "'  && (sellplay='play' or sellplay='both')");
                        l--;
                    }
                    if (rs1.next()) {
                        setgametomain(count, rs1.getInt("games.idGames"), rs1, j, X, Y);
                        X += 320;
                    }
                    u--;
                }
                Y += 400;
            }
            System.out.println(System.nanoTime() - v);
            sco.setContent(mainPane);
            sco.setStyle("-fx-background-color: rgba(0,0,0,0)");
        } catch (Exception e) {
            Log.error(e);
        }
    }
    static SimpleDateFormat sdf;

    private void setgametomain(int count, int ids, ResultSet rs1, int j, int X, int Y) {
        // homebutton.setVisible(false);
        try {
            String[] URLname = rs1.getString("background").split("/.");
            String type = URLname[URLname.length - 1];
            String imgPath = rs1.getString("idGames") + "_image_" + type;
            System.out.println(imgPath);

            String imgDir = new Utility().getTempDir() + "img\\";
            String fullPath = imgDir + imgPath;
            File f = new File(imgDir + imgPath);
            System.out.println(f.exists());
            System.out.println(imgDir + imgPath);
            if (f.exists()) {
                System.out.println("before");
                image[j] = new ImageView("file:///" + fullPath);
                System.out.println("added from folder");
            } else {
                System.out.println("in else condition");
                image[j] = new ImageView(rs1.getString("background"));
                System.out.println("aftef imageView");
                saveIMG.saveImage(rs1.getString("idGames"), rs1.getString("background"), imgPath);
            }

            image[j].setFitHeight(270);
            image[j].setFitWidth(250);
            image[j].setLayoutX(5);
            image[j].setLayoutY(5);

            playb[j] = new JFXButton("Play");
            playb[j].setStyle("-fx-background-color: rgba(52, 152, 219,1)");
            playb[j].setMinSize(80, 10);
            playb[j].setLayoutY(310);
            playb[j].setLayoutX(20);

            name[j] = new Label(rs1.getString("name"));
            name[j].setStyle("-fx-background-color: rgba(153, 153, 153,0); -fx-text-fill: rgb(94, 239, 45);");
            name[j].setFont(Font.font(20));
            name[j].setMinSize(100, 10);
            name[j].setMaxWidth(250);
            name[j].setLayoutY(280);
            name[j].setLayoutX(20);

            playb[j].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        getDetails(ids, false);
                        //Runtime.getRuntime().exec(rs1.getString("gamePath"));
                        System.out.println("starting game...........");
                        new playGame(idgame + "", dgp);
                        Thread.sleep(5000);
                    } catch (Exception e) {
                        Log.error(e);
                    }
                }
            });
            Detailsb[j] = new JFXButton("Details");
            Detailsb[j].setStyle("-fx-background-color: rgba(52, 152, 219,1)");
            Detailsb[j].setMinSize(80, 10);
            Detailsb[j].setLayoutY(310);
            Detailsb[j].setLayoutX(120);
            Detailsb[j].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        getDetails(ids, true);
                        System.out.println("going to details");
                    } catch (Exception e) {
                        Log.error(e);
                    }
                }
            });
            Label la = new Label("Image Loading...");
            la.setStyle("-fx-background-color: rgba(255,255,255,1); -fx-text-fill: rgb(94, 239, 45);-fx-font-size: 20pt;");
            la.setLayoutX(40);
            la.setLayoutY(150);
            subpane[j] = new AnchorPane();
            subpane[j].getChildren().addAll(la, image[j]);
            subpane[j].setStyle("-fx-background-color: rgba(255,255,255,1)");
            subpane[j].setMinSize(260, 280);
            pane[j] = new AnchorPane();
            pane[j].getChildren().addAll(playb[j], Detailsb[j], subpane[j], name[j]);
            pane[j].setStyle("-fx-background-color: rgba(0,0,0,0)");
            pane[j].setLayoutX(X);
            pane[j].setLayoutY(Y);
            pane[j].setMinSize(270, 370);
            mainPane.getChildren().addAll(pane[j]);
        } catch (Exception e) {
            Log.error(e);
        }
    }
    static String dgp;
    static int idgame;

    private void getDetails(int ids, boolean b) {
        try {
            String pcID = new Utility().getMachineId();
            homebutton.setVisible(true);
            System.out.println("my query +++++++++++++++++++++++++++++");
            ResultSet rs1 = new CRUDOperationsImpl().search("games.*,machine_has_games.path", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", "where idGames='" + ids + "' and machine_idMachine='" + pcID + "' and (sellplay='play' or sellplay='both')");
            if (rs1.next()) {
                //backimiage.setImage(im);
                //backimiage.setFitHeight(Deatails.getHeight());

                String[] URLname = rs1.getString("background").split("/.");
                String type = URLname[URLname.length - 1];
                String imgPath = rs1.getString("idGames") + "_image_" + type;
                System.out.println(imgPath);

                String imgDir = new Utility().getTempDir() + "img\\";
                String fullPath = imgDir + imgPath;
                File f = new File(imgDir + imgPath);
                System.out.println(f.exists());
                Image im = new Image("file:///" + fullPath);
                System.out.println(imgDir + imgPath);
                if (f.exists()) {
                    
                    
                    
                    System.out.println("before");
                    backimiage.setImage(im);
                    System.out.println("background added");
                    minimage.setImage(im);
                    System.out.println("added from folder");
                } else {
                    System.out.println("in else condition");
                    backimiage = new ImageView(rs1.getString("games.background"));
                    minimage = new ImageView(rs1.getString("games.background"));
                    System.out.println("aftef imageView");
                    saveIMG.saveImage(rs1.getString("idGames"), rs1.getString("background"), imgPath);
                }

                Detailsname.setText(rs1.getString("games.name"));
                DetailsDescription.setText(rs1.getString("games.description"));
                dgp = rs1.getString("machine_has_games.path");
                idgame = rs1.getInt("games.idGames");
                if (b) {
                    detailsplay.setVisible(true);
                } else {
                    detailsplay.setVisible(false);
                }
            }
            Deatails.setVisible(true);
            System.out.println("details...");
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void homebuttonA(ActionEvent event) {
        mainPane.getChildren().clear();
        Deatails.setVisible(false);
        getAllGames();
        homebutton.setVisible(false);
    }

    @FXML
    private void searchkeytype(KeyEvent event) {
        List<String> list = new ArrayList<>();
        try {
            int i = 0;
            boolean b = false;
            if ((search.getText() != null || search.getText().equals(""))) {
                ResultSet rs = new CRUDOperationsImpl().search("distinct games.*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", " where games.name like '" + search.getText() + "%' and machine_has_games.machine_idMachine='" + new Utility().getMachineId() + "'");
                while (rs.next()) {
                    i++;
                    list.add(rs.getString("games.name"));
                    b = true;
                }
            }
            if (b) {
                ObservableList li = FXCollections.observableArrayList(list);
                serchresultlist.setItems(null);
                serchresultlist.setItems(li);
                serchresultlist.setVisible(true);
            } else {
                serchresultlist.setVisible(false);
            }
        } catch (Exception e) {
        }
    }

    @FXML
    private void serchresultlistMouseClicked(MouseEvent event) {
        serchgame((String) serchresultlist.getSelectionModel().getSelectedItem());
        homebutton.setVisible(true);
    }

    private void serchgame(String s) {
        try {
            serchresultlist.setItems(null);
            serchresultlist.setVisible(false);
            int[] i;
            int count = 0;
            mainPane.getChildren().clear();
            ResultSet rs1 = new CRUDOperationsImpl().search("distinct games.*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", " where games.name like '" + s + "%' && (sellplay='play' or sellplay='both') and machine_has_games.machine_idMachine='" + new Utility().getMachineId() + "'");
            while (rs1.next()) {
                count++;
            }
            if (count != 0) {
                i = new int[count];

                ResultSet rs = new CRUDOperationsImpl().search("distinct games.*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", " where games.name like '" + s + "%' && (sellplay='play' or sellplay='both') and machine_has_games.machine_idMachine='" + new Utility().getMachineId() + "' order by idGames DESC");
                int o = 0;
                while (rs.next()) {
                    i[o] = Integer.parseInt(rs.getString("games.idGames"));
                    o++;
                }
                getGames(i);
            } else {
                Label l = new Label("No results for " + search.getText());
                l.setLayoutX(500);
                l.setLayoutY(150);
                l.setMinSize(300, 50);
                l.setFont(Font.font(40));
                l.setStyle("-fx-text-fill: green;");
                mainPane.getChildren().addAll(l);
            }
            search.setText(null);
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    private void loadcombo() {
        try {
            List<String> list = new ArrayList<>();
            ResultSet rs = new CRUDOperationsImpl().search("*", "gametype", " ");
            while (rs.next()) {
                list.add(rs.getString("typeName"));
            }
            ObservableList<String> c = FXCollections.observableArrayList(list);
            Categorycom.setItems(null);
            Categorycom.setItems(c);
        } catch (ClassNotFoundException | SQLException | URISyntaxException | IOException ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void sortby(ActionEvent event) {
        Deatails.setVisible(false);
        String type = null;
        int o = 0;
        try {
            int[] i;
            int count = 0;
            mainPane.getChildren().clear();
            if (!(Categorycom.getSelectionModel().getSelectedItem() == null)) {
                ResultSet c;
                try {
                    c = new CRUDOperationsImpl().search("*", "gametype", "where typeName='" + Categorycom.getSelectionModel().getSelectedItem() + "'");
                    while (c.next()) {
                        type = c.getString("idGameType");
                    }
                } catch (SQLException | URISyntaxException | IOException ex) {
                    Log.error(ex);
                }
            } else {
                getAllGames();
            }
            if ((type != null) && !(type.equals(""))) {
                ResultSet rs1 = new CRUDOperationsImpl().search("games.*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", " where gametype_idGameType='" + type + "'  && (sellplay='play' or sellplay='both') and machine_has_games.machine_idMachine='" + new Utility().getMachineId() + "' ");
                while (rs1.next()) {
                    count++;
                }
                i = new int[count];
                ResultSet rs = new CRUDOperationsImpl().search("games.*", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", " where gametype_idGameType='" + type + "'  && (sellplay='play' or sellplay='both') and machine_has_games.machine_idMachine='" + new Utility().getMachineId() + "' order by idGames DESC");
                while (rs.next()) {
                    i[o] = Integer.parseInt(rs.getString("idGames"));
                    o++;
                }
                getGames(i);
            }
            loadcombo();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    @FXML
    private void detailsplaypOnAction(ActionEvent event) {
        try {
            //Runtime.getRuntime().exec(dgp);
            // playGame.call(,dgp);
            new playGame(idgame + "", dgp);
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.error(e);
        }
    }

    @FXML
    private void hamburgerAction(MouseEvent event) {
        transition.setRate(transition.getRate() * -1);
        transition.play();
        if (Drower.isShown()) {
            Drower.close();
            Drower.setVisible(false);
        } else {
            Drower.setVisible(true);
            Drower.open();
        }
    }

    @FXML
    private void MostplayedA(ActionEvent event) {
        Deatails.setVisible(false);
        String type = null;
        int o = 0;
        int[] i;
        int[] i1;
        int count = 0;
        mainPane.getChildren().clear();

        ResultSet c;
        try {
//                 SELECT count(*)as `count`, games_idGames
//FROM (SELECT games_idGames FROM gamecenter.play_log)as x
//GROUP BY games_idGames ORder by `count` desc LIMIT 5;

            ResultSet rs = new CRUDOperationsImpl().search(" count(*)as count, games_idGames", "(SELECT games_idGames FROM gamecenter.play_log)as x", "GROUP BY games_idGames ORder by count desc LIMIT 5");
            while (rs.next()) {
                ResultSet rr = new CRUDOperationsImpl().search("games.idGames", "games inner join machine_has_games on games.idGames=machine_has_games.games_idGames", "where  games.idGames='" + Integer.parseInt(rs.getString("games_idGames")) + "' && (sellplay='play' or sellplay='both')");
                if (rr.next()) {
                    count++;

                }
            }
            i = new int[count];
            ResultSet rs1 = new CRUDOperationsImpl().search(" count(*)as count, games_idGames", "(SELECT games_idGames FROM gamecenter.play_log)as x", "GROUP BY games_idGames ORder by count desc LIMIT 5");
            while (rs1.next()) {

                ResultSet rr = new CRUDOperationsImpl().search("idGames", "games", "where  idGames='" + Integer.parseInt(rs1.getString("games_idGames")) + "' && (sellplay='play' or sellplay='both')");
                if (rr.next()) {

                    i[o] = Integer.parseInt(rr.getString("idGames"));
                    o++;
                }
            }

            getGames(i);

        } catch (Exception ex) {
            Log.error(ex);
        }
    }

    private void myProgress(ActionEvent event) {
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/memProgress/memProgress.fxml"))));
            stage.setTitle("my progress");
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();
        } catch (Exception ex) {
            Log.error(ex);
        }
    }
}
