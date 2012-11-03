/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Controladores.ControladorJPanelPresentacion;
import Sonidos.Sonidos;
import Utilidades.Juego.Interfaz;
import Utilidades.Juego.Interfaz.Escenas;
import javax.swing.JPanel;
/**
 *
 * @author Alfonso Andrés
 */
public class JPanelContenedor extends JPanel {
    
    private static JPanelContenedor instance;
    private Interfaz actual;
    
    private JPanelContenedor() {
        super(new java.awt.BorderLayout());
        initComponents();
    }

    public static JPanelContenedor getInstance() {
        return instance == null ? (instance = new JPanelContenedor()) : instance;
    }
    
    private void initComponents() {
        actual = JPanelJuego.getInstance();
        cambiarInterfaz(Escenas.ESCENA_MENU);
        agregarComponentesMenuInicial();
        agregarControladores();
    }

    private void agregarControladores() {
        JPanelPresentacion.getInstance().addKeyListener(new ControladorJPanelPresentacion());
    }

    public void agregarComponentesMenuInicial() {
        add(JPanelPresentacion.getInstance());
        JPanelPresentacion.getInstance().iniciar();
        JPanelPresentacion.getInstance().setVisible(true);
        JPanelPresentacion.getInstance().requestFocus();
        Sonidos.getInstance().getSonido(Sonidos.TITLE_SCREEN).play();
    }
    
    public void cambiarInterfaz(Escenas nueva) {
        switch(nueva) {
            case ESCENA_MENU:
                actual = JPanelPresentacion.getInstance();
                break;
            case ESCENA_JUEGO:
                actual = JPanelJuego.getInstance();
                break;
            case ESCENA_EDITOR:
                break;
        }
        actual.reiniciar();
    }
    
    public void terminar() {
        
    }

    public Interfaz getActual() {
        return actual;
    }
    
}
