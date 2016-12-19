/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrencia;

import lenguaje.utils.Runnable2;
import motor.core.java.gui.JFramePrincipal;
import gui.JPanelContenedor;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

/**
 *
 * @author hp
 */
public class HiloPrincipal extends Runnable2 {

    private final JFramePrincipal jFramePrincipal;
    private final BufferStrategy buffer;
    private final JPanelContenedor jPanelContenedor;
//    private final GLG2DCanvas g2DCanvas;
    private Graphics g;
    private final Insets in;

    public HiloPrincipal(JFramePrincipal jFramePrincipal, int FPS) {
        super(FPS);
        this.jFramePrincipal = jFramePrincipal;
        jPanelContenedor = JPanelContenedor.getInstance();
//        jPanelContenedor.createBufferStrategy(2);
//        buffer = jPanelContenedor.getBufferStrategy();
        jFramePrincipal.createBufferStrategy(2);
        buffer = jFramePrincipal.getBufferStrategy();
//        g2DCanvas = new GLG2DCanvas((JComponent) jPanelContenedor);
//        g2DCanvas.getGLDrawable().getGL().glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NONE);
//        jFramePrincipal.add(g2DCanvas);
        in = jFramePrincipal.getInsets();
    }

    @Override
    public void runProceso() {
        if (contFPS == 0) {
            jFramePrincipal.setTitle("(FPS: " + fpsActual + "), Bomberman - NES");
//            System.out.println("(FPS: " + fpsActual + "), Bomberman - NES");
        }
//        final Graphics g = g2DCanvas.getGraphics();
        jPanelContenedor.actualizar(tiempoTranscurrido / 1000000);
        g = buffer.getDrawGraphics().create(in.left, in.top, 656 - in.left - in.right, 620 - in.top - in.bottom);
//        g2DCanvas.repaint();
        jPanelContenedor.paint(g);
        g.dispose();
        if (!buffer.contentsLost())
            buffer.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
