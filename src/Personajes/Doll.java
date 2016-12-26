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
public class Doll extends Enemigo {
    
    public Doll(int x, int y) {
        super(Imagenes.DOLL, x, y, new GamePad());
        velocidad = SPEED_SLOW;
        smart = SMART_LOW;
        wallpass = false;
        puntaje = 400;
        id = "D";
        inicializar();
    }
    
}