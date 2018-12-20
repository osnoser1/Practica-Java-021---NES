/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constants;

/**
 *
 * @author AlfonsoAndrés
 */
public enum State {

    INITIAL,
    UP,
    DOWN,
    RIGHT,
    LEFT,
    DEATH,
    REMOVE(-1);
    private final int value;

    State() {
        value = ordinal();
    }

    State(int value) {
        this.value = value;
    }

    public int val() {
        return value;
    }
}
