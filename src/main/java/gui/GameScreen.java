/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bomberman.configuration.Configuration;
import bomberman.core.GameControl;
import dependencies.Sounds;
import engine.core.graphics.Sprite;
import language.utils.ImageUtilities;
import dependencies.Images;
import engine.core.map.Map;
import characters.*;
import engine.core.Camera;
import utils.game.Screen;
import game.players.states.DeathState;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Transparency;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

import game.constants.Objects;
import engine.core.graphics.spritedefaultstates.NullState;

/**
 *
 * @author Alfonso Andrés
 */
public class GameScreen extends Screen implements PropertyChangeListener {

    private static GameScreen instance;
    private final int x;
    private final int y;
    private boolean defeated, powerUp, door;
    private final Map map;
    private final Image buffer;
    private Dimension SIZE;
    private final Dimension windowSize;
    private final ArrayList<Bomb> bombs;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Brick> bricks;
    private final Bomberman[] players;
    private final Camera window;
    private final GameControl gameControl;
    private final JPanelInformation jPanelInformation;
    private final double internalScaleX;

    private GameScreen(JPanelContainer jPanelContainer) {
        super(jPanelContainer);
        SIZE = new Dimension(1240, 520);
        windowSize = Configuration.getInstance().getWindowSize();
        internalScaleX = ((double)640) / (SIZE.width >> 1);
        window = new Camera(new Rectangle(SIZE.width >> 1, SIZE.height), SIZE);
        bombs = new ArrayList<>();
        bricks = new ArrayList<>();
        enemies = new ArrayList<>();
        players = new Bomberman[1];
        map = Map.getInstance();
        buffer = ImageUtilities.createCompatibleVolatileImage(SIZE.width, SIZE.height, Transparency.OPAQUE);
        x = buffer.getWidth(null) / Map.COLUMNS;
        y = buffer.getHeight(null) / Map.ROWS;
        var g2d = (Graphics2D) buffer.getGraphics();
        drawMap(g2d);
        g2d.dispose();
        players[0] = new Bomberman(40, 40);
        gameControl = new GameControl(this);
        jPanelInformation = JPanelInformation.getInstance();
        restart();
    }

    public static GameScreen getInstance(JPanelContainer jPanelContainer) {
        return instance == null ? (instance = new GameScreen(jPanelContainer)) : instance;
    }

    @Override
    public final void restart() {
        eraseEnemies();
        bricks.clear();
        map.reset();
        window.restart();
        firstPlayer().restart(1, 1);
        firstPlayer().addPropertyChangeListener(this);
        bombs.clear();
        powerUp = door = false;
        generateMap();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void setSIZE(final Dimension dim) {
        jPanelInformation.setSIZE(dim);
        var x1 = (int) Math.round(dim.width / 16.0);
        var y1 = (int) Math.round(dim.height / 14.0);
        SIZE = new Dimension(dim.width * 2 - x1, dim.height - y1);
        System.out.println(dim + " " + SIZE + " " + y1 + " " + x1);
    }

    private void drawMap(final Graphics2D g2d) {
        g2d.setColor(new java.awt.Color(80, 160, 0));
        g2d.fillRect(x, y, SIZE.width - x * 2, SIZE.height - y * 2);
        for (short i = 0; i < Map.ROWS; i++)
            for (short j = 0; j < Map.COLUMNS; j++)
                if (map.contains(i, j, SteelBlock.class))
                    g2d.drawImage(Images.STEEL_BLOCK, j * x, i * y, x, y, null);
    }

    private Enemy determinateEnemy(final int i, final int j, final String a) {
        final var player = Objects.getInstance(a);
        if (player != null)
            player.setLocation(j * x, i * y);
        return (Enemy) player;
    }

    @Override
    public void draw(final Graphics2D g) {
        jPanelInformation.draw(g);
//        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        var position = window.getPosition();
        g.translate(0, jPanelInformation.getHeight());
        g.scale(internalScaleX, 1);
        g.drawImage(buffer, 0, 0, position.width, position.height, position.x, position.y, position.x + position.width, position.y + position.height, null);
        g.translate(-position.x, position.y);
        drawScene(g);
        g.dispose();
    }

    private void drawScene(final Graphics2D g2d) {
        drawBricks(g2d);
        drawBombs(g2d);
        drawPlayers(g2d);
        gameControl.draw(g2d);
    }

    private void drawPlayers(final Graphics2D g) {
        for (final var enemy : enemies)
            if (window.contains(enemy))
                enemy.draw(g);
        firstPlayer().draw(g);
    }

    private void drawBombs(Graphics2D g) {
        for (final var bomb : bombs)
            if (window.contains(bomb)) {
                bomb.draw(g);
            }
    }

    private void drawBricks(Graphics2D g) {
        for (final var brick : bricks) {
            if (!window.contains(brick))
                continue;
            brick.draw(g);
        }
    }

    public void eraseBrick(final int row, final int column) {
        var sprite = map.getSprite(row, column, Brick.class, SpecialBrick.class);
        Stream.of(sprite).forEach((l) -> {
            if(l instanceof Brick && !(l.getCurrentState() instanceof NullState)) {
                ((Brick) l).destroy();
                return;
            }
            if(!(l instanceof SpecialBrick)) {
                return;
            }
            var le = (SpecialBrick) l;
            le.createEnemies(this);
            if (!le.isDoor()) {
                le.removePowerUp();
                map.delete(le);
            }
        });
    }

    public void erasePlayer(final int row, final int column) {
        if (!map.contains(row, column, firstPlayer()))
            return;
        firstPlayer().die();
    }

    public void eraseEnemies() {
        for (var enemy : enemies)
            enemy.stopIntelligence();
        enemies.clear();
    }

    public void eraseEnemy(final int row, final int column) {
        for (final var enemy : enemies)
            if (map.contains(row, column, enemy)) {
                enemy.death(this);
                jPanelInformation.increaseScore(enemy.getScore());
            }   
    }

    public void eraseBomb(final int row, final int column) {
        for (final var bomb : bombs)
            if (!(bomb.getCurrentState() instanceof DeathState)
                    && map.contains(row, column, bomb)) {
                bomb.detonate(this);
                return;
            }
    }

    public void eraseBomb(final Bomberman bomberman) {
        bombs.stream().filter(bomb -> (!bomb.hasDetonated() && bomb.belongs(bomberman))).forEachOrdered((bomb) -> bomb.detonate(this));
    }

    public int getEnemiesLength() {
        return enemies.size();
    }

    public void enableIntelligence() {
        for (final var enemy : enemies)
            enemy.getIntelligence().start();
    }

    public Bomberman firstPlayer() {
        return players[0];
    }

    @Override
    public void update(final long elapsedTime) {
        window.update(firstPlayer().getCenter());
        updatePlayer(elapsedTime);
        updateBombs(elapsedTime);
        for (var i = 0; i < enemies.size(); i++) {
            final var enemy = enemies.get(i);
            enemy.update(this, elapsedTime);
            if (enemy.getCurrentState() instanceof NullState) {
                enemies.remove(i--);
                if (enemies.isEmpty()) {
                    if (!defeated)
                        Sounds.getInstance().play(Sounds.PAUSE);
                    defeated = true;
                } else
                    defeated = false;
            } else if(!(enemy.getCurrentState() instanceof DeathState)) {
                map.update(enemy);
            }
        }
        for (var i = 0; i < bricks.size(); i++) {
            final var brick = bricks.get(i);
            brick.update(this, elapsedTime);
            if (brick.getCurrentState() instanceof NullState
                    && !brick.isSpecial()) {
                if(!map.delete(brick)) {
                    map.delete(brick.getSpecialBrick());
                }
                bricks.remove(i--);
            }
        }
        gameControl.update();
    }

    private void generateMap() {
        final var r = new Random();
        int c, f, d;
        map.add(firstPlayer());
        for (var i = 0; i < 55; i++)
            do {
                c = r.nextInt(30);
                f = r.nextInt(12);
                if (c < 3 && f == 1 || c == 1 && f < 3)
                    continue;
                if (map.isEmpty(f, c)) {
                    addTile(r, "L", f, c);
                    break;
                }
            } while (true);
        for (var i = 0; i < 10; i++)
            do {
                c = r.nextInt(30);
                f = r.nextInt(12);
                d = r.nextInt(8);
                if (map.isEmpty(f, c)) {
                    addTile(r, determinateEnemy(d), f, c);
                    break;
                }
            } while (true);
        map.show();
    }

    private void addTile(final Random r, final String object, final int i, final int j) {
        var c = -1;
        if (!door) {
            door = true;
            c = 8;
        } else if (!powerUp) {
            powerUp = true;
            c = r.nextInt(6);
        }
        final var s = object.equals("L") ? new Brick(j * x, i * y, c) : determinateEnemy(i, j, object);
        map.add(s);
        if (object.equals("L"))
            bricks.add((Brick) s);
        else
            enemies.add((Enemy) s);
    }

    public String determinateEnemy(final int c) {
        return c > 6 ? Objects.PONTAN.getValue() : Objects.getEnemies()[c].getValue();
    }

    private void updatePlayer(final long elapsedTime) {
        final var b = firstPlayer();
        if(!b.isEnteredTheDoor()) {
            b.update(this, elapsedTime);
        }
        map.update(b);
        if (b.getCurrentState() instanceof NullState) {
            map.delete(b);
            if (Sounds.getInstance().isPlaying(Sounds.JUST_DIED))
                return;
            firstPlayer().removePropertyChangeListener(this);
            jPanelInformation.decreaseRemainingLives();
            jPanelInformation.stopCountdown();
            if (jPanelInformation.getRemainingLives() < 0) {
                jPanelInformation.setRemainingLives(2);
                jPanelInformation.setScore(0);
                MessageScreen.getInstance(null).setLevel((short) 1);
                players[0] = (Bomberman) Objects.getInstance("B");
                jPanelContainer.setScreen(Scene.GAME_OVER);
            } else
                jPanelContainer.setScreen(Scene.STAGE);
        } else if (b.isEnteredTheDoor()) {
            if (Sounds.getInstance().isPlaying(Sounds.LEVEL_COMPLETE))
                return;
            jPanelInformation.stopCountdown();
            MessageScreen.getInstance(null).increaseLevel();
            if (MessageScreen.getInstance(null).endgame()) {

            } else
                jPanelContainer.setScreen(Scene.STAGE);
        }
    }

    private void updateBombs(final long elapsedTime) {
        for (var i = 0; i < bombs.size(); i++) {
            var bomb = bombs.get(i);
            bomb.update(this, elapsedTime);
            if (bomb.getCurrentState() instanceof NullState) {
                this.map.delete(bomb);
                bombs.remove(i--);
            }
        }
    }

    public Map getMap() {
        return map;
    }

    public void addEnemy(int row, int column, String enemy) {
        var sprite = determinateEnemy(row, column, enemy);
        enemies.add(sprite);
        map.add(sprite);
        sprite.startIntelligence();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        var name = evt.getPropertyName();
        var value = evt.getNewValue();
        if (name.equals(Bomberman.Events.ADD_BOMB.name())) {
            Sounds.getInstance().play(Sounds.BOMB_PLANT);
            this.map.add((Sprite) value);
            this.bombs.add((Bomb) value);
        }
    }
}
