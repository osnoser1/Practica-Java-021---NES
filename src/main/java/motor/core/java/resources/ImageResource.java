/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.java.resources;

import java.awt.Image;
import lenguaje.utils.ManejadorDeArchivos;
import motor.core.Resource;

/**
 *
 * @author AlfonsoAndrés
 */
public class ImageResource implements Resource<Image> {

    public static final int VOLATILE = 0, BUFFERED = 2;

    @Override
    public Image load(String s, int type) {
        final var mda = ManejadorDeArchivos.getInstance();
        return type == VOLATILE ? mda.loadVolatileImageJAR(s) : mda.loadBufferedImageJAR(s);
    }

}
