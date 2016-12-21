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
public class Pass extends Enemigo {
    
    public Pass(int x, int y) {
        super(Imagenes.PASS, x, y, null);
        velocidad = SPEED_FAST;
        smart = SMART_HIGH;
        puntaje = 4000;
        wallpass = false;
        id = "P";
        inteligencia = new Inteligencia(this);
        inicializar();
    }
    
}