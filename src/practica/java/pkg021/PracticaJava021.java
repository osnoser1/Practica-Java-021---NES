/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica.java.pkg021;
import gui.JFramePrincipal;
import Sonidos.Sonidos;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *
 * @author Alfonso Andrés
 */
public class PracticaJava021 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(PracticaJava021.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFramePrincipal();
            }
        });
    }
}
