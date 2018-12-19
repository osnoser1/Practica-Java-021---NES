/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman.core;

import gui.JPanelJuego;
import personajes.Bomberman;
import personajes.Enemigo;
import personajes.Ladrillo;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author AlfonsoAndrés
 */
public class ControlJuego {

    private final JPanelJuego jPanelJuego;
    private CopyOnWriteArrayList<Enemigo> enemigos;
    private CopyOnWriteArrayList<Ladrillo> ladrillos;
    private Bomberman[] jugadores;

    public ControlJuego(JPanelJuego jPanelJuego) {
        this.jPanelJuego = jPanelJuego;
    }

    public void actualizar() {
        
    }

    public void pintar(Graphics g2) {
        
    }
    
    public boolean hayColision(Rectangle rectangulo) {
        return false;
    }
    
    public void avisarColisiones() {
        
    }
    
}
