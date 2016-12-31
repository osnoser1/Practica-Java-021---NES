/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.map.Mapa;
import static Personajes.Personaje.Direccion.*;
import Utilidades.Juego.Interfaz;
import gui.JPanelJuego;
import motor.core.graphics.Sprite;
import motor.core.graphics.Imagen;
import motor.core.input.IGamePadController;

public abstract class Personaje extends Sprite {

    public static enum Direccion {
        HORIZONTAL, VERTICAL;
    }

    protected int varx = 3, vary = 3, smart;
    protected static final int SPEED_SLOWEST = 1, SPEED_SLOW = 2, SPEED_MID = 4, SPEED_FAST = 5, SMART_LOW = 1, SMART_MID = 2, SMART_HIGH = 3, SMART_IMPOSSIBLE = 4;
    protected Inteligencia inteligencia;
    protected IGamePadController padController;
    protected boolean wallpass, dentroBomb, BOMBPASS;

    protected Personaje(final Imagen imagen, final int x, final int y) {
        super(imagen, x, y);
    }

    @Override
    public void actualizar(final Interfaz escena, final long tiempoTranscurrido) {
        super.actualizar(escena, tiempoTranscurrido);
    }
    
    public final Inteligencia getInteligencia() {
        return inteligencia;
    }

    public final void movimientoDerecha(final JPanelJuego jPanelJuego) {
        velocidad = Math.abs(velocidad);
        updateX(jPanelJuego);
    }

    public final void movimientoIzquierda(final JPanelJuego jPanelJuego) {
        velocidad = -Math.abs(velocidad);
        updateX(jPanelJuego);
    }

    public final void movimientoArriba(final JPanelJuego jPanelJuego) {
        velocidad = -Math.abs(velocidad);
        updateY(jPanelJuego);
    }

    public final void movimientoAbajo(final JPanelJuego jPanelJuego) {
        velocidad = Math.abs(velocidad);
        updateY(jPanelJuego);
    }

    public void iniciarInteligencia() {
        inteligencia = new Inteligencia(this);
        inteligencia.iniciar();
    }

    public void detenerInteligencia() {
        if (inteligencia == null)
            return;
        inteligencia.detenerInteligencia();
        inteligencia = null;
    }

    private void updateX(final JPanelJuego jPanelJuego) {
        if (!choqueCentral(Bomb.class))
            dentroBomb = false;
        int ajuste = avanzarX(jPanelJuego.getMapa());
        if (ajuste != 0)
            trasladar(ajuste, 0);
    }

    private void updateY(final JPanelJuego jPanelJuego) {
        if (!choqueCentral(Bomb.class))
            dentroBomb = false;
        int ajuste = avanzarY(jPanelJuego.getMapa());
        if (ajuste != 0)
            trasladar(0, ajuste);
    }

    private boolean choque(final Mapa m, final Direccion d, final int valEje, final Class<?>... classes) {
        for (final Class<?> se : classes)
            if (d == VERTICAL && (m.contiene(valEje, getPosicionX(getX() + 2 * varx), se) || m.contiene(valEje, getPosicionX(getX() + imagen.getAncho() - 2 * varx), se))
                    || d == HORIZONTAL && (m.contiene(getPosicionY(getY() + 2 * vary), valEje, se) || m.contiene(getPosicionY(getY() + imagen.getAlto() - 2 * vary), valEje, se)))
                return true;
        return false;
    }

    protected final boolean choqueX(final Mapa m, final int x, final Class<?>... classes) {
        return choque(m, HORIZONTAL, x, classes);
    }

    protected final boolean choqueY(final Mapa m, final int y, final Class<?>... classes) {
        return choque(m, VERTICAL, y, classes);
    }

    protected final boolean choqueCentral(final Class<?>... classes) {
        return Mapa.getInstance().contiene(this, classes);
    }

    public void setWallpass(boolean Wallpass) {
        this.wallpass = Wallpass;
    }

    public final int avanzarX(final Mapa m) {
        int ajuste = 0;
        int pos = velocidad < 0 ? getPosicionX(getX() + velocidad) : getPosicionX(getX() + imagen.getAncho() + velocidad);
        if (choqueX(m, pos, Aluminio.class) || !wallpass && choqueX(m, pos, Ladrillo.class) || !BOMBPASS && !dentroBomb && choqueX(m, pos, Bomb.class))
            ajuste = velocidad < 0
                    ? pos * imagen.getAncho() + imagen.getAncho() - (getX() + velocidad)
                    : pos * imagen.getAncho() - (1 + imagen.getAncho() + getX() + Math.abs(velocidad));
        return velocidad + ajuste;
    }

    public final int avanzarY(final Mapa m) {
        int ajuste = 0;
        int pos = velocidad < 0 ? getPosicionY(getY() + velocidad) : getPosicionY(getY() + imagen.getAlto() + velocidad);
        if (choqueY(m, pos, Aluminio.class) || !wallpass && choqueY(m, pos, Ladrillo.class) || !BOMBPASS && !dentroBomb && choqueY(m, pos, Bomb.class))
            ajuste = velocidad < 0
                    ? pos * imagen.getAlto() + imagen.getAlto() - (getY() + velocidad)
                    : pos * imagen.getAlto() - (1 + imagen.getAlto() + getY() + Math.abs(velocidad));
        return velocidad + ajuste;
    }

    public boolean isInteligenciaActivada() {
        return inteligencia != null;
    }

    public void setBOMBPASS(boolean BOMBPASS) {
        this.BOMBPASS = BOMBPASS;
    }

}
