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
public class Kondoria extends Enemigo {
    
    public Kondoria(int x, int y) {
        velocidad = SPEED_SLOWEST;
        smart = SMART_HIGH;
        wallpass = true;
        puntaje = 1000;
        identificacion = "K";
        inicializar(Imagenes.KONDORIA, new Point(x, y), null);
    }
    
}