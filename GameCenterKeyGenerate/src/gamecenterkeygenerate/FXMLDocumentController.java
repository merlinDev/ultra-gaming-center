/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamecenterkeygenerate;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import static java.lang.Math.random;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Isu
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private JFXButton generate;
    @FXML
    private ImageView close;
    @FXML
    private JFXTextField cusnic;
    @FXML
    private Label key;
    @FXML
    private JFXTextField pcno;
    @FXML
    private JFXButton edit;
    @FXML
    private JFXTextField search;
    @FXML
    private TableView<ActivationDetails> table;
    @FXML
    private TableColumn<?, ?> numpc;
    @FXML
    private TableColumn<?, ?> actkey;

    @FXML
    private TableColumn<?, ?> nic;

    private ObservableList<ActivationDetails> data;
    @FXML
    private AnchorPane reload;
    @FXML
    private JFXButton reloadbut;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
           loadDataToTable();

        //validate no of pc's
        pcno.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!"0123456789".contains(event.getCharacter())) {
                    event.consume();
                }

            }
        });

        //search by clicking enter button
        search.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        String find = search.getText();

                        URL url = new URL("https://gamekeys-33ec2.firebaseio.com/users.json");

                        //connection that make with the url and software
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        int responseCode = con.getResponseCode();
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inLine;
                        StringBuffer response = new StringBuffer();
                        while ((inLine = in.readLine()) != null) {
                            response.append(inLine);
                        }
                        in.close();
                        JSONArray j = new JSONArray(response.toString());

//remove ActivationDetails objects that have currenly in the list
                        data.clear();

                        //Iterate through the json array to get the json objects
                        for (int i = 0; i < j.length(); i++) {
                            JSONObject jo = j.getJSONObject(i);
                            String ni = jo.getString("nic");
                            int pc = jo.getInt("pcs");
                            String ke = jo.getString("key");

                            //Check user entered nic in search textfield with json array nic
                            if (ni.equals(find)) {
                                data.add(new ActivationDetails(ni, pc + "", ke));
                                nic.setCellValueFactory(new PropertyValueFactory<>("customernic"));
                                numpc.setCellValueFactory(new PropertyValueFactory<>("numofpc"));
                                actkey.setCellValueFactory(new PropertyValueFactory<>("activationkey"));

                                break;

                            }

                        }
                        table.setItems(null);
                        table.setItems(data);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void loadDataToTable() {

        try {
            data = FXCollections.observableArrayList();

            URL url = new URL("https://gamekeys-33ec2.firebaseio.com/users.json");
            HttpURLConnection con2 = (HttpURLConnection) url.openConnection();
            con2.setRequestMethod("GET");
            int rescode = con2.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(con2.getInputStream()));
            String inLine;
            StringBuffer resp = new StringBuffer();
            while ((inLine = br.readLine()) != null) {
                resp.append(inLine);
            }
            br.close();

            if (!resp.toString().equals("null")) {
                JSONArray j = new JSONArray(resp.toString());
                for (int i = 0; i < j.length(); i++) {
                    JSONObject jo = j.getJSONObject(i);
                    String ni = jo.getString("nic");
                    int pc = jo.getInt("pcs");
                    String ke = jo.getString("key");
                    data.add(new ActivationDetails(ni, pc + "", ke));
                    nic.setCellValueFactory(new PropertyValueFactory<>("customernic"));
                    numpc.setCellValueFactory(new PropertyValueFactory<>("numofpc"));
                    actkey.setCellValueFactory(new PropertyValueFactory<>("activationkey"));

                    table.setItems(null);
                    table.setItems(data);
                }
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //check internet connection
    private boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    @FXML
    private void generateKey(ActionEvent event) {
        boolean flag = true;
        try {
            if (netIsAvailable()) {
                String s1 = cusnic.getText();

                //generate a random no to create the activation key
                Random rand = new Random();
                int n = rand.nextInt(500) + 1;
                MD5Code m = new MD5Code();

                //concat nic to random no to generate activation key
                String s = m.MD5(n + s1);
                key.setText(s);
                int pcnum = Integer.parseInt(pcno.getText());

                //check the all fields are filled
                if (s1 == null || s1.equals("") || pcno.getText() == null || pcno.getText().equals("")) {
                    Alert al1 = new Alert(Alert.AlertType.ERROR, "All fields are required...!!!", ButtonType.OK);
                    al1.showAndWait();
                    if (al1.getResult() == ButtonType.OK) {
                        al1.close();
                    }
                } else {

                    //validate nic
                    if ((!cusnic.getText().trim().matches("[0-9]{9}([0-9]{3})?[x|X|v|V]"))) {
                        Alert al2 = new Alert(Alert.AlertType.ERROR, "Invalid NIC...!!!", ButtonType.OK);
                        al2.showAndWait();
                        if (al2.getResult() == ButtonType.OK) {
                            al2.close();
                        }
                    } else {
                        URL url = new URL("https://gamekeys-33ec2.firebaseio.com/users.json");

                        //Retrieve data from google cloud
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        int responseCode = con.getResponseCode();
                        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        JSONArray j = null;
                        if (response.toString().equals("null")) {
                            j = new JSONArray();
                        } else {
                            j = new JSONArray(response.toString());
                        }

                        for (int i = 0; i < j.length(); i++) {
                            JSONObject jo = j.getJSONObject(i);
                            String nic = jo.getString("nic");

                            //Validate cloud nic with user entered nic(can't there be two json objects with same nic)
                            if (nic.equals(s1)) {
                                flag = false;
                            }
                        }

                        //create new json object if there are no equal nic
                        if (flag) {
                            JSONObject json = new JSONObject();
                            JSONArray pcidArray = new JSONArray();
                            json.put("pcids", pcidArray);
                            json.put("nic", s1);
                            json.put("pcs", pcnum);
                            json.put("key", s);
                            j.put(json);
                            System.out.println(json);
                            //Data save with PUT method to google cloud
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("PUT");
                            connection.setDoOutput(true);
                            connection.setRequestProperty("Content-Type", "application/json");
                            connection.setRequestProperty("Accept", "application/json");
                            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                            osw.write(j.toString());
                            osw.flush();
                            osw.close();
                            System.err.println(connection.getResponseCode());

                            loadDataToTable();
                            cusnic.setText("");
                            pcno.setText("");

                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Synchronized with the cloud successfullly...!!!", ButtonType.OK);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.OK) {
                                alert.close();
                            }
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR, "This NIC has already added...!!!", ButtonType.OK);
                            a.showAndWait();
                            if (a.getResult() == ButtonType.OK) {
                                a.close();
                            }
                        }
                    }

                }

            } else {
                Alert al = new Alert(Alert.AlertType.ERROR, "Please check your internet connection...!!!", ButtonType.OK);
                al.showAndWait();
                if (al.getResult() == ButtonType.OK) {
                    al.close();
                }
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void pcnum(KeyEvent event) {

    }

    @FXML
    private void setSelectedData(MouseEvent event) {
        ActivationDetails ad = table.getSelectionModel().getSelectedItem();
        cusnic.setText(ad.getCustomernic());
        pcno.setText(ad.getNumofpc());
        key.setText(ad.getActivationkey());
    }

    @FXML
    private void editPcNum(ActionEvent event) {
        //update pc no
        try {
            //retrive database data
            URL url = new URL("https://gamekeys-33ec2.firebaseio.com/users.json");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONArray j = new JSONArray(response.toString());
            for (int i = 0; i < j.length(); i++) {
                JSONObject jo = j.getJSONObject(i);
                String ni = jo.getString("nic");
                int pc = jo.getInt("pcs");

                if (cusnic.getText().equals(ni)) {
                    System.out.println("awa");

                    //create new json object to set new updated pc number
                    JSONObject jo1 = new JSONObject();
                    jo1.put("nic", cusnic.getText());
                    jo1.put("pcs", pcno.getText());
                    jo1.put("key", key.getText());

                    //delete old json object from the array list(not in the firebase)
                    j.remove(i);

                    //put new json object to the json array
                    j.put(jo1);
                    System.out.println(j.toString());
                }

            }

            //Delete all data from firebase
            HttpURLConnection con1 = (HttpURLConnection) url.openConnection();
            con1.setRequestMethod("DELETE");
            con1.setDoOutput(true);
            con1.connect();
            System.out.println(con1.getResponseCode() + " " + con1.getResponseMessage());

            System.out.println(j.toString());

            //Save new json array
            HttpURLConnection con2 = (HttpURLConnection) url.openConnection();
            con2.setRequestMethod("PUT");
            con2.setDoOutput(true);
            con2.setRequestProperty("Content-Type", "application/json");
            con2.setRequestProperty("Accept", "application/json");
            OutputStreamWriter osw1 = new OutputStreamWriter(con2.getOutputStream());
            osw1.write(j.toString());
            osw1.flush();
            osw1.close();
            System.out.println(con2.getResponseCode() + " " + con2.getResponseMessage());

            loadDataToTable();
            cusnic.setText("");
            pcno.setText("");
            Alert al = new Alert(Alert.AlertType.INFORMATION, "Synchronized with the cloud successfullly...!!!", ButtonType.OK);
            al.showAndWait();
            if (al.getResult() == ButtonType.OK) {
                al.close();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void reloadToTable(ActionEvent event) {
        loadDataToTable();
        search.setText("");
        cusnic.setText("");
        pcno.setText("");
    }

}
