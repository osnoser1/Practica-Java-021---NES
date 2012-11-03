/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades.Juego;

/**
 *
 * @author AlfonsoAndrés
 */
public interface Interfaz {
    
    public enum Escenas {
        ESCENA_MENU,
        ESCENA_JUEGO,
        ESCENA_EDITOR
    }
  
    public abstract void reiniciar();
    public abstract void pintar(java.awt.Graphics g);
    public abstract void actualizar(long tiempoEnMilisegundos);
    
}
