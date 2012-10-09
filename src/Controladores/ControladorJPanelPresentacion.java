/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Dialog.Controles;
import GUI.*;
import Sonidos.Sonidos;
import java.awt.event.KeyEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorJPanelPresentacion extends java.awt.event.KeyAdapter{
    
    private JPanelPresentacion jpanelpresentacion1;
    private JPanelGrafico jpanelgrafico1;
    private JPanelAvisos jpanelavisos1;
    private JPanelContenedor jpanelcontenedor1;

    public ControladorJPanelPresentacion(JPanelContenedor parent) {
        this.jpanelpresentacion1 = parent.getJPanelPresentacion();
        this.jpanelgrafico1 = parent.getJPanelGrafico();
        this.jpanelavisos1 = parent.getJPanelAvisos();
        this.jpanelcontenedor1=parent;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==Controles.getSelect()){
            this.jpanelpresentacion1.siguienteOpcion();
            System.out.println("Prueba de accion: keyPressed");
        }
        else if(e.getKeyCode()==Controles.getStart()){
            this.jpanelpresentacion1.setOpcionSeleccionada();
            int opcion=this.jpanelpresentacion1.getOpcionSeleccionada();
            this.jpanelpresentacion1.setVisible(false);
            Sonidos.getInstance().getSonido(Sonidos.TITLE_SCREEN).stop();
            switch(opcion){
                case JPanelPresentacion.START:
                    this.jpanelcontenedor1.add(jpanelavisos1);
                    this.jpanelavisos1.iniciarJPanelStage();
                    new Hilos.HiloPanelPresentacion(jpanelcontenedor1).start();
                    break;
                case JPanelPresentacion.MAP_EDITOR:
                    this.jpanelcontenedor1.add(jpanelgrafico1);
                    this.jpanelgrafico1.modoEditor();
                    break;
            }
            
        }
        
    }
    
}
