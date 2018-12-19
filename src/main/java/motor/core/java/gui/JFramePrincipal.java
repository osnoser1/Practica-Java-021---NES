/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.java.gui;

import motor.core.java.controllers.CJFramePrincipal;
import lenguaje.utils.ManejadorDeArchivos;
import concurrencia.HiloPrincipal;
import java.awt.Frame;
import java.awt.Graphics;

/**
 *
 * @author Alfonso Andrés
 */
public class JFramePrincipal extends Frame {

    final HiloPrincipal hiloPrincipal;

    public JFramePrincipal() {
        initComponents();
        setVisible(true);
        hiloPrincipal = new HiloPrincipal(this, (short) 60);
        hiloPrincipal.start();
    }

    private void initComponents() {
        requestFocusInWindow();
        setFocusable(true);
        setAutoRequestFocus(true);
        setIgnoreRepaint(true);
//        add(JPanelContenedor.getInstance());
//        setJMenuBar(JBarraMenu.getInstance());
        setSize(656, 600);
        var controller = CJFramePrincipal.getInstance().setJFramePrincipal(this);
        addWindowFocusListener(controller);
        addWindowListener(controller);
        addComponentListener(controller);
        addKeyListener(CJFramePrincipal.Teclado.getInstance());
        setIconImage(ManejadorDeArchivos.getInstance().loadImageJAR("Imagenes/bomber_32x32.png"));
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
