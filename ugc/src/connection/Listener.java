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

import MachineDetails.ViewMachineController;
import accounts.user;
import gamefx.GameCenter;
import gamefx.Log;
import gamefx.Utility;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listener acts as a server waiting for commands to be sent.
 *
 * @author Roshana Pitigala
 */
public class Listener extends Thread {

    private final ServerSocket serverSocket;
    private Socket server;
    private DataInputStream in;
    private DataOutputStream out;
    private static Listener t;

    private Listener() throws Exception {
        // <editor-fold defaultstate="collapsed" desc="Listener">
        serverSocket = new ServerSocket(Connection.PORT);
        serverSocket.setSoTimeout(0);
        // </editor-fold>
    }

    @Override
    public void run() {
        // <editor-fold defaultstate="collapsed" desc="run">
        while (true) {
            try {
                server = serverSocket.accept();
                in = new DataInputStream(server.getInputStream());
                out = new DataOutputStream(server.getOutputStream());
                out.writeUTF(action(in.readUTF()));

                server.close();
            } catch (Exception e) {
                Log.error(e);
            }
        }
        // </editor-fold>
    }

    /**
     * Start Listener. Call this on system start.
     *
     */
    public static void initialize() {
        // <editor-fold defaultstate="collapsed" desc="initialize">
        try {
            t = new Listener();
            t.start();
        } catch (Exception e) {
            Log.error(e);
        }
        // </editor-fold>
    }

    /**
     * Stop Listener. Call this on system exit.
     *
     * @throws IOException
     */
    public static void terminate() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="terminate">
        t.server.close();
        t.stop();
        // </editor-fold>
    }

    private String action(final String command) {
        // <editor-fold defaultstate="collapsed" desc="action">
        if (command == null) {
            return Command.ERROR;
        }
        try {
            String[] com = command.split(";");
            switch (com[0]) {
                case Command.SMS:
                    return ((new Utility().sendSMS(com[2], com[1])) ? (Command.OK) : (Command.ERROR));
                case Command.LOGOUT:
                    Command.systemLogout(true);
                    break;
                case Command.SHUTDOWN:
                    Command.systemShutdown(true);
                    break;
                case Command.REQUEST_USER_SESSION_DATA:
                    return Command.encodeUserSessionData(user.getMachineId(),
                            user.getUserType(),
                            user.getIdUser(),
                            user.getLoggedInDateTime(),
                            user.getPackageTime(),
                            user.getFullDuration());
                case Command.PLAY_WARNING_SOUND:
                    user.playWarning();
                    break;
                case Command.USER_SESSION_DATA_LOGIN:
                    ViewMachineController x = GameCenter.getAdminHome();
                    if (x != null) {
                        x.addNewRecord(com[1], com[2], com[3], com[4], Long.parseLong(com[5]), Long.parseLong(com[6]));
                    }
                    break;
                case Command.USER_SESSION_DATA_LOGOUT:
                    ViewMachineController y = GameCenter.getAdminHome();
                    if (y != null) {
                        y.removeRecord(com[1]);
                    }
                    break;
                case Command.CMD:
                    return Command.cmd(com[1], ((com[2].equals("true")) ? (false) : (true)));
                default:
                    return Command.UNKNOWN_COMMAND;
            }
        } catch (Exception e) {
            Log.error(e);
            return Command.ERROR;
        }
        return Command.OK;
        // </editor-fold>
    }
}
