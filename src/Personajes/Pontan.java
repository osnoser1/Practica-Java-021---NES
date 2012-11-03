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
public class Pontan extends Enemigo{

    public Pontan(int x,int y){
        super();
        this.velocidad=this.SPEED_FAST;
        this.Smart=this.SMART_HIGH;
        this.puntaje=8000;
        this.wallpass=true;
        this.x=x;
        this.y=y;
        this.identificacion="p";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.PONTAN, new Point(x, y), null);
    }
}
