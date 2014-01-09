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
public class Ovapi extends Enemigo {
    
    public Ovapi(int x, int y) {
        velocidad = SPEED_SLOW;
        smart = SMART_MID;
        puntaje = 2000;
        wallpass = true;
        identificacion = "o";
        inicializar(Imagenes.OVAPI, new Point(x, y), null);
    }
    
}
