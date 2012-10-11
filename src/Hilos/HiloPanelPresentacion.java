/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Dependencias.Metodos;
import Dependencias.Sonido;
import GUI.JPanelAvisos;
import GUI.JPanelContenedor;
import GUI.JPanelGrafico;
import Sonidos.Sonidos;

/**
 *
 * @author Alfonso Andrés
 */
public class HiloPanelPresentacion implements Runnable{

    private Thread hilo;

    public HiloPanelPresentacion() {
        this.hilo = new Thread(this);
    }
    
    @Override
    public void run() {
        Sonido sonido = Sonidos.getInstance().getSonido(Sonidos.LEVEL_START);
        while(sonido.isPlaying()){
            Metodos.sleep(500);
            System.out.println("Sonido: " + sonido.getFramePosition() + " " + sonido.getFrameLength());
        }
        sonido.stop();
        JPanelAvisos.getInstance().setVisible(false);
        JPanelContenedor.getInstance().add(JPanelGrafico.getInstance());
        JPanelGrafico.getInstance().iniciarJuego();
    }

    public void start() {
        hilo.start();
    }
    
}
