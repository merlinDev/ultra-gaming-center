package memData;


import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
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
public class server extends Thread{
    
    private ServerSocket ss;
    public static String fullPath;
    public static void main(String[] args) {
        new server(560).start();
      
    }
    
    public server(int port){
        try {
            
            ss=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }
    
   
    @Override
    synchronized public void run(){
        while (true) {            
            try {
                Socket c=ss.accept();
                ObjectInputStream oin=new ObjectInputStream(c.getInputStream());
                Object folderPath=oin.readObject();
                fullPath=folderPath.toString().replace("\\", "/");
                System.out.println("folder path is :"+fullPath);
                
                System.out.println("connected........");
               //String path="C:\\Users\\nipun\\Desktop\\IMG\\lol";
                //String fileName="disti did.png";
                
                System.out.println("full path : "+fullPath);
                saveFile(c,fullPath);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
          
    }

    private void saveFile(Socket c, String savePath) throws IOException {
        System.out.println("saveFile");
        DataInputStream dis=new DataInputStream(c.getInputStream());
        FileOutputStream fos=new FileOutputStream(savePath);
        byte[] buffer=new byte[4096];
        
        int fileSize=1670866;
        int read=0;
        int totalRead=0;
        int remain=fileSize;
        while ((read=dis.read(buffer,0,Math.min(buffer.length, remain))) > 0){            
            totalRead+=read;
            remain-=read;
            System.out.println("read" + totalRead+ " bytes. ");
            fos.write(buffer,0,read);
            
        }
        fos.close();
        dis.close();
        
        
        System.out.println("server done");
        
    }
    
}
