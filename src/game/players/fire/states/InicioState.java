package game.players.fire.states;

import Utilidades.Juego.Interfaz;
import gui.JPanelJuego;
import java.util.function.Supplier;
import motor.core.graphics.Sprite;
import motor.core.graphics.SpriteState;
import motor.core.graphics.spritedefaultstates.NullState;
import motor.core.input.GamePad;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * @author AlfonsoAndres
 */
public class InicioState extends game.players.states.InicioState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Interfaz escena, long tiempoTranscurrido) {
        if (sprite.actualizarAnimacion(tiempoTranscurrido)) {
            ((JPanelJuego) escena).getMapa().remover(sprite);
            sprite.setEstadoActual(NullState::new);
        }
    }

}
