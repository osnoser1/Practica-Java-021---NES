/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import GUI.JPanelJuego;
import Personajes.Bomberman;
import java.awt.Graphics2D;

/**
 *
 * @author hp
 */
public class HiloPrincipal implements Runnable {
    
    Thread hilo;
    JPanelJuego jpaneljuego;
    public HiloPrincipal(JPanelJuego jpaneljuego) {
        this.jpaneljuego=jpaneljuego;
        
        
        hilo=new Thread(this);
    }
    
    
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException ex) {}

            jpaneljuego.repaint();
        }
    }
    public void start(){
        hilo.start();
    }

    public void stop(){
        hilo.stop();
    }


   
    
}
