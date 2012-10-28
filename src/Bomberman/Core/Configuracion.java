/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Bomberman.Core;

import java.awt.Dimension;

/**
 *
 * @author AlfonsoAndrés
 */
public class Configuracion {
    
    private static Configuracion instance;
    public Dimension tamañoVentana = new Dimension(656,620);
    
    private Configuracion() { }

    public static Configuracion getInstance() {
        return instance == null ? (instance = new Configuracion()) : instance;
    }
    
}
