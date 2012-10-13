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
//        thread=new Thread(this);
//        thread.start();
    }

    public static ControladorKeyBoardJPanelJuego getInstance() {
        return instance == null ? (instance = new ControladorKeyBoardJPanelJuego()) : instance;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        teclado.presionarTecla(e.getKeyCode());
        ejecutarAcciones(e);        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclado.liberarTecla(e.getKeyCode());
    }

    private void ejecutarAcciones(KeyEvent e) {KeyEvent get = e;
        if(JPanelJuego.getInstance().primerJugador()!=null){
            if(get.getKeyCode()==Dialog.Controles.getA()){
                JPanelJuego.getInstance().primerJugador().CrearBomb();
            }
            if(get.getKeyCode()==Dialog.Controles.getB()){
                if(JPanelJuego.getInstance().primerJugador().getDETONATOR()&&JPanelJuego.getInstance().primerJugador().bombasPuestas()!=0)
                    JPanelJuego.getInstance().primerJugador().getBombs().get(0).detonar();
            }
            if(get.getKeyCode()==Dialog.Controles.getStart());
        }
    }
    
}