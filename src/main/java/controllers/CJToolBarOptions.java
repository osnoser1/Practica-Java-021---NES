/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.JToolbarOptionsModels;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class CJToolBarOptions implements java.awt.event.ActionListener{

    private final JToolbarOptionsModels jToolbarOptionsModels;

    public CJToolBarOptions() {
        this.jToolbarOptionsModels = new JToolbarOptionsModels();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
/*
         switch (e.getActionCommand()) {
            case "Load map":
                this.jToolbarOptionsModels.loadMap();
                break;
            case "Save map":
                this.jToolbarOptionsModels.saveMap();
                break;
            case "Start":
                this.jToolbarOptionsModels.start();
                if (GameScreen.getInstance(null).getEnemies() != null)
                    GameScreen.getInstance(null).enableIntelligence();
                break;
        }
    */
    }
}
