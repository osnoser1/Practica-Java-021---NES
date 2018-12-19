/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.map;

import java.util.ArrayList;
import java.util.stream.Stream;
import motor.core.graphics.Sprite;

/**
 *
 * @author AlfonsoAndres
 */
class Casilla {

    private final ArrayList<Sprite> al;

    public Casilla() {
        al = new ArrayList<>();
    }

    public boolean add(final Sprite s) {
        return al.add(s);
    }
    
    Sprite[] get(final Class<?>... classes) {
        return al.stream().filter((t) -> Stream.of(classes).anyMatch(c -> c.isInstance(t))).toArray(Sprite[]::new);
    }
    
    public boolean remove(final Sprite s) {
        return al.remove(s);
    }

    public boolean containsAny(final Class<?>... classes) {
        for (var sprite : al) {
            for (var c : classes) {
                if(c.isInstance(sprite)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    boolean containsAny(final Sprite... sprites) {
        return Stream.of(sprites).anyMatch(al::contains);
    }
    
    public boolean isEmpty() {
        return al.isEmpty();
    }

    public int length() {
        return al.size();
    }

}
