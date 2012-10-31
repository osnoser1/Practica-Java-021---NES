/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Bomberman.Core.Configuracion;
import Controladores.CJFramePrincipal;
import Dependencias.Imagenes;
import Dependencias.ManejadorDeArchivos;
import Utilidades.Graficos.AdministradorDePantalla;
import java.awt.DisplayMode;

/**
 *
 * @author Alfonso Andrés
 */
public class JFramePrincipal extends javax.swing.JFrame{

    private static final DisplayMode MODOS_POSIBLES[] = {
        new DisplayMode( 1900, 1080, 32, 0 ),
        new DisplayMode( 1366, 768, 32, 0 ),
        new DisplayMode( 1280, 768, 32, 0 ),
        new DisplayMode( 1280, 1024, 32, 0 ),
        new DisplayMode( 1024, 768, 32, 0 ),
        new DisplayMode( 800, 600, 32, 0 ),
    };
    
    public JFramePrincipal(){
        super("Bomberman - NES");
        initComponents();
    }

    private void initComponents() {
        requestFocusInWindow();
        setFocusable(true);
        setAutoRequestFocus(true);
        Imagenes.cargarImagenes();
        add(JPanelContenedor.getInstance());
        setSize(Configuracion.getInstance().tamañoVentana);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(CJFramePrincipal.getInstance());
        addWindowListener(CJFramePrincipal.getInstance());
        addComponentListener(CJFramePrincipal.getInstance());
        addKeyListener(CJFramePrincipal.Teclado.getInstance());
        setIconImage(ManejadorDeArchivos.getInstance().loadBufferedImageJAR("/bomber_32x32.png"));
//        AdministradorDePantalla administrador = new AdministradorDePantalla();
//        administrador.encontrarPrimerModoCompatible(MODOS_POSIBLES);
//        administrador.establecerPantallaCompleta(this);
//        JPanelPresentacion.getInstance().repaint();
    }
    
}
