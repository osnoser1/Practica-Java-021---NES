/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package characters;

import dependencies.Images;
import engine.core.game.Screen;
import engine.core.graphics.AnimationWrapper;
import engine.core.graphics.Image;
import engine.core.graphics.spritedefaultstates.NullState;
import engine.core.map.Position;
import game.players.fire.states.InitialState;
import gui.GameScreen;

import java.awt.*;
import java.util.HashMap;

/**
 * @author hp
 */
public class Fire extends Character {

    private final int length;
    private int[] lengthDirections;
    private Point[] pos;
    private Image[] images;

    public Fire(final int x, final int y, final int length, final GameScreen gameScreen) {
        super(new Image(Images.FIRE, 7, 4, 2.5f), x, y);
        this.length = length;
        initialize(gameScreen);
        determineSizeAndPlayerDeath(gameScreen);
        createSprites();
    }

    public final void initialize(GameScreen gameScreen) {
        super.animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "0,1,2,3", 4000 / 60));
            }
        };
        lengthDirections = new int[4];
        setCurrentState(InitialState::new);
        gameScreen.getMap().add(this);
    }

    private Point getPosSprite(final Direction d, int i) {
        return d == Direction.UP ? new Point(x, y - i * image.getHeight()) : d == Direction.DOWN ? new Point(x, y + i * image.getHeight()) : d == Direction.RIGHT ? new Point(x + i * image.getWidth(), y) : new Point(x - i * image.getWidth(), y);
    }

    private void createSprites() {
        var bs = new boolean[4];
        short index = 0;
        images = new Image[1 + lengthDirections[0] + lengthDirections[1] + lengthDirections[2] + lengthDirections[3]];
        pos = new Point[1 + lengthDirections[0] + lengthDirections[1] + lengthDirections[2] + lengthDirections[3]];
        pos[index] = new Point(x, y);
        images[index++] = new Image(image.getImage(), 7, 4, (float) 2.5, 0);
        for (var i = 1; i <= length; i++)
            for (var value : Direction.values()) {
                if (bs[value.ordinal()])
                    continue;
                final var p = getPosSprite(value, i);
                if (i <= lengthDirections[value.ordinal()] && i != length) {
                    pos[index] = p;
                    images[index++] = new Image(image.getImage(), 7, 4, 2.5f, value == Direction.UP || value == Direction.DOWN ? 6 : 5);
                }
                if (i < lengthDirections[value.ordinal()] || lengthDirections[value.ordinal()] == 0)
                    continue;
                if (i == length) {
                    pos[index] = p;
                    images[index++] = new Image(image.getImage(), 7, 4, 2.5f, value.ordinal() + 1);
                }
                bs[value.ordinal()] = true;
            }
    }

    @Override
    public void update(final Screen screen, final long elapsedTime) {
        super.update(screen, elapsedTime);
        if (currentState instanceof NullState)
            return;
        final var i = animations.get(InitialState.class).animation.getCurrentFrame();
        for (final var sprite : images)
            sprite.update(i);
    }

    @Override
    public void draw(final Graphics2D g) {
        if (currentState instanceof NullState || !isActive())
            return;
        for (var i = 0; i < images.length; i++)
            images[i].draw(g, pos[i].x, pos[i].y);
    }

    private void determineSizeAndPlayerDeath(final GameScreen gameScreen) {
        var position = gameScreen.getMap().getPosition(this);
        var bs = new boolean[4];
        for (var i = 1; i <= length; i++) {
            if (!bs[Direction.UP.ordinal()] && detTamDir(gameScreen, Direction.UP, i, position.row - i, position.column))
                bs[Direction.UP.ordinal()] = true;
            if (!bs[Direction.DOWN.ordinal()] && detTamDir(gameScreen, Direction.DOWN, i, position.row + i, position.column))
                bs[Direction.DOWN.ordinal()] = true;
            if (!bs[Direction.RIGHT.ordinal()] && detTamDir(gameScreen, Direction.RIGHT, i, position.row, position.column + i))
                bs[Direction.RIGHT.ordinal()] = true;
            if (!bs[Direction.LEFT.ordinal()] && detTamDir(gameScreen, Direction.LEFT, i, position.row, position.column - i))
                bs[Direction.LEFT.ordinal()] = true;
        }
        checkPlayerDeathCenterExplosion(gameScreen, position);
    }

    private boolean detTamDir(final GameScreen gameScreen, final Direction d, final int i, final int row, final int column) {
        var m = gameScreen.getMap();
        boolean b1, b2;
        if (d == Direction.UP || d == Direction.DOWN) {
            b1 = collisionY(m, row, SteelBlock.class, Brick.class, Bomb.class, SpecialBrick.class);
            b2 = collisionY(m, row, Bomberman.class, Enemy.class);
        } else {
            b1 = collisionX(m, column, SteelBlock.class, Brick.class, Bomb.class, SpecialBrick.class);
            b2 = collisionX(m, column, Bomberman.class, Enemy.class);
        }
        if (b1) {
            gameScreen.eraseBrick(row, column);
            gameScreen.eraseBomb(row, column);
        }
        if (b2) {
            gameScreen.eraseEnemy(row, column);
            if (!gameScreen.firstPlayer().getFLAMEPASS())
                gameScreen.erasePlayer(row, column);
        }
        if (!b1 && length != i)
            return false;
        lengthDirections[d.ordinal()] = b1 ? i - 1 : i;
        return true;
    }

    private void checkPlayerDeathCenterExplosion(GameScreen gameScreen, Position position) {
        if (gameScreen.getMap().contains(position.row, position.column, Enemy.class)) {
            gameScreen.eraseEnemy(position.row, position.column);
        }
        if (centralCollision(Bomberman.class) && !gameScreen.firstPlayer().getFLAMEPASS()) {
            gameScreen.erasePlayer(position.row, position.column);
        }
    }

    private enum Direction {

        UP, DOWN, RIGHT, LEFT
    }

}
