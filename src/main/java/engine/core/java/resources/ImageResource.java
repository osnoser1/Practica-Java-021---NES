/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.java.resources;

import engine.core.Resource;
import language.utils.FileManager;

import java.awt.*;

/**
 * @author AlfonsoAndrés
 */
public class ImageResource implements Resource<Image> {

    public static final int VOLATILE = 0, BUFFERED = 2;

    @Override
    public Image load(String s, int type) {
        final var mda = FileManager.getInstance();
        return type == VOLATILE ? mda.loadVolatileImageJAR(s) : mda.loadBufferedImageJAR(s);
    }

}
