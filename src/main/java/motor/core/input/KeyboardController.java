/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.input;

import java.util.HashMap;

import motor.core.input.GamePad.Buttons;
import motor.core.input.Keyboard.KeyState;
import static motor.core.input.Keyboard.KeyState.PRESSED;

/**
 *
 * @author AlfonsoAndres
 */
public abstract class KeyboardController implements IGamePadController {

    private final HashMap<Integer, Buttons> mapper;
    private final HashMap<Buttons, Boolean> buffer;

    protected KeyboardController(HashMap<Integer, Buttons> mapper) {
        if (mapper == null) {
            throw new IllegalArgumentException("mapper null.");
        }
        this.mapper = mapper;
        this.buffer = new HashMap<>();
        Keyboard.getInstance().keyChangedSubscribe(this::keyChanged);
    }

    @Override
    public void update(GamePad g) {
        synchronized (buffer) {
            if (buffer.isEmpty()) {
                return;
            }
            buffer.forEach(g::setPress);
        }
    }

    private Void keyChanged(int keyCode, KeyState keyState) {
        if (mapper.containsKey(keyCode)) {
            synchronized (buffer) {
                buffer.put(mapper.get(keyCode), keyState == PRESSED);
            }
        }
        return null;
    }

}
