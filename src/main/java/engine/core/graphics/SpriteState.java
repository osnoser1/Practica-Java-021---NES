/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package engine.core.graphics;

import engine.core.input.GamePad;
import utils.game.Screen;

import java.util.function.Supplier;

/**
 * @author AlfonsoAndres
 */
public interface SpriteState {

    Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad);

    void update(Sprite sprite, Screen screen, long elapsedTime);

    void onExit(Sprite sprite, Screen screen);
}
