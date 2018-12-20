/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomb.states;

import characters.Bomb;
import utils.game.Screen;
import gui.GameScreen;
import java.util.function.Supplier;
import engine.core.graphics.Sprite;
import engine.core.graphics.SpriteState;
import engine.core.input.GamePad;

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
        update((Bomb) sprite, (GameScreen) screen, elapsedTime);
    }

    private void update(Bomb bomb, GameScreen gameScreen, long elapsedTime) {
        bomb.updateAnimation(elapsedTime);
        if (bomb.isTimeOver()) {
            bomb.detonate(gameScreen);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
    }

}
