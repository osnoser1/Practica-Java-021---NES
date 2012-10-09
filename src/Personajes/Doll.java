/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import GUI.JPanelJuego;
import java.awt.Graphics;
import java.awt.Graphics2D;
/**
 *
 * @author hp
 */
public class Doll extends Enemigo{

    public Doll(int x,int y){
         super(new Animation(Imagenes.DOLL_1,0,3),new Animation(Imagenes.DOLL_1,3,3),new Animation(Imagenes.DOLL_1,3,3),new Animation(Imagenes.DOLL_1,0,3),new Animation(Imagenes.DOLL_1.get(Imagenes.DOLL_1.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));

        //this.Sprite=Imagenes.DOLL_1;
        this.Speed=this.SPEED_MID;
        this.Smart=this.SMART_LOW;
        this.Wallpass=false;
        this.Point=400;
        this.x=x;
        this.y=y;
        this.Identificacion="D";
        this.Inteligencia=new Smart(this);
    }


}
