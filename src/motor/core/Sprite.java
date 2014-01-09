/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import Dependencias.Imagen;
import GUI.JPanelJuego;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author
 */
public abstract class Sprite {

    protected int x, y, velocidad;
    protected HashMap<Integer, ControlAnimacion> animaciones;
    protected Imagen imagen;
    private Estado estadoAnterior, estadoActual;
    protected String identificacion;
    protected boolean activo;
    protected Point posicionMapa;

    public enum Estado {

        INICIO,
        ARRIBA,
        ABAJO,
        DERECHA,
        IZQUIERDA,
        MUERTE,
        ELIMINADO
    }

    public abstract void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido);

    protected void inicializar(Imagen imagen, Point posicion) {
        this.imagen = imagen;
        x = posicion.x;
        y = posicion.y;
        imagen.setPosicion(new Point(x + imagen.getAnchoEscalado() / 2, y + imagen.getAltoEscalado() / 2));
        posicionMapa = new Point(getCentro().x / imagen.getAnchoEscalado(), getCentro().y / imagen.getAltoEscalado());
        activo = true;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public HashMap<Integer, ControlAnimacion> getAnimaciones() {
        return animaciones;
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado estado) {
        estadoAnterior = estadoActual;
        estadoActual = estado;
    }

    public Estado getEstadoAnterior() {
        return estadoAnterior;
    }

    public java.awt.Rectangle getRectagulo() {
        return new java.awt.Rectangle(x, y, imagen.getAnchoEscalado(), imagen.getAltoEscalado());
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        imagen.setPosicion(new Point(x + imagen.getAnchoEscalado() / 2, y + imagen.getAltoEscalado() / 2));
        posicionMapa.x = x / imagen.getAnchoEscalado();
        posicionMapa.y = y / imagen.getAltoEscalado();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public short getPosicionX(int X) {
        return (short) (X / imagen.getAnchoEscalado());
    }

    public short getPosicionY(int Y) {
        return (short) (Y / imagen.getAltoEscalado());
    }

    public Point getCentro() {
        return imagen.getPosicion();
    }

    public void trasladar(int dx, int dy) {
        x += dx;
        y += dy;
        imagen.trasladar(dx, dy);
    }

    /**
     *
     * @return Devuelve true si el personaje esta activo, false si no lo está.
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     *
     * @param activo indica si quieres activar o no el personaje
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Point getPosicionMapa() {
        return posicionMapa;
    }

    public void reiniciar() {
        setEstadoActual(Estado.INICIO);
        activo = true;
    }

    public void fijarCasilla(int x, int y) {
        setLocation(x * imagen.getAnchoEscalado(), y * imagen.getAltoEscalado());
    }

    protected boolean actualizarAnimacion(long tiempoTranscurrido) {
        return animaciones.get(getEstadoActual().ordinal()).actualizar(tiempoTranscurrido);
    }

    public void borrar(Graphics g, java.awt.image.BufferedImage imagen) {
        g.drawImage(imagen.getSubimage(x, y, this.imagen.getAnchoEscalado(), this.imagen.getAltoEscalado()), x, y, null);
    }

    public void pintar(Graphics g) {
        if (!activo || getEstadoActual() == Estado.ELIMINADO) {
            return;
        }
        imagen.actualizar(getEstadoActual().ordinal(), animaciones.get(getEstadoActual().ordinal()).getCuadroActual());
        imagen.pintar(g);
    }

    public void pintar(Graphics g, int x, int y, int ancho, int alto) {
        if (!activo || getEstadoActual() == Estado.ELIMINADO) {
            return;
        }
        imagen.actualizar(getEstadoActual().ordinal(), animaciones.get(getEstadoActual().ordinal()).getCuadroActual());
        imagen.pintar(g, x, y, ancho, alto);
    }

    /**
     * @return the imagen
     */
    public Imagen getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
    }

    public void estadoArriba(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
    }

    public void estadoAbajo(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
    }

    public void estadoDerecha(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
    }

    public void estadoIzquierda(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
    }

    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
    }

}
