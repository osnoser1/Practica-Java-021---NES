/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils.game;

import gui.JPanelContainer;

import java.awt.*;
import java.text.DecimalFormat;

/**
 * @author AlfonsoAndrés
 */
public abstract class Screen {

    private static final GraphicsDevice gd;
    private static final DecimalFormat df;
    private static int acceleratedMemory;

    static {
        var ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();
        df = new DecimalFormat("0.0");
        acceleratedMemory = gd.getAvailableAcceleratedMemory();
        System.out.println("Initial Acc. Mem.: "
                + df.format(((double) acceleratedMemory) / (1024 * 1024)) + " MB");
    }

    public JPanelContainer jPanelContainer;

    public Screen(JPanelContainer jPanelContainer) {
        this.jPanelContainer = jPanelContainer;
    }

    public abstract void restart();

    public abstract void draw(final java.awt.Graphics2D g);

    public abstract void update(final long elapsedTime);

    public abstract void setSIZE(final Dimension d);

    public enum Scene {
        MENU,
        STAGE,
        GAME,
        BONUS,
        GAME_OVER,
        EDITOR
    }

}
