/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.java;

import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import motor.utils.Image;
import sun.net.www.content.audio.x_aiff;

/**
 *
 * @author Alfonso Andrés
 */
public class JavaGraphics implements motor.utils.Graphics {

    private Graphics g;
    private static final JavaGraphics javaGraphics = new JavaGraphics(null);

    private JavaGraphics(final Graphics g) {
        this.g = g;
    }

    public static JavaGraphics set(final Graphics g) {
        javaGraphics.g = g;
        return javaGraphics;
    }

    public static JavaGraphics get(final Graphics g) {
        return new JavaGraphics(g);
    }

    @Override
    public void drawImage(final Image image, final Rectangle destinationRect, final Color color) {
        g.drawImage(((JavaImage) image).bufferedImage(), destinationRect.x, destinationRect.y, destinationRect.width, destinationRect.height, null);
    }

    @Override
    public void drawImage(final Image image, int x, int y) {
        g.drawImage(((JavaImage) image).bufferedImage(), x, y, null);
    }

    @Override
    public void drawImage(final Image image, int x, int y, int ancho, int alto) {
        g.drawImage(((JavaImage) image).bufferedImage(), x, y, ancho, alto, null);
    }

    @Override
    public void drawImage(final Image image, Point location, Dimension dimension) {
        drawImage(image, location.x, location.y, dimension.width, dimension.height);
    }

    @Override
    public void drawImage(Image image, int x, int y, Dimension dimension) {
        drawImage(image, x, y, dimension.width, dimension.height);
    }

    @Override
    public void translate(int x, int y) {
        g.getColor();
    }

    @Override
    public void setColor(Color c) {
        g.setColor(c);
    }

    @Override
    public void fillRect(int x, int y, int ancho, int alto) {
        g.fillRect(x, y, ancho, alto);
    }

    @Override
    public void setFont(Font font) {
        g.setFont(font);
    }

    @Override
    public void drawString(String string, int x, int y) {
        g.drawString(string, x, y);
    }
}
