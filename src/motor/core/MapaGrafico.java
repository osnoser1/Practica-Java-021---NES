/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import motor.utils.Graphics;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;


/**
 *
 * @author hp
 */
public class MapaGrafico extends Mapa {

    private static MapaGrafico instance;
    private final Terrenos terreno;
    private final Dimension tamañoNivel;

    public enum Capa {
        INFERIOR,
        SUPERIOR;
    }

    private MapaGrafico(String[][][] mapa, Terrenos terreno) {
        super(mapa);
        this.terreno = terreno;
        tamañoNivel = new Dimension(getX(getLengthColumnas(Capa.INFERIOR.ordinal())), getY(getLengthFilas(Capa.INFERIOR.ordinal())));
    }

    public static MapaGrafico getInstance(String[][][] mapa, Terrenos terreno) {
        return instance == null ? (instance = new MapaGrafico(mapa, terreno)) : instance;
    }

    @Override
    public void actualizar() {
    }

    public void pintar(Graphics g, Capa capa) {
        pintarImagen(g, capa);
    }

    private void pintarImagen(Graphics g, Capa capa) {
//        g.setColor(Color.BLACK);
        for (int i = 0, filas = getLengthFilas(capa.ordinal()); i < filas; i++) {
//            g.drawLine(0, i * terreno.getAltoEscalado(), getLengthColumnas(capa.ordinal()) * terreno.getAnchoEscalado(), i * terreno.getAltoEscalado());
            for (int j = 0, columnas = getLengthColumnas(capa.ordinal()); j < columnas; j++) {
//                g.drawLine(j * terreno.getAnchoEscalado(), 0, j * terreno.getAnchoEscalado(), filas * terreno.getAltoEscalado());
                if (!"FFFF".equals(mapa[capa.ordinal()][i][j])) {
                    terreno.pintar(g, terreno.getAnchoEscalado() * j, terreno.getAltoEscalado() * i, getTerreno(i, j, capa), getPosicion(i, j, capa));
                    //prueba(g,i,j,capa);
                }
            }
        }
    }

//    private void prueba(Graphics g, int i, int j, Capa capa) {
//        g.setColor(Color.red);
//        g.drawRect(terreno.getAnchoEscalado() * j, terreno.getAltoEscalado() * i, terreno.getAnchoEscalado(), terreno.getAltoEscalado());
//        g.drawString(i + "  " + j, terreno.getAnchoEscalado() * j, terreno.getAltoEscalado() * i + 15);
//        g.setColor(Color.yellow);
//        g.drawString(mapa[capa.ordinal()][i][j], terreno.getAnchoEscalado() * j + 4, terreno.getAltoEscalado() * i + 40);
//    }

    public int getFila(int y) {
        return y / terreno.getAltoEscalado();
    }

    public int getColumna(int x) {
        return x / terreno.getAnchoEscalado();
    }

    public final int getY(int fila) {
        return fila * terreno.getAltoEscalado();
    }

    public final int getX(int columna) {
        return columna * terreno.getAnchoEscalado();
    }

    Rectangle getCasilla(Point location) {
        return new Rectangle(getX(getColumna(location.x)), getY(getFila(location.y)), getWidthCuadroImagen(), getHeightCuadroImagen());
    }

    public int getTerreno(int fila, int columna, Capa capa) {
        if ("FFFF".equals(mapa[capa.ordinal()][fila][columna])) {
            return -1;
        }
        return codigo.decodificarTerreno(mapa[capa.ordinal()][fila][columna]);
    }

    public int getPosicion(int fila, int columna, Capa capa) {
        if ("FFFF".equals(mapa[capa.ordinal()][fila][columna])) {
            return -1;
        }
        return codigo.decodificarPosicion(mapa[capa.ordinal()][fila][columna]);
    }

    public Dimension getTamañoNivel() {
        return tamañoNivel;
    }

    public int getWidthCuadroImagen() {
        return tamañoNivel.width / getLengthColumnas(Capa.INFERIOR.ordinal());
    }

    public int getHeightCuadroImagen() {
        return tamañoNivel.height / getLengthFilas(Capa.INFERIOR.ordinal());
    }
}
