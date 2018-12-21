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

public class RightState implements SpriteState {

    private boolean up, down, right;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        up = gamePad.isPress(UP);
        down = gamePad.isPress(DOWN);
        return (right = gamePad.isPress(RIGHT))
                ? null : gamePad.isPress(LEFT)
                ? LeftState::new : up
                ? UpState::new : down
                ? DownState::new : null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (up || down || right) {
            sprite.updateAnimation(elapsedTime);
        }
        var p = ((Character) sprite);
        if (right) {
            p.moveRight((GameScreen) screen);
        }
        if (up) {
            p.moveUp((GameScreen) screen);
        } else if (down) {
            p.moveDown((GameScreen) screen);
        }
        if (!(sprite instanceof Bomberman)) {
            return;
        }
        Sounds.getInstance().play(Sounds.UP, up);
        Sounds.getInstance().play(Sounds.DOWN, !up && down);
        Sounds.getInstance().play(Sounds.LEFT, right);
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().stop(Sounds.LEFT);
        }
    }

}
