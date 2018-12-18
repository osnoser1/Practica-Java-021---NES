package lenguaje.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class ImageUtilities {

    static public BufferedImage invertirImagen(BufferedImage imagen) {
        if (imagen == null)
            return null;
        Color colorPixel;
        for (int i = 0, rgb = 0; i < imagen.getWidth(); i++)
            for (int j = 0; j < imagen.getHeight(); j++) {
                colorPixel = new Color(imagen.getRGB(i, j));
                rgb = new Color(255 - colorPixel.getRed(), 255 - colorPixel.getGreen(), 255 - colorPixel.getBlue()).getRGB();
                imagen.setRGB(i, j, rgb);
            }
        return imagen;
    }

    static public Image invertirImagen(Image imagen) {
        return (Image) invertirImagen(convertImage(imagen));
    }

    static public BufferedImage convertImage(Image imagen) {
        if (imagen == null)
            return null;
        BufferedImage img = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        img.createGraphics().drawImage(imagen, 0, 0, null);
        return img;
    }

    public static BufferedImage createCompatibleImage(final int width, final int height, final int transparency) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage newImage = gc.createCompatibleImage(width, height, transparency);
        return newImage;
    }

    public static BufferedImage createCompatibleImage(final int width, final int height) {
        return createCompatibleImage(width, height, Transparency.BITMASK);
    }

    public static BufferedImage createCompatibleImage(final BufferedImage image) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if (image.getColorModel().equals(gc.getColorModel())) {
            System.out.println("Optimized");
            return image;
        } 
        BufferedImage newImage = gc.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.BITMASK);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage createCompatibleImage(final Image image) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage newImage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.BITMASK);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        image.flush();
        return newImage;
    }

    public static VolatileImage createCompatibleVolatileImage(final int width, final int height, final int transparency) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        VolatileImage newImage = gc.createCompatibleVolatileImage(width, height, transparency);
        if (transparency == Transparency.TRANSLUCENT) {
            Graphics2D g = newImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.dispose();
        }
        System.out.println(newImage.getCapabilities().isAccelerated());
        return newImage;
    }

    public static VolatileImage createCompatibleVolatileImage(Image image) {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        VolatileImage newImage = gc.createCompatibleVolatileImage(image.getWidth(null), image.getHeight(null), Transparency.TRANSLUCENT);
        Graphics2D g = newImage.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.clearRect(0, 0, image.getWidth(null), image.getHeight(null));
        g.drawImage(image, 0, 0, null);
        g.dispose();
        image.flush();
        System.out.println(newImage.getCapabilities().isAccelerated());
        return newImage;
    }

    static public BufferedImage cambiarColor(BufferedImage img, Color crSeleccionado, Color crCambiar, float tolerancia) {
        if (img == null)
            return null;
        Color colorPixel;
        for (int i = 0; i < img.getWidth(); i++)
            for (int j = 0; i < img.getHeight(); j++) {
                colorPixel = new Color(img.getRGB(i, j));
                if (tolerancia > Math.sqrt(Math.pow(crSeleccionado.getRed() - colorPixel.getRed(), 2) + Math.pow(crSeleccionado.getGreen() - colorPixel.getGreen(), 2) + Math.pow(crSeleccionado.getBlue() - colorPixel.getBlue(), 2)))
                    img.setRGB(i, j, crCambiar.getRGB());
            }
        return img;
    }

    static public Color invertirColor(Color color) {
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }

    public static boolean intersect(BufferedImage ia, Point pa, BufferedImage ib, Point pb) throws Exception {
        int XLoopStart = 0;
        int XLoopEnd = 0;
        int YLoopStart = 0;
        int YLoopEnd = 0;
        if (pb.x > pa.x) {
            XLoopStart = (int) pb.x;
            XLoopEnd = (int) pa.x + ia.getWidth();
        } else {
            XLoopStart = (int) pa.x;
            XLoopEnd = (int) pb.x + ib.getWidth();
        }
        if (pb.y > pa.y) {
            YLoopStart = (int) pb.y;
            YLoopEnd = (int) pa.y + ia.getHeight();
        } else {
            YLoopStart = (int) pa.y;
            YLoopEnd = (int) pb.y + ib.getHeight();
        }
        for (int x = XLoopStart; x < XLoopEnd; x++)
            for (int y = YLoopStart; y < YLoopEnd; y++) {
                int xa = x - (int) pa.x;
                int ya = y - (int) pa.y;
                int xb = x - (int) pb.x;
                int yb = y - (int) pb.y;
                if (xa < ia.getWidth() && ya < ia.getHeight() && xb < ib.getWidth() && yb < ib.getHeight()) {
                    Color ca = new Color(ia.getRGB(xa, ya), true);
                    Color cb = new Color(ib.getRGB(xb, yb), true);
                    if (ca.getAlpha() > 0 && cb.getAlpha() > 0)
                        return true;
                }
            }
        return false;
    }

}
