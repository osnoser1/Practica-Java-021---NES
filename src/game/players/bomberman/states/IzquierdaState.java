/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.players.bomberman.states;

import Personajes.Personaje;
import gui.JPanelJuego;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.input.GamePad;
import static motor.core.input.GamePad.Botones.*;

/**
 * 
 * @author AlfonsoAndres
 */
public class IzquierdaState implements SpriteState {

    private boolean isPress;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        isPress = gamePad.isPress(IZQUIERDA);
        return gamePad.isPress(DERECHA)
                ? DerechaState::new : gamePad.isPress(ARRIBA)
                ? ArribaState::new : gamePad.isPress(ABAJO)
                ? AbajoState::new : null;
    }

    @Override
    public void update(Sprite sprite, Utilidades.Juego.Interfaz escena, long tiempoTranscurrido) {
        if (isPress) {
//            Sonidos.getInstance().play(Sonidos.LEFT);
            sprite.actualizarAnimacion(tiempoTranscurrido);
            ((Personaje) sprite).movimientoIzquierda((JPanelJuego) escena);
        } else  {
//            Sonidos.getInstance().detener(Sonidos.LEFT);
        }
    }

}
