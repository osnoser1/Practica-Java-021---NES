/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package Dependencias;

import java.awt.Point;

/**
 *
 * @author hp
 */
public class Mapa {
    
    public static final short COLUMNAS = 31, FILAS = 13;
    private static Mapa instance;
    private String mapa[][];

    private Mapa() {
        mapa = new String[FILAS][COLUMNAS];
        for(int i = 0; i < FILAS; i++) {
            for(int j = 0; j < COLUMNAS; j++) {
                if(i % 2 != 1 && j % 2 != 1 || i == 0 || i == 12 || j == 0 || j == 30)
                    mapa[i][j] = "A";
                else
                    mapa[i][j] = "V";
            }
        }
    }

    public static Mapa getInstance() {
        return instance == null ? (instance = new Mapa()) : instance;
    }

    public void setMapa(String[][] mapa) {
        this.mapa = mapa;
    }

    public String[][] getMapa() {
        return mapa;
    }

    public void setObjeto(String objeto, short x, short y) {
        mapa[x][y] = objeto;
    }

    public void setObjeto(String objeto, Point posicion) {
        mapa[posicion.y][posicion.x] = objeto;
    }
    
    public String getObjeto(int x, int y) {
        return mapa[x][y];
    }
    
    public String getObjeto(Point posicion) {
        return mapa[posicion.y][posicion.x];
    }

    public void borrarMapa() {
        for(int i = 0; i < FILAS; i++) {
            for(int j = 0; j < COLUMNAS; j++) {
                if(i % 2 != 1 && j % 2 != 1 || i == 0 || i == 12 || j == 0 || j == 30)
                    mapa[i][j] = "A";
                else
                    mapa[i][j] = "V";
            }
        }
    }

    public void mostrarMapa() {
        for(int i = 0; i < FILAS; i++) {
            for(int j = 0; j < COLUMNAS; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public String getObjetoMapa(int i, int j) {
        return mapa[i][j];
    }

}