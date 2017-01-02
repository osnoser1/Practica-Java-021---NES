/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.players.bomb.states;

import Personajes.Bomb;
import Utilidades.Juego.Interfaz;
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
        return ((Bomb) sprite).isExplosionEnded() ? NullState::new : null;
    }

    @Override
    public void update(Sprite sprite, Interfaz escena, long tiempoTranscurrido) {
    }

    @Override
    public void onExit(Sprite sprite, Interfaz escena) {
    }

}
