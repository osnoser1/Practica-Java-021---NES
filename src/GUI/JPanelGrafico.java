
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

import Bomberman.Core.Constantes;
import Dependencias.Mapa;
import Sonidos.Sonidos;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Alfonso Andrés
 */
public class JPanelGrafico extends javax.swing.JPanel implements Constantes {
    private static JPanelGrafico instance;
    private JPanel jPanel;

    private JPanelGrafico() {
        super(new java.awt.BorderLayout());
        initComponents();
    }

    public static JPanelGrafico getInstance() {
        return instance == null ? (instance = new JPanelGrafico()) : instance;
    }

    private void initComponents() {
        setVisible(false);
        jPanel = new JPanel(new java.awt.FlowLayout(0, 0, 0));
        jPanel.setBackground(new Color(188, 188, 188));
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                escalamientoJPanel(c.getSize());
                // System.out.println("Prueba evento componentResized "+c.getSize());
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

    public void generarMapa() {
        Random random = new Random();
        int a, b, c;
        JPanelJuego jPanelJuego = JPanelJuego.getInstance();
        Mapa.getInstance().setObjetoMapa("B", (short)1, (short)1);
        for(int i = 0; i < 55; i++) {
            do {
                a = random.nextInt(30);
                b = random.nextInt(12);
                if(a < 3 && b == 1 || a == 1 && b < 3)
                    continue;
                if("V".equals(Mapa.getInstance().getObjetoMapa(b, a))) {
                    Mapa.getInstance().setObjetoMapa("L", (short)b, (short)a);
                    jPanelJuego.pintarCasilla(b, a);
                    break;
                }
            } while(true);
        }
        for(int i = 0; i < 10; i++) {
            do {
                a = random.nextInt(30);
                b = random.nextInt(12);
                c = random.nextInt(8);
                if("V".equals(Mapa.getInstance().getObjetoMapa(b, a))) {
                    Mapa.getInstance().setObjetoMapa(determinarEnemigo(c), (short)b, (short)a);
                    jPanelJuego.pintarCasilla(b, a);
                    break;
                }
            } while(true);
        }
    }

    public void iniciarJuego() {
        JPanelJuego jPanelJuego = JPanelJuego.getInstance();
        generarMapa();
        add(JPanelInformacion.getInstance(), java.awt.BorderLayout.NORTH);
        jPanel.add(jPanelJuego);
        add(jPanel);
        Sonidos.getInstance().getSonido(Sonidos.STAGE_THEME).loop();
        setVisible(true);
        jPanelJuego.requestFocus();
        JPanelInformacion.getInstance().iniciarCuentaRegresiva();
        jPanelJuego.iniciarHiloPrincipal();
    }

    public String determinarEnemigo(int c) {
        return c > 6 ? Objetos.PONTAN.getValue() : Objetos.getEnemigos()[c].getValue();
    }
    
}