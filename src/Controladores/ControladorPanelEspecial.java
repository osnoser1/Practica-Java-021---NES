/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Dependencias.Mapa;
import GUI.BotonEspecial;
import java.awt.event.ActionEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorPanelEspecial implements java.awt.event.ActionListener{

    private BotonEspecial botonespecial1;
    
    public ControladorPanelEspecial(BotonEspecial botonespecial1) {
        this.botonespecial1 = botonespecial1;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Prueba de accion: Boton Especial");
        String opcion="V";
        switch(this.botonespecial1.getName()){
            case "Bomberman":
                opcion="B";
                break;
            case "Ballom":
                opcion="b";
                break;
            case "Oneal":
                opcion="O";
                break;
            case "Doll":
                opcion="D";
                break;
            case "Minvo":
                opcion="M";
                break;
            case "Kondoria":
                opcion="K";
                break;
            case "Ovapi":
                opcion="o";
                break;
            case "Pass":
                opcion="P";
                break;
            case "Pontan":
                opcion="p";
                break;
            case "Ladrillo":
                opcion="L";
                break;
            case "Piso":
                opcion="V";
                break;
        }
        Mapa.setObjeto(opcion);
    }
    
}
