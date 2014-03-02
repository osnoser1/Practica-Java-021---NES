/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.util.HashMap;
import motor.core.EstadosSprite.Estado;
import static motor.core.GamePad.Botones.*;
import motor.core.escenas.Juego;

/**
 *
 *
 */
public class MovimientosPersonaje {

    public enum TipoMovimiento {
        DESPLAZAR_X,
        DESPLAZAR_Y,
        NINGUNO,
    }

    private final Personaje personaje;
    public boolean colisionDerecha, colisionIzquierda, colisionArriba, colisionAbajo;

    public MovimientosPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public void actualizar(Juego escenaJuego, long tiempoTranscurrido) {
        final HashMap<Integer, TipoMovimiento> acciones = personaje.getAcciones();
        final Estado estadoActual = personaje.getEstadoActual();
        final TipoMovimiento tm = acciones.get(estadoActual.ordinal());
        switch (tm) {
            case DESPLAZAR_X:
                verificarDesplazamientoX();
                break;
            case DESPLAZAR_Y:
                verificarDesplazamientoY();
                break;
		default:
			break;
        }
    }

    public void desplazarseX() {
//        if (personaje.isPress(IZQUIERDA)) {
//            personaje.setVelocidadX(-Math.abs(personaje.getVelocidadX()));
//        } else {
//            personaje.setVelocidadX(Math.abs(personaje.getVelocidadX()));
//        }
        personaje.trasladar(personaje.getVelocidadX(), 0);
    }

    public void desplazarseY() {
//        if (personaje.isPress(ARRIBA)) {
//            personaje.setVelocidadY(-Math.abs(personaje.getVelocidadY()));
//        } else {
//            personaje.setVelocidadY(Math.abs(personaje.getVelocidadY()));
//        }
        personaje.trasladar(0, personaje.getVelocidadY());
    }

    private void verificarDesplazamientoX() {
        if (personaje.isPress(IZQUIERDA) && !colisionIzquierda || personaje.isPress(DERECHA) && !colisionDerecha) {
            desplazarseX();
        }
    }

    private void verificarDesplazamientoY() {
        if (personaje.isPress(ARRIBA) && !colisionArriba || personaje.isPress(ABAJO) && !colisionAbajo) {
            desplazarseY();
        }
    }

}
