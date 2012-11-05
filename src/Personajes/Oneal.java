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
public class Oneal extends Enemigo {
    
    public Oneal(int x, int y) {
        velocidad = SPEED_MID;
        smart = SMART_MID;
        puntaje = 200;
        wallpass = false;
        identificacion = "O";
        inteligencia = new Smart(this);
        inicializar(Imagenes.ONEAL, new Point(x, y), null);
    }
    
}
