package motor.core.java.graphics;

import java.awt.*;
import java.awt.image.*;
import java.lang.reflect.*;
import javax.swing.*;

/**
 * A classe GestorPantalla gerencia a inicializa��o e visualiza��o de modos de
 * tela cheia.
 *
 * @author David Buzatto
 */
public class GestorPantalla {

    private final GraphicsDevice device;
    private JFrame jFrame;

    public GestorPantalla() {
        GraphicsEnvironment environment
                = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }

    public DisplayMode[] getCompatibleDisplayModes() {
        return device.getDisplayModes();
    }

    public DisplayMode findFirstCompatibleMode(DisplayMode[] modes) {
        DisplayMode[] goodModes = device.getDisplayModes();
        for (DisplayMode mode : modes)
            for (DisplayMode goodMode : goodModes)
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
            } catch (IllegalArgumentException ex) {
            }
            // fix para o Mac OS
            jFrame.setSize(displayMode.getWidth(),
                    displayMode.getHeight());
        }

        // evita deadlock no Java 1.4
        try {
            EventQueue.invokeAndWait(
                    new Runnable() {
                        @Override
                        public void run() {
                            jFrame.createBufferStrategy(2);
                        }
                    }
            );
        } catch (InterruptedException | InvocationTargetException ex) {
            // ignora
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
                } catch (IllegalArgumentException ex) {
                }
            jFrame.setSize(displayMode.getWidth(),
                    displayMode.getHeight());
        }
        jFrame.setVisible(true);
        try {
            EventQueue.invokeAndWait(
                    new Runnable() {
                        @Override
                        public void run() {
                            jFrame.createBufferStrategy(2);
                        }
                    }
            );
        } catch (InterruptedException | InvocationTargetException ex) {
            // ignora
        }
    }

    public Graphics2D getGraphics() {
        final Window window = device.getFullScreenWindow();
        final BufferStrategy strategy = window != null ? window.getBufferStrategy() : jFrame.getBufferStrategy();
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void update() {
        final Window window = device.getFullScreenWindow();
        final BufferStrategy strategy = window != null ? window.getBufferStrategy() : jFrame.getBufferStrategy();
        if (!strategy.contentsLost())
            strategy.show();
        Toolkit.getDefaultToolkit().sync();
    }

    public Window getScreenWindow() {
        final Window window = device.getFullScreenWindow();
        return window != null ? window : jFrame;
    }

    public int getWidth() {
        final Window window = device.getFullScreenWindow();
        if (window != null)
            return window.getWidth();
        else
            return 0;
    }

    public int getHeight() {
        final Window window = device.getFullScreenWindow();
        if (window != null)
            return window.getHeight();
        else
            return 0;
    }

    public void restoreScreen() {
        final Window window = device.getFullScreenWindow();
        if (window != null)
            window.dispose();
        device.setFullScreenWindow(null);
    }

    public BufferedImage createCompatibleImage(int w, int h, int transparency) {
        final Window window = device.getFullScreenWindow();
        if (window != null) {
            GraphicsConfiguration gc = window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, transparency);
        }
        return null;
    }

    public void mostrarFps(int fps) {
        final Window window = device.getFullScreenWindow();
        if (window == null)
            jFrame.setTitle("(FPS: " + fps + ")");
    }

}
