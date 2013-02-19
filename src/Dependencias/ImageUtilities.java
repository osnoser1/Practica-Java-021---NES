package Dependencias;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageUtilities {
	
    public ImageUtilities() {
	// TODO: Add your code here
    }
    
    static public BufferedImage invertirImagen(BufferedImage imagen){
        if(imagen == null)
            return null;
        Color colorPixel;
        for(int i = 0, rgb = 0; i < imagen.getWidth(); i++) {
            for(int j = 0; j < imagen.getHeight(); j++){
                colorPixel = new Color(imagen.getRGB(i, j));
                rgb = new Color(255 - colorPixel.getRed(), 255 - colorPixel.getGreen(), 255 - colorPixel.getBlue()).getRGB();
                imagen.setRGB(i, j, rgb);
            }
        }
	return imagen;
    }
	
    static public Image invertirImagen(Image imagen){
	return (Image)invertirImagen(convertImage(imagen));
    }
    
    static public BufferedImage convertImage(Image imagen){
        if(imagen == null)
            return null;
        BufferedImage img = new BufferedImage(imagen.getWidth(null), imagen.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        img.createGraphics().drawImage(imagen, 0, 0, null);
        return img;
    }
    
    static public BufferedImage cambiarColor(BufferedImage img, Color crSeleccionado, Color crCambiar, float tolerancia){
        if(img == null)
            return null;
        Color colorPixel;
        for(int i = 0; i < img.getWidth(); i++) {
            for(int j = 0; i < img.getHeight(); j++) {
                colorPixel = new Color(img.getRGB(i, j));
                if(tolerancia > Math.sqrt(Math.pow(crSeleccionado.getRed() - colorPixel.getRed(), 2) + Math.pow(crSeleccionado.getGreen() - colorPixel.getGreen(), 2) + Math.pow(crSeleccionado.getBlue() - colorPixel.getBlue(), 2)))
                    img.setRGB(i, j, crCambiar.getRGB());
            }
        }
        return img;
    }
    
    static public Color invertirColor(Color color){
        return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
    }
}
