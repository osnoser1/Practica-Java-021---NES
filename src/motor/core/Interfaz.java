/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import motor.utils.Graphics;
import core.java.JavaImage;
import java.awt.Dimension;

/**
 *
 * @author AlfonsoAndrés
 */
public abstract class Interfaz {

    public enum Escenas {

        ESCENA_MENU,
        ESCENA_STAGE,
        ESCENA_JUEGO,
        ESCENA_BONUS,
        ESCENA_GAME_OVER,
        ESCENA_EDITOR
    }
    public Universo universo;
    protected JavaImage imagen;
    protected Dimension size;

    public Interfaz(Universo universo) {
        this.universo = universo;
    }

    public JavaImage getImagen() {
        return imagen;
    }

    public abstract void reiniciar();

    public abstract void pintar(Graphics g);

    public abstract void actualizar(long tiempoEnMilisegundos);

    public abstract void setSize(java.awt.Dimension size);
}
