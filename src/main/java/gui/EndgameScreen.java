/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

/**
 *
 * @author Alfonso Andrés
 */
public class EndgameScreen {
    
    private static EndgameScreen instance;

    private EndgameScreen() {
        initComponents();
    }

    public static EndgameScreen getInstance() {
        return instance == null ? (instance = new EndgameScreen()) : instance;
    }
    
    private void initComponents() { }
    
}
