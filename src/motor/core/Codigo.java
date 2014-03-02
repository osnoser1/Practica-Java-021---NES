/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.awt.Point;

/**
 *
 * @author hp
 */
public class Codigo {

    private static Codigo instance;

    public static Codigo getInstance() {
        return instance == null ? (instance = new Codigo()) : instance;
    }

    public int decodificarTerreno(String codigo){
        return Integer.parseInt(codigo.substring(0, 2));
    }
    public int decodificarPosicion(String codigo){
        return Integer.parseInt(codigo.substring(2, 4));
    }

    public Point decodificar(String codigo) {
        return new Point(Integer.parseInt(codigo.substring(0, 2)), Integer.parseInt(codigo.substring(2, 4)));
    }

    public String codificar(int fila,int columna){
        String codigo="";
        if(fila<10)
            codigo="0"+fila;
        else if(fila<100)
            codigo=""+fila;

        if(columna<10)
            codigo+="0"+columna;
        else if(columna<100)
            codigo+=""+columna;

        return codigo;
    }

}
