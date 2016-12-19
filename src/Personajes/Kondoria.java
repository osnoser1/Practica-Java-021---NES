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
public class Kondoria extends Enemigo {
    
    public Kondoria(int x, int y) {
        super(Imagenes.KONDORIA, x, y, null);
        velocidad = SPEED_SLOWEST;
        smart = SMART_HIGH;
        wallpass = true;
        puntaje = 1000;
        id = "K";
        inicializar();
    }
    
}