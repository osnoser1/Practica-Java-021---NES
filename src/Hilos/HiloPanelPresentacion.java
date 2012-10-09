/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Dependencias.Metodos;
import Dependencias.Sonido;
import GUI.JPanelContenedor;
import GUI.JPanelGrafico;
import Sonidos.Sonidos;

/**
 *
 * @author Alfonso Andrés
 */
public class HiloPanelPresentacion implements Runnable{

    private JPanelContenedor jpanelcontenedor1;
    private Thread hilo;

    public HiloPanelPresentacion(JPanelContenedor jpanelcontenedor1) {
        this.jpanelcontenedor1 = jpanelcontenedor1;
        this.hilo = new Thread(this);
    }
    
    @Override
    public void run() {
        JPanelGrafico jpanelgrafico1=this.jpanelcontenedor1.getJPanelGrafico();
        //jpanelgrafico1.generarMapa();
        Sonido sonido = Sonidos.getInstance().getSonido(Sonidos.LEVEL_START);
        while(sonido.isPlaying()){
            Metodos.sleep(500);
            System.out.println("Sonido: "+sonido.getFramePosition()+" "+sonido.getFrameLength());
        }
        sonido.stop();
        this.jpanelcontenedor1.getJPanelAvisos().setVisible(false);
        this.jpanelcontenedor1.add(jpanelgrafico1);
        jpanelgrafico1.iniciarJuego();
    }

    public void start() {
        hilo.start();
    }
    
}
