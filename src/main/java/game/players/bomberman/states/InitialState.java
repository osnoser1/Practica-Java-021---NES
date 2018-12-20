/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.players.bomberman.states;

import utilidades.juego.Screen;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;

/**
 * 
 * @author AlfonsoAndres
 */
public class InitialState extends game.players.states.InitialState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return LeftState::new;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
    }

}
