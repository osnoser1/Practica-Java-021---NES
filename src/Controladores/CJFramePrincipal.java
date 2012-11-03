/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import GUI.JPanelPresentacion;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class CJFramePrincipal extends java.awt.event.WindowAdapter implements ComponentListener{
    
    public static final class Teclado extends java.awt.event.KeyAdapter{

        private static Teclado instance;
        
        private Teclado() { }
        
        public static Teclado getInstance(){
            return instance == null ? (instance = new Teclado()) : instance;
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
            Dependencias.Teclado.getInstance().presionarTecla(e.getKeyCode());
//            System.out.println("Tecla presionada");
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Dependencias.Teclado.getInstance().liberarTecla(e.getKeyCode());
        }
        
    } 
    
    private static CJFramePrincipal instance;
    
    private CJFramePrincipal() { }
    
    public static CJFramePrincipal getInstance() {
        return instance == null ? (instance = new CJFramePrincipal()) : instance;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        System.out.println("windowGainedFocus");
        if(JPanelPresentacion.getInstance().isVisible())
            JPanelPresentacion.getInstance().requestFocus();
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
