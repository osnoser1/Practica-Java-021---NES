/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import java.awt.Point;

/**
 *
 * @author hp
 */
public class Balloom extends Enemigo {
    
    public Balloom(int x,int y) {
        this.velocidad=this.SPEED_SLOW;
        this.Smart=this.SMART_LOW;
        this.puntaje=100;
        this.wallpass=false;
        this.x=x;
        this.y=y;
        this.identificacion="b";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.BALLOOM, new Point(x, y), null);
    }

}

