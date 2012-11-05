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
public class Minvo extends Enemigo {

    public Minvo(int x, int y) {
        velocidad = SPEED_FAST;
        smart = SMART_MID;
        puntaje = 800;
        wallpass = false;
        identificacion = "M";
        inteligencia = new Smart(this);
        inicializar(Imagenes.MINVO, new Point(x, y), null);
    }
    
}