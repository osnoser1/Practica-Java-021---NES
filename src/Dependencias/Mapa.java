/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Dependencias;

import motor.core.graphics.Sprite;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author hp
 */
public class Mapa {

    private final class Casilla {

        private final ArrayList<String> al;

        public Casilla() {
            al = new ArrayList<>();
        }

        public boolean add(final String s) {
            boolean b = al.add(s);
            Collections.sort(al);
            return b;
        }

        public boolean remove(final String s) {
            return al.remove(s);
        }

        public boolean contains(final String s) {
            return Collections.binarySearch(al, s) >= 0;
        }

        public boolean isEmpty() {
            return al.isEmpty();
        }

        public int length() {
            return al.size();
        }

    }

    public static final short COLUMNAS = 31, FILAS = 13;
    private static Mapa instance;
    private Casilla mapa[][];

    private Mapa() {
        init();
    }

    private void init() {
        mapa = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++)
            for (int j = 0; j < COLUMNAS; j++) {
                mapa[i][j] = new Casilla();
                if (i % 2 != 1 && j % 2 != 1 || i == 0 || i == 12 || j == 0 || j == 30)
                    mapa[i][j].add("A");
            }
    }

    public static Mapa getInstance() {
        return instance == null ? (instance = new Mapa()) : instance;
    }

    public boolean contiene(final String objeto, final int x, final int y) {
        return mapa[x][y].contains(objeto);
    }

    public boolean contiene(final int x, final int y, final String... ses) {
        for (final String se : ses)
            if (contiene(se, x, y))
                return true;
        return false;
    }

    public boolean estaVacio(int x, int y) {
        return mapa[x][y].isEmpty();
    }

    public void agregar(final String objeto, final java.awt.Point p) {
        agregar(objeto, p.y, p.x);
    }

    private void agregar(final String objeto, final int x, final int y) {
        mapa[x][y].add(objeto);
    }

    public void agregar(final Sprite p) {
        agregar(p.getId(), p.getPosicionMapa());
    }

    private boolean remover(final String objeto, final int x, final int y) {
        return mapa[x][y].remove(objeto);
    }

    public boolean remover(final String objeto, java.awt.Point p) {
        return remover(objeto, p.y, p.x);
    }

    public boolean remover(final Sprite p) {
        return remover(p.getId(), p.getPosicionMapa());
    }

    public void borrarMapa() {
        init();
    }

    public void actualizar(final Sprite p) {
        final Point pAct = p.getPosicionMapa(),
                pAnt = p.getPosAntMapa();
        if (pAct.equals(pAnt)) {
            if (contiene(pAct.y, pAct.x, p.getId()))
                return;
            agregar(p.getId(), pAct);
        }
        remover(p.getId(), pAnt);
        if (!contiene(pAct.y, pAct.x, p.getId()))
            agregar(p.getId(), pAct);
    }

    public void mostrar() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                sb.append(mapa[i][j].length());
            }
            sb.append("\r\n");
        }
        System.out.println(sb);
    }

}
