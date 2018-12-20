/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language.utils;

/**
 *
 * @author Alfonso
 */
public class Triple<P, S, T> extends Pair<P, S> {
    
    private static final long serialVersionUID = 1L;
    
    private T third;

    public static <P, S, T> Triple<P, S, T> de(P first, S second, T third) {
        return new Triple<>(first, second, third);
    }
    
    public Triple(P first, S second, T third) {
        super(first, second);
        this.third = third;
    }

    public T getThird() {
        return third;
    }

    public void setThird(T third) {
        this.third = third;
    }
    
    @Override
    public String toString() {
        String fstStr;
        if (getFirst() == null) {
                fstStr = "null";
        } else {
                fstStr = getFirst().toString();
        }

        String sndStr;
        if (getSecond() == null) {
                sndStr = "null";
        } else {
                sndStr = getSecond().toString();
        }

        String thirdStr;
        if (third == null) {
                thirdStr = "null";
        } else {
                thirdStr = third.toString();
        }

        return "Triple: first=" + fstStr + "; second=" + sndStr + "; third="
                        + thirdStr;
    }
    
}
