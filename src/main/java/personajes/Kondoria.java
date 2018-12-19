/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personajes;

import dependencias.Imagenes;
import motor.core.input.GamePad;


/**
 *
 * @author hp
 */
public class Kondoria extends Enemigo {
    
    public Kondoria(int x, int y) {
        super(Imagenes.KONDORIA, x, y, new GamePad());
        velocidad = SPEED_SLOWEST;
        smart = SMART_HIGH;
        wallpass = true;
        puntaje = 1000;
        id = "K";
        inicializar();
    }
    
}