/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;
import Controladores.ControladorJPanelPresentacion;
import Sonidos.Sonidos;
import java.awt.BorderLayout;
import javax.swing.JPanel;
/**
 *
 * @author Alfonso Andrés
 */
public class JPanelContenedor extends JPanel{
    
    private static JPanelContenedor instance;
    private JBarraMenu jbarramenu1;
    
    private JPanelContenedor() {
        super(new java.awt.BorderLayout());
        initComponents();
    }

    public static JPanelContenedor getInstance() {
        return instance == null ? (instance = new JPanelContenedor()) : instance;
    }
    
    private void initComponents() {
        this.jbarramenu1=new JBarraMenu();
        agregarComponentesMenuInicial();
        agregarControladores();
    }

    public JBarraMenu getJBarraMenu() {
        return jbarramenu1;
    }

    private void agregarControladores() {
        JPanelPresentacion.getInstance().addKeyListener(new ControladorJPanelPresentacion());
    }

    public void agregarComponentesMenuInicial() {
        add(jbarramenu1,BorderLayout.NORTH);
        add(JPanelPresentacion.getInstance());
        JPanelPresentacion.getInstance().iniciar();
        JPanelPresentacion.getInstance().setVisible(true);
        JPanelPresentacion.getInstance().requestFocus();
        Sonidos.getInstance().getSonido(Sonidos.TITLE_SCREEN).play();
    }
    
}
