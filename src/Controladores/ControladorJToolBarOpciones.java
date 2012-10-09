/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import GUI.JPanelContenedor;
import Modelos.ModelosJToolBarOpciones;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorJToolBarOpciones implements java.awt.event.ActionListener{

    private JPanelContenedor jpanelcontenedor1;
    private ModelosJToolBarOpciones modelosjtoolbaropciones;

    public ControladorJToolBarOpciones(JPanelContenedor jpanelcontenedor1) {
        this.jpanelcontenedor1 = jpanelcontenedor1;
        this.modelosjtoolbaropciones=new ModelosJToolBarOpciones(this.jpanelcontenedor1);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "Load map":
                this.modelosjtoolbaropciones.loadMapa();
                break;
            case "Save map":
                this.modelosjtoolbaropciones.saveMapa();
                break;
            case "Start":
                this.modelosjtoolbaropciones.start();
                if(this.jpanelcontenedor1.getJPanelGrafico().getJPanelJuego().getenemigos()!=null)
                    this.jpanelcontenedor1.getJPanelGrafico().getJPanelJuego().activarInteligencias();
                break;
        }
    }
    
}
