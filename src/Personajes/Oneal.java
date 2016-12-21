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
public class Oneal extends Enemigo {
    
    public Oneal(int x, int y) {
        super(Imagenes.ONEAL, x, y, null);
        velocidad = SPEED_MID;
        smart = SMART_MID;
        puntaje = 200;
        wallpass = false;
        id = "O";
        inteligencia = new Inteligencia(this);
        inicializar();
    }
    
}
