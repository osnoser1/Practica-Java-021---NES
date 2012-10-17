/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import Dependencias.Mapa;
import GUI.JPanelJuego;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author hp
 */
public class Ladrillo extends Sprite {
    int time=6,tipo;
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
                   Mapa.getInstance().setObjetoMapa("V", JPanelJuego.getPosicionY(getCenterY()), JPanelJuego.getPosicionX(getCenterX()));
                 
                   if(Especial){
                       ladrilloespecial=new LadrilloEspecial(x,y,tipo);
                   }
                   else{
                      // JPanelJuego.getLadrillos().set(i,null);
                      //JPanelJuego.getLadrillos().remove(i);
                   }
                    
               }
            }            
        });
        
    }
    public void start(){
            timer.start();
        }
    public Timer getTimer() {
        return timer;
    }
    public LadrilloEspecial getLadrilloEspecial(){
        return ladrilloespecial;
    }
    
   
}
