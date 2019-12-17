/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalsoftwareproject;

import gamefx.Utility;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author NiroIsu
 */
public class BackupHandler {

    public boolean backupDataWithDatabase(String dumpExePath, String host, String port, String user, String password, String database, String backupPath) {
        boolean status = false;
        try {
            Process p = null;

            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date();
            String filepath = "backup.sql";

            String batchCommand = "";
            System.out.println(dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"");
            if (password != "") {
                //Backup with database
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --password=" + password + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            } else {
                batchCommand = dumpExePath + " -h " + host + " --port " + port + " -u " + user + " --add-drop-database -B " + database + " -r \"" + backupPath + "" + filepath + "\"";
            }

            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(batchCommand);
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                status = true;
                System.out.println("Backup created successfully for with DB " + database + " in " + host + ":" + port);
            } else {
                status = false;
                System.out.println("Could not create the backup for with DB " + database + " in " + host + ":" + port);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public boolean restoreDatabase(String dbUserName, String dbPassword, String source) {
// String[] executeCmd = new String[]{"\"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe\""+ " -u" + dbUserName + " -p" + dbPassword + " < " + source};
// String[] executeCmd = new String[]{"\"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe\"", " -u " + dbUserName, " -p" + dbPassword,  " < " + source};
//        String executeCmd = "\"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe\"" + " -u " + dbUserName + " -p" + dbPassword + " < " + source;
        String[] executeCmd = new String[]{"\"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysql.exe\"", "-u" + dbUserName, "-p" + dbPassword, "-e", " source " + source};
//        for (int i = 0; i < executeCmd.length; i++) {
//            String string = executeCmd[i];
//            System.out.print(string);
//        }
        System.out.println(executeCmd);
        Process runtimeProcess;
        try {
            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(runtimeProcess.getInputStream()));
            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            int processComplete = runtimeProcess.waitFor();
            if (processComplete == 0) {
                System.out.println("Backup restored successfully with " + source);
                return true;
            } else {
                System.out.println("Could not restore the backup " + source);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;

    }

    public static void main(String[] args) {
        BackupHandler backupHandler = new BackupHandler();
//        backupHandler.backupDataWithDatabase("\"C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\mysqldump.exe\"", "localhost", "3306", "root", "1234", "formu", new Utility().getTempDir());
        backupHandler.restoreDatabase("root", "1234", new Utility().getTempDir() + "backup.sql");
    }
}
