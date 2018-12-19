/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.players.bomberman.states;

import dependencias.Sonidos;
import personajes.Bomberman;
import personajes.Personaje;
import utilidades.juego.Interfaz;
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

    private boolean arriba, abajo, izquierda;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, GamePad gamePad) {
        arriba = gamePad.isPress(ARRIBA);
        abajo = gamePad.isPress(ABAJO);
        return gamePad.isPress(DERECHA) 
                ? DerechaState::new : (izquierda = gamePad.isPress(IZQUIERDA))
                ? null : arriba
                ? ArribaState::new : abajo
                ? AbajoState::new : null;
    }

    @Override
    public void update(Sprite sprite, utilidades.juego.Interfaz escena, long tiempoTranscurrido) {
        if(izquierda || arriba || abajo) {
            sprite.actualizarAnimacion(tiempoTranscurrido);
        }
        var p = (Personaje) sprite;
        if (izquierda) {
            p.movimientoIzquierda((JPanelJuego) escena);
        } 
        if(arriba) {
            p.movimientoArriba((JPanelJuego) escena);
        } else if(abajo) {
            p.movimientoAbajo((JPanelJuego) escena);
        }
        if(!(sprite instanceof Bomberman)) {
            return;
        }
        Sonidos.getInstance().play(Sonidos.UP, arriba);
        Sonidos.getInstance().play(Sonidos.DOWN, !arriba && abajo);
        Sonidos.getInstance().play(Sonidos.RIGHT, izquierda);
    }

    @Override
    public void onExit(Sprite sprite, Interfaz escena) {
        if(sprite instanceof Bomberman) {
            Sonidos.getInstance().detener(Sonidos.RIGHT);
        }
    }

}
