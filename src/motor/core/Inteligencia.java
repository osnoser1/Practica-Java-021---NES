/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import concurrencia.Runnable2;
import motor.core.escenas.Juego;

/**
 *
 * @author hp
 */
public abstract class Inteligencia extends Runnable2 {

    protected Personaje personaje;
    protected GamePad gamePad;
    protected Modo modoActual;
    protected Juego escenaJuego;

    public Inteligencia(Modo modo) {
        super(60);
        modoActual = modo;
    }

    public void asociar(Personaje personaje, Juego escenaJuego) {
        this.personaje = personaje;
        gamePad = personaje.getGamePad();
        this.escenaJuego = escenaJuego;
    }

    public void desasociar() {
        personaje = null;
        gamePad = null;
        escenaJuego = null;
    }

    Modo getModo() {
        return modoActual;
    }

    public enum Modo {
        HILO, SECUENCIAL
    }

    @Override
    public void start() throws IllegalThreadStateException {
        if(modoActual == Modo.HILO)
            super.start();
        estaActivo = true;
    }

    @Override
    public void pausar() {
        if(modoActual == Modo.HILO)
            super.pausar();
        pausa = true;
    }

}
