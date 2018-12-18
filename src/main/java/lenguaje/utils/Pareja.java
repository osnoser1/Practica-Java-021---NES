/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje.utils;

import java.io.Serializable;

/**
 *
 * @author Alfonso
 */
public class Pareja<P, S> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private P primero;
    private S segundo;

    public static <P, S> Pareja<P, S> de(P primero, S segundo) {
        return new Pareja<>(primero, segundo);
    }
    
    public Pareja(P primero, S segundo) {
        this.primero = primero;
        this.segundo = segundo;
    }

    public P getPrimero() {
        return primero;
    }

    public S getSegundo() {
        return segundo;
    }

    public void setPrimero(P primero) {
        this.primero = primero;
    }

    public void setSegundo(S segundo) {
        this.segundo = segundo;
    }
    
    @Override
    public String toString() {
            String fstStr;
            if (primero == null) {
                    fstStr = "null";
            } else {
                    fstStr = primero.toString();
            }
            String sndStr;
            if (segundo == null) {
                    sndStr = "null";
            } else {
                    sndStr = segundo.toString();
            }

            return "Pareja: primerot=" + fstStr + "; segundo=" + sndStr;
    }
    
}
