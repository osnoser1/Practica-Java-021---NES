/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import characters.Bomberman;
import characters.Character;
import dependencies.Sounds;
import engine.core.graphics.Sprite;
import engine.core.graphics.SpriteState;
import engine.core.input.GamePad;
import gui.GameScreen;
import utils.game.Screen;

import java.util.function.Supplier;

import static engine.core.input.GamePad.Buttons.*;

public class DownState implements SpriteState {

    private boolean down;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        down = gamePad.isPress(DOWN);
        return gamePad.isPress(RIGHT)
                ? RightState::new : gamePad.isPress(LEFT)
                ? LeftState::new : gamePad.isPress(UP)
                ? UpState::new : null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (down) {
            sprite.updateAnimation(elapsedTime);
            ((Character) sprite).moveDown((GameScreen) screen);
        }
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().play(Sounds.DOWN, down);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().stop(Sounds.DOWN);
        }
    }

}
