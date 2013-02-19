/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alfonso Andrés
 */
public class Metodos {
    
    public static void sleep(long miliseconds){
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
