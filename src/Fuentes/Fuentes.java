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
    
    private Font calibri, joystixMonospacce;

    public Fuentes() {
        try {
            calibri = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/Fuentes/calibri.ttf"));
            joystixMonospacce = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/Fuentes/joystix_monospace.otf"));
        } catch(FontFormatException | IOException ex) {
            System.out.println("Error al cargar fuente");
        }
    }

    public Font getCalibri(float size) {
        return calibri != null ? calibri.deriveFont(Font.PLAIN, size) : null;
    }

    public Font getJoystixMonospacce(float size) {
        return joystixMonospacce != null ? joystixMonospacce.deriveFont(Font.CENTER_BASELINE, size) : null;
    }
    
}
