/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import gui.BotonEspecial;
import java.awt.event.ActionEvent;
import juego.constantes.Objetos;

/**
 *
 * @author Alfonso Andrés
 */
public class CPanelEspecial implements java.awt.event.ActionListener {

    private final BotonEspecial botonespecial1;
    
    public CPanelEspecial(BotonEspecial botonespecial1) {
        this.botonespecial1 = botonespecial1;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Prueba de accion: Boton Especial");
        CJEditorDeMapas.getInstance().setObjetoSeleccionado(Objetos.valueOf(botonespecial1.getName()).getValue());
    }
    
}
