/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import GUI.JPanelJuego;
import Modelos.ModelosJToolBarOpciones;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorJToolBarOpciones implements java.awt.event.ActionListener{

    private ModelosJToolBarOpciones modelosjtoolbaropciones;

    public ControladorJToolBarOpciones() {
        this.modelosjtoolbaropciones = new ModelosJToolBarOpciones();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Cargar mapa":
                this.modelosjtoolbaropciones.loadMapa();
                break;
            case "Guardar mapa":
                this.modelosjtoolbaropciones.saveMapa();
                break;
            case "Iniciar":
                this.modelosjtoolbaropciones.start();
                if(JPanelJuego.getInstance().getenemigos()!=null)
                    JPanelJuego.getInstance().activarInteligencias();
                break;
        }
    }
    
}
