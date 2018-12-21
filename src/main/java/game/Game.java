/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import engine.core.GameCore;
import engine.core.java.controllers.CJFramePrincipal;
import gui.JPanelContainer;

import java.awt.*;

/**
 * @author AlfonsoAndrés
 */
public class Game extends GameCore {

    private JPanelContainer jpc;

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

    @Override
    public void init() {
        super.init();
        screen.getScreenWindow().addKeyListener(CJFramePrincipal.Keyboard.getInstance());
        jpc = JPanelContainer.getInstance();
    }

    @Override
    public void update(long elapsedTime) {
        jpc.update(elapsedTime);
    }

    @Override
    public void draw(Graphics2D g) {
        jpc.paint(g);
    }

}
