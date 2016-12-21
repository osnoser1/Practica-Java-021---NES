/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego.constantes;

/**
 *
 * @author AlfonsoAndrés
 */
public enum Estado {

    INICIO,
    ARRIBA,
    ABAJO,
    DERECHA,
    IZQUIERDA,
    MUERTE,
    ELIMINADO(-1);
    private final int value;

    private Estado() {
        value = ordinal();
    }

    private Estado(int value) {
        this.value = value;
    }

    public int val() {
        return value;
    }
}
