/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Bomberman.Configuracion.Configuracion;
import Sonidos.Sonidos;
import motor.core.Interfaz;
import motor.core.Interfaz.Escenas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import motor.core.Universo;

/**
 *
 * @author Alfonso Andrés
 */
public class ContenedorGrafico extends javax.swing.JComponent implements Universo {

    private static ContenedorGrafico instance;
    private Interfaz actual;
    public Escenas escenaSeleccionada;

    private ContenedorGrafico() {
        initComponents();
    }

    public static ContenedorGrafico getInstance() {
        return instance == null ? (instance = new ContenedorGrafico()) : instance;
    }

    private void initComponents() {
        actual = EscenaJuego.getInstance(this);
        cambiarInterfaz(Escenas.ESCENA_MENU);
        agregarComponentesMenuInicial();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component) e.getSource();
                escalamientoJPanel(c.getSize());
                Configuracion.getInstance().tamañoVentana.width = c.getWidth();
                Configuracion.getInstance().tamañoVentana.height = c.getHeight();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        actual.pintar(JavaGraphics.set(g));
    }

    public void agregarComponentesMenuInicial() {
        JPanelPresentacion.getInstance(this).iniciar();
        Sonidos.getInstance().get(Sonidos.TITLE_SCREEN).play();
    }

    public void cambiarInterfaz(Escenas nueva) {
        escenaSeleccionada = nueva;
        actual.reiniciar();
        System.runFinalization();
        System.gc();
        switch (nueva) {
            case ESCENA_MENU:
                actual = JPanelPresentacion.getInstance(this);
                break;
            case ESCENA_STAGE:
                actual = JPanelAvisos.getInstance(this);
                ((JPanelAvisos) actual).iniciarJPanelStage();
                break;
            case ESCENA_JUEGO:
                actual = EscenaJuego.getInstance(this);
                Sonidos.getInstance().get(Sonidos.STAGE_THEME).loop();
                ((EscenaJuego) actual).activarInteligencias();
                JPanelInformacion.getInstance().iniciarCuentaRegresiva();
                break;
            case ESCENA_EDITOR:
                break;
            case ESCENA_GAME_OVER:
                actual = JPanelAvisos.getInstance(this);
                ((JPanelAvisos) actual).iniciarJPanelGameOver();
                break;
            case ESCENA_BONUS:
                break;
        }
    }

    public void terminar() {
    }

    public void escalamientoJPanel(Dimension dim) {
        EscenaJuego.getInstance(this).setSize(dim);
        JPanelInformacion.getInstance().setSIZE(dim);
    }

    public void actualizar(long tiempoEnMilisegundos) {
        actual.actualizar(tiempoEnMilisegundos);
    }
}
