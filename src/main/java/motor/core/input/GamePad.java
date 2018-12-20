/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.input;

import java.util.HashMap;
import static motor.core.input.GamePad.Buttons.*;

/**
 *
 * @author AlfonsoAndrés
 */
public class GamePad {

    public enum Buttons {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        AI_UP,
        AI_DOWN,
        AI_LEFT,
        AI_RIGHT,
        AD_UP,
        AD_DOWN,
        AD_LEFT,
        AD_RIGHT,
        A,
        B,
        R1,
        R2,
        L1,
        L2,
        START,
        SELECT,
        NONE
    }

    private final HashMap<Buttons, ButtonState> buttons = new HashMap<>() {
        {
            for (var value : Buttons.values()) {
                put(value, new ButtonState(false, true));
            }
            put(NONE, new ButtonState(true, true));
        }
    };

    public boolean isPress(Buttons buttons) {
        return this.buttons.get(buttons).isPressed();
    }

    public void setPress(Buttons buttons, boolean pressed) {
        if (!this.buttons.get(buttons).isEnabled()) {
            return;
        }
        this.buttons.get(buttons).setPressed(pressed);
    }

    private class ButtonState {

        private boolean pressed, enabled;

        public ButtonState(boolean pressed, boolean enabled) {
            this.pressed = pressed;
            this.enabled = enabled;
        }

        public boolean isPressed() {
            return pressed;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

    }

}
