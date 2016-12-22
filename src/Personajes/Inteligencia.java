/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import gui.JPanelJuego;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.swing.Timer;
import juego.constantes.Estado;
import static juego.constantes.Estado.*;

/**
 *
 * @author hp
 */
public class Inteligencia {

    private final Personaje personaje;
    private Timer timer;
    private Random random;

    int a = 1, b = 1, time = 0;

    public Inteligencia(Personaje personaje) {
        this.personaje = personaje;
        random = new Random();
        determinarInteligencia();
    }

    private void determinarInteligencia() {
        timer = personaje.smart == Personaje.SMART_LOW ? new Timer(50, e -> {
            if (++time % 50 == 0) {
                a = ((a = random.nextInt(2)) == 0
                        ? IZQUIERDA : a == 1 ? DERECHA : a == 2 ? ARRIBA : ABAJO).val();
            }
            movimientoEje(a, JPanelJuego.getInstance(null));
            if (JPanelJuego.getInstance(null).primerJugador().getEstadoActual() == MUERTE.val()) {
                timer.stop();
            }
        }) : personaje.smart == Personaje.SMART_MID || personaje.smart == Personaje.SMART_HIGH ? new Timer(50, e -> {
            if (++time % 50 == 0) {
                    a = (random.nextInt(2) == 0 ? IZQUIERDA : DERECHA).val();
                    b = (random.nextInt(2) == 0 ? ARRIBA : ABAJO).val();
            }
            movimientoEje(a, JPanelJuego.getInstance(null));
            movimientoEje(b, JPanelJuego.getInstance(null));
            if (JPanelJuego.getInstance(null).primerJugador().getEstadoActual() == MUERTE.val()) {
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

    private void movimientoEje(int direccion, JPanelJuego jPanelJuego) {
        personaje.setEstadoActual(direccion);
        Consumer<JPanelJuego> f = (direccion == ARRIBA.val() ? personaje::movimientoArriba
                : direccion == ABAJO.val() ? personaje::movimientoAbajo
                : direccion == DERECHA.val() ? personaje::movimientoDerecha
                : personaje::movimientoIzquierda);
        f.accept(jPanelJuego);
    }

}
