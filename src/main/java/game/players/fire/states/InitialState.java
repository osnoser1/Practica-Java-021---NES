package game.players.fire.states;

import utils.game.Screen;
import engine.core.graphics.Sprite;
import engine.core.graphics.SpriteState;
import engine.core.graphics.spritedefaultstates.NullState;
import engine.core.input.GamePad;
import gui.GameScreen;

import java.util.function.Supplier;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author AlfonsoAndres
 */
public class InitialState extends game.players.states.InitialState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (sprite.updateAnimation(elapsedTime)) {
            ((GameScreen) screen).getMap().delete(sprite);
            sprite.setCurrentState(NullState::new);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
    }

}
