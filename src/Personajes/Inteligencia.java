/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import gui.JPanelJuego;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.Timer;
import juego.constantes.Estado;
import motor.core.input.GamePad;
import motor.core.input.GamePad.Botones;
import static motor.core.input.GamePad.Botones.*;

/**
 *
 * @author hp
 */
public class Inteligencia {

    private final ArrayList<Botones> buffer;
    private final Personaje personaje;
    private final GamePad gamePad;
    private Timer timer;
    private final Random random;

    private int a = 1, time = -1;

    public Inteligencia(Personaje personaje) {
        this.personaje = personaje;
        this.gamePad = personaje.gamePad;
        this.buffer = new ArrayList<>();
        random = new Random();
        determinarInteligencia();
    }

    private void determinarInteligencia() {
        timer = personaje.smart == Personaje.SMART_LOW ? new Timer(100, e -> {
            if (++time % 20 == 0) {
                bufferProcess((a = random.nextInt(4)) == 0
                        ? IZQUIERDA : a == 1 ? DERECHA : a == 2 ? ARRIBA : ABAJO);
            }
            if (JPanelJuego.getInstance(null).primerJugador().getEstadoActual() == Estado.MUERTE.val()) {
                timer.stop();
            }
        }) : personaje.smart == Personaje.SMART_MID || personaje.smart == Personaje.SMART_HIGH ? new Timer(100, e -> {
            if (++time % 20 == 0) {
                bufferProcess(random.nextInt(2) == 0 ? IZQUIERDA : DERECHA,
                        random.nextInt(2) == 0 ? ARRIBA : ABAJO);
            }
            if (JPanelJuego.getInstance(null).primerJugador().getEstadoActual() == Estado.MUERTE.val()) {
                timer.stop();
            }
        }) : null;
    }

    public Timer getTimer() {
        return timer;
    }

    public void detenerInteligencia() {
        if (timer == null) {
            return;
        }
        timer.stop();
    }

    public void iniciar() {
        if (timer == null) {
            return;
        }
        timer.start();
    }

    private void bufferProcess(Botones... botones) {
        bufferClear();
        buffer.addAll(Arrays.asList(botones));
        bufferApply();
    }

    private void bufferClear() {
        buffer.forEach(t -> {
            gamePad.setPress(t, false);
        });
        buffer.clear();
    }

    private void bufferApply() {
        buffer.forEach(t -> {
            gamePad.setPress(t, true);
        });
    }

}
