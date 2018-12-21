/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman.core;

import characters.Bomberman;
import characters.Brick;
import characters.Enemy;
import gui.GameScreen;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author AlfonsoAndrés
 */
public class GameControl {

    private final GameScreen gameScreen;
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<Brick> bricks;
    private Bomberman[] players;

    public GameControl(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update() {

    }

    public void draw(Graphics g2) {

    }

    public boolean hasCollision(Rectangle rectangle) {
        return false;
    }

}
