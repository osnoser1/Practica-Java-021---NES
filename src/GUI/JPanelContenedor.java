/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Controladores.ControladorJPanelPresentacion;
import Sonidos.Sonidos;
import Utilidades.Juego.Interfaz;
import javax.swing.JPanel;
/**
 *
 * @author Alfonso Andrés
 */
public class JPanelContenedor extends JPanel implements Interfaz{
    
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

    @Override
    public void reiniciar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void pintar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
