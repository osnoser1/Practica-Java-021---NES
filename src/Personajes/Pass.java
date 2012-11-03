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
public class Pass extends Enemigo{

    public Pass(int x,int y){
        super();
        this.velocidad=this.SPEED_FAST;
        this.Smart=this.SMART_HIGH;
        this.puntaje=4000;
        this.wallpass=false;
        this.x=x;
        this.y=y;
        this.identificacion="P";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.PASS, new Point(x, y), null);
    }
}
