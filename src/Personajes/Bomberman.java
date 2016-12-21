/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.ControlAnimacion;
import motor.core.graphics.Imagen;
import Dependencias.Imagenes;
import motor.core.input.Teclado;
import gui.JPanelJuego;
import Dependencias.Sonidos;
import Utilidades.Juego.Control;
import Utilidades.Juego.Control.Botones;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import static juego.constantes.Estado.*;

public class Bomberman extends Personaje {

    private boolean SPEED, DETONADOR, FLAMEPASS, MYSTERY;
    private int FLAMES, BOMBS;
    private static CopyOnWriteArrayList<Bomb> bombas;
    private boolean entroALaPuerta;

    public Bomberman(final int x, final int y) {
        super(new Imagen(Imagenes.BOMBERMAN, 6, 6, (float) 2.5), x, y);
        gamePad = new Control();
        bombas = new CopyOnWriteArrayList<>();
        velocidad = SPEED_MID;
        BOMBS = 10;
        FLAMES = 10;
        SPEED = true;
        wallpass = true;
        DETONADOR = true;
        BOMBPASS = true;
        FLAMEPASS = true;
        MYSTERY = true;
        id = "B";
        teclado = Teclado.getInstance();
        inicializar();
    }

    public final void inicializar() {
        super.animaciones = new HashMap<Integer, ControlAnimacion>() {
            {
                put(INICIO.val(), new ControlAnimacion("0", 4000 / 60));
                put(ARRIBA.val(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(ABAJO.val(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(DERECHA.val(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(IZQUIERDA.val(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(MUERTE.val(), new ControlAnimacion("0,1,2,3,4", 300));
            }
        };
        setEstadoActual(IZQUIERDA.val());
    }

    @Override
    public void actualizar(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        for (Bomb bomba : bombas) {
            bomba.actualizar(jPanelJuego, tiempoTranscurrido);
            if (bomba.getEstadoActual() == ELIMINADO.val()) {
                jPanelJuego.getMapa().remover(bomba);
                bombas.remove(bomba);
            }
        }
    }

    public void colisionaCon(final Personaje otro) {
        if (getEstadoActual() == MUERTE.val())
            return;
        Sonidos.getInstance().detener(Sonidos.UP, Sonidos.DOWN, Sonidos.LEFT, Sonidos.RIGHT);
        Sonidos.getInstance().play(Sonidos.DEATH);
        setEstadoActual(MUERTE.val());
    }

    public void setDETONATOR(boolean DETONATOR) {
        this.DETONADOR = DETONATOR;
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if (SPEED)
            velocidad = SPEED_FAST;
        else
            velocidad = SPEED_MID;
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
    }

    public void incrementarBombas(final int incremento) {
        BOMBS += incremento;
    }

    public void incrementarFlamas(final int incremento) {
        FLAMES += incremento;
    }

    public int getBOMBS() {
        return BOMBS;
    }

    public boolean getMYSTERY() {
        return MYSTERY;
    }

    public int getFLAMES() {
        return FLAMES;
    }

    public boolean getDETONADOR() {
        return DETONADOR;
    }

    public boolean getFLAMEPASS() {
        return FLAMEPASS;
    }

    public void crearBomba(final JPanelJuego jPanelJuego) {
        if (choqueCentral("X")) {
            dentroBomb = true;
            return;
        }
        if (!choqueCentral("L") && bombas.size() < BOMBS) {
            Sonidos.getInstance().play(Sonidos.BOMB_PLANT);
            final Bomb b = new Bomb(posicionMapa.x * imagen.getAncho(), posicionMapa.y * imagen.getAlto(), this);
            jPanelJuego.getMapa().agregar(b);
            bombas.add(b);
            dentroBomb = true;
        }
    }

    public CopyOnWriteArrayList<Bomb> getBombs() {
        return bombas;
    }

    @Override
    public void estadoInicio(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        setEstadoActual(IZQUIERDA.val());
    }

    @Override
    public void estadoArriba(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion(jPanelJuego);
        if (verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoAbajo(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion(jPanelJuego);
        if (verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoDerecha(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion(jPanelJuego);
        if (verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoIzquierda(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion(jPanelJuego);
        if (verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoMuerte(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if (actualizarAnimacion(tiempoTranscurrido)) {
            Sonidos.getInstance().detener();
            setEstadoActual(ELIMINADO.val());
            Sonidos.getInstance().play(Sonidos.JUST_DIED);
        }
    }

    private void verificarTeclasAccion(final JPanelJuego jPanelJuego) {
        if (teclado.teclaPresionada(gamePad.get(Botones.A)))
            crearBomba(jPanelJuego);
        if (teclado.teclaPresionada(gamePad.get(Botones.B)) && DETONADOR)
            detonarBomba(jPanelJuego);
    }

    private boolean verificarMovimiento(final JPanelJuego jPanelJuego) {
        if (entroALaPuerta)
            return false;
        boolean movimiento = true;
        if (teclado.teclaPresionada(get(Botones.ARRIBA))) {
            setEstadoActual(ARRIBA.val());
            Sonidos.getInstance().play(Sonidos.UP);
            movimientoArriba(jPanelJuego);
        } else if (teclado.teclaPresionada(get(Botones.ABAJO))) {
            Sonidos.getInstance().detener(Sonidos.UP);
            setEstadoActual(ABAJO.val());
            Sonidos.getInstance().play(Sonidos.DOWN);
            movimientoAbajo(jPanelJuego);
        } else {
            Sonidos.getInstance().detener(Sonidos.UP, Sonidos.DOWN);
            movimiento = false;
        }
        if (teclado.teclaPresionada(get(Botones.DERECHA))) {
            setEstadoActual(DERECHA.val());
            Sonidos.getInstance().play(Sonidos.LEFT);
            movimientoDerecha(jPanelJuego);
            movimiento = true;
        } else if (teclado.teclaPresionada(get(Botones.IZQUIERDA))) {
            Sonidos.getInstance().detener(Sonidos.LEFT);
            setEstadoActual(IZQUIERDA.val());
            Sonidos.getInstance().play(Sonidos.RIGHT);
            movimientoIzquierda(jPanelJuego);
            movimiento = true;
        } else
            Sonidos.getInstance().detener(Sonidos.LEFT, Sonidos.RIGHT);
        return movimiento;
    }

    public void reiniciar(int x, int y) {
        super.reiniciar();
        fijarCasilla(x, y);
        bombas.clear();
        imagen.setActive(true);
        entroALaPuerta = false;
    }

    private void detonarBomba(final JPanelJuego jPanelJuego) {
        for (final Bomb bomba : bombas)
            if (bomba.getEstadoActual() != MUERTE.val()) {
                bomba.detonar(jPanelJuego);
                return;
            }
    }

    public void setEntroALaPuerta(boolean b) {
        entroALaPuerta = true;
    }

    public boolean isEntroALaPuerta() {
        return entroALaPuerta;
    }

}
