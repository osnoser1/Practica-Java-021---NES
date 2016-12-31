/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import Personajes.Personaje;
import gui.JPanelJuego;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;
import static motor.core.input.GamePad.Botones.*;

public class DerechaState implements SpriteState {

    private boolean arriba, abajo, derecha;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        arriba = gamePad.isPress(ARRIBA);
        abajo = gamePad.isPress(ABAJO);
        return (derecha = gamePad.isPress(DERECHA))
                ? null : gamePad.isPress(IZQUIERDA)
                ? IzquierdaState::new : arriba
                ? ArribaState::new : abajo
                ? AbajoState::new : null;
    }

    @Override
    public void update(Sprite sprite, Utilidades.Juego.Interfaz escena, long tiempoTranscurrido) {
        if (arriba || abajo || derecha) {
            sprite.actualizarAnimacion(tiempoTranscurrido);
        }
        Personaje p = ((Personaje) sprite);
        if (derecha) {
            p.movimientoDerecha((JPanelJuego) escena);
        }
        if(arriba) {
            p.movimientoArriba((JPanelJuego) escena);
        } else if(abajo) {
            p.movimientoAbajo((JPanelJuego) escena);
        }
    }

}
