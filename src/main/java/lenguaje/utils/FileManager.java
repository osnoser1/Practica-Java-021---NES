/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 *
 */
public class FileManager {

    private static FileManager instance;

    private FileManager() { }
    
    public static FileManager getInstance() {
        return instance == null ? (instance = new FileManager()) : instance;
    }
    
    public boolean saveFile(File file, String string) {
        try {
            try (var printWriter1 = new PrintWriter(file)) {
                printWriter1.print(string);
            }
            return true;
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
    public String loadFile(File file) {
        //Ejm: "C:/Windows/text.txt"
        try {
            var br = new BufferedReader(new FileReader(file));
            var in = new StringBuilder();
            String linea;
            while((linea = br.readLine()) != null) {
                in.append(linea);
                if(br.ready())
                    in.append(System.lineSeparator());
            }
            return in.toString();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } 
        return null;
    } 
    
    public String loadFile(String pathname) {
        //Ejm: "C:/Windows/text.txt"
        return loadFile(new File(pathname));
    }
    
    public BufferedImage loadBufferedImage(File file) {
        if(file == null || !file.exists())
            return null;
        try {
            return ImageUtilities.createCompatibleImage(ImageIO.read(file));
        } catch(IOException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public BufferedImage loadBufferedImage(String pathName) {
        //Ejm: "C:/Windows/Web/Wallpaper/Windows/img0.jpg"
        return loadBufferedImage(new File(pathName));
    }
    
    public Image loadImage(File file) {
        return loadBufferedImage(file);
    }
    
    public Image loadImage(String pathName) {
        return loadImage(new File(pathName));
    }
    
    public Icon loadIcon(File file) {
        return loadImage(file) == null ? null : new ImageIcon(loadImage(file));
    }
    
    public Icon loadIcon(String pathName) {
        return loadIcon(new File(pathName));
    }
    
    public boolean saveImage(BufferedImage image, String formatName, File file) {
        try {
            ImageIO.write(image, formatName, file);
        }catch(IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean saveImage(BufferedImage image, File file) {
        return saveImage(image, file.getPath().substring(file.getPath().length() - 3), file);
        
    }
    
    public boolean saveImage(BufferedImage image, String pathName) {
        return saveImage(image, new File(pathName));
    }
    
    public boolean saveImage(Image image, String formatName, File file) {
        return saveImage(ImageUtilities.convertImage(image), formatName, file);
    }
    
    public BufferedImage loadBufferedImageJAR(String pathName) {
        return ImageUtilities.createCompatibleImage(loadImageJAR(pathName));
    }

    public VolatileImage loadVolatileImageJAR(String pathName) {
        return ImageUtilities.createCompatibleVolatileImage(loadImageJAR(pathName));
    }

    public ImageIcon loadImageIconJAR(String pathName) {
        return new ImageIcon(getClass().getClassLoader().getResource(pathName));
    }
    
    public Image loadImageJAR(String pathName) {
        return loadImageIconJAR(pathName).getImage();
    }
    
    public Icon loadIconJAR(String pathName) {
        return loadImageIconJAR(pathName);
    }

    public Clip loadClipJar(String pathName) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource(pathName)));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) { 
            System.err.println(ex.getMessage());
        }
        return clip;
    }
    
    public Clip loadClip(String pathName){
        //Ejm: "C:/Windows/Web/Wallpaper/Windows/img0.jpg"
        Clip clip = null;
        try {
            clip = loadClip(new URL(pathName));
        } catch (MalformedURLException ex) {
            System.err.println(ex.getMessage());
        }
        return clip;
    }
    
    public Clip loadClip(URL path){
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(path));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println(ex.getMessage());
        }
        return clip;
    }

    public Document loadXml(String pathName) {
        var dbf = DocumentBuilderFactory.newInstance();
        try {
            var db = dbf.newDocumentBuilder();
            return db.parse(new File(pathName));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public Document loadXmlJar(String pathName) {
        var dbf = DocumentBuilderFactory.newInstance();
        try {
            var db = dbf.newDocumentBuilder();
            return db.parse(getClass().getClassLoader().getResource(pathName).toString());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
}