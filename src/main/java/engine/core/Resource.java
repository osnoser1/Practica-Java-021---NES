/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core;

/**
 * @param <E>
 * @author AlfonsoAndrés
 */
public interface Resource<E> {

    E load(final String s, final int type);

}
