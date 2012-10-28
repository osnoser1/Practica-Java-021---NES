/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author hp
 */
public class Minvo extends Enemigo{

    public Minvo(int x,int y){
        super();
        this.Speed=this.SPEED_FAST;
        this.Smart=this.SMART_MID;
        this.Point=800;
        this.Wallpass=false;
        this.x=x;
        this.y=y;
        this.identificacion="M";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.MINVO, new Point(x, y), null);
    }
}
