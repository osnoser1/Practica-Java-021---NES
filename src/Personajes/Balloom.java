/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author hp
 */
public class Balloom extends Enemigo {
    
    public Balloom(int x,int y){
        super(new Animation(Imagenes.BALLOOM_1,0,3),new Animation(Imagenes.BALLOOM_1,3,3),new Animation(Imagenes.BALLOOM_1,3,3),new Animation(Imagenes.BALLOOM_1,0,3),new Animation(Imagenes.BALLOOM_1.get(Imagenes.BALLOOM_1.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));
        //this.Sprite=Imagenes.BALLOOM_1;
        this.Speed=this.SPEED_SLOW;
        this.Smart=this.SMART_LOW;
        this.Point=100;
        this.Wallpass=false;
        this.x=x;
        this.y=y;
        this.Identificacion="b";
        this.Inteligencia=new Smart(this);
    }

}

