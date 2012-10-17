/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controladores.ControladorTecladoJPanelFinDeJuego;
import java.awt.Color;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelFinDeJuego extends javax.swing.JPanel{
    
    private static JPanelFinDeJuego instance;

    private JPanelFinDeJuego() {
        super();
        initComponents();
    }

    public static JPanelFinDeJuego getInstance() {
        return instance == null ? (instance = new JPanelFinDeJuego()) : instance;
    }
    
    private void initComponents() {
        addKeyListener(new ControladorTecladoJPanelFinDeJuego());
        setBackground(Color.BLACK);
    }

    public void iniciarJPanelFinDeJuego() {
        
    }
    
}
