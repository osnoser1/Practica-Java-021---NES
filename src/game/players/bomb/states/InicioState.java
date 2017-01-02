/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.players.bomb.states;

import Personajes.Bomb;
import Utilidades.Juego.Interfaz;
import gui.JPanelJuego;
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
        return null;
    }

    @Override
    public void update(Sprite sprite, Interfaz escena, long tiempoTranscurrido) {
        update((Bomb) sprite, (JPanelJuego) escena, tiempoTranscurrido);
    }

    private void update(Bomb bomb, JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        bomb.actualizarAnimacion(tiempoTranscurrido);
        if (bomb.isTimeOver()) {
            bomb.detonar(jPanelJuego);
        }
    }

    @Override
    public void onExit(Sprite sprite, Interfaz escena) {
    }

}
