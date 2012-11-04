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
    private int ancho;
    private int alto;
    private int estado;
    private float anchoEscalado;
    private float altoEscalado;
    private boolean active;
    private Color color;
    // The scale used to display the sprite strip
    private float escala;


    public Imagen(BufferedImage textura, int filas, int columnas, Point posicion, float escala, int estadoInicial) {
        this(textura, filas, columnas, posicion, escala);
        estado = estadoInicial;
    }
    
    public Imagen(BufferedImage textura, int filas, int columnas, Point posicion, float escala) {
        active = true;
        this.filas = filas;
        this.columnas = columnas;
        this.posicion = posicion;
        this.escala = escala;
        imagen = textura;
        /* El ancho y alto de una imagen de la rejilla 
        es el total entre el número de columnas y el
        total entre el numero de filas respectivamente.*/
        ancho = imagen.getWidth() / columnas;
        alto = imagen.getHeight() / filas;
        anchoEscalado = ancho * escala;
        altoEscalado = alto * escala;
    }

    public void actualizar(int frameActual) {
        if (!active)
            return;
        if (frameActual < 0 || frameActual > columnas)
            System.err.println("No existe el cuadro " + frameActual + ".");
        // Grab the correct frame in the image strip by multiplying the currentFrame index by the frame width
        sourceRect = new Rectangle(frameActual * ancho, estado * alto, ancho, alto);
        // Grab the correct frame in the image strip by multiplying the currentFrame index by the frame width
        destinationRect = new Rectangle(posicion.x - (int)(anchoEscalado) / 2, posicion.y - (int)(altoEscalado) / 2,
        (int)(anchoEscalado), (int)(altoEscalado));
    }
    
    /**
     *
     * @param estado
     * @param frameActual
     */
    public void actualizar(int estado, int frameActual)
    {
        this.estado = estado;
        actualizar(frameActual);
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
    public Point getPosicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    public void setPosicion(Point posicion) {
        this.posicion = posicion;
    }
    
    public void trasladar(int dx, int dy) {
        posicion.x += dx;
        posicion.y += dy;
    }

    /**
     * @return the getWidth()
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @return the getHeight()
     */
    public int getAlto() {
        return alto;
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
     * @return el color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color el color a establecer
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return la escala
     */
    public float getEscala() {
        return escala;
    }

    /**
     * @param scale la escala a establecer
     */
    public void setEscala(float scale) {
        this.escala = scale;
    }

    /**
     * @return las columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @param columnas las columnas a establecer
     */
    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    /**
     * @return las filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @param filas las filas a establecer
     */
    public void setFilas(int filas) {
        this.filas = filas;
    }

    /**
     * @return la imagen
     */
    public BufferedImage getImagen() {
        return imagen;
    }

    /**
     * @param imagen la imagen a establecer
     */
    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
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
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    public BufferedImage getSubimage(int fila, int columna) {
        return imagen.getSubimage(columna * ancho, fila * alto, ancho, alto);
    }

    public void borrar(Graphics g, BufferedImage imagen) {
        if(destinationRect == null)
            return;
        g.drawImage(imagen.getSubimage(destinationRect.x, destinationRect.y, destinationRect.width, destinationRect.height), destinationRect.x, destinationRect.y, null);
    }

}