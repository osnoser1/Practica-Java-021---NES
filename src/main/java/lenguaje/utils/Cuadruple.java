/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lenguaje.utils;

/**
 *
 * @author Alfonso
 */
public class Cuadruple<P, S, T, C> extends Triple<P, S, T> {

    private static final long serialVersionUID = 1L;
    
    private C cuarto;
    
    public static <P, S, T, C> Cuadruple<P, S, T, C> de(P primero, S segundo, T tercero, C cuarto) {
        return new Cuadruple<>(primero, segundo, tercero, cuarto);
    }
    
    public Cuadruple(P primero, S segundo, T tercero, C cuarto) {
        super(primero, segundo, tercero);
        this.cuarto = cuarto;
    }

    public C getCuarto() {
        return cuarto;
    }

    public void setCuarto(C cuarto) {
        this.cuarto = cuarto;
    }
    
}
