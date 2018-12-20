/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.constants;

import characters.Balloom;
import characters.Bomberman;
import characters.Doll;
import characters.Kondoria;
import characters.Minvo;
import characters.Oneal;
import characters.Ovapi;
import characters.Pass;
import characters.Pontan;
import java.util.stream.Stream;
import engine.core.graphics.Sprite;

/**
 *
 * @author AlfonsoAndrés
 */
public enum Objects {

    BRICK("L"),
    FLOOR("V"),
    BOMBERMAN("B"),
    BALLOM("b"),
    ONEAL("O"),
    DOLL("D"),
    MINVO("M"),
    KONDORIA("K"),
    OVAPI("o"),
    PASS("P"),
    PONTAN("p"),
    FIRE;
    private static final String[] enemies;
    private String value;

    static {
        enemies = Stream.of(getEnemies()).map(Objects::getValue).toArray(String[]::new);
    }
    
    Objects(String value) {
        this.value = value;
    }

    Objects() {
    }

    public String getValue() {
        return value;
    }

    public static Objects[] getEnemies() {
        return new Objects[]{BALLOM, DOLL, KONDORIA, MINVO, ONEAL, OVAPI, PASS, PONTAN};
    }

    public static String[] getEnemiesValues() {
        return enemies;
    }
    
    public static Sprite getInstance(String id) {
        if (id.equals(BOMBERMAN.value))
            return new Bomberman(0, 0);
        if (id.equals(BALLOM.value))
            return new Balloom(0, 0);
        if (id.equals(DOLL.value))
            return new Doll(0, 0);
        if (id.equals(KONDORIA.value))
            return new Kondoria(0, 0);
        if (id.equals(MINVO.value))
            return new Minvo(0, 0);
        if (id.equals(ONEAL.value))
            return new Oneal(0, 0);
        if (id.equals(OVAPI.value))
            return new Ovapi(0, 0);
        if (id.equals(PASS.value))
            return new Pass(0, 0);
        if (id.equals(PONTAN.value))
            return new Pontan(0, 0);
        return null;
    }

}
