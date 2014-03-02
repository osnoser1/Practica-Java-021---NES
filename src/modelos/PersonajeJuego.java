package modelos;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.Timer;
import motor.core.Personaje;

public abstract class PersonajeJuego extends Personaje {

    protected int varx = 3, vary = 3, smart;
    protected final int SPEED_SLOWEST = 1, SPEED_SLOW = 2, SPEED_MID = 4, SPEED_FAST = 5, SMART_LOW = 1, SMART_MID = 2, SMART_HIGH = 3, SMART_IMPOSSIBLE = 4;
    protected Timer timer;
    protected boolean wallpass, dentroBomb;

    public void setWallpass(boolean Wallpass) {
        this.wallpass = Wallpass;
    }

    public boolean getWallpass() {
        return wallpass;
    }

    public boolean isDentroBomb() {
        return dentroBomb;
    }

    public void setDentroBomb(boolean dentroBomb) {
        this.dentroBomb = dentroBomb;
    }

}
