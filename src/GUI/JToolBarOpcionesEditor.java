/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controladores.ControladorJToolBarOpciones;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Alfonso Andrés
 */
public class JToolBarOpcionesEditor extends JToolBar{

    private ControladorJToolBarOpciones controladorJToolBarOpciones;
    private final int CANTIDAD_BOTONES=3;

    JToolBarOpcionesEditor(ControladorJToolBarOpciones controladorJToolBarOpciones) {
        super();
        this.controladorJToolBarOpciones=controladorJToolBarOpciones;
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(640,80));
        this.setLayout(new java.awt.FlowLayout(30,30,30));
        this.setBorderPainted(false);
        initJButton();
    }

    private void initJButton() {
        for(int i=0;i<this.CANTIDAD_BOTONES;i++){
            JButton jButton=new JButton(getTitle(i));
            jButton.setActionCommand(getTitle(i));
            jButton.addActionListener(controladorJToolBarOpciones);
            this.add(jButton);
        }
    }

    private String getTitle(int i) {
        int opcion=0;
        if(opcion++==i) return "Load map";
        else if(opcion++==i) return "Save map";
        return "Start";
    }

}
