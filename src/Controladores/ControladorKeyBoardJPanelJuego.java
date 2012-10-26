/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;
import Dependencias.Teclado;
import GUI.JPanelJuego;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
/**
 *
 * @author hp
 */
public class ControladorKeyBoardJPanelJuego extends KeyAdapter{
    
    private static ControladorKeyBoardJPanelJuego instance;
    private final Teclado teclado;
    
    private ControladorKeyBoardJPanelJuego() {
        teclado = Teclado.getInstance();
    }

    public static ControladorKeyBoardJPanelJuego getInstance() {
        return instance == null ? (instance = new ControladorKeyBoardJPanelJuego()) : instance;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        teclado.presionarTecla(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclado.liberarTecla(e.getKeyCode());
    }
    
}