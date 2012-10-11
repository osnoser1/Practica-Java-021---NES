/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bomberman.Core;

/**
 *
 * @author AlfonsoAndrés
 */
public class Configuracion {
    
    private static Configuracion instance;
    
    private Configuracion() { }

    public static Configuracion getInstance() {
        return instance == null ? (instance = new Configuracion()) : instance;
    }
    
}
