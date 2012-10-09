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
public class Pass extends Enemigo{

    public Pass(int x,int y){
        super(new Animation(Imagenes.PASS_1,0,3),new Animation(Imagenes.PASS_1,3,3),new Animation(Imagenes.PASS_1,3,3),new Animation(Imagenes.PASS_1,0,3),new Animation(Imagenes.PASS_1.get(Imagenes.PASS_1.size()-1),Imagenes.MUERTE_ENEMIGOS,0,5));
        
        //this.Sprite=Imagenes.PASS_1;
        this.Speed=this.SPEED_FAST;
        this.Smart=this.SMART_HIGH;
        this.Point=4000;
        this.Wallpass=false;
        this.x=x;
        this.y=y;
        this.Identificacion="P";
        this.Inteligencia=new Smart(this);
    }
}
