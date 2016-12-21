/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades.Juego;

import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author AlfonsoAndrés
 */
public class Control {
    
    public static enum Botones {
        ARRIBA,
        ABAJO,
        IZQUIERDA,
        DERECHA,
        AIARRIBA,
        AIABAJO,
        AIIZQUIERDA,
        AIDERECHA,
        ADARRIBA,
        ADABAJO,
        ADIZQUIERDA,
        ADDERECHA,
        A,
        B,
        R1,
        R2,
        L1,
        L2,
        START,
        SELECT
    }
    private final HashMap<Botones, Integer> boton = new HashMap<Botones, Integer>() {
        {
                put(Botones.ABAJO, KeyEvent.VK_DOWN);
                put(Botones.ARRIBA, KeyEvent.VK_UP);
                put(Botones.DERECHA, KeyEvent.VK_RIGHT);
                put(Botones.IZQUIERDA, KeyEvent.VK_LEFT);
                put(Botones.A, KeyEvent.VK_X);
                put(Botones.B, KeyEvent.VK_Z);
                put(Botones.SELECT, KeyEvent.VK_SHIFT);
                put(Botones.START, KeyEvent.VK_ENTER);
                put(Botones.R1, KeyEvent.VK_A);
                put(Botones.R2, KeyEvent.VK_D);
                put(Botones.L1, KeyEvent.VK_S);
                put(Botones.L2, KeyEvent.VK_F);
            }};

    public int get(Botones boton){
        return this.boton.get(boton);
    }
    
    public void set(Botones boton, int keyCode){
        this.boton.put(boton, keyCode);
    }
    
}
