/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Bomberman.Core.Constantes;
import GUI.BotonEspecial;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorPanelEspecial implements java.awt.event.ActionListener, Constantes{

    private BotonEspecial botonespecial1;
    
    public ControladorPanelEspecial(BotonEspecial botonespecial1) {
        this.botonespecial1 = botonespecial1;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Prueba de accion: Boton Especial");
        CJEditorDeMapas.getInstance().setObjetoSeleccionado(Objetos.valueOf(botonespecial1.getName()).getValue());
    }
    
}
