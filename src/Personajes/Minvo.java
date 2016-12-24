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
public class Minvo extends Enemigo {

    public Minvo(int x, int y) {
        super(Imagenes.MINVO, x, y, new GamePad());
        velocidad = SPEED_FAST;
        smart = SMART_MID;
        puntaje = 800;
        wallpass = false;
        id = "M";
        inicializar();
    }
    
}