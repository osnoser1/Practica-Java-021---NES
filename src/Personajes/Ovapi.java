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
public class Ovapi extends Enemigo {
    
    public Ovapi(int x, int y) {
        super(Imagenes.OVAPI, x, y, new GamePad());
        velocidad = SPEED_SLOWEST;
        smart = SMART_MID;
        puntaje = 2000;
        wallpass = true;
        id = "o";
        inicializar();
    }
    
}
