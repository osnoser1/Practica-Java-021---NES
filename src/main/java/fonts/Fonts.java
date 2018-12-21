/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fonts;

import java.awt.*;
import java.io.IOException;

/**
 * @author Alfonso Andrés
 */
public class Fonts {

    private static Font calibri;
    private static Font joystixMonospacce;
    private static Fonts instance;

    static {
        try {
            calibri = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream("fonts/calibri.ttf"));
            joystixMonospacce = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream("fonts/joystix_monospace.otf"));
        } catch (FontFormatException | IOException ex) {
            System.out.println("Error loading fonts.");
        }
    }

    private Fonts() {

    }

    public static Fonts getInstance() {
        return instance == null ? instance = new Fonts() : instance;
    }

    public Font getCalibri(float size) {
        return calibri != null ? calibri.deriveFont(Font.PLAIN, size) : null;
    }

    public Font getJoystixMonospacce(float size) {
        return joystixMonospacce != null ? joystixMonospacce.deriveFont(Font.CENTER_BASELINE, size) : null;
    }

}
