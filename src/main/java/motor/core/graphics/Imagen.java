/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author AlfonsoAndrés
 */
public final class Imagen {

    private final Image imagen;
    private final int filas, columnas;
    private final Rectangle sourceRect; //Rectangulo del frameActual
    private final int ancho, alto;
    private int estado;
    private int anchoEscalado, altoEscalado;
    private boolean active;
    private Color color;
    private float escala; // The scale used to display the sprite strip

    /**
     *
     * @param textura
     * @param filas
     * @param columnas
     * @param escala
     * @param estadoInicial
     */
    public Imagen(final Image textura, final int filas, final int columnas, final float escala, final int estadoInicial) {
        this(textura, filas, columnas, escala);
        estado = estadoInicial;
    }

    /**
     *
     * @param textura
     * @param filas
     * @param columnas
     * @param escala
     */
    public Imagen(final Image textura, int filas, int columnas, final float escala) {
        active = true;
        this.filas = filas;
        this.columnas = columnas;
        imagen = textura;
        ancho = imagen.getWidth(null) / columnas;
        alto = imagen.getHeight(null) / filas;
        sourceRect = new Rectangle(0, 0, ancho, alto);
        setEscala(escala);
    }

    /**
     *
     * @param frameActual
     */
    public void actualizar(int frameActual) {
        if (!active)
            return;
        if (frameActual < 0 || frameActual > columnas)
            System.err.println("No existe el cuadro " + frameActual + ".");
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
     * @return las columnas
     */
    public int getColumnas() {
        return columnas;
    }

    /**
     * @return las filas
     */
    public int getFilas() {
        return filas;
    }

    /**
     * @return el ancho
     */
    public int getAncho() {
        return (int) anchoEscalado;
    }

    /**
     * @return el alto
     */
    public int getAlto() {
        return (int) altoEscalado;
    }

    /**
     *
     * @return la grilla con el sprite completo
     */
    public Image getImagen() {
        return imagen;
    }

    /**
     *
     * @param escala
     */
    public void setEscala(final float escala) {
        this.escala = escala;
        anchoEscalado = (int) (ancho * this.escala);
        altoEscalado = (int) (alto * this.escala);
    }

    /**
     *
     * @param g
     * @param x
     * @param y
     */
    public void pintar(final Graphics2D g, final int x, final int y) {
        if (!active)
            return;
        g.drawImage(imagen, x, y, x + anchoEscalado, y + altoEscalado, sourceRect.x, sourceRect.y, sourceRect.x + sourceRect.width, sourceRect.y + sourceRect.height, color, null);
    }

}
