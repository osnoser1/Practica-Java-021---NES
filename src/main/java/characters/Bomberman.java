/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import dependencies.Images;
import dependencies.Sounds;
import engine.core.game.Screen;
import engine.core.graphics.AnimationWrapper;
import engine.core.graphics.Image;
import engine.core.graphics.spritedefaultstates.NullState;
import engine.core.input.GamePad;
import engine.core.input.GamePad.Buttons;
import game.core.input.PlayerOneKeyboardController;
import game.players.bomberman.states.*;
import gui.GameScreen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class Bomberman extends Character {

    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private boolean SPEED, DETONATOR, FLAMEPASS, MYSTERY;
    private int FLAMES, BOMBS;
    private boolean enteredTheDoor;
    private int numberPumpsCreated;

    public Bomberman(final int x, final int y) {
        super(new Image(Images.BOMBERMAN, 6, 6, (float) 2.5), x, y);
        gamePad = new GamePad();
        padController = PlayerOneKeyboardController.getInstance();
        speed = SPEED_MID;
        BOMBS = 1;
        FLAMES = 1;
        SPEED = false;
        wallpass = false;
        DETONATOR = true;
        BOMBPASS = false;
        FLAMEPASS = false;
        MYSTERY = false;
        id = "B";
        initialize();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    public final void initialize() {
        super.animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "0", 4000 / 60));
                put(UpState.class, new AnimationWrapper(1, "2,1,0,1", 4000 / 60));
                put(DownState.class, new AnimationWrapper(2, "2,1,0,1", 4000 / 60));
                put(RightState.class, new AnimationWrapper(3, "2,1,0,1", 4000 / 60));
                put(LeftState.class, new AnimationWrapper(4, "2,1,0,1", 4000 / 60));
                put(DeathState.class, new AnimationWrapper(5, "0,1,2,3,4", 300));
            }
        };
        setCurrentState(LeftState::new);
    }

    @Override
    public void update(final Screen screen, final long elapsedTime) {
        var gameScreen = (GameScreen) screen;
        padController.update(gamePad);
        checkActionKeys(gameScreen);
        super.update(gameScreen, elapsedTime);
        checkDeath(gameScreen);
    }

    public void die() {
        Sounds.getInstance().stop(Sounds.UP, Sounds.DOWN, Sounds.LEFT, Sounds.RIGHT);
        Sounds.getInstance().play(Sounds.DEATH);
        setCurrentState(DeathState::new);
    }

    private void checkDeath(GameScreen gameScreen) {
        if (currentState instanceof DeathState || currentState instanceof NullState
                || !gameScreen.getMap().contains(this, Enemy.class))
            return;
        die();
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if (SPEED)
            speed = SPEED_FAST;
        else
            speed = SPEED_MID;
    }

    public void increaseBombs(final int count) {
        BOMBS += count;
    }

    public void increaseFlames(final int count) {
        FLAMES += count;
    }

    public int getBOMBS() {
        return BOMBS;
    }

    public boolean getMYSTERY() {
        return MYSTERY;
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public int getFLAMES() {
        return FLAMES;
    }

    public boolean getDETONATOR() {
        return DETONATOR;
    }

    public void setDETONATOR(boolean DETONATOR) {
        this.DETONATOR = DETONATOR;
    }

    public boolean getFLAMEPASS() {
        return FLAMEPASS;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
    }

    private void addBomb() {
        if (centralCollision(Bomb.class)) {
            insideBomb = true;
            return;
        }
        if (!centralCollision(Brick.class, SpecialBrick.class) && numberPumpsCreated < BOMBS) {
            final var bomb = new Bomb(getCenter().x / image.getWidth() * image.getWidth(),
                    getCenter().y / image.getHeight() * image.getHeight(), this);
            insideBomb = true;
            numberPumpsCreated += 1;
            changes.firePropertyChange(Events.ADD_BOMB.name(), null, bomb);
        }
    }

    private void checkActionKeys(final GameScreen gameScreen) {
        if (currentState instanceof DeathState || currentState instanceof NullState)
            return;
        if (gamePad.isPress(Buttons.A))
            addBomb();
        if (gamePad.isPress(Buttons.B) && DETONATOR)
            detonatorBomb(gameScreen);
    }

    public void restart(int x, int y) {
        setCurrentState(InitialState::new);
        setAxisPosition(x, y);
        numberPumpsCreated = 0;
        image.setActive(true);
        enteredTheDoor = false;
    }

    private void detonatorBomb(final GameScreen screen) {
        screen.eraseBomb(this);
    }

    public boolean isEnteredTheDoor() {
        return enteredTheDoor;
    }

    public void setEnteredTheDoor(boolean b) {
        enteredTheDoor = true;
    }

    public void decreaseNumberPumpsCreated() {
        if (numberPumpsCreated > 0) {
            numberPumpsCreated -= 1;
        }
    }

    public enum Events {
        ADD_BOMB,
    }

}
