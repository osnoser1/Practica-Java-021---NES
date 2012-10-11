/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Controladores.ControladorTecladoJPanelFinDeJuego;
import Dependencias.Metodos;
import Dependencias.Sonido;
import GUI.*;
import Sonidos.Sonidos;

/**
 *
 * @author Alfonso Andrés
 */
public class HiloTransicionPuerta implements Runnable{

    private Thread hilo;

    public HiloTransicionPuerta() {
        this.hilo = new Thread(this);
    }
    
    @Override
    public void run() {
//        JPanelGrafico jpanelgrafico1=this.jpanelcontenedor1.getJPanelGrafico();
        Sonido sonido = Sonidos.getInstance().getSonido(Sonidos.LEVEL_COMPLETE);
        while(sonido.isPlaying()){
            Metodos.sleep(500);
            System.out.println("Sonido: "+sonido.getFramePosition()+" "+sonido.getFrameLength());
        }
        JPanelJuego.Jugador=null;
        JPanelInformacion jPanelInformacion = JPanelInformacion.getInstance();
        JPanelContenedor.getInstance().remove(JPanelGrafico.getInstance());
        JPanelJuego.getInstance().reiniciarJPanelJuego();
        jPanelInformacion.detenerCuentaRegresiva();
        JPanelAvisos jPanelAvisos = JPanelAvisos.getInstance();
        jPanelAvisos.aumentarNivel();
        if(jPanelAvisos.finDeJuego()){
            jPanelInformacion.setVidasRestantes(2);
            JPanelFinDeJuego jPanelFinDeJuego = JPanelFinDeJuego.getInstance();
            jPanelFinDeJuego.iniciarJPanelFinDeJuego();
            JPanelContenedor.getInstance().add(jPanelFinDeJuego);
            jPanelFinDeJuego.requestFocus();
            jPanelFinDeJuego.addKeyListener(new ControladorTecladoJPanelFinDeJuego());
        }else{
            jPanelAvisos.iniciarJPanelStage(); 
            JPanelContenedor.getInstance().add(jPanelAvisos);
            new HiloPanelPresentacion().start();
        }
    }

    public void start() {
        hilo.start();
    }
    
}
