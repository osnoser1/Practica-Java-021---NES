/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.input;

import java.util.HashMap;

/**
 *
 * @author AlfonsoAndrés
 */
public final class Teclado {
    
    public enum EstadoTecla{
        PRESIONADA,
        LIBERADA,
    }
    
    private static Teclado instance;
    private final HashMap<Integer, EstadoTecla> teclas;

    private Teclado() {
        teclas = new HashMap<>();
    }

    public static Teclado getInstance() {
        return instance == null ? (instance = new Teclado()) : instance;
    }
    
    public void presionarTecla(int keyCode){
        teclas.put(keyCode, EstadoTecla.PRESIONADA);
    }
    
    public void liberarTecla(int keyCode){
        teclas.put(keyCode, EstadoTecla.LIBERADA);
    }
    
    public boolean teclaPresionada(int keyCode){
        return teclas.get(keyCode) == EstadoTecla.PRESIONADA;
    }
    
    public boolean teclaPresionada() {
        return teclas.containsValue(EstadoTecla.PRESIONADA);
    }
    
    public boolean teclaLiberada(int keyCode){
        return !teclaPresionada(keyCode);
    }
}
