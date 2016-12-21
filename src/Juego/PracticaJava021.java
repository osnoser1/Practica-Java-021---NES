/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;
import motor.core.java.gui.JFramePrincipal;
import java.awt.Toolkit;
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
//        final String os = System.getProperty("os.name");
//        if (os.contains("Windows"))
//            System.setProperty("sun.java2d.d3d", "True");
//        else
//        System.setProperty("sun.java2d.trace", "timestamp,log,count");
        System.setProperty("sun.java2d.trace", "count");
//        System.setProperty("sun.java2d.opengl", "True");
//        System.setProperty("sun.java2d.noddraw", "true");
//        System.setProperty("sun.java2d.translaccel", "true");
//        try {
//            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            Logger.getLogger(PracticaJava021.class.getName()).log(Level.SEVERE, null, ex);
//        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JFramePrincipal();
            }
        });
    }
}
