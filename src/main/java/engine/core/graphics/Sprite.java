/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.graphics;

import utils.game.Screen;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.function.Supplier;

import engine.core.graphics.spritedefaultstates.NullState;
import engine.core.input.GamePad;

/**
 * @author
 */
public abstract class Sprite {

    protected int speed, x, y;
    protected HashMap<Class<? extends SpriteState>, AnimationWrapper> animations;
    protected final Image image;
    protected SpriteState currentState;
    protected GamePad gamePad;
    protected String id;
    private final Point center;
    private AnimationWrapper currentAnimationWrapper;

    public void update(final Screen screen, final long elapsedTime) {
        if (currentState instanceof NullState) {
            return;
        }
        if (image.isActive()) {
            image.update(currentAnimationWrapper.row,
                    currentAnimationWrapper.animation.getCurrentFrame());
        }
        var supplier = currentState.handleInput(this, gamePad);
        if (supplier != null) {
            currentState.onExit(this, screen);
            setCurrentState(supplier);
        }
        currentState.update(this, screen, elapsedTime);
    }

    protected Sprite(final Image image, final int x, final int y) {
        this.image = image;
        this.x = x;
        this.y = y;
        center = new Point();
    }

    public final String getId() {
        return id;
    }

    public final SpriteState getCurrentState() {
        return currentState;
    }

    public final void setCurrentState(final Supplier<SpriteState> supplier) {
        currentState = supplier.get();
        currentAnimationWrapper = animations.get(currentState.getClass());
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getWidth() {
        return image.getWidth();
    }

    public final int getHeight() {
        return image.getHeight();
    }

    protected final short getAxisHorizontal(int X) {
        return (short) (X / image.getWidth());
    }

    protected final short getAxisVertical(int Y) {
        return (short) (Y / image.getHeight());
    }

    public final Point getCenter() {
        center.setLocation(getX() + image.getWidth() / 2, getY() + image.getHeight() / 2);
        return center;
    }

    public final void translate(final int dx, final int dy) {
        x += dx;
        y += dy;
    }

    /**
     * @return Returns true if the character is active, false if it is not.
     */
    public final boolean isActive() {
        return image.isActive();
    }

    /**
     * @param active indicates whether or not you want to activate the character.
     */
    public final void setActive(final boolean active) {
        image.setActive(active);
    }

    public final void setAxisPosition(final int x, final int y) {
        setLocation(x * image.getWidth(), y * image.getHeight());
    }

    public final boolean updateAnimation(final long elapsedTime) {
        return currentAnimationWrapper.animation.update(elapsedTime);
    }

    public void draw(final Graphics2D g) {
        if (!image.isActive() || currentState instanceof NullState)
            return;
        image.draw(g, x, y);
    }

    /**
     * @return the image
     */
    public final Image getImage() {
        return image;
    }

    public final GamePad getGamePad() {
        return gamePad;
    }

}
