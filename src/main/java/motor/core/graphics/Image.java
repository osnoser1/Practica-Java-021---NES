/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * @author AlfonsoAndrés
 */
public final class Image {

    private final java.awt.Image image;
    private final int files, columns;
    private final Rectangle sourceRect; // currentFrame Rectangle
    private final int width, height;
    private int state;
    private int scaledWidth, scaledHeight;
    private boolean active;
    private Color color;
    private float scale; // The scale used to display the sprite strip

    /**
     * @param image
     * @param files
     * @param columns
     * @param scale
     * @param initialState
     */
    public Image(final java.awt.Image image, final int files, final int columns, final float scale, final int initialState) {
        this(image, files, columns, scale);
        state = initialState;
    }

    /**
     * @param image
     * @param files
     * @param columns
     * @param scale
     */
    public Image(final java.awt.Image image, int files, int columns, final float scale) {
        active = true;
        this.files = files;
        this.columns = columns;
        this.image = image;
        width = this.image.getWidth(null) / columns;
        height = this.image.getHeight(null) / files;
        sourceRect = new Rectangle(0, 0, width, height);
        setScale(scale);
    }

    /**
     * @param currentFrame
     */
    public void update(int currentFrame) {
        if (!active)
            return;
        if (currentFrame < 0 || currentFrame > columns)
            System.err.println("There is not frame " + currentFrame + ".");
        // Grab the correct frame in the image strip by multiplying the currentFrame index by the frame width
        sourceRect.setBounds(currentFrame * width, state * height, width, height);
    }

    /**
     * @param state
     * @param currentFrame
     */
    public void update(int state, int currentFrame) {
        this.state = state;
        update(currentFrame);
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return las columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return las files
     */
    public int getFiles() {
        return files;
    }

    /**
     * @return el width
     */
    public int getWidth() {
        return scaledWidth;
    }

    /**
     * @return el height
     */
    public int getHeight() {
        return scaledHeight;
    }

    /**
     * @return image
     */
    public java.awt.Image getImage() {
        return image;
    }

    /**
     * @param scale
     */
    public void setScale(final float scale) {
        this.scale = scale;
        scaledWidth = (int) (width * this.scale);
        scaledHeight = (int) (height * this.scale);
    }

    /**
     * @param g
     * @param x
     * @param y
     */
    public void draw(final Graphics2D g, final int x, final int y) {
        if (!active)
            return;
        g.drawImage(image, x, y, x + scaledWidth, y + scaledHeight, sourceRect.x, sourceRect.y, sourceRect.x + sourceRect.width, sourceRect.y + sourceRect.height, color, null);
    }

}
