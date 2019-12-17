package memData;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nipun
 */
public class client {
    
    private static Socket socket;
        
    public client(String ip,int port,String file,String folder){
        try {
            socket=new Socket(ip,port);
            sendFile(file,folder);
            System.out.println("sending");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(String file, String folder) throws IOException {
        DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
        FileInputStream fis=new FileInputStream(file);
        byte[] buffer= new byte[4096];
        ObjectOutputStream out=new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(folder);
        out.flush();
        System.out.println("folder path flushed....");
        //out.close();
        while (fis.read(buffer) > 0) {            
            dos.write(buffer);

        }
        
        fis.close();
        dos.close();
        System.out.println("client done");
        System.out.println("full path is: "+server.fullPath);
    }
    
}
