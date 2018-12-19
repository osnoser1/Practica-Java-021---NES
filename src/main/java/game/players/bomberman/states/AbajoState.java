/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import dependencias.Sonidos;
import personajes.Bomberman;
import personajes.Personaje;
import utilidades.juego.Interfaz;
import gui.JPanelJuego;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;
import static motor.core.input.GamePad.Botones.*;

public class AbajoState implements SpriteState {

    private boolean abajo;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        abajo = gamePad.isPress(ABAJO);
        return gamePad.isPress(DERECHA)
                ? DerechaState::new : gamePad.isPress(IZQUIERDA)
                ? IzquierdaState::new : gamePad.isPress(ARRIBA)
                ? ArribaState::new : null;
    }

    @Override
    public void update(Sprite sprite, utilidades.juego.Interfaz escena, long tiempoTranscurrido) {
        if (abajo) {
            sprite.actualizarAnimacion(tiempoTranscurrido);
            ((Personaje) sprite).movimientoAbajo((JPanelJuego) escena);
        }
        if(sprite instanceof Bomberman) {
            Sonidos.getInstance().play(Sonidos.DOWN, abajo);
        }
    }

    @Override
    public void onExit(Sprite sprite, Interfaz escena) {
        if(sprite instanceof Bomberman) {
            Sonidos.getInstance().detener(Sonidos.DOWN);
        }
    }

}
