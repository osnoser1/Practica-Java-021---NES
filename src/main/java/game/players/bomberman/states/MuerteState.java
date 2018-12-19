/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import dependencias.Sonidos;
import personajes.Bomberman;
import utilidades.juego.Interfaz;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.graphics.spritedefaultstates.NullState;
import motor.core.input.GamePad;

/**
 *
 * @author AlfonsoAndres
 */
public class MuerteState extends game.players.states.MuerteState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return null;
    }

    @Override
    public void update(Sprite sprite, utilidades.juego.Interfaz escena, long tiempoTranscurrido) {
        if (!sprite.actualizarAnimacion(tiempoTranscurrido)) {
            return;
        }
        sprite.setEstadoActual(NullState::new);
        if (sprite instanceof Bomberman) {
            Sonidos.getInstance().detener();
            Sonidos.getInstance().play(Sonidos.JUST_DIED);
        }
    }

    @Override
    public void onExit(Sprite sprite, Interfaz escena) {
    }

}
