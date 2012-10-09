/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author hp
 */
public class Ladrillo extends Sprite {
    int time=6,i,tipo;
    Timer timer;
    boolean Especial=false;
    public LadrilloEspecial ladrilloespecial=null;
    
    public Ladrillo(int x1,int y1,int tipo1) {
        super(new Animation(Imagenes.LADRILLO,0,7));
        Speed=0;
        this.x=x1;
        this.y=y1;
        this.tipo=tipo1;
        if(tipo!=-1)
            Especial=true;
        
        timer=new Timer(55,new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
               update();
               time--;
               
                   
               if(time==0){
                   timer.stop();
                   animation=null;
                   JPanelJuego.getMapa().setObjetoMapa(JPanelJuego.getPosicionY(getCenterY()), JPanelJuego.getPosicionX(getCenterX()), "V");
                 
                   if(Especial){
                       ladrilloespecial=new LadrilloEspecial(x,y,tipo,i);
                   }
                   else{
                      // JPanelJuego.getLadrillos().set(i,null);
                      //JPanelJuego.getLadrillos().remove(i);
                   }
                    
               }
            }            
        });
        
    }
    public void start(int a){
            this.i=a;
            timer.start();
        }
    public Timer getTimer() {
        return timer;
    }
    public LadrilloEspecial getLadrilloEspecial(){
        return ladrilloespecial;
    }
    
   
}
