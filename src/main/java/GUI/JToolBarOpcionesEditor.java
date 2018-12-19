/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Controladores.CJToolBarOpciones;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Alfonso Andrés
 */
public class JToolBarOpcionesEditor extends JToolBar{
    
    private static JToolBarOpcionesEditor instance;
    private final CJToolBarOpciones controladorJToolBarOpciones;
    private final int CANTIDAD_BOTONES = 3;
    
    public static JToolBarOpcionesEditor getInstance() {
        return instance == null ? (instance = new JToolBarOpcionesEditor()) : instance;
    }

    private JToolBarOpcionesEditor() {
        super();
        this.controladorJToolBarOpciones = new CJToolBarOpciones();
        initComponents();
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(640,80));
        this.setLayout(new java.awt.FlowLayout(30,30,30));
        this.setBorderPainted(false);
        initJButton();
    }

    private void initJButton() {
        for(var i = 0; i < this.CANTIDAD_BOTONES; i++){
            var jButton = new JButton(getTitle(i));
            jButton.setActionCommand(getTitle(i));
            jButton.addActionListener(controladorJToolBarOpciones);
            this.add(jButton);
        }
    }

    private String getTitle(int i) {
        var opcion = 0;
        if(opcion++ == i) return "Cargar mapa";
        if(opcion++ == i) return "Guardar mapa";
        return "Iniciar";
    }

}
