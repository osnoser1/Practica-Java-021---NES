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
public class Balloom extends Enemigo {
    
    public Balloom(int x, int y) {
        super(Imagenes.BALLOOM, x, y, null);
        velocidad = SPEED_SLOW;
        smart = SMART_LOW;
        puntaje = 100;
        wallpass = false;
        id = "b";
        inicializar();
    }
    
}