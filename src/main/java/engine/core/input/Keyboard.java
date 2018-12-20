/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.input;

import java.util.ArrayList;
import java.util.HashMap;

import static engine.core.input.Keyboard.KeyState.*;

/**
 *
 * @author AlfonsoAndrés
 */
public final class Keyboard {

    @java.lang.FunctionalInterface
    public interface Consumer<One, Two, Three> {

        Three apply(One one, Two two);
    }

    public enum KeyState {
        PRESSED,
        RELEASED,
    }

    private static Keyboard instance;
    private final HashMap<Integer, KeyState> keys;
    private final ArrayList<Consumer<Integer, KeyState, Void>> keyChanged;

    private Keyboard() {
        keys = new HashMap<>();
        keyChanged = new ArrayList<>();
    }

    public static Keyboard getInstance() {
        return instance == null ? (instance = new Keyboard()) : instance;
    }

    public void keyPress(int keyCode) {
        keys.put(keyCode, PRESSED);
        keyChanged.forEach(f -> f.apply(keyCode, PRESSED));
    }

    public void keyRelease(int keyCode) {
        keys.put(keyCode, RELEASED);
        keyChanged.forEach(f -> f.apply(keyCode, RELEASED));
    }

    public boolean isKeyPressed(int keyCode) {
        return keys.get(keyCode) == PRESSED;
    }

    public boolean isKeyPressed() {
        return keys.containsValue(PRESSED);
    }

    public boolean isKeyReleased(int keyCode) {
        return !isKeyPressed(keyCode);
    }

    public void keyChangedSubscribe(Consumer<Integer, KeyState, Void> function) {
        keyChanged.add(function);
    }

}
