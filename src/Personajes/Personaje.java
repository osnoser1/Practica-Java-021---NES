/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Mapa;
import motor.core.input.Teclado;
import static Personajes.Personaje.Direccion.*;
import gui.JPanelJuego;
import motor.core.graphics.Sprite;
import Utilidades.Juego.Control;
import Utilidades.Juego.Control.Botones;
import static juego.constantes.Estado.*;
import motor.core.graphics.Imagen;

public abstract class Personaje extends Sprite {

    public static enum Direccion {
        HORIZONTAL, VERTICAL;
    }

    protected int varx = 3, vary = 3, smart;
    protected static final int SPEED_SLOWEST = 1, SPEED_SLOW = 2, SPEED_MID = 4, SPEED_FAST = 5, SMART_LOW = 1, SMART_MID = 2, SMART_HIGH = 3, SMART_IMPOSSIBLE = 4;
    protected Inteligencia inteligencia;
    protected Teclado teclado;
    protected Control gamePad;
    protected boolean wallpass, dentroBomb, BOMBPASS;

    protected Personaje(final Imagen imagen, final int x, final int y) {
        super(imagen, x, y);
    }

    @Override
    public void actualizar(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        final int actual = getEstadoActual();
        if (INICIO.val() == actual)
            estadoInicio(jPanelJuego, tiempoTranscurrido);
        else if (ARRIBA.val() == actual)
            estadoArriba(jPanelJuego, tiempoTranscurrido);
        else if (ABAJO.val() == actual)
            estadoAbajo(jPanelJuego, tiempoTranscurrido);
        else if (DERECHA.val() == actual)
            estadoDerecha(jPanelJuego, tiempoTranscurrido);
        else if (IZQUIERDA.val() == actual)
            estadoIzquierda(jPanelJuego, tiempoTranscurrido);
        else if (MUERTE.val() == actual)
            estadoMuerte(jPanelJuego, tiempoTranscurrido);
    }

    protected void reiniciar(){
        setEstadoActual(INICIO.val());
        setActivo(true);
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

    public final void updateX(final JPanelJuego jPanelJuego) {
        if (!choqueCentral("X"))
            dentroBomb = false;
        int ajuste = avanzarX(jPanelJuego.getMapa());
        if (ajuste != 0)
            trasladar(ajuste, 0);
    }

    public final void updateY(final JPanelJuego jPanelJuego) {
        if (!choqueCentral("X"))
            dentroBomb = false;
        int ajuste = avanzarY(jPanelJuego.getMapa());
        if (ajuste != 0)
            trasladar(0, ajuste);
    }

    private boolean choque(final Mapa m, final Direccion d, final int valEje, final String... ses) {
        for (final String se : ses)
            if (d == VERTICAL && (m.contiene(se, valEje, getPosicionX(getX() + 2 * varx)) || m.contiene(se, valEje, getPosicionX(getX() + imagen.getAncho() - 2 * varx)))
                    || d == HORIZONTAL && (m.contiene(se, getPosicionY(getY() + 2 * vary), valEje) || m.contiene(se, getPosicionY(getY() + imagen.getAlto() - 2 * vary), valEje)))
                return true;
        return false;
    }

    protected final boolean choqueX(final Mapa m, final int x, final String... ses) {
        return choque(m, HORIZONTAL, x, ses);
    }

    protected final boolean choqueY(final Mapa m, final int y, final String... ses) {
        return choque(m, VERTICAL, y, ses);
    }

    protected final boolean choqueCentral(final String a) {
        return Mapa.getInstance().contiene(a, posicionMapa.y, posicionMapa.x);
    }

    public void setWallpass(boolean Wallpass) {
        this.wallpass = Wallpass;
    }

    public final int avanzarX(final Mapa m) {
        int ajuste = 0;
        int pos = velocidad < 0 ? getPosicionX(getX() + velocidad) : getPosicionX(getX() + imagen.getAncho() + velocidad);
        if (choqueX(m, pos, "A") || !wallpass && choqueX(m, pos, "L") || !BOMBPASS && !dentroBomb && choqueX(m, pos, "X"))
            ajuste = velocidad < 0
                    ? pos * imagen.getAncho() + imagen.getAncho() - (getX() + velocidad)
                    : pos * imagen.getAncho() - (1 + imagen.getAncho() + getX() + Math.abs(velocidad));
        return velocidad + ajuste;
    }

    public final int avanzarY(final Mapa m) {
        int ajuste = 0;
        int pos = velocidad < 0 ? getPosicionY(getY() + velocidad) : getPosicionY(getY() + imagen.getAlto() + velocidad);
        if (choqueY(m, pos, "A") || !wallpass && choqueY(m, pos, "L") || !BOMBPASS && !dentroBomb && choqueY(m, pos, "X"))
            ajuste = velocidad < 0
                    ? pos * imagen.getAlto() + imagen.getAlto() - (getY() + velocidad)
                    : pos * imagen.getAlto() - (1 + imagen.getAlto() + getY() + Math.abs(velocidad));
        return velocidad + ajuste;
    }

    public boolean isInteligenciaActivada() {
        return inteligencia != null;
    }

    public final int get(final Botones boton) {
        return gamePad.get(boton);
    }

    public void setBOMBPASS(boolean BOMBPASS) {
        this.BOMBPASS = BOMBPASS;
    }

}
