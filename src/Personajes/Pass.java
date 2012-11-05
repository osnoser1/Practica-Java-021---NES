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
public class Pass extends Enemigo {
    
    public Pass(int x, int y) {
        velocidad = SPEED_FAST;
        smart = SMART_HIGH;
        puntaje = 4000;
        wallpass = false;
        identificacion = "P";
        inteligencia = new Smart(this);
        inicializar(Imagenes.PASS, new Point(x, y), null);
    }
    
}