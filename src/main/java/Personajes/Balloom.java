/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import motor.core.input.GamePad;


/**
 *
 * @author hp
 */
public class Balloom extends Enemigo {
    
    public Balloom(int x, int y) {
        super(Imagenes.BALLOOM, x, y, new GamePad());
        velocidad = SPEED_SLOWEST;
        smart = SMART_LOW;
        puntaje = 100;
        wallpass = false;
        id = "b";
        inicializar();
    }
    
}