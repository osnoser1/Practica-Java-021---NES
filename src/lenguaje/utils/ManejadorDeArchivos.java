/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 *
 */
public class ManejadorDeArchivos {

    private static ManejadorDeArchivos instance;

    private ManejadorDeArchivos() { }
    
    public static ManejadorDeArchivos getInstance() {
        return instance == null ? (instance = new ManejadorDeArchivos()) : instance;
    }
    
    public boolean guardarArchivo(File file, String string) {
        try {
            try (PrintWriter printWriter1 = new PrintWriter(file)) {
                printWriter1.print(string);
                printWriter1.close();
            }
            return true;
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
    
    public String cargarArchivo(File file) {
        //Ejm: "C:/Windows/texto.txt"
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder entrada = new StringBuilder();
            String linea;
            while((linea = br.readLine()) != null) {
                entrada.append(linea);
                if(br.ready())
                    entrada.append(System.lineSeparator());
            }
            return entrada.toString();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } 
        return null;
    } 
    
    public String cargarArchivo(String pathname) {
        //Ejm: "C:/Windows/texto.txt"
        return cargarArchivo(new File(pathname));
    }
    
    public BufferedImage loadBufferedImage(File file) {
        if(file == null || !file.exists())
            return null;
        try {
            return ImageIO.read(file);
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
        return (Image)loadBufferedImage(file);
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
    
    public boolean saveImage(BufferedImage imagen, String formatName, File file) {
        try {
            ImageIO.write(imagen, formatName, file);
        }catch(IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean saveImage(BufferedImage imagen, File file) {
        return saveImage(imagen, file.getPath().substring(file.getPath().length() - 3, file.getPath().length()), file);
        
    }
    
    public boolean saveImage(BufferedImage imagen, String pathName) {
        return saveImage(imagen, new File(pathName));
    }
    
    public boolean saveImage(Image imagen, String formatName, File file) {
        return saveImage(ImageUtilities.convertImage(imagen), formatName, file);
    }
    
    public BufferedImage loadBufferedImageJAR(String pathName) {
        return ImageUtilities.convertImage(loadImageJAR(pathName));
    }
    
    public ImageIcon loadImageIconJAR(String pathName) {
        return new ImageIcon(getClass().getResource(pathName));
    }
    
    public Image loadImageJAR(String pathName) {
        return loadImageIconJAR(pathName).getImage();
    }
    
    public Icon loadIconJAR(String pathName) {
        return loadImageIconJAR(pathName);
    }
    
    public Clip cargarClipJAR(String pathName) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(pathName)));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) { 
            System.err.println(ex.getMessage());
        }
        return clip;
    }
    
    public Clip cargarClip(String pathName){
        //Ejm: "C:/Windows/Web/Wallpaper/Windows/img0.jpg"
        Clip clip = null;
        try {
            clip = cargarClip(new URL(pathName));
        } catch (MalformedURLException ex) {
            System.err.println(ex.getMessage());
        }
        return clip;
    }
    
    public Clip cargarClip(URL ruta){
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(ruta));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.err.println(ex.getMessage());
        }
        return clip;
    }

    public Document cargarArchivoXML(String pathName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(new File(pathName));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public Document cargarArchivoXMLJar(String pathName) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(getClass().getResource(pathName).toString());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
}