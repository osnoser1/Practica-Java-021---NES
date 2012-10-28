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
public class Kondoria extends Enemigo {
  
    public Kondoria(int x,int y){
        super();
        this.Speed=this.SPEED_SLOWEST;
        this.Smart=this.SMART_HIGH;
        this.Wallpass=true;
        this.Point=1000;
        this.x=x;
        this.y=y;
        this.identificacion="K";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.KONDORIA, new Point(x, y), null);
    }
 

}
