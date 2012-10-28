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
public class Doll extends Enemigo{

    public Doll(int x,int y){
         super();
        this.Speed=this.SPEED_MID;
        this.Smart=this.SMART_LOW;
        this.Wallpass=false;
        this.Point=400;
        this.x=x;
        this.y=y;
        this.identificacion="D";
        this.inteligencia=new Smart(this);
        inicializar(Imagenes.DOLL, new Point(x, y), null);
    }


}
