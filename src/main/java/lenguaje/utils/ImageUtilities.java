package lenguaje.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class ImageUtilities {

    static public BufferedImage invertImage(BufferedImage image) {
        if (image == null)
            return null;
        Color colorPixel;
        for (int i = 0, rgb; i < image.getWidth(); i++)
            for (var j = 0; j < image.getHeight(); j++) {
                colorPixel = new Color(image.getRGB(i, j));
                rgb = new Color(255 - colorPixel.getRed(), 255 - colorPixel.getGreen(), 255 - colorPixel.getBlue()).getRGB();
                image.setRGB(i, j, rgb);
            }
        return image;
    }

    static public Image invertImage(Image image) {
        return invertImage(convertImage(image));
    }

    static public BufferedImage convertImage(Image image) {
        if (image == null)
            return null;
        var img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        img.createGraphics().drawImage(image, 0, 0, null);
        return img;
    }

    public static BufferedImage createCompatibleImage(final int width, final int height, final int transparency) {
        var gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return gc.createCompatibleImage(width, height, transparency);
    }

    public static BufferedImage createCompatibleImage(final int width, final int height) {
        return createCompatibleImage(width, height, Transparency.BITMASK);
    }

    public static BufferedImage createCompatibleImage(final BufferedImage image) {
        var gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if (image.getColorModel().equals(gc.getColorModel())) {
            System.out.println("Optimized");
            return image;
        }
        var newImage = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.BITMASK);
        var g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage createCompatibleImage(final Image image) {
        var gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        var newImage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.BITMASK);
        var g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        image.flush();
        return newImage;
    }

    public static VolatileImage createCompatibleVolatileImage(final int width, final int height, final int transparency) {
        var gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        var newImage = gc.createCompatibleVolatileImage(width, height, transparency);
        if (transparency == Transparency.TRANSLUCENT) {
            var g = newImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.dispose();
        }
        System.out.println(newImage.getCapabilities().isAccelerated());
        return newImage;
    }

    public static VolatileImage createCompatibleVolatileImage(Image image) {
        var gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        var newImage = gc.createCompatibleVolatileImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
        var g = newImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.clearRect(0, 0, image.getWidth(null), image.getHeight(null));
        g.drawImage(image, 0, 0, null);
        g.dispose();
        image.flush();
        System.out.println(newImage.getCapabilities().isAccelerated());
        return newImage;
    }

    static public BufferedImage changeColor(BufferedImage img, Color selectedColor, Color newColor, float tolerance) {
        if (img == null)
            return null;
        Color colorPixel;
        for (var i = 0; i < img.getWidth(); i++)
            for (var j = 0; i < img.getHeight(); j++) {
                colorPixel = new Color(img.getRGB(i, j));
                if (tolerance > Math.sqrt(Math.pow(selectedColor.getRed() - colorPixel.getRed(), 2) + Math.pow(selectedColor.getGreen() - colorPixel.getGreen(), 2) + Math.pow(selectedColor.getBlue() - colorPixel.getBlue(), 2)))
                    img.setRGB(i, j, newColor.getRGB());
            }
        return img;
    }

    static public Color invertColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

    public static boolean intersect(BufferedImage ia, Point pa, BufferedImage ib, Point pb) {
        var XLoopStart = 0;
        var XLoopEnd = 0;
        var YLoopStart = 0;
        var YLoopEnd = 0;
        if (pb.x > pa.x) {
            XLoopStart = pb.x;
            XLoopEnd = pa.x + ia.getWidth();
        } else {
            XLoopStart = pa.x;
            XLoopEnd = pb.x + ib.getWidth();
        }
        if (pb.y > pa.y) {
            YLoopStart = pb.y;
            YLoopEnd = pa.y + ia.getHeight();
        } else {
            YLoopStart = pa.y;
            YLoopEnd = pb.y + ib.getHeight();
        }
        for (var x = XLoopStart; x < XLoopEnd; x++)
            for (var y = YLoopStart; y < YLoopEnd; y++) {
                var xa = x - pa.x;
                var ya = y - pa.y;
                var xb = x - pb.x;
                var yb = y - pb.y;
                if (xa < ia.getWidth() && ya < ia.getHeight() && xb < ib.getWidth() && yb < ib.getHeight()) {
                    var ca = new Color(ia.getRGB(xa, ya), true);
                    var cb = new Color(ib.getRGB(xb, yb), true);
                    if (ca.getAlpha() > 0 && cb.getAlpha() > 0)
                        return true;
                }
            }
        return false;
    }

}
