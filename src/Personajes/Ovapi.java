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
public class Ovapi extends Enemigo{

    public Ovapi(int x,int y){
        super();
        this.velocidad=this.SPEED_SLOW;
        this.Smart=this.SMART_MID;
        this.Point=2000;
        this.Wallpass=true;
        this.x=x;
        this.y=y;
        this.identificacion="o";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.OVAPI, new Point(x, y), null);
    }
}
