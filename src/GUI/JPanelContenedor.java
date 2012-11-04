/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Hilos.HiloPrincipal;
import Sonidos.Sonidos;
import Utilidades.Juego.Interfaz;
import Utilidades.Juego.Interfaz.Escenas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;
/**
 *
 * @author Alfonso Andrés
 */
public class JPanelContenedor extends JPanel {
    
    private static JPanelContenedor instance;
    private Interfaz actual;
    public Escenas escenaSeleccionada;
    
    private JPanelContenedor() {
        super(new java.awt.BorderLayout());
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
            }
        });
        new HiloPrincipal(this, (short)60).start();
    }

    @Override
    public void paint(Graphics g) {
        actual.pintar(g);
    }

    public void agregarComponentesMenuInicial() {
        JPanelPresentacion.getInstance(this).iniciar();
        Sonidos.getInstance().getSonido(Sonidos.TITLE_SCREEN).play();
    }
    
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
