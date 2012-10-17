/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author AlfonsoAndrés
 */
public class Imagen {
    
    
    private BufferedImage imagen;
    private int columnas;
    private int filas;
    private Rectangle sourceRect; //Rectangulo del frameActual
    private Rectangle destinationRect; //Rectangulo en donde se va a pintar
    private Point posicion;
    private int width;
    private int height;
    private boolean active;
    private Color color;
    // The scale used to display the sprite strip
    private float scale;


    public Imagen(BufferedImage textura, int filas, int columnas, Point posicion, float scale) {
        active = true;
        this.filas = filas;
        this.columnas = columnas;
        this.posicion = posicion;
        this.scale = scale;
        imagen = textura;
        /* El ancho y alto de una imagen de la rejilla 
        es el total entre el número de columnas y el
        total entre el numero de filas respectivamente.*/
        width = imagen.getWidth() / columnas;
        height = imagen.getHeight() / filas;
    }

    /**
     *
     * @param estado
     * @param frameActual
     */
    public void actualizar(int estado, int frameActual)
    {
        if (!active)
            return;
        if (frameActual < 0 || frameActual > getColumnas())
            System.err.println("No existe el cuadro " + frameActual + ".");
        // Grab the correct frame in the image strip by multiplying the currentFrame index by the frame width
        sourceRect = new Rectangle(frameActual * width, estado * height, width, height);
        // Grab the correct frame in the image strip by multiplying the currentFrame index by the frame width
        destinationRect = new Rectangle(posicion.x - (int)(width * scale) / 2, posicion.y - (int)(height * scale) / 2,
        (int)(width * scale), (int)(height * scale));
    }

    /**
     *
     * @param g
     */
    public void pintar(Graphics g)
    {
        if (!active)
            return;
        g.drawImage(imagen.getSubimage(sourceRect.x, sourceRect.y, sourceRect.width, sourceRect.height), 
                destinationRect.x, destinationRect.y, 
                destinationRect.width, destinationRect.height, color, null);
    }
    
    /**
     * @return the posicion
     */
    public Point getposicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }

    /**
     * @return the getWidth()
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the getHeight()
     */
    public int getHeight() {
        return height;
    }

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
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the scale
     */
    public float getScale() {
        return scale;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * @return the columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @param columnas the columnas to set
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * @return the filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @param filas the filas to set
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }

}