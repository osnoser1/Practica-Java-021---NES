/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelos.ModelosJToolBarOpciones;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class CJToolBarOpciones implements java.awt.event.ActionListener{

    private final ModelosJToolBarOpciones modelosjtoolbaropciones;

    public CJToolBarOpciones() {
        this.modelosjtoolbaropciones = new ModelosJToolBarOpciones();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
/*
         switch (e.getActionCommand()) {
            case "Cargar mapa":
                this.modelosjtoolbaropciones.loadMapa();
                break;
            case "Guardar mapa":
                this.modelosjtoolbaropciones.saveMapa();
                break;
            case "Iniciar":
                this.modelosjtoolbaropciones.start();
                if (JPanelJuego.getInstance(null).getEnemigos() != null)
                    JPanelJuego.getInstance(null).activarInteligencias();
                break;
        }
    */
    }
}
