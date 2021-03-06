package engine.core.java.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;

/**
 * @author David Buzatto
 */
public class ScreenManager {

    private final GraphicsDevice device;
    private JFrame jFrame;

    public ScreenManager() {
        var environment
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }

    public DisplayMode[] getCompatibleDisplayModes() {
        return device.getDisplayModes();
    }

    public DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
        var goodModes = device.getDisplayModes();
        for (var mode : modes)
            for (var goodMode : goodModes)
                if (displayModesMatch(mode, goodMode))
                    return mode;
        return null;

    }

    public DisplayMode getCurrentDisplayMode() {
        return device.getDisplayMode();
    }

    public boolean displayModesMatch(final DisplayMode mode1, final DisplayMode mode2) {
        if (mode1.getWidth() != mode2.getWidth()
                || mode1.getHeight() != mode2.getHeight())
            return false;
        if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
                && mode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
                && mode1.getBitDepth() != mode2.getBitDepth())
            return false;
        return !(mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
                && mode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
                && mode1.getRefreshRate() != mode2.getRefreshRate());
    }

    public void setFullScreen(final DisplayMode displayMode) {
        if (jFrame == null)
            jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setUndecorated(true);
        jFrame.setIgnoreRepaint(true);
        jFrame.setResizable(false);
        device.setFullScreenWindow(jFrame);
        if (displayMode != null
                && device.isDisplayChangeSupported()) {
            try {
                device.setDisplayMode(displayMode);
            } catch (IllegalArgumentException ignored) {
            }
            // fix para o Mac OS
            jFrame.setSize(displayMode.getWidth(),
                    displayMode.getHeight());
        }

        // evita deadlock no Java 1.4
        try {
            EventQueue.invokeAndWait(
                    () -> jFrame.createBufferStrategy(2)
            );
        } catch (InterruptedException | InvocationTargetException ex) {
            // ignore
        }
    }

    public void setWideScreen(final DisplayMode displayMode) {
        if (jFrame == null)
            jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setIgnoreRepaint(true);
        jFrame.setResizable(false);
        if (displayMode != null) {
            if (device.isDisplayChangeSupported())
                try {
                    device.setDisplayMode(displayMode);
                } catch (IllegalArgumentException ignored) {
                }
            jFrame.setSize(displayMode.getWidth(),
                    displayMode.getHeight());
        }
        jFrame.setVisible(true);
        try {
            EventQueue.invokeAndWait(
                    () -> jFrame.createBufferStrategy(2)
            );
        } catch (InterruptedException | InvocationTargetException ex) {
            // ignore
        }
    }

    public Graphics2D getGraphics() {
        final var window = device.getFullScreenWindow();
        final var strategy = window != null ? window.getBufferStrategy() : jFrame.getBufferStrategy();
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void update() {
        final var window = device.getFullScreenWindow();
        final var strategy = window != null ? window.getBufferStrategy() : jFrame.getBufferStrategy();
        if (!strategy.contentsLost())
            strategy.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public Window getScreenWindow() {
        final var window = device.getFullScreenWindow();
        return window != null ? window : jFrame;
    }

    public int getWidth() {
        final var window = device.getFullScreenWindow();
        if (window != null)
            return window.getWidth();
        else
            return 0;
    }

    public int getHeight() {
        final var window = device.getFullScreenWindow();
        if (window != null)
            return window.getHeight();
        else
            return 0;
    }

    public void restoreScreen() {
        final var window = device.getFullScreenWindow();
        if (window != null)
            window.dispose();
        device.setFullScreenWindow(null);
    }

    public BufferedImage createCompatibleImage(int w, int h, int transparency) {
        final var window = device.getFullScreenWindow();
        if (window != null) {
            var gc = window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, transparency);
        }
        return null;
    }

    public void showFps(int fps) {
        final var window = device.getFullScreenWindow();
        if (window == null)
            jFrame.setTitle("(FPS: " + fps + ")");
    }

}
