/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamer;

import gamefx.Utility;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author nipun
 */
public class saveIMG {

    public static void saveImage(String id, String url, String imgPath) {
        try {
            System.out.println("saving img");
            try (InputStream in = new URL(url).openStream()) {

                String[] imgType = url.split("/.");

                String type = imgType[imgType.length - 1];
                // System.out.println("exception");
                String path = new Utility().getTempDir() + "img\\";
                File pathFile = new File(path);
                pathFile.mkdirs();
                Files.copy(in, Paths.get(path+imgPath));
                System.out.println("done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
