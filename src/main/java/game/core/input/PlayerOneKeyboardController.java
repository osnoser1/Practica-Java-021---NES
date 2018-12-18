/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.core.input;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import motor.core.input.GamePad.Botones;
import static motor.core.input.GamePad.Botones.*;
import motor.core.input.IGamePadController;
import motor.core.input.KeyboardController;

/**
 * 
 * @author AlfonsoAndres
 */
public class PlayerOneKeyboardController extends KeyboardController {

    private static PlayerOneKeyboardController instance;
    
    private PlayerOneKeyboardController() {
        super(new HashMap<Integer, Botones>(){{
            put(KeyEvent.VK_DOWN, ABAJO);
            put(KeyEvent.VK_UP, ARRIBA);
            put(KeyEvent.VK_RIGHT, DERECHA);
            put(KeyEvent.VK_LEFT, IZQUIERDA);
            put(KeyEvent.VK_X, A);
            put(KeyEvent.VK_Z,B);
            put(KeyEvent.VK_SHIFT, SELECT);
            put(KeyEvent.VK_ENTER, START);
            put(KeyEvent.VK_A, R1);
            put(KeyEvent.VK_D, R2);
            put(KeyEvent.VK_S, L1);
            put(KeyEvent.VK_F, L2);
        }});
    }

    public static IGamePadController getInstance() {
        return instance == null ? instance = new PlayerOneKeyboardController() : instance;
    }
    
}
