/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import gui.JPanelJuego;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author hp
 */

public class Inteligencia {
    
    private final Personaje personaje;
    private Timer timer;
    private Random random;
    
    public Inteligencia(Personaje personaje){
        this.personaje=personaje;
        random=new Random();
        determinarInteligencia();
    }

    private void determinarInteligencia() {
        /*
         if(personaje.smart == personaje.SMART_LOW){
         timer=new Timer(50,new AbstractAction(){
         int a=1,time=0;
         @Override
         public void actionPerformed(ActionEvent e) {
         time++;
         if(time%50==0){
         if(reconocimientoX())
         a=getRandom().nextInt(2);
         if(reconocimientoY())
         a=getRandom().nextInt(2);
         }
         if(a==0) {
         personaje.setEstadoActual(Personaje.Estado.IZQUIERDA);
         personaje.movimientoIzquierda();
         } else if(a==1) {
         personaje.setEstadoActual(Personaje.Estado.DERECHA);
         personaje.movimientoDerecha();
         } else if(a==2) {
         personaje.setEstadoActual(Personaje.Estado.ARRIBA);
         personaje.movimientoArriba();
         } else if(a==3) {
         personaje.setEstadoActual(Personaje.Estado.ABAJO);
         personaje.movimientoAbajo();
         } if(personaje.getPosicionMapa().equals(JPanelJuego.getInstance(null).primerJugador().getPosicionMapa()) && !JPanelJuego.getInstance(null).primerJugador().getMYSTERY()){
         timer.stop();
         JPanelJuego.getInstance(null).borrarJugador();
         }
         }            
         });
        
         }
         else if(personaje.smart==personaje.SMART_MID){
         timer=new Timer(50,new AbstractAction(){
         int a=1,b=1,time=0;
         @Override
         public void actionPerformed(ActionEvent e) {
         time++;
         if(time%50==0){
         if(reconocimientoX())
         a=getRandom().nextInt(2);
         if(reconocimientoY())
         b=getRandom().nextInt(2);
         }
         if(a==0) {
         personaje.setEstadoActual(Personaje.Estado.IZQUIERDA);
         personaje.movimientoIzquierda();
         } else if(a==1) {
         personaje.setEstadoActual(Personaje.Estado.DERECHA);
         personaje.movimientoDerecha();
         }
         if(b==0) {
         personaje.setEstadoActual(Personaje.Estado.ARRIBA);
         personaje.movimientoArriba();
         }
         else if(b==1) {
         personaje.setEstadoActual(Personaje.Estado.ABAJO);
         personaje.movimientoAbajo();
         }
         if(personaje.getPosicionMapa().equals(JPanelJuego.getInstance(null).primerJugador().getPosicionMapa()) && !JPanelJuego.getInstance(null).primerJugador().getMYSTERY()){
         timer.stop();
         JPanelJuego.getInstance(null).borrarJugador();
         }
         }            
         });
      
         }
         else if(personaje.smart==personaje.SMART_HIGH){
         timer=new Timer(50,new AbstractAction(){
         int a=1,b=1,time=0;
         @Override
         public void actionPerformed(ActionEvent e) {
         time++;
         if(time%50==0){
         if(reconocimientoX())
         a=getRandom().nextInt(2);
         if(reconocimientoY())
         b=getRandom().nextInt(2);
         }
         if(a==0) {
         personaje.setEstadoActual(Personaje.Estado.IZQUIERDA);
         personaje.movimientoIzquierda();
         }
         else if(a==1) {
         personaje.setEstadoActual(Personaje.Estado.DERECHA);
         personaje.movimientoDerecha();
         }
         if(b==0) {
         personaje.setEstadoActual(Personaje.Estado.ARRIBA);
         personaje.movimientoArriba();
         }
         else if(b==1) {
         personaje.setEstadoActual(Personaje.Estado.ABAJO);
         personaje.movimientoAbajo();
         }
         if(personaje.getPosicionMapa().equals(JPanelJuego.getInstance(null).primerJugador().getPosicionMapa()) && !JPanelJuego.getInstance(null).primerJugador().getMYSTERY()){
         timer.stop();
         JPanelJuego.getInstance(null).borrarJugador();
         }
         }            
         });
         }
         */
    }

    public Timer getTimer() {
        return timer;
    }
    
    public void detenerInteligencia() {
        if (timer == null)
            return;
        timer.stop();
    }
    
    public void iniciar() {
        if (timer == null)
            return;
        timer.start();
    }
    
//    public boolean reconocimientoX(){
//        return (personaje.ChoqueDerecha("V", 1)||personaje.ChoqueIzquierda("V", 1));
//    }
//    public boolean reconocimientoY(){
//        return (personaje.ChoqueArriba("V", 1)||personaje.ChoqueAbajo("V", 1));
//    }

    /**
     * @return the random
     */
    public Random getRandom() {
        return random;
    }

    /**
     * @param random the random to set
     */
    public void setRandom(Random random) {
        this.random = random;
    }

}
