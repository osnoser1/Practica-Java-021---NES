/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.java.controllers;

import bomberman.configuracion.Configuration;
import gui.JPanelContainer;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import motor.core.java.gui.JFramePrincipal;

/**
 * @author Alfonso Andrés
 */
public class CJFramePrincipal extends java.awt.event.WindowAdapter implements ComponentListener {

    public static final class Keyboard extends java.awt.event.KeyAdapter {

        private static Keyboard instance;

        private Keyboard() {
        }

        public static Keyboard getInstance() {
            return instance == null ? (instance = new Keyboard()) : instance;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            motor.core.input.Keyboard.getInstance().keyPress(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            motor.core.input.Keyboard.getInstance().keyRelease(e.getKeyCode());
        }

    }

    private JPanelContainer container;
    private static CJFramePrincipal instance;

    private CJFramePrincipal() {
    }

    public static CJFramePrincipal getInstance() {
        return instance == null ? (instance = new CJFramePrincipal()) : instance;
    }

    public CJFramePrincipal setJFramePrincipal(JFramePrincipal aThis) {
        this.container = JPanelContainer.getInstance();
        return this;
    }

    @Override
    public void windowGainedFocus(WindowEvent e) {
        System.out.println("windowGainedFocus");
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
        System.out.println("windowDeiconified");
    }

    @Override
    public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        System.out.println("windowOpened");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(1);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        System.out.println("componentResized");
        var jFramePrincipal = (JFramePrincipal) e.getComponent();
        var c = Configuration.getInstance();
        var in = jFramePrincipal.getInsets();
        c.setWindowSize(jFramePrincipal.getWidth() - in.left - in.bottom, jFramePrincipal.getHeight() - in.top - in.bottom);
//        container.setSIZE(e.getComponent().getSize());
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
