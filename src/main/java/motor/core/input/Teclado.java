/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import static motor.core.input.Teclado.EstadoTecla.*;

/**
 *
 * @author AlfonsoAndrés
 */
public final class Teclado {

    @java.lang.FunctionalInterface
    public interface Consumer<One, Two, Three> {

        public Three apply(One one, Two two);
    }

    public enum EstadoTecla {
        PRESIONADA,
        LIBERADA,
    }

    private static Teclado instance;
    private final HashMap<Integer, EstadoTecla> teclas;
    private final ArrayList<Consumer<Integer, EstadoTecla, Void>> keyChanged;

    private Teclado() {
        teclas = new HashMap<>();
        keyChanged = new ArrayList<>();
    }

    public static Teclado getInstance() {
        return instance == null ? (instance = new Teclado()) : instance;
    }

    public void presionarTecla(int keyCode) {
        teclas.put(keyCode, PRESIONADA);
        keyChanged.forEach(f -> {
            f.apply(keyCode, PRESIONADA);
        });
    }

    public void liberarTecla(int keyCode) {
        teclas.put(keyCode, LIBERADA);
        keyChanged.forEach(f -> {
            f.apply(keyCode, LIBERADA);
        });
    }

    public boolean teclaPresionada(int keyCode) {
        return teclas.get(keyCode) == PRESIONADA;
    }

    public boolean teclaPresionada() {
        return teclas.containsValue(PRESIONADA);
    }

    public boolean teclaLiberada(int keyCode) {
        return !teclaPresionada(keyCode);
    }

    public void keyChangedSubscribe(Consumer<Integer, EstadoTecla, Void> function) {
        keyChanged.add(function);
    }

}
