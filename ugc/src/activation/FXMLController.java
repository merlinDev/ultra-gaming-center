/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package activation;

import com.cyclotech.repository.CRUDOperations;
import com.cyclotech.repository.CRUDOperationsImpl;
import com.jfoenix.controls.JFXTextField;
import gamefx.Log;
import gamefx.Utility;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author Isu
 */
public class FXMLController implements Initializable {

    @FXML
    private JFXTextField actkey;
    private static final File file = new File(new Utility().getTempDir() + "Activation.properties");

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void close(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void activate(ActionEvent event) {
        try {
            String key = actkey.getText();

            //Send a GET request to retrive data from firebase
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
            System.out.println(j);
            boolean outerflag = false;
            outer:
            for (int i = 0; i < j.length(); i++) {
                JSONObject jo = j.getJSONObject(i);
                String ni = jo.getString("nic");
                int pc = jo.getInt("pcs");
                String ke = jo.getString("key");

                //check json array exists
                JSONArray jarray = null;
                if (jo.has("pcids")) {
                    jarray = jo.getJSONArray("pcids");
                } else {
                    jarray = new JSONArray();
                    jo.put("pcids", jarray);
                }

                //Check the user entered key with firebase key
                if (ke.equals(key)) {
                    outerflag = false;
                    System.out.println("firebase key ekt text field eke key eka samana una..");
                    CRUDOperations crud = new CRUDOperationsImpl();
                    FileOutputStream fileout = null;
                    try {
                        ResultSet rs = crud.search("*", "activation", "");

                        //Select the last value(row) of the database activation table
                        rs.last();
                        //System.out.println("last row eka gaththa");
                        int rowNo = rs.getRow();
                        System.out.println("Row no:" + rowNo);

                        //When the row no equals to 0, save the data  to activation table
                        if (rowNo == 0) {
                            System.out.println("row count eka 0ta samana una");
                            crud.save("activation(nic, activation.key, noofpcs, activation.status)", "'" + ni + "', '" + ke + "', '1', 'active'");

                            Properties properties = new Properties();
                            properties.setProperty("Nic", ni);
                            properties.setProperty("No of pcs", pc + "");
                            properties.setProperty("Key", ke);
                            properties.setProperty("mac", getSerialNumber());
                            fileout = new FileOutputStream(file);
                            properties.store(fileout, "Activation Details");
                            fileout.close();

                            //catch the returened serial no
                            String serialNo = getSerialNumber();

                            //put that serial no to the json object that inside the json array
                            JSONObject id = new JSONObject();
                            id.put("no", serialNo);

                            //save the jason array to the firebase(So we can know the activated no of pcs)
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

                            Alert a1 = new Alert(Alert.AlertType.INFORMATION, "Activation successfull...!", ButtonType.OK);
                            a1.showAndWait();
                            if (a1.getResult() == ButtonType.OK) {
                                a1.close();
                                gamefx.GameCenter.changeStage("accounts/login.fxml", true, true);
                            }

                            //When the row count not equals to 0
                        } else //Check the database key with the user entered key
                         if (rs.getString("key").equals(key)) {

                                boolean flag = true;

                                //get the json objects in the json array wich inside the json array
                                for (int k = 0; k < jarray.length(); k++) {

                                    JSONObject jobj = jarray.getJSONObject(k);
                                    String sn = jobj.getString("no");

                                    //check the pc serial no with the serial nos in json array
                                    if (sn.trim().equals(getSerialNumber().trim())) {

                                        Properties properties = new Properties();
                                        properties.setProperty("Nic", ni);
                                        properties.setProperty("No of pcs", pc + "");
                                        properties.setProperty("Key", ke);
                                        properties.setProperty("mac", getSerialNumber());
                                        fileout = new FileOutputStream(file);
                                        properties.store(fileout, "Activation Details");
                                        fileout.close();

                                        flag = false;
                                        Alert a1 = new Alert(Alert.AlertType.INFORMATION, "Activation successfull...!", ButtonType.OK);
                                        a1.showAndWait();
                                        if (a1.getResult() == ButtonType.OK) {
                                            a1.close();
                                            gamefx.GameCenter.changeStage("accounts/login.fxml", true, true);
                                            break outer;
                                        }
                                    }
                                }

                                if (flag) {
                                    //Check the number of pcs in the database with firebase pc numbers
                                    if (rs.getInt("noofpcs") >= pc) {
                                        System.out.println("table eke pc no eka firebase eke pc no ekt wada wadi ho samana una");
                                        Alert a = new Alert(Alert.AlertType.ERROR, "You have exceeded your pc limit, you entered...!", ButtonType.OK);
                                        a.showAndWait();
                                        if (a.getResult() == ButtonType.OK) {
                                            a.close();
                                        }
                                    } else {
                                        //System.out.println("table eke pc no eka firebase eke pc no ekt wada wadi ho samana une na");
                                        crud.update("activation", "noofpcs='" + (rs.getInt("noofpcs") + 1) + "'", "'" + rs.getString("idactivation") + "'");

                                        //When activated it creates a folder in documents folder
                                        Properties properties = new Properties();
                                        properties.setProperty("Nic", ni);
                                        properties.setProperty("No of pcs", pc + "");
                                        properties.setProperty("Key", ke);
                                        properties.setProperty("mac", getSerialNumber());
                                        fileout = new FileOutputStream(file);
                                        properties.store(fileout, "Activation Details");
                                        fileout.close();

                                        String serialNo = getSerialNumber();

                                        //put that serial no to the json object that inside the json array
                                        JSONObject id = new JSONObject();
                                        id.put("no", serialNo);
                                        jarray.put(id);
                                        jo.put("pcids", jarray);

                                        //save the jason array to the firebase(So we can know the activated no of pcs)
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

                                        Alert a2 = new Alert(Alert.AlertType.INFORMATION, "Activation successfull...!", ButtonType.OK);
                                        a2.showAndWait();
                                        if (a2.getResult() == ButtonType.OK) {
                                            a2.close();
                                            gamefx.GameCenter.changeStage("accounts/login.fxml", true, true);
                                            break;
                                        }
                                    }
                                }

                            } else {

                                Alert a3 = new Alert(Alert.AlertType.ERROR, "Please check the activation key again...!", ButtonType.OK);
                                a3.showAndWait();
                                if (a3.getResult() == ButtonType.OK) {
                                    a3.close();
                                    break;
                                }
                            }

                    } catch (Exception ex) {
                        Log.error(ex);
                    }
                }
            }
            if (outerflag) {

                Alert a4 = new Alert(Alert.AlertType.ERROR, "Please check the activation key again...!", ButtonType.OK);
                a4.showAndWait();
                if (a4.getResult() == ButtonType.OK) {
                    a4.close();
                }
            }
        } catch (MalformedURLException ex) {
            Log.error(ex);
        } catch (IOException ex) {
            Log.error(ex);
        }
    }

    //take  serial number
    private String getSerialNumber() throws IOException, InterruptedException {

        return GetNetworkAddress.GetAddress("mac");
    }

}
