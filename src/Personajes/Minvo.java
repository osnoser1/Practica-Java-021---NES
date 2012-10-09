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
public class Minvo extends Enemigo{

    public Minvo(int x,int y){
        super(new Animation(Imagenes.MINVO_1,0,3),new Animation(Imagenes.MINVO_1,3,3),new Animation(Imagenes.MINVO_1,3,3),new Animation(Imagenes.MINVO_1,0,3),new Animation(Imagenes.MINVO_1.get(Imagenes.MINVO_1.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));
        
       // this.Sprite=Imagenes.MINVO_1;
        this.Speed=this.SPEED_FAST;
        this.Smart=this.SMART_MID;
        this.Point=800;
        this.Wallpass=false;
        this.x=x;
        this.y=y;
        this.Identificacion="M";
        this.Inteligencia=new Smart(this);
    }
}
