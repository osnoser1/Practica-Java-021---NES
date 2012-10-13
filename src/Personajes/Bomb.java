/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Sonidos.Sonidos;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public class Bomb extends Sprite {
    int time=8;
    Timer timer;
    Fire fire=null;

    
    public Bomb(final int x,final int y) {
        super(new Animation(Imagenes.BOMB,0,3));
        //Sprite=Imagenes.BOMB;
        this.x=x;
        this.y=y;
        
        timer=new Timer(400,new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
               update();
               time--;
              
               if(time==0&&!JPanelJuego.getInstance().primerJugador().getDETONATOR())
                  detonar();
  
            }            
        });
        timer.start();
        
    }
    public Fire getFire() {
        return fire;
    }
    public void detonar(){
        timer.stop();
        animation=null;
        fire=new Fire(x,y,JPanelJuego.getInstance().primerJugador().getFLAMES(), this);
        Sonidos.getInstance().getNewSonido(Sonidos.EXPLOSION_1).play();
        
    }
}
