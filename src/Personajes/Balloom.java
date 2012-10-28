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
import java.awt.Point;

/**
 *
 * @author hp
 */
public class Balloom extends Enemigo {
    
    public Balloom(int x,int y){
        super();
        this.Speed=this.SPEED_SLOW;
        this.Smart=this.SMART_LOW;
        this.Point=100;
        this.Wallpass=false;
        this.x=x;
        this.y=y;
        this.identificacion="b";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.BALLOOM, new Point(x, y), null);
    }

}

