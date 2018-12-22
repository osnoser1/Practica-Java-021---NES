/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.graphics.spritedefaultstates;

import engine.core.game.Screen;
import engine.core.graphics.Sprite;
import engine.core.graphics.SpriteState;
import engine.core.input.GamePad;

import java.util.function.Supplier;

/**
 * @author AlfonsoAndres
 */
public class NullState implements SpriteState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
    }

}
