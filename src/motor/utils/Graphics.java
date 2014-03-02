/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Alfonso Andrés
 */
public interface Graphics {

    public void drawImage(final Image image, Rectangle destinationRect, java.awt.Color color);

    public void drawImage(final Image image, int x, int y);

    public void drawImage(final Image image, int x, int y, final Dimension dimension);

    public void drawImage(final Image image, int x, int y, int ancho, int alto);

    public void drawImage(final Image image, Point location, Dimension dimension);

    public void translate(int x, int y);

    public void setColor(Color c);

    public void fillRect(int x, int y, int ancho, int alto);

    public void setFont(Font joystixMonospacce);

    public void drawString(String string, int i, int i0);
}
