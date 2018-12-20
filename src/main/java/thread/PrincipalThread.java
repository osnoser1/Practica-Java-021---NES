/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import bomberman.configuration.Configuration;
import gui.JPanelContainer;
import language.utils.Runnable2;
import engine.core.java.gui.JFramePrincipal;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

/**
 *
 * @author hp
 */
public class PrincipalThread extends Runnable2 {

    private final JFramePrincipal jFramePrincipal;
    private final BufferStrategy buffer;
    private final JPanelContainer jPanelContainer;
//    private final GLG2DCanvas g2DCanvas;
    private Graphics g;
    private final Insets in;
    private final Configuration c;

    public PrincipalThread(JFramePrincipal jFramePrincipal, int FPS) {
        super(FPS);
        this.jFramePrincipal = jFramePrincipal;
        jPanelContainer = JPanelContainer.getInstance();
//        jPanelContainer.createBufferStrategy(2);
//        buffer = jPanelContainer.getBufferStrategy();
        jFramePrincipal.createBufferStrategy(2);
        buffer = jFramePrincipal.getBufferStrategy();
//        g2DCanvas = new GLG2DCanvas((JComponent) jPanelContainer);
//        g2DCanvas.getGLDrawable().getGL().glTexParameterf(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NONE);
//        jFramePrincipal.add(g2DCanvas);
        in = jFramePrincipal.getInsets();
        c = Configuration.getInstance();
    }

    @Override
    public void runProcess() {
        if (contFPS == 0) {
            jFramePrincipal.setTitle("(FPS: " + currentFps + "), Bomberman - NES");
//            System.out.println("(FPS: " + currentFps + "), Bomberman - NES");
        }
//        final Graphics g = g2DCanvas.getGraphics();
        jPanelContainer.update(elapsedTime / 1000000);
        g = buffer.getDrawGraphics();
        g.translate(in.left, in.top);
        ((Graphics2D) g).scale(c.getScaleX(), c.getScaleY());
//        g2DCanvas.repaint();
        jPanelContainer.paint(g);
        g.dispose();
        if (!buffer.contentsLost())
            buffer.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
