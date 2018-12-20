/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman.core;

import gui.GameScreen;
import personajes.Bomberman;
import personajes.Enemy;
import personajes.Brick;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
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
