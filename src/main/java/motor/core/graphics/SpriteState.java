/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package motor.core.graphics;

import Utilidades.Juego.Interfaz;
import java.util.function.Supplier;
import motor.core.input.GamePad;

/**
 * 
 * @author AlfonsoAndres
 */
public interface SpriteState {

    Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad);
    void update(Sprite sprite, Interfaz escena, long tiempoTranscurrido);
    void onExit(Sprite sprite, Interfaz escena);
}
