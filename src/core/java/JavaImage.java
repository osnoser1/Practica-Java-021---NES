/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core.java;

import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import motor.utils.Image;

/**
 *
 * @author Alfonso Andrés
 */
public class JavaImage implements Image {

    private final BufferedImage bi;
    private final JavaGraphics jg;

    private JavaImage(BufferedImage bi) {
        this.bi = bi;
        jg = JavaGraphics.get(bi.getGraphics());
    }
    
    public JavaImage(int width, int height, int imageType) {
        bi = new BufferedImage(width, height, imageType);
        jg = JavaGraphics.get(bi.getGraphics());
    }
    
    public static JavaImage get(BufferedImage bi) {
        return new JavaImage(bi);
    }
    
    @Override
    public JavaImage getSubimage(int x, int y, int w, int h) {
        BufferedImage bi1 = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        bi1.createGraphics().drawRenderedImage(bi, AffineTransform.getTranslateInstance(-x, -y));
        return JavaImage.get(bi1);
    }

    @Override
    public JavaImage getSubimage(Rectangle rectangle) {
        return getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public int getWidth() {
        return bi.getWidth();
    }

    @Override
    public int getHeight() {
        return bi.getHeight();
    }

    @Override
    public JavaGraphics getGraphics() {
        return jg;
    }

    public BufferedImage bufferedImage() {
        return bi;
    }
    
}
