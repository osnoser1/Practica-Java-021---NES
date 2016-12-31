/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import Dependencias.Sonidos;
import Personajes.Bomberman;
import Personajes.Personaje;
import Utilidades.Juego.Interfaz;
import gui.JPanelJuego;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;
import static motor.core.input.GamePad.Botones.*;

public class ArribaState implements SpriteState {

    private boolean arriba;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        arriba = gamePad.isPress(ARRIBA);
        return gamePad.isPress(DERECHA)
                ? DerechaState::new : gamePad.isPress(IZQUIERDA)
                ? IzquierdaState::new : arriba
                ? null : gamePad.isPress(ABAJO)
                ? AbajoState::new : null;
    }

    @Override
    public void update(Sprite sprite, Interfaz escena, long tiempoTranscurrido) {
        if (arriba) {
            sprite.actualizarAnimacion(tiempoTranscurrido);
            ((Personaje) sprite).movimientoArriba((JPanelJuego) escena);
        }
        if(sprite instanceof Bomberman) {
            Sonidos.getInstance().play(Sonidos.UP, arriba);
        }
    }

    @Override
    public void onExit(Sprite sprite, Interfaz escena) {
        if(sprite instanceof Bomberman) {
            Sonidos.getInstance().detener(Sonidos.UP);
        }
    }

}
