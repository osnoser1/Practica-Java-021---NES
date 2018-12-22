/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import characters.Bomberman;
import characters.Character;
import dependencies.Sounds;
import engine.core.game.Screen;
import engine.core.graphics.Sprite;
import engine.core.graphics.SpriteState;
import engine.core.input.GamePad;
import gui.GameScreen;

import java.util.function.Supplier;

import static engine.core.input.GamePad.Buttons.*;

public class UpState implements SpriteState {

    private boolean up;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        up = gamePad.isPress(UP);
        return gamePad.isPress(RIGHT)
                ? RightState::new : gamePad.isPress(LEFT)
                ? LeftState::new : up
                ? null : gamePad.isPress(DOWN)
                ? DownState::new : null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (up) {
            sprite.updateAnimation(elapsedTime);
            ((Character) sprite).moveUp((GameScreen) screen);
        }
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().play(Sounds.UP, up);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().stop(Sounds.UP);
        }
    }

}
