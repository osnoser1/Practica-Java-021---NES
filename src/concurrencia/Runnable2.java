/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrencia;

/**
 *
 * @author Alfonso
 * @author Felix
 * @author Juan
 */
public abstract class Runnable2 implements Runnable {

    protected Thread hilo;
    protected int FPS;
    protected long tiempoEnMilisegundos;
    protected long tiempoTranscurrido;
    protected Boolean estaActivo = false;
    protected Boolean pausa = false;

    private Runnable2() {
        hilo = new Thread(this);
    }

    public Runnable2(int FPS) {
        this();
        this.FPS = FPS;
        tiempoEnMilisegundos = 1000 / FPS;
    }

    public abstract void runProceso();

    @Override
    public synchronized void run() {
        long tiempoActual = System.currentTimeMillis();
        while(estaActivo){
				if(pausa)
					try {
						this.wait();
					} catch (final InterruptedException e) {
						e.printStackTrace();
					}
//            if(pausa){
//                try {
//                    Thread.sleep(500);
//                } catch (final Exception e) { }
//                continue;
//            }
            tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            if(tiempoTranscurrido > tiempoEnMilisegundos){
                tiempoActual = System.currentTimeMillis();
                runProceso();
            }else
                try {
                    Thread.sleep(tiempoEnMilisegundos - tiempoTranscurrido);
                } catch (final Exception e) { }
        }
    }

    public void start() throws IllegalThreadStateException {
        estaActivo = true;
        hilo.start();
    }

    public void stop(){
        estaActivo = false;
    }

    public void setFPS(short FPS) {
        this.FPS = FPS;
        tiempoEnMilisegundos = 1000 / FPS;
    }

    public int getFPS() {
        return FPS;
    }

    public void pausar() {
        if(!estaActivo)
            throw new IllegalArgumentException();
        pausa = true;
    }

    public synchronized void reanudar() {
        if(!estaActivo)
            throw new IllegalArgumentException();
        	pausa = false;
        	notify();
    }

}
