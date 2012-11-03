
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Sonidos.Sonidos;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelGrafico extends javax.swing.JPanel {
    
    private static JPanelGrafico instance;

    private JPanelGrafico() {
        super(new java.awt.BorderLayout());
        initComponents();
    }

    public static JPanelGrafico getInstance() {
        return instance == null ? (instance = new JPanelGrafico()) : instance;
    }

    private void initComponents() {
        setVisible(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                escalamientoJPanel(c.getSize());
            }
        });
    }

    public void escalamientoJPanel(Dimension dim) {
        if(!isVisible())
            return;
        JPanelJuego.getInstance().setSIZE(dim);
        JPanelInformacion.getInstance().setSIZE(dim);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public void iniciarJuego() {
        JPanelJuego jPanelJuego = JPanelJuego.getInstance();
        jPanelJuego.generarMapa();
        jPanelJuego.repaint();
        add(JPanelInformacion.getInstance(), java.awt.BorderLayout.NORTH);
        add(jPanelJuego);
        Sonidos.getInstance().getSonido(Sonidos.STAGE_THEME).loop();
        setVisible(true);
        JPanelInformacion.getInstance().iniciarCuentaRegresiva();
        jPanelJuego.iniciarHiloPrincipal();
    }
    
}