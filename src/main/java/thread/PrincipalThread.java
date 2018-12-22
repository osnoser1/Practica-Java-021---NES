/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import bomberman.configuration.Configuration;
import engine.core.game.Game;
import engine.core.java.gui.JFramePrincipal;
import language.utils.Runnable2;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * @author hp
 */
public class PrincipalThread extends Runnable2 {

    private final JFramePrincipal jFramePrincipal;
    private final BufferStrategy buffer;
    private final Game game;
    private final Insets in;
    private final Configuration c;
    //    private final GLG2DCanvas g2DCanvas;
    private Graphics g;

    public PrincipalThread(JFramePrincipal jFramePrincipal, int FPS) {
        super(FPS);
        this.jFramePrincipal = jFramePrincipal;
        game = jFramePrincipal.getGame();
//        game.createBufferStrategy(2);
//        buffer = game.getBufferStrategy();
        jFramePrincipal.createBufferStrategy(2);
        buffer = jFramePrincipal.getBufferStrategy();
//        g2DCanvas = new GLG2DCanvas((JComponent) game);
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
        game.update(elapsedTime / 1000000);
        g = buffer.getDrawGraphics();
        g.translate(in.left, in.top);
        ((Graphics2D) g).scale(c.getScaleX(), c.getScaleY());
//        g2DCanvas.repaint();
        game.paint(g);
        g.dispose();
        if (!buffer.contentsLost())
            buffer.show();
        Toolkit.getDefaultToolkit().sync();
    }
}
