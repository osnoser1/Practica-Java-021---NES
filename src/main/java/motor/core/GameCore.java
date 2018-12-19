package motor.core;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import motor.core.java.graphics.GestorPantalla;

public abstract class GameCore {
    
    protected static final int FONT_SIZE = 24;

    private static final DisplayMode[] POSSIBLE_MODES = {
        new DisplayMode(620, 600, 32, 0),
        new DisplayMode(1366, 768, 32, 0)
    };
    
    private boolean estaCorriendo;
    private long tiempoBucle, tiempoFPS;
    private int fps;
    protected GestorPantalla screen;
    
    public void stop() {
        estaCorriendo = false;
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
            // primeiro aguarda que a m�quina virtual finaliza por si pr�pria
            try {
                Thread.sleep( 2000 );
            } catch ( InterruptedException ignored) { }
            // o sistema ainda est� rodando, ent�o for�a a finaliza��o
            System.exit( 0 );
        });
        thread.setDaemon( true );
        thread.start();
    }
    
    public void init() {
        screen = new GestorPantalla();
        final var displayMode
                =                screen.findFirstCompatibleMode( POSSIBLE_MODES );
//        screen.setFullScreen(displayMode);
//        screen.setWideScreen(displayMode);
        screen.setWideScreen(POSSIBLE_MODES[0]);
//        Window window = screen.getScreenWindow();
//        window.setFont( new Font( "Dialog", Font.PLAIN, FONT_SIZE ) );
//        window.setBackground( Color.BLUE );
//        window.setForeground(Color.WHITE);
        tiempoBucle = 1000000000 / 5000;
        estaCorriendo = true;
    }    
    
    public void gameLoop() {
        var tiempoAnterior = System.nanoTime();
        while (estaCorriendo) {
            var now = System.nanoTime();
            var tiempoTranscurrido = now - tiempoAnterior;
            showFps(now);
            if (tiempoTranscurrido > tiempoBucle) {
                tiempoAnterior = now;
                update(tiempoTranscurrido / 1000000);
                var g = screen.getGraphics();
                draw(g);
                g.dispose();
                screen.update();
                ++fps;
            }
            sleep(tiempoTranscurrido);
        }
    }

    private void showFps(long tiempoActual) {
        if (tiempoActual - tiempoFPS <= 1000000000)
            return;
        tiempoFPS = tiempoActual;
        screen.mostrarFps(fps);
        fps = 0;
    }

    private void sleep(final long tiempoTranscurrido) {
        if (tiempoTranscurrido > tiempoBucle)
            return;
        Thread.yield();
        try {
            Thread.sleep((tiempoBucle - tiempoTranscurrido) / 1000000);
        } catch (final Exception ignored) {
        }
    }

    public abstract void update(final long tiempoTranscurrido);
    
    public abstract void draw(final Graphics2D g);
    
}