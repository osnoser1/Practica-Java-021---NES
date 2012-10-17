/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilidadesJuego;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author AlfonsoAndrés
 */
public class GamePad {
    
    public enum Botones {
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        A,
        B,
        R,
        L,
        START,
        SELECT
    }
    private HashMap<Botones, Integer> boton = new HashMap<Botones, Integer>(){{
                put(Botones.ABAJO, KeyEvent.VK_DOWN);
                put(Botones.ARRIBA, KeyEvent.VK_UP);
                put(Botones.DERECHA, KeyEvent.VK_RIGHT);
                put(Botones.IZQUIERDA, KeyEvent.VK_LEFT);
                put(Botones.A, KeyEvent.VK_X);
                put(Botones.B, KeyEvent.VK_C);
                put(Botones.SELECT, KeyEvent.VK_C);
                put(Botones.START, KeyEvent.VK_SHIFT);
                put(Botones.R, KeyEvent.VK_A);
                put(Botones.L, KeyEvent.VK_S);
            }};

    public int getBoton(Botones boton){
        return this.boton.get(boton);
    }
    
    public void setBoton(Botones boton, int keyCode){
        this.boton.put(boton, keyCode);
    }
    
}
