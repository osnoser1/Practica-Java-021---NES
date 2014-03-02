/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.utils;

import java.awt.Rectangle;

/**
 *
 * @author Alfonso Andrés
 */
public interface Image {

    public int getWidth();

    public int getHeight();

    public Graphics getGraphics();

    public Image getSubimage(int x, int y, int w, int h);

    public Image getSubimage(Rectangle rectangle);
}
