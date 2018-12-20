/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.utils;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author AlfonsoAndrés
 * @param <E>
 */
public class ObjectPool<E> {

    private final ArrayList<E> pool;
    private final Stack<E> free;
    private final ObjectFactory<E> of;
    private final int size;
    private int count;

    public ObjectPool(ObjectFactory<E> of, int size) {
        this.of = of;
        this.size = size;
        pool = new ArrayList<>(size);
        free = new Stack<>();
    }

    public E create() {
        final var e = !free.empty() ? free.pop() : count < size ? of.create() : null;
        if (e == null)
            throw new IndexOutOfBoundsException("Object pool is filled");
        pool.add(e);
        return e;
    }

    public void free(final E object) {
        if (!pool.contains(object))
            return;
        pool.remove(object);
        free.add(object);
    }

}
