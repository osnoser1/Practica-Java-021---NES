/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Dependencias.Metodos;
import Dependencias.Sonido;
import GUI.*;
import Sonidos.Sonidos;

/**
 *
 * @author Alfonso Andrés
 */
public class HiloPanelTransicionMuerte implements Runnable{

    private Thread hilo;

    public HiloPanelTransicionMuerte() {
        this.hilo = new Thread(this);
    }
    
    @Override
    public void run() {
        Sonido sonido = Sonidos.getInstance().getSonido(Sonidos.JUST_DIED);
        while(sonido.isPlaying()){
            Metodos.sleep(500);
            System.out.println("Sonido: "+sonido.getFramePosition()+" "+sonido.getFrameLength());
        }
        JPanelJuego.Jugador = null;
        JPanelInformacion jPanelInformacion = JPanelInformacion.getInstance();
        JPanelContenedor.getInstance().remove(JPanelGrafico.getInstance());
        JPanelJuego.getInstance().reiniciarJPanelJuego();
        jPanelInformacion.disminuirVidasRestantes();
        jPanelInformacion.detenerCuentaRegresiva();
        JPanelAvisos jPanelAvisos = JPanelAvisos.getInstance();
        if(jPanelInformacion.getVidasRestantes()<0){
            jPanelInformacion.setVidasRestantes(2);
            jPanelAvisos.iniciarJPanelGameOver();
            jPanelAvisos.setNivel(new Short(1+""));
            JPanelContenedor.getInstance().add(jPanelAvisos);
            jPanelAvisos.requestFocus();
            jPanelAvisos.addKeyListener(new Controladores.ControladorTecladoJPanelAvisos());
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
