/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.graphics;

import gui.JPanelJuego;
import motor.core.ControlAnimacion;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author
 */
public abstract class Sprite {

    protected int velocidad, x, y;
    protected HashMap<Integer, ControlAnimacion> animaciones;
    protected final Imagen imagen;
    private int estadoAnterior, estadoActual;
    protected String id;
    private final Point centro;

    public void actualizar(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
        if (isActivo() && estadoActual != -1)
            imagen.actualizar(estadoActual, animaciones.get(estadoActual).getCuadroActual());
    }

    protected Sprite(final Imagen imagen, final int x, final int y) {
        this.imagen = imagen;
        this.x = x;
        this.y = y;
        centro = new Point();
    }

    public final String getId() {
        return id;
    }

    public final int getEstadoActual() {
        return estadoActual;
    }

    public final void setEstadoActual(final int estado) {
        estadoAnterior = estadoActual;
        estadoActual = estado;
    }

    public final int getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getAncho() {
        return imagen.getAncho();
    }

    public final int getAlto() {
        return imagen.getAlto();
    }

    protected final short getPosicionX(int X) {
        return (short) (X / imagen.getAncho());
    }

    protected final short getPosicionY(int Y) {
        return (short) (Y / imagen.getAlto());
    }

    public final Point getCentro() {
        centro.setLocation(getX() + imagen.getAncho() / 2, getY() + imagen.getAlto() / 2);
        return centro;
    }

    public final void trasladar(final int dx, final int dy) {
        x += dx;
        y += dy;
    }

    /**
     *
     * @return Devuelve true si el personaje esta activo, false si no lo está.
     */
    public final boolean isActivo() {
        return imagen.isActive();
    }

    /**
     *
     * @param activo indica si quieres activar o no el personaje
     */
    public final void setActivo(final boolean activo) {
        imagen.setActive(activo);
    }

    public final void fijarCasilla(final int x, final int y) {
        setLocation(x * imagen.getAncho(), y * imagen.getAlto());
    }

    protected final boolean actualizarAnimacion(final long tiempoTranscurrido) {
        return animaciones.get(estadoActual).actualizar(tiempoTranscurrido);
    }

    public void pintar(final Graphics2D g) {
        if (!imagen.isActive() || estadoActual == -1)
            return;
        imagen.pintar(g, x, y);
    }

    /**
     * @return the imagen
     */
    public final Imagen getImagen() {
        return imagen;
    }

    public void estadoInicio(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
    }

    public void estadoArriba(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
    }

    public void estadoAbajo(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
    }

    public void estadoDerecha(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
    }

    public void estadoIzquierda(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
    }

    public void estadoMuerte(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
    }

}
