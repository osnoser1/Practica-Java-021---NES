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

    public ControladorJPanelPresentacion() { }

    @Override
    public void keyPressed(KeyEvent e) {
        JPanelPresentacion jPanelPresentacion = JPanelPresentacion.getInstance();
        if(e.getKeyCode()==Controles.getSelect()){
            jPanelPresentacion.siguienteOpcion();
            System.out.println("Prueba de accion: keyPressed");
        }
        else if(e.getKeyCode()==Controles.getStart()){
            jPanelPresentacion.setOpcionSeleccionada();
            int opcion = jPanelPresentacion.getOpcionSeleccionada();
            jPanelPresentacion.setVisible(false);
            Sonidos.getInstance().getSonido(Sonidos.TITLE_SCREEN).stop();
            switch(opcion){
                case JPanelPresentacion.START:
                    JPanelContenedor.getInstance().add(JPanelAvisos.getInstance());
                    JPanelAvisos.getInstance().iniciarJPanelStage();
                    new Hilos.HiloPanelPresentacion().start();
                    break;
                case JPanelPresentacion.MAP_EDITOR:
                    JPanelContenedor.getInstance().add(JEditorDeMapas.getInstance());
//                    JPanelContenedor.getInstance().add(JPanelGrafico.getInstance());
//                    JPanelGrafico.getInstance().modoEditor();
                    break;
            }
            
        }
        
    }
    
}
