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

    private JPanelContenedor jpanelcontenedor1;
    private Thread hilo;

    public HiloTransicionPuerta(JPanelContenedor jpanelcontenedor1) {
        this.jpanelcontenedor1 = jpanelcontenedor1;
        this.hilo = new Thread(this);
    }
    
    @Override
    public void run() {
        JPanelGrafico jpanelgrafico1=this.jpanelcontenedor1.getJPanelGrafico();
        Sonido sonido = Sonidos.getInstance().getSonido(Sonidos.LEVEL_COMPLETE);
        while(sonido.isPlaying()){
            Metodos.sleep(500);
            System.out.println("Sonido: "+sonido.getFramePosition()+" "+sonido.getFrameLength());
        }
        JPanelJuego.Jugador=null;
        JPanelInformacion jPanelInformacion = jpanelgrafico1.getJPanelInformacion();
        this.jpanelcontenedor1.remove(JPanelJuego.getJPanelGrafico());
        jpanelgrafico1.getJPanelJuego().reiniciarJPanelJuego();
        jPanelInformacion.detenerCuentaRegresiva();
        JPanelAvisos jPanelAvisos = this.jpanelcontenedor1.getJPanelAvisos();
        jPanelAvisos.aumentarNivel();
        if(jPanelAvisos.finDeJuego()){
            jPanelInformacion.setVidasRestantes(2);
            JPanelFinDeJuego jPanelFinDeJuego = this.jpanelcontenedor1.getJPanelFinDeJuego();
            jPanelFinDeJuego.iniciarJPanelFinDeJuego();
            this.jpanelcontenedor1.add(jPanelFinDeJuego);
            jPanelFinDeJuego.requestFocus();
            jPanelFinDeJuego.addKeyListener(new ControladorTecladoJPanelFinDeJuego(JPanelJuego.getJPanelGrafico().getJPanelContenedor()));
        }else{
            jPanelAvisos.iniciarJPanelStage(); 
            this.jpanelcontenedor1.add(jPanelAvisos);
            new HiloPanelPresentacion(this.jpanelcontenedor1).start();
        }
    }

    public void start() {
        hilo.start();
    }
    
}
