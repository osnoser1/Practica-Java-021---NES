/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Dependencias.Metodos;
import GUI.JPanelJuego;
import Personajes.Bomberman;
import java.awt.Graphics2D;

/**
 *
 * @author hp
 */
public class HiloPrincipal implements Runnable {
    
    private Thread hilo;
    private JPanelJuego jpaneljuego;
    private short FPS;
    private long tiempoEnMilisegundos;

    public HiloPrincipal(Thread hilo, JPanelJuego jpaneljuego, short FPS) {
        this.hilo = hilo;
        this.jpaneljuego = jpaneljuego;
        this.FPS = FPS;
        this.tiempoEnMilisegundos = 1000 / FPS;
    }
    
    public HiloPrincipal(JPanelJuego jPanelJuego, short FPS) {
        this(new Thread(), jPanelJuego, FPS);
    }
    
    @Override
    public void run() {
        while(true){
            Metodos.sleep(tiempoEnMilisegundos);
            Actualizar();
            Pintar();
        }
    }
    public void start(){
        hilo.start();
    }

    public void stop(){
        hilo.stop();
    }

    private void Actualizar() {
        
    }

    private void Pintar() {
        jpaneljuego.repaint();
    }

    public void setFPS(short FPS) {
        this.FPS = FPS;
        this.tiempoEnMilisegundos = 1000 / FPS;
    }

    public short getFPS() {
        return FPS;
    }
    
}
