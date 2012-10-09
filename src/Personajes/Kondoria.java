/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import java.awt.Graphics;

/**
 *
 * @author hp
 */
public class Kondoria extends Enemigo {
  
    public Kondoria(int x,int y){
        super(new Animation(Imagenes.KONDORIA_1,0,3),new Animation(Imagenes.KONDORIA_1,3,3),new Animation(Imagenes.KONDORIA_1,3,3),new Animation(Imagenes.KONDORIA_1,0,3),new Animation(Imagenes.KONDORIA_1.get(Imagenes.KONDORIA_1.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));
       // this.Sprite=Imagenes.KONDORIA_1;
        this.Speed=this.SPEED_SLOWEST;
        this.Smart=this.SMART_HIGH;
        this.Wallpass=true;
        this.Point=1000;
        this.x=x;
        this.y=y;
        this.Identificacion="K";
        this.Inteligencia=new Smart(this);
    }
 

}
