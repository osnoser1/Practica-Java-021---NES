/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.ladrillo.states;

import personajes.Brick;
import utilidades.juego.Screen;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.graphics.spritedefaultstates.NullState;
import motor.core.input.GamePad;

/**
 *
 * @author AlfonsoAndres
 */
public class DeathState extends game.players.states.DeathState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (sprite.updateAnimation(elapsedTime)) {
            sprite.setCurrentState(NullState::new);
            ((Brick) sprite).activateSpecialBrick();
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
    }

}
