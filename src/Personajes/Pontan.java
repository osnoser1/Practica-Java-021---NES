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
public class Pontan extends Enemigo {
    
    public Pontan(int x, int y) {
        super(Imagenes.PONTAN, x, y, new GamePad());
        velocidad = SPEED_MID;
        smart = SMART_HIGH;
        puntaje = 8000;
        wallpass = true;
        id = "p";
        inicializar();
    }
    
}