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
package connection;

import com.cyclotech.repository.DB;
import gamefx.Log;
import gamefx.Utility;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.SocketAddress;

/**
 *
 * @author Roshana Pitigala
 */
public class Speaker {

    /**
     * Send commands to a specific machine.
     *
     * @param ToIPAddress IP address of the machine.
     * @param Message Commands, separated by colon(;).
     * @return response.
     */
    public synchronized static String send(String ToIPAddress, String Message) {
        // <editor-fold defaultstate="collapsed" desc="send">
        if (ToIPAddress == null || ToIPAddress.equals("")) {
            return Command.ERROR;
        }
        String response = "";
        try {
            Socket client = new Socket(Inet4Address.getByName(ToIPAddress), Connection.PORT);
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            DataInputStream in = new DataInputStream(client.getInputStream());
            out.writeUTF(Message);
            out.flush();
            response = in.readUTF();
            out.close();
            client.close();
        } catch (Exception ex) {
            Log.error(ex);
        }

        return response;
        // </editor-fold>
    }

    /**
     * Send commands to a specific machine using machine id.
     *
     * @param toMachineId Id of the machine.
     * @param Message Commands, separated by colon(;).
     * @return response.
     */
    public static String sendToMachine(String toMachineId, String Message) {
        // <editor-fold defaultstate="collapsed" desc="sendToMachine">
        return send(new Utility().getIpAddress(toMachineId), Message);
        // </editor-fold>
    }

    /**
     * Send SMS via the server to the given phone number.
     *
     * @param Message Forgot Password Reset Code.
     * @param PhoneNo Phone number to send the code.
     * @return true if SMS sent successfully, false otherwise.
     */
    public static boolean sendSMS(final String Message, final String PhoneNo) {
        // <editor-fold defaultstate="collapsed" desc="sendForgotPasswordSMS">
        return send(DB.getIpAddress(),
                (Command.SMS + ";" + Message + ";" + PhoneNo)).equals(Command.OK);
        //</editor-fold>
    }

}
