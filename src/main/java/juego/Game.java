/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import motor.core.java.controllers.CJFramePrincipal;
import gui.JPanelContenedor;
import java.awt.Graphics2D;
import motor.core.GameCore;

/**
 *
 * @author AlfonsoAndrés
 */
public class Game extends GameCore {

    public static void main(String[] args) {
//        final String os = System.getProperty("os.name");
//        if (os.contains("Windows"))
//            System.setProperty("sun.java2d.d3d", "True");
//        else
//        System.setProperty("sun.java2d.trace", "timestamp,log,count");
        System.setProperty("sun.java2d.trace", "count");
        System.setProperty("sun.java2d.opengl", "True");
        System.setProperty("sun.java2d.noddraw", "true");
        new Game().run();
    }

    private JPanelContenedor jpc;

    @Override
    public void init() {
        super.init();
        screen.getScreenWindow().addKeyListener(CJFramePrincipal.Teclado.getInstance());
        jpc = JPanelContenedor.getInstance();
    }

    @Override
    public void update(long tiempoTranscurrido) {
        jpc.actualizar(tiempoTranscurrido);
    }

    @Override
    public void draw(Graphics2D g) {
        jpc.paint(g);
    }

}
