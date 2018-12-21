/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman.configuration;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * @author AlfonsoAndrés
 */
public class Configuration {

    private static Configuration instance;
    private final Dimension windowSize = new Dimension(656, 600);
    private final Dimension internalResolution = new Dimension(640, 560);
    private final Point2D.Double scale = new Point2D.Double(1, 1);

    private Configuration() {
    }

    public static Configuration getInstance() {
        return instance == null ? (instance = new Configuration()) : instance;
    }

    public void setWindowSize(int width, int height) {
        windowSize.setSize(width, height);
        scale.setLocation(
                windowSize.getWidth() / internalResolution.getWidth(),
                windowSize.getHeight() / internalResolution.getHeight()
        );
    }

    public Dimension getWindowSize() {
        return windowSize;
    }

    public double getScaleX() {
        return scale.x;
    }

    public double getScaleY() {
        return scale.y;
    }

}
