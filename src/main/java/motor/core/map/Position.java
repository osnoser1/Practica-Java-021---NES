/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.map;

/**
 * @author AlfonsoAndres
 */
public class Position implements Cloneable {

    public final int row, column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    @Override
    protected Object clone() {
        return new Position(row, column); // To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        var hash = 3;
        hash = 67 * hash + this.row;
        hash = 67 * hash + this.column;
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
        final var other = (Position) obj;
        return this.row == other.row && this.column == other.column;
    }

}
