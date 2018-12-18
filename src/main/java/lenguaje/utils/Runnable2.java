/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Alfonso
 * @author Felix
 * @author Juan
 */
public abstract class Runnable2 implements Runnable {

    protected Thread hilo;
    protected int FPS, contFPS;
    protected long nanosegundos, tiempoTranscurrido, tiempoFPS;
    protected boolean estaActivo = false;
    protected boolean pausa = false;
    protected int fpsActual;


    public Runnable2(int FPS) {
        this.FPS = FPS;
        nanosegundos = 1000000000 / FPS;
    }

    public abstract void runProceso();

    protected void preInit() {
    }

    @Override
    public synchronized void run() {
        preInit();
        estaActivo = true;
        long tiempoAnterior = System.nanoTime();
        while (estaActivo) {
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("interrupted");
                estaActivo = false;
                break;
            }
            if (pausa)
                try {
                    this.wait();
                } catch (final InterruptedException e) {
                }
            long now = System.nanoTime();
            tiempoTranscurrido = now - tiempoAnterior;
            if (now - tiempoFPS > 1000000000) {
                fpsActual = contFPS;
                tiempoFPS = now;
                contFPS = 0;
            }
            if (tiempoTranscurrido > nanosegundos) {
                tiempoAnterior = now;
                runProceso();
                ++contFPS;
            } else {
                Thread.yield();
                try {
                    Thread.sleep((nanosegundos - tiempoTranscurrido) / 1000000);
                } catch (final Exception e) {
                }
            }
        }
    }

    public void start() throws IllegalThreadStateException {
        if (estaActivo) {
            return;
        }
        hilo = new Thread(this);
        hilo.setDaemon(true);
        hilo.start();
    }

    public void stop() {
        estaActivo = false;
    }

    public void setFPS(short FPS) {
        this.FPS = FPS;
        nanosegundos = 1000 / FPS;
    }

    public int getFPS() {
        return FPS;
    }

    public void pausar() {
        if (!estaActivo) {
            throw new IllegalArgumentException();
        }
        pausa = true;
    }

    public synchronized void reanudar() {
        if (!estaActivo) {
            throw new IllegalArgumentException();
        }
        pausa = false;
        notify();
    }

    public boolean estaActivo() {
        return estaActivo;
    }

}
