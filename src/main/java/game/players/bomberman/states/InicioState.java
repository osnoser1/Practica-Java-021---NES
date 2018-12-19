/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.players.bomberman.states;

import utilidades.juego.Interfaz;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;

/**
 * 
 * @author AlfonsoAndres
 */
public class InicioState extends game.players.states.InicioState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return IzquierdaState::new;
    }

    @Override
    public void update(Sprite sprite, utilidades.juego.Interfaz escena, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onExit(Sprite sprite, Interfaz escena) {
    }

}
