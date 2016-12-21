/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelFinDeJuego {
    
    private static JPanelFinDeJuego instance;

    private JPanelFinDeJuego() {
        initComponents();
    }

    public static JPanelFinDeJuego getInstance() {
        return instance == null ? (instance = new JPanelFinDeJuego()) : instance;
    }
    
    private void initComponents() { }
    
}
