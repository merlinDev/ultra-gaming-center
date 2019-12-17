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
package gamefx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * This class communicates with the properties file were the game paths are
 * stored. It can add/retrieve/modify settings into the properties file.
 *
 * GamePathProp version 1.17.08.08
 *
 * @author Roshana Pitigala
 */
public class gamePathProp {

    private static Properties config = new Properties();
    private OutputStream output;
    private InputStream input;
    private static final File configFile = new File(new Utility().getTempDir() + "gamePath.properties");

    public gamePathProp() {
        checkFile();
    }

    /**
     * Checks the existence and Read/Write Permissions of 'gamePath.properties '
     * file and/or creates a new file.
     *
     * @return Returns true if file exist with required permissions.
     */
    private boolean checkFile() {
        // <editor-fold defaultstate="collapsed" desc="checkFile">
        try {
            if (!configFile.exists()) {
                new File(new Utility().getTempDir()).mkdirs();
                if (configFile.createNewFile()) {
                    config = new Properties();
                    output = new FileOutputStream(configFile);
                    config.store(output, null);
                    output.close();
                    return true;
                }
            } else {
                return (configFile.canRead() && configFile.canWrite());
            }
        } catch (Exception e) {
            Log.error(e);
        }
        return false;
        //</editor-fold>
    }

    private void LoadProperties() throws IOException {
        // <editor-fold defaultstate="collapsed" desc="LoadProperties">
        config = new Properties();
        input = new FileInputStream(configFile);
        config.load(input);
        input.close();
        //</editor-fold>
    }

    /**
     * Saves the path under the Game ID.
     *
     * @param gameID games.idGames value.
     * @param Path full path of the executable file (*.exe).
     * @return Returns true if Data saved.
     */
    public boolean setPath(String gameID, String Path) {
        // <editor-fold defaultstate="collapsed" desc="setPath">
        boolean status = false;
        if (!checkFile()) {
            return false;
        }
        try {
            LoadProperties();
            output = new FileOutputStream(configFile);
            config.setProperty(gameID, Path);
            config.store(output, null);
            status = true;
            output.close();
        } catch (Exception e) {
            Log.error(e);
        }
        return status;
        //</editor-fold>
    }

    /**
     * Gets the Path of the exe file of the relevant game.
     *
     * @param gameID
     * @return Returns the Path of the executable file.
     */
    public String getPath(String gameID) {
        // <editor-fold defaultstate="collapsed" desc="getPath">
        try {
            LoadProperties();
            return config.getProperty(gameID);
        } catch (NullPointerException e) {
        } catch (Exception e) {
            Log.error(e);
        }

        return null;
        //</editor-fold>
    }

    /**
     * Get installed games count.
     *
     * @return number of games.
     */
    public int getGamesCount() {
        // <editor-fold defaultstate="collapsed" desc="getGamesCount">
        try {
            LoadProperties();
            return config.size();
        } catch (NullPointerException e) {
        } catch (Exception e) {
            Log.error(e);
        }

        return 0;
        //</editor-fold>
    }

    /**
     * Get Ids of installed games.
     *
     * @return game ids in an array.
     */
    public Object[] getGameIds() {
        // <editor-fold defaultstate="collapsed" desc="getGameIds">
        try {
            LoadProperties();
            return config.keySet().toArray();
        } catch (NullPointerException e) {
        } catch (Exception e) {
            Log.error(e);
        }

        return null;
        //</editor-fold>
    }

    /**
     * Remove information of a relevant game.
     *
     * @param gameID Game ID.
     * @return true if success, false otherwise.
     */
    public boolean removeGame(String gameID) {
        // <editor-fold defaultstate="collapsed" desc="removeGame">
        try {
            LoadProperties();
            if (config.remove(gameID).toString().equals(gameID)) {
                return true;
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
            Log.error(e);
        }

        return false;
        //</editor-fold>
    }
}
