/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package motor.core.graphics;

import motor.core.ControlAnimacion;

/**
 * 
 * @author AlfonsoAndres
 */
public class AnimationWrapper {

    public final int fila;
    public final ControlAnimacion animacion;

    public AnimationWrapper(int fila, String frames, long tiempoFotograma) {
        this.fila = fila;
        this.animacion = new ControlAnimacion(frames, tiempoFotograma);
    }
    
}
