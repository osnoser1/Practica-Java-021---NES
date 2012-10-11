/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Controladores.ControladorJFramePrincipal;
import Dependencias.Imagenes;
import Dependencias.ManejadorDeArchivos;

/**
 *
 * @author Alfonso Andrés
 */
public class JFramePrincipal extends javax.swing.JFrame{

    public JFramePrincipal(){
        super("Bomberman - NES");
        initComponents();
    }

    private void initComponents() {
        Imagenes.cargarImagenes();
        this.add(JPanelContenedor.getInstance());
        this.setSize(656,620);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        ControladorJFramePrincipal controladorJFramePrincipal = new ControladorJFramePrincipal();
        this.addWindowFocusListener(controladorJFramePrincipal);
        this.addWindowListener(controladorJFramePrincipal);
        this.addComponentListener(controladorJFramePrincipal);
        this.setIconImage(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/bomber_32x32.png"));
    }
    
}
