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
public class Pair<P, S> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private P first;
    private S second;

    public static <P, S> Pair<P, S> de(P first, S second) {
        return new Pair<>(first, second);
    }
    
    public Pair(P first, S second) {
        this.first = first;
        this.second = second;
    }

    public P getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(P first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }
    
    @Override
    public String toString() {
            String fstStr;
            if (first == null) {
                    fstStr = "null";
            } else {
                    fstStr = first.toString();
            }
            String sndStr;
            if (second == null) {
                    sndStr = "null";
            } else {
                    sndStr = second.toString();
            }

            return "Pair: first=" + fstStr + "; second=" + sndStr;
    }
    
}
