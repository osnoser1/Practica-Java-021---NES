/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package personajes;

import dependencias.Imagenes;
import motor.core.graphics.Imagen;
import motor.core.graphics.Sprite;

/**
 * 
 * @author AlfonsoAndres
 */
public class Aluminio extends Sprite {

    public Aluminio(int x, int y) {
        super(new Imagen(Imagenes.ACERO, 1, 1, 1), x, y);
    }

}
