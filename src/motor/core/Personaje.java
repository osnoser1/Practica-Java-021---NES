/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.awt.Point;
import java.util.HashMap;
import lenguaje.utils.Pareja;
import motor.core.GamePad.Botones;
import motor.core.MovimientosPersonaje.TipoMovimiento;
import motor.core.Teclado.Control;
import motor.core.escenas.Juego;

public class Personaje extends Sprite {

    protected Inteligencia inteligencia;
    protected final MovimientosPersonaje mp;
    protected GamePad gamePad;
    protected HashMap<Integer, TipoMovimiento> acciones;
    protected Automata automata;

    protected Personaje() {
        mp = new MovimientosPersonaje(this);
    }

    @Override
    public void actualizar(Juego escenaJuego, long tiempoTranscurrido) {
        if (inteligencia != null) {
            inteligencia.runProceso();
        }
        super.actualizar(escenaJuego, tiempoTranscurrido);
        automata.comprobarAutomata(this);
        actualizarDireccion();
        actualizarAnimacion(tiempoTranscurrido);
        Colisiones.colisionMapa(this);
        mp.actualizar(escenaJuego, tiempoTranscurrido);
    }

    public void inicializar(Imagen imagen, Point posicion, int velodidadX, int velocidadY, HashMap<Integer, Pareja<Integer, ControlAnimacion>> animaciones, HashMap<Integer, TipoMovimiento> acciones, Automata automata, Control control) {
        super.inicializar(imagen, posicion, velodidadX, velocidadY, animaciones);
        this.acciones = acciones;
        this.automata = automata;
        Teclado.getInstance().asociar(gamePad = new GamePad(), control);
    }

    public HashMap<Integer, TipoMovimiento> getAcciones() {
        return acciones;
    }

    public boolean isPress(Botones b) {
        return gamePad.isActived(b) && gamePad.isPress(b);
    }

    public GamePad getGamePad() {
        return gamePad;
    }

    public void addInteligencia(Inteligencia inteligencia, Juego escenaJuego) {
        this.inteligencia = inteligencia;
        inteligencia.asociar(this, escenaJuego);
    }

    public Inteligencia getInteligencia() {
        return inteligencia;
    }

    public void iniciarInteligencia() throws NullPointerException {
        if (inteligencia == null) {
            throw new NullPointerException("inteligencia is null.");
        }
        inteligencia.start();
    }

    public void detenerInteligencia() throws NullPointerException {
        if (inteligencia == null) {
            throw new NullPointerException("inteligencia is null.");
        }
        inteligencia.stop();
    }

    public MovimientosPersonaje getMp() {
        return mp;
    }

    public void setEstadoTecla(Botones boton, Boolean estadoTecla) {
        gamePad.setActivated(boton, estadoTecla);
    }

    private void actualizarDireccion() {
        if (isPress(Botones.ARRIBA)) {
            velocidadY = -Math.abs(velocidadY);
        }
        if (isPress(Botones.ABAJO)) {
            velocidadY = Math.abs(velocidadY);
        }
        if (isPress(Botones.DERECHA)) {
            velocidadX = Math.abs(velocidadX);
        }
        if (isPress(Botones.IZQUIERDA)) {
            velocidadX = -Math.abs(velocidadX);
        }
    }

}
