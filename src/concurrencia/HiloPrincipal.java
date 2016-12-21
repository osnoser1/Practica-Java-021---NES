/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrencia;

import lenguaje.utils.Runnable2;
import motor.core.java.gui.JFramePrincipal;
import gui.JPanelContenedor;
import org.jogamp.glg2d.GLG2DCanvas;

/**
 *
 * @author hp
 */
public class HiloPrincipal extends Runnable2 {

    private final JFramePrincipal jFramePrincipal;
    private final JPanelContenedor jPanelContenedor;
    private final GLG2DCanvas g2DCanvas;

    public HiloPrincipal(JFramePrincipal jFramePrincipal, int FPS) {
        super(FPS);
        this.jFramePrincipal = jFramePrincipal;
        jPanelContenedor = JPanelContenedor.getInstance();
        g2DCanvas = new GLG2DCanvas(jPanelContenedor);
//        g2DCanvas.getGLDrawable().getGL().glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NONE);
        jFramePrincipal.add(g2DCanvas);
    }

    @Override
    public void runProceso() {
        if (contFPS == 0) {
            jFramePrincipal.setTitle("(FPS: " + fpsActual + "), Bomberman - NES");
//            System.out.println("(FPS: " + fpsActual + "), Bomberman - NES");
        }
        jPanelContenedor.actualizar(tiempoTranscurrido / 1000000);
        g2DCanvas.repaint();
    }
}
