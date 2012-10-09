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
public class Oneal extends Enemigo{

    public Oneal(int x,int y){
        super(new Animation(Imagenes.ONEAL_1,0,3),new Animation(Imagenes.ONEAL_1,3,3),new Animation(Imagenes.ONEAL_1,3,3),new Animation(Imagenes.ONEAL_1,0,3),new Animation(Imagenes.ONEAL_1.get(Imagenes.ONEAL_1.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));
        
       // this.Sprite=Imagenes.ONEAL_1;
        this.Speed=this.SPEED_MID;
        this.Smart=this.SMART_MID;
        this.Point=200;
        this.Wallpass=false;
        this.x=x;
        this.y=y;
        this.Identificacion="O";
        this.Inteligencia=new Smart(this);
    }
}
