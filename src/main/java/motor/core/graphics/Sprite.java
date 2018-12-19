/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.graphics;

import utilidades.juego.Interfaz;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.function.Supplier;
import motor.core.graphics.spritedefaultstates.NullState;
import motor.core.input.GamePad;

/**
 *
 * @author
 */
public abstract class Sprite {

    protected int velocidad, x, y;
    protected HashMap<Class<? extends SpriteState>, AnimationWrapper> animaciones;
    protected final Imagen imagen;
    protected SpriteState estadoActual;
    protected GamePad gamePad;
    protected String id;
    private final Point centro;
    private AnimationWrapper currentAnimationWrapper;

    public void actualizar(final Interfaz interfaz, final long tiempoTranscurrido) {
        if (estadoActual instanceof NullState) {
            return;
        }
        if(imagen.isActive()) {
            imagen.actualizar(currentAnimationWrapper.fila,
                    currentAnimationWrapper.animacion.getCuadroActual());
        }
        var supplier = estadoActual.handleInput(this, gamePad);
        if(supplier != null) {
            estadoActual.onExit(this, interfaz);
            setEstadoActual(supplier);
        }
        estadoActual.update(this, interfaz, tiempoTranscurrido);
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

    public final SpriteState getEstadoActual() {
        return estadoActual;
    }    

    public final void setEstadoActual(final Supplier<SpriteState> supplier) {
        estadoActual = supplier.get();
        currentAnimationWrapper = animaciones.get(estadoActual.getClass());
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

    public final boolean actualizarAnimacion(final long tiempoTranscurrido) {
        return currentAnimationWrapper.animacion.actualizar(tiempoTranscurrido);
    }

    public void pintar(final Graphics2D g) {
        if (!imagen.isActive() || estadoActual instanceof NullState)
            return;
        imagen.pintar(g, x, y);
    }

    /**
     * @return the imagen
     */
    public final Imagen getImagen() {
        return imagen;
    }
    
    public final GamePad getGamePad() {
        return gamePad;
    }

}
