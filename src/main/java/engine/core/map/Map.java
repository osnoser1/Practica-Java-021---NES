/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.core.map;

import characters.SteelBlock;
import engine.core.graphics.Sprite;

import java.util.HashMap;

/**
 * @author hp
 */
public class Map {

    public static final short COLUMNS = 31, ROWS = 13;
    private static Map instance;
    private final Tile[][] map;
    private final HashMap<Sprite, SpritePosition> mapper;

    private Map() {
        map = new Tile[ROWS][COLUMNS];
        mapper = new HashMap<>();
        init();
    }

    public static Map getInstance() {
        return instance == null ? (instance = new Map()) : instance;
    }

    private void init() {
        for (var i = 0; i < ROWS; i++) {
            for (var j = 0; j < COLUMNS; j++) {
                map[i][j] = new Tile();
                if (i % 2 != 1 && j % 2 != 1 || i == 0 || i == 12 || j == 0 || j == 30) {
                    map[i][j].add(new SteelBlock(0, 0));
                }
            }
        }
    }

    public Position getPosition(Sprite s) {
        return mapper.containsKey(s) ? mapper.get(s).getCurrentPosition() : null;
    }

    public boolean contains(final int row, final int column, Sprite... s) {
        return map[row][column].containsAny(s);
    }

    public boolean contains(final int row, final int column, final Class<?>... classes) {
        return map[row][column].containsAny(classes);
    }

    public boolean contains(Sprite s, final Class<?>... classes) {
        var p = mapper.get(s).getCurrentPosition();
        return map[p.row][p.column].containsAny(classes);
    }

    public boolean isEmpty(int row, int column) {
        return map[row][column].isEmpty();
    }

    public void add(final Sprite sprite) {
        var ps = new SpritePosition(sprite);
        var p = ps.getCurrentPosition();
        map[p.row][p.column].add(sprite);
        mapper.put(sprite, ps);
    }

    public Sprite[] getSprite(final int row, final int column, final Class<?>... c) {
        return map[row][column].get(c);
    }

    public boolean delete(final Sprite s) {
        if (mapper.get(s) == null) {
            return false;
        }
        var p = mapper.get(s).getCurrentPosition();
        return map[p.row][p.column].remove(s) && mapper.remove(s) != null;
    }

    public void reset() {
        init();
    }

    public void update(final Sprite s) {
        if (mapper.get(s) == null || !mapper.get(s).update()) {
            return;
        }
        final Position pAct = mapper.get(s).getCurrentPosition(),
                pAnt = mapper.get(s).getPreviousPosition();
        map[pAnt.row][pAnt.column].remove(s);
        map[pAct.row][pAct.column].add(s);
    }

    public void show() {
        var sb = new StringBuilder();
        for (var i = 0; i < ROWS; i++) {
            for (var j = 0; j < COLUMNS; j++) {
                sb.append(map[i][j].length());
            }
            sb.append("\r\n");
        }
        System.out.println(sb);
    }

}
