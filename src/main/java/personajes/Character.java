/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personajes;

import gui.GameScreen;
import motor.core.map.Map;
import static personajes.Character.Direction.*;
import utilidades.juego.Screen;
import motor.core.graphics.Sprite;
import motor.core.graphics.Image;
import motor.core.input.IGamePadController;

public abstract class Character extends Sprite {

    public enum Direction {
        HORIZONTAL, VERTICAL
    }

    protected final int varX = 3;
    protected final int varY = 3;
    protected int smart;
    protected static final int SPEED_SLOWEST = 1, SPEED_SLOW = 2, SPEED_MID = 4, SPEED_FAST = 5, SMART_LOW = 1, SMART_MID = 2, SMART_HIGH = 3, SMART_IMPOSSIBLE = 4;
    protected Intelligence intelligence;
    protected IGamePadController padController;
    protected boolean wallpass, insideBomb, BOMBPASS;

    protected Character(final Image image, final int x, final int y) {
        super(image, x, y);
    }

    @Override
    public void update(final Screen screen, final long elapsedTime) {
        super.update(screen, elapsedTime);
    }
    
    public final Intelligence getIntelligence() {
        return intelligence;
    }

    public final void moveRight(final GameScreen gameScreen) {
        speed = Math.abs(speed);
        updateX(gameScreen);
    }

    public final void moveLeft(final GameScreen gameScreen) {
        speed = -Math.abs(speed);
        updateX(gameScreen);
    }

    public final void moveUp(final GameScreen gameScreen) {
        speed = -Math.abs(speed);
        updateY(gameScreen);
    }

    public final void moveDown(final GameScreen gameScreen) {
        speed = Math.abs(speed);
        updateY(gameScreen);
    }

    public void startIntelligence() {
        intelligence = new Intelligence(this);
        intelligence.start();
    }

    public void stopIntelligence() {
        if (intelligence == null)
            return;
        intelligence.stop();
        intelligence = null;
    }

    private void updateX(final GameScreen gameScreen) {
        if (!centralCollision(Bomb.class))
            insideBomb = false;
        var adjustment = getAdjustmentX(gameScreen.getMap());
        if (adjustment != 0)
            translate(adjustment, 0);
    }

    private void updateY(final GameScreen gameScreen) {
        if (!centralCollision(Bomb.class))
            insideBomb = false;
        var adjustment = getAdjustmentY(gameScreen.getMap());
        if (adjustment != 0)
            translate(0, adjustment);
    }

    private boolean collision(final Map m, final Direction d, final int valEje, final Class<?>... classes) {
        for (final var se : classes)
            if (d == VERTICAL && (m.contains(valEje, getAxisHorizontal(getX() + 2 * varX), se) || m.contains(valEje, getAxisHorizontal(getX() + image.getWidth() - 2 * varX), se))
                    || d == HORIZONTAL && (m.contains(getAxisVertical(getY() + 2 * varY), valEje, se) || m.contains(getAxisVertical(getY() + image.getHeight() - 2 * varY), valEje, se)))
                return true;
        return false;
    }

    protected final boolean collisionX(final Map m, final int x, final Class<?>... classes) {
        return collision(m, HORIZONTAL, x, classes);
    }

    protected final boolean collisionY(final Map m, final int y, final Class<?>... classes) {
        return collision(m, VERTICAL, y, classes);
    }

    protected final boolean centralCollision(final Class<?>... classes) {
        return Map.getInstance().contains(this, classes);
    }

    public void setWallpass(boolean wallpass) {
        this.wallpass = wallpass;
    }

    private int getAdjustmentX(final Map m) {
        var adjustment = 0;
        int pos = speed < 0 ? getAxisHorizontal(getX() + speed) : getAxisHorizontal(getX() + image.getWidth() + speed);
        if (collisionX(m, pos, SteelBlock.class) || !wallpass && collisionX(m, pos, Brick.class) || !BOMBPASS && !insideBomb && collisionX(m, pos, Bomb.class))
            adjustment = speed < 0
                    ? pos * image.getWidth() + image.getWidth() - (getX() + speed)
                    : pos * image.getWidth() - (1 + image.getWidth() + getX() + Math.abs(speed));
        return speed + adjustment;
    }

    private int getAdjustmentY(final Map m) {
        var adjustment = 0;
        int pos = speed < 0 ? getAxisVertical(getY() + speed) : getAxisVertical(getY() + image.getHeight() + speed);
        if (collisionY(m, pos, SteelBlock.class) || !wallpass && collisionY(m, pos, Brick.class) || !BOMBPASS && !insideBomb && collisionY(m, pos, Bomb.class))
            adjustment = speed < 0
                    ? pos * image.getHeight() + image.getHeight() - (getY() + speed)
                    : pos * image.getHeight() - (1 + image.getHeight() + getY() + Math.abs(speed));
        return speed + adjustment;
    }

    public void setBOMBPASS(boolean BOMBPASS) {
        this.BOMBPASS = BOMBPASS;
    }

}
