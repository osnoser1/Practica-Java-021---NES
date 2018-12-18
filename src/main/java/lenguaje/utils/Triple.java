/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje.utils;

/**
 *
 * @author Alfonso
 */
public class Triple<P, S, T> extends Pareja<P, S> {
    
    private static final long serialVersionUID = 1L;
    
    private T tercero;

    public static <P, S, T> Triple<P, S, T> de(P primero, S segundo, T tercero) {
        return new Triple<>(primero, segundo, tercero);
    }
    
    public Triple(P primero, S segundo, T tercero) {
        super(primero, segundo);
        this.tercero = tercero;
    }

    public T getTercero() {
        return tercero;
    }

    public void setTercero(T tercero) {
        this.tercero = tercero;
    }
    
    @Override
    public String toString() {
        String fstStr;
        if (getPrimero() == null) {
                fstStr = "null";
        } else {
                fstStr = getPrimero().toString();
        }

        String sndStr;
        if (getSegundo() == null) {
                sndStr = "null";
        } else {
                sndStr = getSegundo().toString();
        }

        String thirdStr;
        if (tercero == null) {
                thirdStr = "null";
        } else {
                thirdStr = tercero.toString();
        }

        return "Triple: primero=" + fstStr + "; segundo=" + sndStr + "; tercero="
                        + thirdStr;
    }
    
}
