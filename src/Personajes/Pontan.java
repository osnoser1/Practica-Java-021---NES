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
public class Pontan extends Enemigo {
    
    public Pontan(int x, int y) {
        velocidad = SPEED_FAST;
        smart = SMART_HIGH;
        puntaje = 8000;
        wallpass = true;
        identificacion = "p";
        inicializar(Imagenes.PONTAN, new Point(x, y), null);
    }
    
}