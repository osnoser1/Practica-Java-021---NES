/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades.Graficos;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;

/**
 *
 * @author AlfonsoAndrés
 */
public class AdministradorDePantalla {

    private GraphicsDevice dispositivo;
    
    public AdministradorDePantalla() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        dispositivo = environment.getDefaultScreenDevice();
    }
    
    public DisplayMode encontrarPrimerModoCompatible(DisplayMode[] modos) {
        DisplayMode[] modosBuenos = dispositivo.getDisplayModes();
        for(DisplayMode modo : modos) {
            for(DisplayMode modoBueno : modosBuenos) {
                if(coincideModoVisualizacion(modo, modoBueno))
                    return modo;
            }
        }
        return null;
    }

    private boolean coincideModoVisualizacion(DisplayMode modo1, DisplayMode modo2) {
         if ( modo1.getWidth() != modo2.getWidth() ||
                modo1.getHeight() != modo2.getHeight() ) {
            return false;
        }
        if ( modo1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                modo2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                modo1.getBitDepth() != modo2.getBitDepth() ) {
            return false;
        }
        if ( modo1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                modo2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                modo1.getRefreshRate() != modo2.getRefreshRate() ) {
            return false;
        }
        return true;
    }
    
    public void establecerPantallaCompleta(JFrame frame) {
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setUndecorated( true );
        frame.setIgnoreRepaint( true );
        frame.setResizable( false );
        dispositivo.setFullScreenWindow(frame);
    }
    
}
