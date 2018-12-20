/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.graphics.spritedefaultstates;

import utilidades.juego.Screen;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;

/**
 *
 * @author AlfonsoAndres
 */
public class NullState implements SpriteState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen escena, long tiempoTranscurrido) {
    }

    @Override
    public void onExit(Sprite sprite, Screen escena) {
    }

}
