/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.game;

import java.awt.*;

/**
 * @author AlfonsoAndrés
 */
public abstract class Screen {

    public abstract void restart();

    public abstract void draw(final java.awt.Graphics2D g);

    public abstract void update(final long elapsedTime, Game game);

    public abstract void setSIZE(final Dimension d);

}
