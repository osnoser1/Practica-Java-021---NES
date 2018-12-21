/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import dependencies.Images;
import dependencies.Sounds;
import engine.core.graphics.Image;
import engine.core.graphics.Sprite;
import engine.core.map.Position;
import gui.GameScreen;
import utils.game.Screen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hp
 */
public class SpecialBrick extends Sprite {

    private final int type;
    private final AtomicInteger enemiesNumber;
    private Timer timer;
    private boolean removedStatus;
    private Position position;

    public SpecialBrick(final int x, final int y, int type) {
        super(new Image(Images.SPECIAL_BRICK, 1, 9, 2.5f), x, y);
        image.update(type);
        this.type = type;
        this.enemiesNumber = new AtomicInteger();
    }

    @Override
    public void update(Screen screen, long elapsedTime) {
        var gameScreen = (GameScreen) screen;
        if (gameScreen.firstPlayer().isEnteredTheDoor())
            return;
        addPendingEnemies(gameScreen);
        var point = gameScreen.getMap().getPosition(gameScreen.firstPlayer());
        if (point != null && point.equals(gameScreen.getMap().getPosition(this)))
            if (type != getDoor() && !removedStatus) {
                determineSkill(gameScreen);
                Sounds.getInstance().play(Sounds.POWER_UP_2);
                Sounds.getInstance().change(Sounds.STAGE_THEME, Sounds.FIND_THE_DOOR, true);
                removePowerUp();
            } else if (gameScreen.getEnemiesLength() == 0) {
                Sounds.getInstance().stop();
                Sounds.getInstance().play(Sounds.LEVEL_COMPLETE);
                gameScreen.firstPlayer().setEnteredTheDoor(true);
                System.out.println("Entered by the door");
            }
    }

    private int getDoor() {
        return 8;
    }

    public boolean isDoor() {
        return type == getDoor();
    }

    public void removePowerUp() {
        if (type != getDoor())
            removedStatus = true;
    }

    public void determineSkill(GameScreen gameScreen) {
        if (type == 7) {
        } else if (type == 0)
            gameScreen.firstPlayer().increaseFlames(1);
        else if (type == 1)
            gameScreen.firstPlayer().increaseBombs(1);
        else if (type == 2)
            gameScreen.firstPlayer().setDETONATOR(true);
        else if (type == 3)
            gameScreen.firstPlayer().setSPEED(true);
        else if (type == 4)
            gameScreen.firstPlayer().setBOMBPASS(true);
        else if (type == 5)
            gameScreen.firstPlayer().setWallpass(true);
        else if (type == 6)
            gameScreen.firstPlayer().setFLAMEPASS(true);
        else if (type == 7)
            gameScreen.firstPlayer().setMYSTERY(true);
    }

    public void createEnemies(final GameScreen gameScreen) {
        position = gameScreen.getMap().getPosition(this);
        if (timer != null && timer.isRunning()) {
            return;
        }
        timer = new Timer(500, new AbstractAction() {
            int time = 5;

            @Override
            public void actionPerformed(ActionEvent e) {
                increaseCounterEnemies();
                if (--time < 0) {
                    timer.stop();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private void increaseCounterEnemies() {
        if (enemiesNumber.get() < 5) {
            enemiesNumber.incrementAndGet();
        }
    }

    private void addPendingEnemies(GameScreen gameScreen) {
        for (int i = 0, n = enemiesNumber.get(); i < n; i++) {
            gameScreen.addEnemy(position.row, position.column, gameScreen.determinateEnemy(3));
        }
        enemiesNumber.set(0);
    }

    public boolean isRemovedStatus() {
        return removedStatus;
    }

}
