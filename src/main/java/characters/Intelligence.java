/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import engine.core.input.GamePad;
import engine.core.input.GamePad.Buttons;
import game.players.states.DeathState;
import gui.GameScreen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static engine.core.input.GamePad.Buttons.*;

/**
 * @author hp
 */
public class Intelligence {

    private final ArrayList<Buttons> buffer;
    private final Character character;
    private final GamePad gamePad;
    private final Random random;
    private Timer timer;
    private int a = 1, time = -1;

    public Intelligence(Character character) {
        this.character = character;
        this.gamePad = character.getGamePad();
        this.buffer = new ArrayList<>();
        random = new Random();
        determineIntelligence();
    }

    private void determineIntelligence() {
        timer = character.smart == Character.SMART_LOW ? new Timer(100, e -> {
            if (++time % 20 == 0) {
                bufferProcess((a = random.nextInt(4)) == 0
                        ? LEFT : a == 1 ? RIGHT : a == 2 ? UP : DOWN);
            }
            if (GameScreen.getInstance(null).firstPlayer().getCurrentState() instanceof DeathState) {
                timer.stop();
            }
        }) : character.smart == Character.SMART_MID || character.smart == Character.SMART_HIGH ? new Timer(100, e -> {
            if (++time % 20 == 0) {
                bufferProcess(random.nextInt(2) == 0 ? LEFT : RIGHT,
                        random.nextInt(2) == 0 ? UP : DOWN);
            }
            if (GameScreen.getInstance(null).firstPlayer().getCurrentState() instanceof DeathState) {
                timer.stop();
            }
        }) : null;
    }

    public Timer getTimer() {
        return timer;
    }

    public void stop() {
        if (timer == null) {
            return;
        }
        timer.stop();
    }

    public void start() {
        if (timer == null) {
            return;
        }
        timer.start();
    }

    private void bufferProcess(Buttons... buttons) {
        bufferClear();
        buffer.addAll(Arrays.asList(buttons));
        bufferApply();
    }

    private void bufferClear() {
        buffer.forEach(t -> gamePad.setPress(t, false));
        buffer.clear();
    }

    private void bufferApply() {
        buffer.forEach(t -> gamePad.setPress(t, true));
    }

}
