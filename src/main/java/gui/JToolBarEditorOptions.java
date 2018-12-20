/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import controllers.CJToolBarOptions;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Alfonso Andrés
 */
public class JToolBarEditorOptions extends JToolBar{
    
    private static JToolBarEditorOptions instance;
    private final CJToolBarOptions cjToolBarOptions;
    private final int BUTTONS_LENGTH = 3;
    
    public static JToolBarEditorOptions getInstance() {
        return instance == null ? (instance = new JToolBarEditorOptions()) : instance;
    }

    private JToolBarEditorOptions() {
        super();
        this.cjToolBarOptions = new CJToolBarOptions();
        initComponents();
    }
    
    private void initComponents() {
        this.setPreferredSize(new Dimension(640,80));
        this.setLayout(new java.awt.FlowLayout(30,30,30));
        this.setBorderPainted(false);
        initJButton();
    }

    private void initJButton() {
        for(var i = 0; i < this.BUTTONS_LENGTH; i++){
            var jButton = new JButton(getTitle(i));
            jButton.setActionCommand(getTitle(i));
            jButton.addActionListener(cjToolBarOptions);
            this.add(jButton);
        }
    }

    private String getTitle(int i) {
        var option = 0;
        if(option++ == i) return "Load map";
        if(option++ == i) return "Save map";
        return "Start";
    }

}
