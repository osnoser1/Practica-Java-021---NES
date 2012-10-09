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
    
    JBarraMenu jbarramenu1;
    JPanelPresentacion jpanelpresentacion1;
    JPanelAvisos jpanelavisos1;
    JPanelGrafico jpanelgrafico1;
    JPanelFinDeJuego jpanelfindejuego1;
    
    public JPanelContenedor() {
        super(new java.awt.BorderLayout());
        initComponents();
    }

    private void initComponents() {
        this.jbarramenu1=new JBarraMenu();
        this.jpanelpresentacion1=new JPanelPresentacion();
        this.jpanelavisos1= new JPanelAvisos();
        this.jpanelfindejuego1=new JPanelFinDeJuego();
        this.jpanelgrafico1=new JPanelGrafico(this);
        agregarComponentesMenuInicial();
        agregarControladores();
    }

    public JBarraMenu getJBarraMenu() {
        return jbarramenu1;
    }

    public JPanelFinDeJuego getJPanelFinDeJuego() {
        return jpanelfindejuego1;
    }

    public JPanelAvisos getJPanelAvisos() {
        return jpanelavisos1;
    }

    public JPanelGrafico getJPanelGrafico() {
        return jpanelgrafico1;
    }

    public JPanelPresentacion getJPanelPresentacion() {
        return jpanelpresentacion1;
    }

    private void agregarControladores() {
        this.jpanelpresentacion1.addKeyListener(new ControladorJPanelPresentacion(this));
    }

    public void agregarComponentesMenuInicial() {
        add(jbarramenu1,BorderLayout.NORTH);
        add(this.jpanelpresentacion1);
        this.jpanelpresentacion1.iniciar();
        this.jpanelpresentacion1.setVisible(true);
        this.jpanelpresentacion1.requestFocus();
        Sonidos.getInstance().getSonido(Sonidos.TITLE_SCREEN).play();
    }
    
}
