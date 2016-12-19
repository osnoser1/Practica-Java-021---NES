/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Dependencias.Metodos;
import GUI.JFramePrincipal;
import GUI.JPanelContenedor;
import java.awt.image.BufferStrategy;

/**
 *
 * @author hp
 */
public class HiloPrincipal implements Runnable {
    
    private Thread hilo;
    private JPanelContenedor jPanelContenedor;
    private short FPS;
    private long tiempoEnMilisegundos;
    private long tiempoTranscurrido;
    private boolean estaActivo;
    private boolean pausa;
    private BufferStrategy buffer;
    private final JFramePrincipal jFramePrincipal;

    public HiloPrincipal(JFramePrincipal jFramePrincipal, short FPS) {
        hilo = new Thread(this);
        this.jPanelContenedor = JPanelContenedor.getInstance();
        this.jFramePrincipal = jFramePrincipal;
        this.FPS = FPS;
        buffer = jFramePrincipal.getBufferStrategy();
        tiempoEnMilisegundos = 1000 / FPS;
    }
    
    @Override
    public void run() {
        long tiempoActual = System.currentTimeMillis();
        while(estaActivo){
            if(pausa){
                Metodos.sleep(500);
                continue;
            }
            tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            if(tiempoTranscurrido > tiempoEnMilisegundos){
                tiempoActual = System.currentTimeMillis();
                jPanelContenedor.actualizar(tiempoEnMilisegundos);
                jFramePrincipal.paintComponents(buffer.getDrawGraphics());
                buffer.show();
//                jPanelContenedor.repaint();
                tiempoTranscurrido = 0;
            }else
                Metodos.sleep(tiempoEnMilisegundos - tiempoTranscurrido);
        }
    }
    
    public void start(){
        estaActivo = true;
        hilo.start();
    }

    public void stop(){
        estaActivo = false;
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
