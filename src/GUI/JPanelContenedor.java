/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Bomberman.Core.Configuracion;
import Sonidos.Sonidos;
import Utilidades.Juego.Interfaz;
import Utilidades.Juego.Interfaz.Escenas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
/**
 *
 * @author Alfonso Andrés
 */
public class JPanelContenedor extends javax.swing.JComponent {
    
    private static JPanelContenedor instance;
    private Interfaz actual;
    public Escenas escenaSeleccionada;
    
    private JPanelContenedor() {
        initComponents();
    }

    public static JPanelContenedor getInstance() {
        return instance == null ? (instance = new JPanelContenedor()) : instance;
    }
    
    private void initComponents() {
        actual = JPanelJuego.getInstance(this);
        cambiarInterfaz(Escenas.ESCENA_MENU);
        agregarComponentesMenuInicial();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                escalamientoJPanel(c.getSize());
                Configuracion.getInstance().tamañoVentana.width = c.getWidth();
                Configuracion.getInstance().tamañoVentana.height = c.getHeight();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        actual.pintar(g);
    }

    public void agregarComponentesMenuInicial() {
        JPanelPresentacion.getInstance(this).iniciar();
        Sonidos.getInstance().getSonido(Sonidos.TITLE_SCREEN).play();
    }
    
    public void cambiarInterfaz(Escenas nueva) {
        escenaSeleccionada = nueva;
        actual.reiniciar();
        System.runFinalization();
        System.gc();
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
                Sonidos.getInstance().getSonido(Sonidos.STAGE_THEME).loop();
                JPanelInformacion.getInstance().iniciarCuentaRegresiva();
                break;
            case ESCENA_EDITOR:
                break;
            case ESCENA_GAME_OVER:
                actual = JPanelAvisos.getInstance(this);
                ((JPanelAvisos)actual).iniciarJPanelGameOver();
                break;
        }
    }
    
    public void terminar() {
        
    }
    
    public void escalamientoJPanel(Dimension dim) {
        JPanelJuego.getInstance(this).setSIZE(dim);
        JPanelInformacion.getInstance().setSIZE(dim);
    }

    public void actualizar(long tiempoEnMilisegundos) {
        actual.actualizar(tiempoEnMilisegundos);
    }
    
}
