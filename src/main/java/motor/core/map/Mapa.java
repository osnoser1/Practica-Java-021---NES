/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core.map;

import personajes.Aluminio;
import motor.core.graphics.Sprite;
import java.util.HashMap;

/**
 *
 * @author hp
 */
public class Mapa {

    public static final short COLUMNAS = 31, FILAS = 13;
    private static Mapa instance;
    private final Casilla[][] mapa;
    private final HashMap<Sprite, PosicionSprite> mapper;

    private Mapa() {
        mapa = new Casilla[FILAS][COLUMNAS];
        mapper = new HashMap<>();
        init();
    }

    private void init() {
        for (var i = 0; i < FILAS; i++) {
            for (var j = 0; j < COLUMNAS; j++) {
                mapa[i][j] = new Casilla();
                if (i % 2 != 1 && j % 2 != 1 || i == 0 || i == 12 || j == 0 || j == 30) {
                    mapa[i][j].add(new Aluminio(0, 0));
                }
            }
        }
    }

    public static Mapa getInstance() {
        return instance == null ? (instance = new Mapa()) : instance;
    }
    
    public Posicion getPosicion(Sprite s) {
        return mapper.containsKey(s) ? mapper.get(s).getPosicionActual() : null;
    }
    
    public boolean contiene(final int fila, final int columna, Sprite... s) {
        return mapa[fila][columna].containsAny(s);
    }
    
    public boolean contiene(final int fila, final int columna, final Class<?>... classes) {
        return mapa[fila][columna].containsAny(classes);
    }
    
    public boolean contiene(Sprite s, final Class<?>... classes) {
        var p = mapper.get(s).getPosicionActual();
        return mapa[p.fila][p.columna].containsAny(classes);
    }

    public boolean estaVacio(int fila, int columna) {
        return mapa[fila][columna].isEmpty();
    }

    public void agregar(final Sprite sprite) {
        var ps = new PosicionSprite(sprite);
        var p = ps.getPosicionActual();
        mapa[p.fila][p.columna].add(sprite);
        mapper.put(sprite, ps);
    }

    public Sprite[] getSprite(final int fila, final int columna, final Class<?>... c) {
        return mapa[fila][columna].get(c);
    }
    
    public boolean remover(final Sprite s) {
        if(mapper.get(s) == null) {
            return false;
        }
        var p = mapper.get(s).getPosicionActual();
        return mapa[p.fila][p.columna].remove(s) && mapper.remove(s) != null;
    }

    public void borrarMapa() {
        init();
    }

    public void actualizar(final Sprite s) {
        if(mapper.get(s) == null || !mapper.get(s).actualizar()) {
            return;
        }
        final Posicion pAct = mapper.get(s).getPosicionActual(),
                pAnt = mapper.get(s).getPosicionAnterior();
        mapa[pAnt.fila][pAnt.columna].remove(s);
        mapa[pAct.fila][pAct.columna].add(s);
    }
    
    public void mostrar() {
        var sb = new StringBuilder();
        for (var i = 0; i < FILAS; i++) {
            for (var j = 0; j < COLUMNAS; j++) {
                sb.append(mapa[i][j].length());
            }
            sb.append("\r\n");
        }
        System.out.println(sb);
    }

}
