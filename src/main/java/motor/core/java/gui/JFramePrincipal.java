/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.java.gui;

import motor.core.java.controllers.CJFramePrincipal;
import lenguaje.utils.FileManager;
import concurrencia.PrincipalThread;
import java.awt.Frame;
import java.awt.Graphics;

/**
 *
 * @author Alfonso Andrés
 */
public class JFramePrincipal extends Frame {

    final PrincipalThread principalThread;

    public JFramePrincipal() {
        initComponents();
        setVisible(true);
        principalThread = new PrincipalThread(this, (short) 60);
        principalThread.start();
    }

    private void initComponents() {
        requestFocusInWindow();
        setFocusable(true);
        setAutoRequestFocus(true);
        setIgnoreRepaint(true);
//        add(JPanelContainer.getInstance());
//        setJMenuBar(JMenuBar.getInstance());
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
}
