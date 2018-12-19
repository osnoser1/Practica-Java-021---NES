/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package motor.core.map;

import motor.core.graphics.Sprite;

/**
 * 
 * @author AlfonsoAndres
 */
class PosicionSprite {

    private final Sprite sprite;
    private Posicion posicionActual;
    private Posicion posicionAnterior;

    public PosicionSprite(Sprite sprite) {
        var centro = sprite.getCentro();
        this.sprite = sprite;
        this.posicionActual = new Posicion(centro.y / sprite.getAlto(), centro.x / sprite.getAncho());
        this.posicionAnterior = (Posicion) posicionActual.clone();
    }
    
    public boolean actualizar() {
        var centro = sprite.getCentro();
        var nuevaPosicionActual = new Posicion(centro.y / sprite.getAlto(), centro.x / sprite.getAncho());
        if(posicionActual.equals(nuevaPosicionActual)) {
            return false;
        }
        posicionAnterior = posicionActual;
        posicionActual = nuevaPosicionActual;
        return true;
    }

    public Posicion getPosicionActual() {
        return posicionActual;
    }

    public Posicion getPosicionAnterior() {
        return posicionAnterior;
    }

}
