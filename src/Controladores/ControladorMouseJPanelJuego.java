/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import GUI.JPanelJuego;
import java.awt.event.MouseEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class ControladorMouseJPanelJuego extends java.awt.event.MouseAdapter{
    
    JPanelJuego jpaneljuego1;

    public ControladorMouseJPanelJuego(JPanelJuego jpaneljuego) {
        this.jpaneljuego1 = jpaneljuego;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        this.jpaneljuego1.pintarCasilla(JPanelJuego.getPosicionX(e.getX()),JPanelJuego.getPosicionY(e.getY()));
        this.jpaneljuego1.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        this.jpaneljuego1.pintarCasilla(JPanelJuego.getPosicionX(e.getX()),JPanelJuego.getPosicionY(e.getY()));
        this.jpaneljuego1.repaint();
    //    ((JPanelJuego)e.getComponent()).setLocation(e.getComponent().getLocationOnScreen().x-e.getXOnScreen(),e.getComponent().getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
    }
    
}
