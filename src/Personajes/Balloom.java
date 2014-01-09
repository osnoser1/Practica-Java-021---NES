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
    
    public Balloom(int x, int y) {
        velocidad = SPEED_SLOW;
        smart = SMART_LOW;
        puntaje = 100;
        wallpass = false;
        identificacion = "b";
        inicializar(Imagenes.BALLOOM, new Point(x, y), null);
    }
    
}