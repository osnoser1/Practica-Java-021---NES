/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Dialog.Controles1;
import gui.JFramePrincipal;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class CJMenuItem implements java.awt.event.ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Controles...":
                new Controles1(null, true).setVisible(true);
                break;
        }
        System.out.println("Prueba de accion: JMenuItem");
    }
    
}
