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
public class Doll extends Enemigo {
    
    public Doll(int x, int y) {
        super(Imagenes.DOLL, x, y, null);
        velocidad = SPEED_MID;
        smart = SMART_LOW;
        wallpass = false;
        puntaje = 400;
        id = "D";
        inicializar();
    }
    
}