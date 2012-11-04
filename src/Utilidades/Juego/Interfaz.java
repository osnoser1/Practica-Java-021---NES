/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades.Juego;

import GUI.JPanelContenedor;

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
    
    public JPanelContenedor jPanelContenedor;
  
    public Interfaz(JPanelContenedor jPanelContenedor) {
        this.jPanelContenedor = jPanelContenedor;
    }
    
    public abstract void reiniciar();
    public abstract void pintar(java.awt.Graphics g);
    public abstract void actualizar(long tiempoEnMilisegundos);
    
}
