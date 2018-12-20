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

/**
 * 
 * @author AlfonsoAndres
 */
public class LeftState implements SpriteState {

    private boolean up, down, left;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        up = gamePad.isPress(UP);
        down = gamePad.isPress(DOWN);
        return gamePad.isPress(RIGHT)
                ? RightState::new : (left = gamePad.isPress(LEFT))
                ? null : up
                ? UpState::new : down
                ? DownState::new : null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if(left || up || down) {
            sprite.updateAnimation(elapsedTime);
        }
        var p = (Character) sprite;
        if (left) {
            p.moveLeft((GameScreen) screen);
        } 
        if(up) {
            p.moveUp((GameScreen) screen);
        } else if(down) {
            p.moveDown((GameScreen) screen);
        }
        if(!(sprite instanceof Bomberman)) {
            return;
        }
        Sounds.getInstance().play(Sounds.UP, up);
        Sounds.getInstance().play(Sounds.DOWN, !up && down);
        Sounds.getInstance().play(Sounds.RIGHT, left);
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
        if(sprite instanceof Bomberman) {
            Sounds.getInstance().stop(Sounds.RIGHT);
        }
    }

}
