/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bomberman.Core;

import gui.EscenaJuego;
import Personajes.Bomberman;
import Personajes.Enemigo;
import Personajes.Ladrillo;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author AlfonsoAndrés
 */
public class ControlJuego {

    private EscenaJuego jPanelJuego;
    private CopyOnWriteArrayList<Enemigo> enemigos;
    private CopyOnWriteArrayList<Ladrillo> ladrillos;
    private Bomberman[] jugadores;

    public ControlJuego(EscenaJuego jPanelJuego) {
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
