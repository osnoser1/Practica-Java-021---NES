/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Bomberman.Core.Configuracion;
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
        add(JPanelContenedor.getInstance());
        setSize(Configuracion.getInstance().tamañoVentana);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        ControladorJFramePrincipal controladorJFramePrincipal = new ControladorJFramePrincipal();
        addWindowFocusListener(controladorJFramePrincipal);
        addWindowListener(controladorJFramePrincipal);
        addComponentListener(controladorJFramePrincipal);
        setIconImage(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/bomber_32x32.png"));
    }
    
}
