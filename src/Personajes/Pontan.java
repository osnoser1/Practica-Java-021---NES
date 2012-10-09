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
public class Pontan extends Enemigo{

    public Pontan(int x,int y){
        super(new Animation(Imagenes.PONTAN_0,0,3),new Animation(Imagenes.PONTAN_0,3,3),new Animation(Imagenes.PONTAN_0,3,3),new Animation(Imagenes.PONTAN_0,0,3),new Animation(Imagenes.PONTAN_0.get(Imagenes.PONTAN_0.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));
        
       //this.Sprite=Imagenes.PONTAN_0;
        this.Speed=this.SPEED_FAST;
        this.Smart=this.SMART_HIGH;
        this.Point=8000;
        this.Wallpass=true;
        this.x=x;
        this.y=y;
        this.Identificacion="p";
        this.Inteligencia=new Smart(this);
    }
}
