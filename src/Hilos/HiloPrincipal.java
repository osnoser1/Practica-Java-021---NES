/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Dependencias.Metodos;
import GUI.JPanelJuego;

/**
 *
 * @author hp
 */
public class HiloPrincipal implements Runnable {
    
    private Thread hilo;
    private JPanelJuego jPanelJuego;
    private short FPS;
    private long tiempoEnMilisegundos;

    public HiloPrincipal(JPanelJuego jPanelJuego, short FPS) {
        this.hilo = new Thread(this);
        this.jPanelJuego = jPanelJuego;
        this.FPS = FPS;
        this.tiempoEnMilisegundos = 1000 / 30;
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
        jPanelJuego.getJugador().actualizar(jPanelJuego);
    }

    private void Pintar() {
        jPanelJuego.repaint();
    }

    public void setFPS(short FPS) {
        this.FPS = FPS;
        this.tiempoEnMilisegundos = 1000 / FPS;
    }

    public short getFPS() {
        return FPS;
    }
    
}
