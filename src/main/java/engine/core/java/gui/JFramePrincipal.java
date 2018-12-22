/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.java.gui;

import engine.core.game.Game;
import engine.core.java.controllers.CJFramePrincipal;
import language.utils.FileManager;
import org.reflections.Reflections;
import thread.PrincipalThread;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Alfonso Andrés
 */
public class JFramePrincipal extends Frame {

    final PrincipalThread principalThread;
    private final Game game;

    public JFramePrincipal(Game game) {
        initComponents();
        setVisible(true);
        this.game = game;
        principalThread = new PrincipalThread(this, (short) 60);
        principalThread.start();
    }

    private void initComponents() {
        requestFocusInWindow();
        setFocusable(true);
        setAutoRequestFocus(true);
        setIgnoreRepaint(true);
        setSize(656, 600);
        var controller = CJFramePrincipal.getInstance().setJFramePrincipal(this);
        addWindowFocusListener(controller);
        addWindowListener(controller);
        addComponentListener(controller);
        addKeyListener(CJFramePrincipal.Keyboard.getInstance());
        setIconImage(FileManager.getInstance().loadImageJAR("images/bomber_32x32.png"));
    }

    @Override
    public void paint(Graphics g) {
    }

    @Override
    public void paintAll(Graphics g) {
    }

    @Override
    public void paintComponents(Graphics g) {
    }

    @Override
    public void update(Graphics g) {
    }

    public Game getGame() {
        return game;
    }
}
