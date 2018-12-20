/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import gui.SpecialButton;
import java.awt.event.ActionEvent;
import game.constants.Objects;

/**
 *
 * @author Alfonso Andrés
 */
public class CSpecialPanel implements java.awt.event.ActionListener {

    private final SpecialButton specialButton;
    
    public CSpecialPanel(SpecialButton specialButton) {
        this.specialButton = specialButton;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action test: Special Button");
        CMapEditorScreen.getInstance().setSelectedObject(Objects.valueOf(specialButton.getName()).getValue());
    }
    
}
