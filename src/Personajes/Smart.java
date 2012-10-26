/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import GUI.JPanelJuego;
import java.awt.event.ActionEvent;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author hp
 */

public class Smart {
    
    private Enemigo personaje;
    private Timer timer;
    private Random random;
    
    public Smart(Enemigo personaje){
        this.personaje=personaje;
        random=new Random();
        DeterminarInteligencia();
    }

    private void DeterminarInteligencia() {
        if(personaje.Smart==personaje.SMART_LOW){
            timer=new Timer(50,new AbstractAction(){
            int a=1,time=0;
                @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                if(time%50==0){
                    if(ReconocimientoX())
                        a=getRandom().nextInt(2);
                    if(ReconocimientoY())
                        a=getRandom().nextInt(2);
                }
                if(a==0)
                    personaje.MovimientoIzquierda();

                else if(a==1)
                    personaje.MovimientoDerecha();
                
                else if(a==2)
                    personaje.MovimientoArriba();

                else if(a==3)
                    personaje.MovimientoAbajo();
                if(JPanelJuego.getInstance().primerJugador()!=null&&JPanelJuego.getPosicionX(JPanelJuego.getInstance().primerJugador().getCenterX())==JPanelJuego.getPosicionX(personaje.getCenterX())&&JPanelJuego.getPosicionY(JPanelJuego.getInstance().primerJugador().getCenterY())==JPanelJuego.getPosicionY(personaje.getCenterY())&&!JPanelJuego.getInstance().primerJugador().getMYSTERY()){
                    timer.stop();
                    JPanelJuego.getInstance().primerJugador().Muerte(null);
                }
                
             //   JPanelJuego.getMapa().setObjetoMapa(JPanelJuego.getPosicionY(personaje.getCenterY()),JPanelJuego.getPosicionX(personaje.getCenterX()), personaje.Identificacion);
                    
            }            
        });
        
        }
        else if(personaje.Smart==personaje.SMART_MID){
            timer=new Timer(50,new AbstractAction(){
            int a=1,b=1,time=0;
                @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                if(time%50==0){
                    if(ReconocimientoX())
                        a=getRandom().nextInt(2);
                    if(ReconocimientoY())
                        b=getRandom().nextInt(2);
                }
                if(a==0)
                    personaje.MovimientoIzquierda();

                else if(a==1)
                    personaje.MovimientoDerecha();
                
                if(b==0)
                    personaje.MovimientoArriba();

                else if(b==1)
                    personaje.MovimientoAbajo();
                if(JPanelJuego.getPosicionX(JPanelJuego.getInstance().primerJugador().getCenterX())==JPanelJuego.getPosicionX(personaje.getCenterX())&&JPanelJuego.getPosicionY(JPanelJuego.getInstance().primerJugador().getCenterY())==JPanelJuego.getPosicionY(personaje.getCenterY())&&!JPanelJuego.getInstance().primerJugador().getMYSTERY()){
                   timer.stop();
                   JPanelJuego.getInstance().primerJugador().Muerte(null);
                }
                
             //   JPanelJuego.getMapa().setObjetoMapa(JPanelJuego.getPosicionY(personaje.getCenterY()),JPanelJuego.getPosicionX(personaje.getCenterX()), personaje.Identificacion);
                    
            }            
        });
      
        }
        else if(personaje.Smart==personaje.SMART_HIGH){
                        timer=new Timer(50,new AbstractAction(){
            int a=1,b=1,time=0;
                            @Override
            public void actionPerformed(ActionEvent e) {
                time++;
                if(time%50==0){
                    if(ReconocimientoX())
                        a=getRandom().nextInt(2);
                    if(ReconocimientoY())
                        b=getRandom().nextInt(2);
                }
                if(a==0)
                    personaje.MovimientoIzquierda();

                else if(a==1)
                    personaje.MovimientoDerecha();
                
                if(b==0)
                    personaje.MovimientoArriba();

                else if(b==1)
                    personaje.MovimientoAbajo();

                if(JPanelJuego.getPosicionX(JPanelJuego.getInstance().primerJugador().getCenterX())==JPanelJuego.getPosicionX(personaje.getCenterX())&&JPanelJuego.getPosicionY(JPanelJuego.getInstance().primerJugador().getCenterY())==JPanelJuego.getPosicionY(personaje.getCenterY())&&!JPanelJuego.getInstance().primerJugador().getMYSTERY()){
                    timer.stop();
                    JPanelJuego.getInstance().primerJugador().Muerte(null);
                }
             //   JPanelJuego.getMapa().setObjetoMapa(JPanelJuego.getPosicionY(personaje.getCenterY()),JPanelJuego.getPosicionX(personaje.getCenterX()), personaje.Identificacion);
                    
            }            
        });
         
        }
//        timer.start();   
    }

    public Timer getTimer() {
        return timer;
    }
    
    public void detenerInteligencia(){
        timer.stop();
    }
    
    public void iniciarInteligencia(){
        timer.start();
    }
    
    public boolean ReconocimientoX(){
        return (personaje.ChoqueDerecha("V", 1)||personaje.ChoqueIzquierda("V", 1));
    }
    public boolean ReconocimientoY(){
        return (personaje.ChoqueArriba("V", 1)||personaje.ChoqueAbajo("V", 1));
    }

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
