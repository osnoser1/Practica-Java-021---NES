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
public class Oneal extends Enemigo {

    public Oneal(int x,int y){
        super();
        this.velocidad=this.SPEED_MID;
        this.Smart=this.SMART_MID;
        this.Point=200;
        this.Wallpass=false;
        this.x=x;
        this.y=y;
        this.identificacion="O";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.ONEAL, new Point(x, y), null);
    }
}
