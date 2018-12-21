/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import characters.Bomberman;
import dependencies.Sounds;
import engine.core.graphics.Sprite;
import engine.core.graphics.SpriteState;
import engine.core.graphics.spritedefaultstates.NullState;
import engine.core.input.GamePad;
import utils.game.Screen;

import java.util.function.Supplier;

/**
 * @author AlfonsoAndres
 */
public class DeathState extends game.players.states.DeathState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (!sprite.updateAnimation(elapsedTime)) {
            return;
        }
        sprite.setCurrentState(NullState::new);
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().stop();
            Sounds.getInstance().play(Sounds.JUST_DIED);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
    }

}
