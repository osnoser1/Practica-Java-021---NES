/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import engine.core.graphics.Sprite;

/**
 *
 * @author AlfonsoAndrés
 */
public class Camera {
    
    private final Rectangle position;
    private final Dimension levelSize;
    private final int quarter, third;

    public Camera(final Rectangle position, final Dimension levelSize) {
        this.position = position;
        this.levelSize = levelSize;
        quarter = levelSize.width / 4;
        third = 3 * quarter;
    }
    
    public void update(final Point guidePoint) {
        if(guidePoint.x > quarter && guidePoint.x < third)
            position.x = guidePoint.x - quarter;
        if(guidePoint.x > third && guidePoint.x < quarter)
            position.x = quarter - guidePoint.x;
        if(position.x < 0)
            position.x = 0;
        if(position.x + position.width > levelSize.width)
            position.x = levelSize.width - position.width;
    }
    
    private boolean contains(int x, int y, int width, int height) {
        return position.intersects(x, y, width, height);
    }

    public Rectangle getPosition() {
        return position;
    }

    public void restart() {
        position.x = position.y = 0;
    }

    public boolean contains(final Sprite s) {
        return contains(s.getX(), s.getY(), s.getWidth(), s.getHeight());
    }
    
}
