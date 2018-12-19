/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import dependencias.Sonidos;
import utilidades.juego.Interfaz;
import utilidades.juego.Interfaz.Escenas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
/**
 *
 * @author Alfonso Andrés
 */
public class JPanelContenedor  extends JComponent  {
    
    private static JPanelContenedor instance;
    private Interfaz actual;
    public Escenas escenaSeleccionada;
    
    private JPanelContenedor() {
        init();
    }

    public static JPanelContenedor getInstance() {
        return instance == null ? (instance = new JPanelContenedor()) : instance;
    }
    
    private void init() {
        actual = JPanelPresentacion.getInstance(this);
//        setIgnoreRepaint(true);
//        setFocusable(false);
        cambiarInterfaz(Escenas.ESCENA_MENU);
        Sonidos.getInstance().play(Sonidos.TITLE_SCREEN);
//        addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                Component c = (Component)e.getSource();
//                escalamientoJPanel(c.getSize());
//                Configuracion.getInstance().tamañoVentana.width = c.getWidth();
//                Configuracion.getInstance().tamañoVentana.height = c.getHeight();
//                JBarraMenu.getInstance().repaint();
//            }
//
//        });
    }

//    @Override
    public void paint(final Graphics g) {
        var g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        actual.pintar(g2d);
    }

//    @Override
//    public void paintAll(Graphics g) {
//    }
//
//    @Override
//    public void update(Graphics g) {
//    }
//
//    @Override
//    public void repaint() {
//    }

    public void cambiarInterfaz(Escenas nueva) {
        escenaSeleccionada = nueva;
        actual.reiniciar();
        switch(nueva) {
            case ESCENA_MENU:
                actual = JPanelPresentacion.getInstance(this);
                break;
            case ESCENA_STAGE:
                actual = JPanelAvisos.getInstance(this);
                ((JPanelAvisos)actual).iniciarJPanelStage();
                break;
            case ESCENA_JUEGO:
                actual = JPanelJuego.getInstance(this);
                Sonidos.getInstance().loop(Sonidos.STAGE_THEME);
                ((JPanelJuego)actual).activarInteligencias();
                JPanelInformacion.getInstance().iniciarCuentaRegresiva();
                break;
            case ESCENA_EDITOR:
                break;
            case ESCENA_GAME_OVER:
                actual = JPanelAvisos.getInstance(this);
                ((JPanelAvisos)actual).iniciarJPanelGameOver();
                break;
            case ESCENA_BONUS:
                break;
        }
    }
    
    public void terminar() {
        
    }
    
    public void setSIZE(Dimension dim) {
        actual.setSIZE(dim);
    }

    public void actualizar(long tiempoEnMilisegundos) {
        actual.actualizar(tiempoEnMilisegundos);
    }
    
}
