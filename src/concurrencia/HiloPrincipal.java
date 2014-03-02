/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrencia;

import gui.ContenedorGrafico;
import gui.JFramePrincipal;
import java.awt.image.BufferStrategy;

/**
 *
 *
 */
public class HiloPrincipal extends Runnable2 {
    
    private final JFramePrincipal jFramePrincipal;
    private final BufferStrategy buffer;
    private final ContenedorGrafico contenedorGrafico;

    public HiloPrincipal(JFramePrincipal jFramePrincipal, int FPS) {
        super(FPS);
        this.jFramePrincipal = jFramePrincipal;
        buffer = jFramePrincipal.getBufferStrategy();
        contenedorGrafico = ContenedorGrafico.getInstance();
    }

    @Override
    public void runProceso() {
        contenedorGrafico.actualizar(tiempoEnMilisegundos);
        jFramePrincipal.paintComponents(buffer.getDrawGraphics());
        buffer.show();
    }
}
