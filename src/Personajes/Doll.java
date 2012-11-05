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
public class Doll extends Enemigo {
    
    public Doll(int x, int y) {
        velocidad = SPEED_MID;
        smart = SMART_LOW;
        wallpass = false;
        puntaje = 400;
        identificacion = "D";
        inteligencia = new Smart(this);
        inicializar(Imagenes.DOLL, new Point(x, y), null);
    }
    
}