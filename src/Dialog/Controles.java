/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dialog;

import java.awt.event.KeyEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class Controles{
    
    private static int arriba=KeyEvent.VK_UP,abajo=KeyEvent.VK_DOWN,izquierda=KeyEvent.VK_LEFT,derecha=KeyEvent.VK_RIGHT,a=KeyEvent.VK_X,b=KeyEvent.VK_Z,select=KeyEvent.VK_SHIFT,start=KeyEvent.VK_ENTER;

    public static int getA() {
        return a;
    }

    public static int getB() {
        return b;
    }

    public static int getSelect() {
        return select;
    }

    public static int getStart() {
        return start;
    }

    public static int getAbajo() {
        return abajo;
    }

    public static int getArriba() {
        return arriba;
    }

    public static int getDerecha() {
        return derecha;
    }

    public static int getIzquierda() {
        return izquierda;
    }
    
}
