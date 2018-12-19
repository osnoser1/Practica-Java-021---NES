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
    
    private static final int arriba=KeyEvent.VK_UP;
    private static final int abajo=KeyEvent.VK_DOWN;
    private static final int izquierda=KeyEvent.VK_LEFT;
    private static final int derecha=KeyEvent.VK_RIGHT;
    private static final int a=KeyEvent.VK_X;
    private static final int b=KeyEvent.VK_Z;
    private static final int select=KeyEvent.VK_SHIFT;
    private static final int start=KeyEvent.VK_ENTER;

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
