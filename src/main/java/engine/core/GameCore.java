package engine.core;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import engine.core.java.graphics.ScreenManager;

public abstract class GameCore {
    
    protected static final int FONT_SIZE = 24;

    private static final DisplayMode[] POSSIBLE_MODES = {
        new DisplayMode(620, 600, 32, 0),
        new DisplayMode(1366, 768, 32, 0)
    };
    
    private boolean isRunning;
    private long loopTime, fpsTime;
    private int fps;
    protected ScreenManager screen;
    
    public void stop() {
        isRunning = false;
    }
    
    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            screen.restoreScreen();
            lazilyExit();
        }
    }
    
    public void lazilyExit() {
        var thread = new Thread(() -> {
            try {
                Thread.sleep( 2000 );
            } catch ( InterruptedException ignored) { }
            System.exit( 0 );
        });
        thread.setDaemon( true );
        thread.start();
    }
    
    public void init() {
        screen = new ScreenManager();
        final var displayMode
                =                screen.findFirstCompatibleMode( POSSIBLE_MODES );
//        screen.setFullScreen(displayMode);
//        screen.setWideScreen(displayMode);
        screen.setWideScreen(POSSIBLE_MODES[0]);
//        Window window = screen.getScreenWindow();
//        window.setFont( new Font( "Dialog", Font.PLAIN, FONT_SIZE ) );
//        window.setBackground( Color.BLUE );
//        window.setForeground(Color.WHITE);
        loopTime = 1000000000 / 5000;
        isRunning = true;
    }    
    
    public void gameLoop() {
        var previousTime = System.nanoTime();
        while (isRunning) {
            var now = System.nanoTime();
            var elapsedTime = now - previousTime;
            showFps(now);
            if (elapsedTime > loopTime) {
                previousTime = now;
                update(elapsedTime / 1000000);
                var g = screen.getGraphics();
                draw(g);
                g.dispose();
                screen.update();
                ++fps;
            }
            sleep(elapsedTime);
        }
    }

    private void showFps(long currentTime) {
        if (currentTime - fpsTime <= 1000000000)
            return;
        fpsTime = currentTime;
        screen.showFps(fps);
        fps = 0;
    }

    private void sleep(final long elapsedTime) {
        if (elapsedTime > loopTime)
            return;
        Thread.yield();
        try {
            Thread.sleep((loopTime - elapsedTime) / 1000000);
        } catch (final Exception ignored) {
        }
    }

    public abstract void update(final long elapsedTime);
    
    public abstract void draw(final Graphics2D g);
    
}