/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

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
    private long tiempoTranscurrido;

    public HiloPrincipal(JPanelJuego jPanelJuego, short FPS) {
        this.hilo = new Thread(this);
        this.jPanelJuego = jPanelJuego;
        this.FPS = FPS;
        this.tiempoEnMilisegundos = 1000 / FPS;
    }
    
    @Override
    public void run() {
        long tiempoActual = System.currentTimeMillis();
        while(true){
            tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            if(tiempoTranscurrido > tiempoEnMilisegundos){
                tiempoActual = System.currentTimeMillis();
                Actualizar(tiempoTranscurrido);
                Pintar();
                tiempoTranscurrido = 0;
            }
        }
    }
    public void start(){
        hilo.start();
    }

    public void stop(){
        hilo.stop();
    }

    private void Actualizar(long tiempoTranscurrido) {
        if(jPanelJuego.primerJugador() != null)
            jPanelJuego.primerJugador().actualizar(jPanelJuego, tiempoTranscurrido);
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
