/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamer;

import accounts.user;
import com.cyclotech.repository.CRUDOperationsImpl;
import gamefx.Log;
import gamefx.MessageBox;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author nipun
 */
public class playGame {

    static Thread t;
    static long startTime;
    static long endtTime;
    static long fullTime;
    static Process run;

    static String idGame;
    static String playSession;
    static String gamePath;

    /**
     *
     * @param idGame
     * @param gamePath
     */
    public playGame(String idGame, String gamePath) {
        playSession = user.getUserPlaySessionId();
        playGame.idGame = idGame;
        playGame.gamePath = gamePath;
        if (run != null) {
            MessageBox.showErrorMessage("A game is already running!", "please close other games before starting a new game");
        } else {
            try {
                run = Runtime.getRuntime().exec(gamePath);
                checkExist(run);

            } catch (IOException ex) {
                MessageBox.showErrorMessage("wrong path", "there is something wrong with the path of this game");
                Log.error(ex);
            } catch (NullPointerException e) {
                MessageBox.showErrorMessage("path not selected", "please contact the administrator for assign correct path to this game.");
                Log.error(e);
            }

        }

    }

    private static long checkExist(Process run) {
        if (run.isAlive()) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (run.isAlive()) {

                    }
                    System.out.println("********** game closed **********");
                    endtTime = System.nanoTime();
                    long playTimeNano = endtTime - startTime;
                    long playTime = TimeUnit.SECONDS.convert(playTimeNano, TimeUnit.NANOSECONDS);
                    System.out.println("played time :" + playTime + "sec");
                    t = null;
                    fullTime = playTime;
                    end();
                    addToTable();

                }

                private void addToTable() {
                    try {
                        System.out.println("adding query");
                        if (user.getUserTypeId() == user.MEMBER) {

                            System.out.println("playlog_member");
                            String logID = new CRUDOperationsImpl().getLastId("idPlay_log", "play_log");
                            new CRUDOperationsImpl().update("play_log", "duration='" + fullTime + "'", "idPlay_log='" + logID + "'");
                        } else if (user.getUserTypeId() == user.GUEST) {
                            System.out.println("playlog_guest");
                            new CRUDOperationsImpl().save("playLog_guest(games_idGames,guest_idguest,dateTime,duration)", "'" + idGame + "','" + user.getIdUser() + "',NOW(),'" + fullTime + "'");
                        }
                        System.out.println("done");
                    } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
                        Log.error(ex);
                    }
                }
            });
            t.start();
            System.out.println("********** game started **********");

            try {
                new CRUDOperationsImpl().save("play_log(games_idGames,play_session_play_session_id,dateTime)", "'" + idGame + "','" + playSession + "',NOW()");
            } catch (SQLException | ClassNotFoundException | URISyntaxException | IOException ex) {
                Log.error(ex);
            }

            startTime = System.nanoTime();
        }

        return fullTime;
    }

    private static void end() {
        run = null;
        System.out.println("process ended");
    }
}
