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
    private long tiempoTranscurrido;
    private boolean estaActivo;
    private boolean pausa;

    public HiloPrincipal(JPanelJuego jPanelJuego, short FPS) {
        hilo = new Thread(this);
        this.jPanelJuego = jPanelJuego;
        this.FPS = FPS;
        tiempoEnMilisegundos = 1000 / FPS;
    }
    
    @Override
    public void run() {
//        long tiempoActual = System.currentTimeMillis();
        while(estaActivo){
            if(pausa){
                Metodos.sleep(500);
                continue;
            }
            Metodos.sleep(tiempoEnMilisegundos);
//            tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
//            if(tiempoTranscurrido > tiempoEnMilisegundos){
//                tiempoActual = System.currentTimeMillis();
                jPanelJuego.actualizar(tiempoEnMilisegundos);
                colisiones();
                jPanelJuego.limpiar();
                pintar();
//                tiempoTranscurrido = 0;
//            }
        }
    }
    
    public void start(){
        estaActivo = true;
        hilo.start();
    }

    public void stop(){
        estaActivo = false;
    }

    private void colisiones() {
        
    }
    
    private void pintar() {
        jPanelJuego.repaint();
    }

    public void setFPS(short FPS) {
        this.FPS = FPS;
        tiempoEnMilisegundos = 1000 / FPS;
    }

    public short getFPS() {
        return FPS;
    }
    
    public void pausar() {
        if(!estaActivo)
            throw new IllegalArgumentException();
        pausa = true;
    }
    
    public void reanudar() {
        if(!estaActivo)
            throw new IllegalArgumentException();
        pausa = false;
    }
}
