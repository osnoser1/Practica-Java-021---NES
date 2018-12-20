/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dialog.Controles1;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class CJMenuItem implements java.awt.event.ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Controles...".equals(e.getActionCommand())) {
            new Controles1(null, true).setVisible(true);
        }
        System.out.println("Action test: JMenuItem");
    }
    
}
