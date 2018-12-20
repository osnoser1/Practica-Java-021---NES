/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomberman.states;

import dependencias.Sounds;
import gui.GameScreen;
import personajes.Bomberman;
import personajes.Character;
import utilidades.juego.Screen;

import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;
import static motor.core.input.GamePad.Buttons.*;

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
        if(sprite instanceof Bomberman) {
            Sounds.getInstance().play(Sounds.UP, up);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
        if(sprite instanceof Bomberman) {
            Sounds.getInstance().stop(Sounds.UP);
        }
    }

}
