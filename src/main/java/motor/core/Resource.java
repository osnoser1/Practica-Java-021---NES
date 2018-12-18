/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

/**
 *
 * @author AlfonsoAndrés
 * @param <E>
 */
public interface Resource<E> {

    public abstract E load(final String s, final int type);

}
