/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import motor.utils.Graphics;
import motor.utils.Image;
import java.awt.Point;
import java.util.HashMap;
import lenguaje.utils.Pareja;
import motor.core.EstadosSprite.Estado;
import motor.core.escenas.Juego;

/**
 *
 * @author
 */
public abstract class Sprite {

    protected int velocidadX, velocidadY;
    private HashMap<Integer, Pareja<Integer, ControlAnimacion>> animaciones;
    private Imagen imagen;
    private Estado estadoAnterior, estadoActual;
    protected String id;
    protected Point posicionMapa;

    public void actualizar(Juego escenaJuego, long tiempoTranscurrido) {
//        super.actualizarEstados(estadoActual, escenaJuego, tiempoTranscurrido);
        final Pareja<Integer, ControlAnimacion> actual = animaciones.get(estadoActual.ordinal());
        imagen.actualizar(actual.getPrimero(), actual.getSegundo().getCuadroActual());
    }

    protected void inicializar(Imagen imagen, Point posicion, int velocidadX, int velocidadY, HashMap<Integer, Pareja<Integer, ControlAnimacion>> animaciones) {
        this.imagen = imagen;
        this.imagen.setLocation(posicion);
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.animaciones = animaciones;
        posicionMapa = new Point(getCentro().x / imagen.getAnchoEscalado(), getCentro().y / imagen.getAltoEscalado());
    }

    public String getIdentificacion() {
        return id;
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
        return imagen.getDestinationRect();
    }

    public void setLocation(int x, int y) {
        imagen.setLocation(x, y);
        posicionMapa.x = x / imagen.getAnchoEscalado();
        posicionMapa.y = y / imagen.getAltoEscalado();
    }

    public int getX() {
        return imagen.getPosicion().x;
    }

    public int getY() {
        return imagen.getPosicion().y;
    }

    public Point getCentro() {
        return new Point(getX() + imagen.getAnchoEscalado() / 2, getY() + imagen.getAltoEscalado() / 2);
    }

    public void trasladar(int dx, int dy) {
        imagen.trasladar(dx, dy);
        posicionMapa.setLocation(getCentro().x / imagen.getAnchoEscalado(), getCentro().y / imagen.getAltoEscalado());
    }

    /**
     *
     * @return Devuelve true si el personaje esta activo, false si no lo está.
     */
    public boolean isActivo() {
        return imagen.isActive();
    }

    /**
     *
     * @param activo indica si quieres activar o no el personaje
     */
    public void setActivo(boolean activo) {
        imagen.setActive(activo);
    }

    public Point getPosicionMapa() {
        if (estadoActual == Estado.ARRIBA) {
            return new Point(posicionMapa.x, (getY() + imagen.getAltoEscalado()) / imagen.getAltoEscalado());
        }
        if (estadoActual == Estado.ABAJO) {
            return new Point(posicionMapa.x, getY() / imagen.getAltoEscalado());
        }
        if (estadoActual == Estado.DERECHA) {
            return new Point(getX() / imagen.getAnchoEscalado(), posicionMapa.y);
        }
        return new Point((getX() + imagen.getAnchoEscalado()) / imagen.getAnchoEscalado(), posicionMapa.y);
    }

    public void reiniciar() {
        setEstadoActual(Estado.INICIO);
        imagen.setActive(true);
    }

    public void fijarCasilla(int x, int y) {
        setLocation(x * imagen.getAnchoEscalado(), y * imagen.getAltoEscalado());
    }

    protected boolean actualizarAnimacion(long tiempoTranscurrido) {
        return animaciones.get(getEstadoActual().ordinal()).getSegundo().actualizar(tiempoTranscurrido);
    }

    public void borrar(Graphics g, Image imagen) {
        this.imagen.borrar(g, imagen);
    }

    public void pintar(Graphics g) {
        if (!imagen.isActive() || getEstadoActual() == Estado.ELIMINADO) {
            return;
        }
        imagen.pintar(g);
//        System.out.println(posicionMapa);
    }

    /**
     * @return the imagen
     */
    public Imagen getImagen() {
        return imagen;
    }

    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }

    public int getVelocidadX() {
        return velocidadX;
    }

    public int getVelocidadY() {
        return velocidadY;
    }

//    public void pintar(Graphics g, int x, int y, int ancho, int alto) {
//        if(!imagen.isActive() || getEstadoActual() == Estado.ELIMINADO)
//            return;
//        imagen.actualizar(getEstadoActual().ordinal(), animaciones.get(getEstadoActual().ordinal()).getCuadroActual());
//        imagen.pintar(g, x, y, ancho, alto);
//    }
//    public short getPosicionX(int X) {
//        return (short)(X / imagen.getAnchoEscalado());
//    }
//
//    public short getPosicionY(int Y) {
//        return (short)(Y / imagen.getAltoEscalado());
//    }
    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }

}
