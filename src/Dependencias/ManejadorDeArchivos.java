/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author Alfonso Andrés
 */
public class ManejadorDeArchivos {

    private static ManejadorDeArchivos manejador;

    private ManejadorDeArchivos() {}
    
    public static ManejadorDeArchivos getInstance() {
        return manejador == null ? (manejador = new ManejadorDeArchivos()) : manejador;
    }
    
    public boolean guardarArchivo(File file, String string){
        try{
            try (PrintWriter printWriter1 = new PrintWriter(file)) {
                printWriter1.print(string);
                printWriter1.close();
            }
            return true;
        }catch(Exception e){}
        return false;
    }
    
    public String cargarArchivo(File file){
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            String string,string1="";
            while((string=br.readLine())!=null) {
                string1+=string+"\n";
            }
            return string1;
        } catch (IOException ex) {
            Logger.getLogger(ManejadorDeArchivos.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    } 
    
    public BufferedImage loadBufferedImage(File file){
        if(file == null || !file.exists())
            return null;
        try{
            return ImageIO.read(file);
        }catch(IOException e){
            return null;
        }
    }
    
    public BufferedImage loadBufferedImage(String pathName){
        //Ejm: "C:/Windows/Web/Wallpaper/Windows/img0.jpg"
        return loadBufferedImage(new File(pathName));
    }
    
    public Image loadImage(File file){
        return (Image)loadBufferedImage(file);
    }
    
    public Image loadImage(String pathName){
        return loadImage(new File(pathName));
    }
    
    public Icon loadIcon(File file){
        return (loadImage(file)==null)?null:new ImageIcon(loadImage(file));
    }
    
    public Icon loadIcon(String pathName){
        return loadIcon(new File(pathName));
    }
    
    public boolean saveImage(BufferedImage imagen,String formatName,File file){
        try{
            ImageIO.write(imagen,formatName,file);
        }catch(IOException e){
            return false;
        }
        return true;
    }
    
    public boolean saveImage(BufferedImage imagen,File file){
        String format=file.getPath().substring(file.getPath().length()-3,file.getPath().length());
        return saveImage(imagen,format,file);
        
    }
    
    public boolean saveImage(BufferedImage imagen,String pathName){
        return saveImage(imagen,new File(pathName));
    }
    
    public boolean saveImage(Image imagen,String formatName,File file){
        return saveImage(ImageUtilities.convertImage(imagen),formatName,file);
    }
    
    public BufferedImage loadBufferedImageJAR(String pathName){
        return ImageUtilities.convertImage(this.loadImageJAR(pathName));
    }
    
    public ImageIcon loadImageIconJAR(String pathName){
        return new ImageIcon(this.getClass().getResource(pathName));
    }
    
    public Image loadImageJAR(String pathName){
        return this.loadImageIconJAR(pathName).getImage();
    }
    
    public Icon loadIconJAR(String pathName){
        return this.loadImageIconJAR(pathName);
    }
    
    public Clip cargarClipJAR(String pathName){
        Clip clip=null;
        try {
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(this.getClass().getResource(pathName)));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) { 
            System.out.println("Error al leer Clip");
        }
        return clip;
    }
    
    public Clip cargarClip(String pathName){
        //Ejm: "C:/Windows/Web/Wallpaper/Windows/img0.jpg"
        Clip clip=null;
        try {
            clip=cargarClip(new URL(pathName));
        } catch (MalformedURLException ex) {}
        return clip;
    }
    
    public Clip cargarClip(URL ruta){
        Clip clip=null;
        try {
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(ruta));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) { }
        return clip;
    }
    
}