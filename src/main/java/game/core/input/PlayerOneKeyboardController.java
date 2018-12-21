/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game.core.input;

import engine.core.input.IGamePadController;
import engine.core.input.KeyboardController;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import static engine.core.input.GamePad.Buttons.*;

/**
 * @author AlfonsoAndres
 */
public class PlayerOneKeyboardController extends KeyboardController {

    private static PlayerOneKeyboardController instance;

    private PlayerOneKeyboardController() {
        super(new HashMap<>() {{
            put(KeyEvent.VK_DOWN, DOWN);
            put(KeyEvent.VK_UP, UP);
            put(KeyEvent.VK_RIGHT, RIGHT);
            put(KeyEvent.VK_LEFT, LEFT);
            put(KeyEvent.VK_X, A);
            put(KeyEvent.VK_Z, B);
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
