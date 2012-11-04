/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import java.util.HashMap;

/**
 *
 * @author AlfonsoAndrés
 */
public class Teclado {
    
    public enum EstadoTecla{
        PRESIONADA,
        LIBERADA,
    }
    
    private static Teclado instance;
    private HashMap<Integer, EstadoTecla> teclas;

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
        return teclas.containsKey(keyCode) && teclas.get(keyCode) == EstadoTecla.PRESIONADA;
    }
    
    public boolean teclaPresionada() {
        for(EstadoTecla estado : teclas.values()) {
            if(estado == EstadoTecla.PRESIONADA)
                return true;
        }
        return false;
    }
    
    public boolean teclaLiberada(int keyCode){
        return !teclaPresionada(keyCode);
    }
}
