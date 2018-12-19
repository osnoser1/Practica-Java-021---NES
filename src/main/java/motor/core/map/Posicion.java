/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.map;

/**
 *
 * @author AlfonsoAndres
 */
public class Posicion implements Cloneable {

    public final int fila, columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    @Override
    protected Object clone() {
        return new Posicion(fila, columna); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        var hash = 3;
        hash = 67 * hash + this.fila;
        hash = 67 * hash + this.columna;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final var other = (Posicion) obj;
        return this.fila == other.fila && this.columna == other.columna;
    }

}
