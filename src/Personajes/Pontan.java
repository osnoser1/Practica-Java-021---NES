/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;


/**
 *
 * @author hp
 */
public class Pontan extends Enemigo {
    
    public Pontan(int x, int y) {
        super(Imagenes.PONTAN, x, y, null);
        velocidad = SPEED_FAST;
        smart = SMART_HIGH;
        puntaje = 8000;
        wallpass = true;
        id = "p";
        inicializar();
    }
    
}