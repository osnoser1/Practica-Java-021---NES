/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import motor.utils.Graphics;
import motor.utils.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author AlfonsoAndrés
 */
public class Imagen {

    private final Image imagen;
    private final int filas, columnas;
    private final Rectangle sourceRect; //Rectangulo del frameActual
    private final Rectangle destinationRect; //Rectangulo en donde se va a pintar
    private final ArrayList<Rectangle> destinationRectAnterior;
    private final int ancho, alto;
    private int estado;
    private final float anchoEscalado, altoEscalado;
    private boolean active;
    private java.awt.Color color;
    // The scale used to display the sprite strip
    private final float escala;

//    public Imagen(Image textura, int filas, int columnas, Point posicion, float escala, int estadoInicial) {
//        this(textura, filas, columnas, posicion, escala);
//        estado = estadoInicial;
//    }

    public Imagen(Image textura, int filas, int columnas, Point posicion, float escala) {
        active = true;
        this.filas = filas;
        this.columnas = columnas;
        this.escala = escala;
        imagen = textura;
        ancho = imagen.getWidth() / columnas;
        alto = imagen.getHeight() / filas;
        anchoEscalado = ancho * escala;
        altoEscalado = alto * escala;
        sourceRect = new Rectangle(0, 0, ancho, alto);
        destinationRect = new Rectangle(posicion.x, posicion.y,
                (int) (anchoEscalado), (int) (altoEscalado));
        destinationRectAnterior = new ArrayList<>();
    }

    public Imagen(Image textura, int filas, int columnas, float escala) {
        this(textura, filas, columnas, new Point(), escala);
    }

//    public Imagen(Image textura, int filas, int columnas, float escala, int estadoInicial) {
//        this(textura, filas, columnas, new Point(), escala, estadoInicial);
//    }

    public void actualizar(int frameActual) {
        if (!active) {
            return;
        }
        if (frameActual < 0 || frameActual > columnas) {
            System.err.println("No existe el cuadro " + frameActual + ".");
        }
        // Grab the correct frame in the image strip by multiplying the currentFrame index by the frame width
        sourceRect.setBounds(frameActual * ancho, estado * alto, ancho, alto);
    }

    /**
     *
     * @param estado
     * @param frameActual
     */
    public void actualizar(int estado, int frameActual) {
        this.estado = estado;
        actualizar(frameActual);
    }

    /**
     *
     * @param g
     */
    public void pintar(Graphics g) {
        if (!active) {
            return;
        }
        g.drawImage(imagen.getSubimage(sourceRect.x, sourceRect.y, sourceRect.width, sourceRect.height),
                destinationRect, color);
    }

//    public void pintar(Graphics g, int x, int y, int ancho, int alto) {
//        if (!active) {
//            return;
//        }
//        g.drawImage(imagen.getSubimage(sourceRect.x, sourceRect.y, sourceRect.width, sourceRect.height),
//                x, y,
//                ancho, alto, color, null);
//    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return la escala
     */
    public float getEscala() {
        return escala;
    }

    /**
     * @return la imagen
     */
    public Image getImagen() {
        return imagen;
    }

    /**
     * @return the anchoEscalado
     */
    public int getAnchoEscalado() {
        return (int) anchoEscalado;
    }

    /**
     * @return the altoEscalado
     */
    public int getAltoEscalado() {
        return (int) altoEscalado;
    }

    /**
     * @return the posicion
     */
    public Point getPosicion() {
        return destinationRect.getLocation();
    }

    /**
     * @param posicion the posicion to set
     */
    public void setLocation(Point posicion) {
        destinationRectAnterior.add((Rectangle) destinationRect.clone());
        destinationRect.setLocation(posicion);
    }

    public void setLocation(int x, int y) {
        destinationRectAnterior.add((Rectangle) destinationRect.clone());
        destinationRect.setLocation(x, y);
    }

    public void trasladar(int dx, int dy) {
        destinationRectAnterior.add((Rectangle) destinationRect.clone());
        destinationRect.translate(dx, dy);
    }

    /**
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    public void borrar(Graphics g, Image imagen) {
        if (destinationRect == null) {
            return;
        }
        for (final Rectangle rectangle : destinationRectAnterior) {
            g.drawImage(imagen.getSubimage(rectangle.x, rectangle.y, rectangle.width, rectangle.height), rectangle.x, rectangle.y);
        }
//        System.out.println(destinationRectAnterior.size());
        destinationRectAnterior.clear();
    }

    Rectangle getDestinationRect() {
        return destinationRect;
    }

    Image getSubImageWithout(int fila, int columna) {
        return imagen.getSubimage(columna * ancho, fila * alto, ancho, alto);
    }

}