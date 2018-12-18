/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Fuentes;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Alfonso Andrés
 */
public class Fuentes {
    
    private static Font calibri, joystixMonospacce;
    private static Fuentes instance;

    static {
        try {
            calibri = Font.createFont(Font.TRUETYPE_FONT, Fuentes.class.getClassLoader().getResourceAsStream("Fuentes/calibri.ttf"));
            joystixMonospacce = Font.createFont(Font.TRUETYPE_FONT, Fuentes.class.getClassLoader().getResourceAsStream("Fuentes/joystix_monospace.otf"));
        } catch (FontFormatException | IOException ex) {
            System.out.println("Error al cargar fuentes");
        }
    }

    private Fuentes() {

    }

    public static Fuentes getInstance() {
        return instance == null ? instance = new Fuentes() : instance;
    }

    public Font getCalibri(float size) {
        return calibri != null ? calibri.deriveFont(Font.PLAIN, size) : null;
    }

    public Font getJoystixMonospacce(float size) {
        return joystixMonospacce != null ? joystixMonospacce.deriveFont(Font.CENTER_BASELINE, size) : null;
    }
    
}
