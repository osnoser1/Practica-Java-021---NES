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
public class Ovapi extends Enemigo{

    public Ovapi(int x,int y){
        super(new Animation(Imagenes.OVAPI_1,0,3),new Animation(Imagenes.OVAPI_1,3,3),new Animation(Imagenes.OVAPI_1,3,3),new Animation(Imagenes.OVAPI_1,0,3),new Animation(Imagenes.OVAPI_1.get(Imagenes.OVAPI_1.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));
        
        //this.Sprite=Imagenes.OVAPI_1;
        this.Speed=this.SPEED_SLOW;
        this.Smart=this.SMART_MID;
        this.Point=2000;
        this.Wallpass=true;
        this.x=x;
        this.y=y;
        this.Identificacion="o";
        this.Inteligencia=new Smart(this);
    }
}
