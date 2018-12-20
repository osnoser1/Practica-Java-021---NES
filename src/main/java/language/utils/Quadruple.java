/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.utils;

/**
 *
 * @author Alfonso
 */
public class Quadruple<F, S, T, Q> extends Triple<F, S, T> {

    private static final long serialVersionUID = 1L;
    
    private Q quarter;
    
    public static <P, S, T, C> Quadruple<P, S, T, C> de(P first, S second, T third, C quarter) {
        return new Quadruple<>(first, second, third, quarter);
    }
    
    public Quadruple(F first, S second, T third, Q quarter) {
        super(first, second, third);
        this.quarter = quarter;
    }

    public Q getQuarter() {
        return quarter;
    }

    public void setQuarter(Q quarter) {
        this.quarter = quarter;
    }
    
}
