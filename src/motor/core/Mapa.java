/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;


/**
 *
 * @author hp
 */
public abstract class Mapa {

    protected String mapa[][][];
    protected Codigo codigo;

    public Mapa(String[][][] mapa) {
        this.mapa = mapa;
        codigo = Codigo.getInstance();
    }

    public abstract void actualizar();

    public int getLengthFilas(int capa) {
        return mapa[capa].length;
    }

    public int getLengthColumnas(int capa) {
        return mapa[capa][0].length;
    }

}