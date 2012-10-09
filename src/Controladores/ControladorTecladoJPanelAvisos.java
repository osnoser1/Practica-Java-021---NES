/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Dialog.Controles;
import GUI.JPanelAvisos;
import GUI.JPanelContenedor;
import Sonidos.Sonidos;
import java.awt.event.KeyEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorTecladoJPanelAvisos extends java.awt.event.KeyAdapter{

    JPanelContenedor jPanelContenedor;
    

    public ControladorTecladoJPanelAvisos(JPanelContenedor jPanelContenedor) {
        this.jPanelContenedor = jPanelContenedor;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Prueba de accion teclado panel avisos");
        if(e.getKeyCode()==Controles.getA()||e.getKeyCode()==Controles.getB()||e.getKeyCode()==Controles.getAbajo()||e.getKeyCode()==Controles.getArriba()||e.getKeyCode()==Controles.getDerecha()||e.getKeyCode()==Controles.getIzquierda()||e.getKeyCode()==Controles.getStart()||e.getKeyCode()==Controles.getSelect()){
            Sonidos.getInstance().getSonido(Sonidos.GAME_OVER).stop();
            this.jPanelContenedor.removeAll();
            this.jPanelContenedor.agregarComponentesMenuInicial();
            this.jPanelContenedor.getJPanelGrafico().getJPanelJuego().reiniciarJPanelJuego();
            ((JPanelAvisos)e.getSource()).removeKeyListener(this);
        }
    }
    
}
