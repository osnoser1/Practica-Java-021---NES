/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Fuentes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

/**
 *
 * @author Alfonso Andrés
 */
public class Fuentes { 
    
    public Fuentes(){}
    
    public Font getCalibri(float size){
        Font calibri=null;
        try {
            calibri=Font.createFont(Font.TRUETYPE_FONT,this.getClass().getResourceAsStream("/Fuentes/calibri.ttf"));
        } catch (FontFormatException | IOException ex) {
            System.out.println("Error al cargar fuente");
        }
        return calibri.deriveFont(Font.PLAIN,size);
    }

    public Font getJoystixMonospacce(float size){
        Font joystixmonospace=null;
        try {
            joystixmonospace=Font.createFont(Font.TRUETYPE_FONT,this.getClass().getResourceAsStream("/Fuentes/joystix_monospace.otf"));
        } catch (FontFormatException | IOException ex) {
            System.out.println("Error al cargar fuente");
        }
        return joystixmonospace.deriveFont(Font.CENTER_BASELINE,size);
    }
    
}
