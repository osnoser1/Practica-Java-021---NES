/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import GUI.JPanelContenedor;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorJFramePrincipal extends java.awt.event.WindowAdapter implements ComponentListener{
    
    JPanelContenedor jpanelcontenedor1;

    public ControladorJFramePrincipal(JPanelContenedor jpanelcontenedor1) {
        this.jpanelcontenedor1 = jpanelcontenedor1;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        System.out.println("windowGainedFocus");
        if(this.jpanelcontenedor1.getJPanelPresentacion().isVisible())
            this.jpanelcontenedor1.getJPanelPresentacion().requestFocus();
        else if(this.jpanelcontenedor1.getJPanelGrafico().getJPanelJuego().isVisible())
            this.jpanelcontenedor1.getJPanelGrafico().getJPanelJuego().requestFocus();
    }

    @Override
    public void windowLostFocus(WindowEvent e) {
        super.windowLostFocus(e);
        System.out.println("windowLostFocus");
    }

    @Override
    public void windowActivated(WindowEvent e) {
        super.windowActivated(e);
        System.out.println("windowActivated");
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        super.windowDeactivated(e);
        System.out.println("windowDeactivated");
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        super.windowDeiconified(e);
        System.out.println("windowDeconified");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        System.out.println("windowOpened");
    }

    @Override
    public void componentResized(ComponentEvent e) {
        
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        
    }

    @Override
    public void componentShown(ComponentEvent e) {
        System.out.println("componentShown");
        
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        System.out.println("componentHidden");
    }
    
}
