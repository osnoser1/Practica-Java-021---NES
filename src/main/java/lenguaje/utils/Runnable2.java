/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje.utils;

/**
 *
 * @author Alfonso
 * @author Felix
 * @author Juan
 */
public abstract class Runnable2 implements Runnable {

    protected Thread thread;
    protected int FPS, contFPS;
    protected long nanoSeconds, elapsedTime, fpsTime;
    protected boolean isActive = false;
    protected boolean pause = false;
    protected int currentFps;


    public Runnable2(int FPS) {
        this.FPS = FPS;
        nanoSeconds = 1000000000 / FPS;
    }

    public abstract void runProcess();

    protected void preInit() {
    }

    @Override
    public synchronized void run() {
        preInit();
        isActive = true;
        var previousTime = System.nanoTime();
        while (isActive) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("interrupted");
                isActive = false;
                break;
            }
            if (pause)
                try {
                    this.wait();
                } catch (final InterruptedException ignored) {
                }
            var now = System.nanoTime();
            elapsedTime = now - previousTime;
            if (now - fpsTime > 1000000000) {
                currentFps = contFPS;
                fpsTime = now;
                contFPS = 0;
            }
            if (elapsedTime > nanoSeconds) {
                previousTime = now;
                runProcess();
                ++contFPS;
            } else {
                Thread.yield();
                try {
                    Thread.sleep((nanoSeconds - elapsedTime) / 1000000);
                } catch (final Exception ignored) {
                }
            }
        }
    }

    public void start() throws IllegalThreadStateException {
        if (isActive) {
            return;
        }
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        isActive = false;
    }

    public void setFPS(short FPS) {
        this.FPS = FPS;
        nanoSeconds = 1000 / FPS;
    }

    public int getFPS() {
        return FPS;
    }

    public void pause() {
        if (!isActive) {
            throw new IllegalArgumentException();
        }
        pause = true;
    }

    public synchronized void resume() {
        if (!isActive) {
            throw new IllegalArgumentException();
        }
        pause = false;
        notify();
    }

    public boolean isActive() {
        return isActive;
    }

}
