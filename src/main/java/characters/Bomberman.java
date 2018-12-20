/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import dependencies.Sounds;
import gui.GameScreen;
import engine.core.graphics.Image;
import dependencies.Images;
import utils.game.Screen;
import game.core.input.PlayerOneKeyboardController;
import game.players.bomberman.states.*;
import engine.core.input.GamePad;
import engine.core.input.GamePad.Buttons;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import engine.core.graphics.AnimationWrapper;
import engine.core.graphics.spritedefaultstates.NullState;

public class Bomberman extends Character {

    private boolean SPEED, DETONATOR, FLAMEPASS, MYSTERY;
    private int FLAMES, BOMBS;
    private static CopyOnWriteArrayList<Bomb> bombs;
    private boolean enteredTheDoor;

    public Bomberman(final int x, final int y) {
        super(new Image(Images.BOMBERMAN, 6, 6, (float) 2.5), x, y);
        gamePad = new GamePad();
        padController = PlayerOneKeyboardController.getInstance();
        bombs = new CopyOnWriteArrayList<>();
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
        for (var bomb : bombs) {
            bomb.update(gameScreen, elapsedTime);
            if (bomb.getCurrentState() instanceof NullState) {
                gameScreen.getMap().delete(bomb);
                bombs.remove(bomb);
            }
        }
    }
    
    public void die() {
        Sounds.getInstance().stop(Sounds.UP, Sounds.DOWN, Sounds.LEFT, Sounds.RIGHT);
        Sounds.getInstance().play(Sounds.DEATH);
        setCurrentState(DeathState::new);
    }
    
    private void checkDeath(GameScreen gameScreen) {
        if(currentState instanceof DeathState || currentState instanceof NullState
                || !gameScreen.getMap().contains(this, Enemy.class))
            return;
        die();
    }
    
    public void setDETONATOR(boolean DETONATOR) {
        this.DETONATOR = DETONATOR;
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if (SPEED)
            speed = SPEED_FAST;
        else
            speed = SPEED_MID;
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
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

    public int getFLAMES() {
        return FLAMES;
    }

    public boolean getDETONATOR() {
        return DETONATOR;
    }

    public boolean getFLAMEPASS() {
        return FLAMEPASS;
    }

    public void addBomb(final GameScreen gameScreen) {
        if (centralCollision(Bomb.class)) {
            insideBomb = true;
            return;
        }
        if (!centralCollision(Brick.class, SpecialBrick.class) && bombs.size() < BOMBS) {
            Sounds.getInstance().play(Sounds.BOMB_PLANT);
            final var b = new Bomb(getCenter().x / image.getWidth() * image.getWidth(),
                    getCenter().y / image.getHeight() * image.getHeight(), this);
            gameScreen.getMap().add(b);
            bombs.add(b);
            insideBomb = true;
        }
    }

    public CopyOnWriteArrayList<Bomb> getBombs() {
        return bombs;
    }
    
    private void checkActionKeys(final GameScreen gameScreen) {
        if(currentState instanceof DeathState || currentState instanceof NullState)
            return;
        if (gamePad.isPress(Buttons.A))
            addBomb(gameScreen);
        if (gamePad.isPress(Buttons.B) && DETONATOR)
            detonatorBomb(gameScreen);
    }

    public void restart(int x, int y) {
        setCurrentState(InitialState::new);
        setAxisPosition(x, y);
        bombs.clear();
        image.setActive(true);
        enteredTheDoor = false;
    }

    private void detonatorBomb(final Screen screen) {
        bombs.stream().filter((bomb) -> (!bomb.hasDetonated())).forEachOrdered((bomb) -> bomb.detonate(screen));
    }

    public void setEnteredTheDoor(boolean b) {
        enteredTheDoor = true;
    }

    public boolean isEnteredTheDoor() {
        return enteredTheDoor;
    }

}
