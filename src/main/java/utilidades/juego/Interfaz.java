/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades.juego;

import gui.JPanelContenedor;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.text.DecimalFormat;

/**
 *
 * @author AlfonsoAndrés
 */
public abstract class Interfaz {

    private static final GraphicsDevice gd;
    private static int accelMemory;
    private static int counter;
    private static final DecimalFormat df;

    static {
        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();
        df = new DecimalFormat("0.0");
        accelMemory = gd.getAvailableAcceleratedMemory();
        System.out.println("Initial Acc. Mem.: "
                + df.format(((double) accelMemory) / (1024 * 1024)) + " MB");
    }

    public enum Escenas {
        ESCENA_MENU,
        ESCENA_STAGE,
        ESCENA_JUEGO,
        ESCENA_BONUS,
        ESCENA_GAME_OVER,
        ESCENA_EDITOR
    }
    
    public JPanelContenedor jPanelContenedor;
  
    public Interfaz(JPanelContenedor jPanelContenedor) {
        this.jPanelContenedor = jPanelContenedor;
    }
    
    public abstract void reiniciar();
    public abstract void pintar(final java.awt.Graphics2D g);
    public abstract void actualizar(final long tiempoEnMilisegundos);
    public abstract void setSIZE(final Dimension d);

    protected final void reportAcelMemory() {
        // report any change in the amount of accelerated memory
        var mem = gd.getAvailableAcceleratedMemory();   // in bytes
        var memChange = mem - accelMemory;

        if (memChange != 0)
            System.out.println(counter + ". Acc. Mem: "
                    + df.format(((double) accelMemory) / (1024 * 1024)) + " MB; Change: "
                    + df.format(((double) memChange) / 1024) + " K");
        accelMemory = mem;
    }

}
