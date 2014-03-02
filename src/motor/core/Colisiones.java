/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.awt.Point;
import java.awt.Rectangle;
import lenguaje.utils.Pareja;
import motor.core.MapaGrafico.Capa;

/**
 *
 * @author Alfonso Andrés
 */
public class Colisiones {

    public enum Colision {
        ARRIBA,
        ABAJO,
        DERECHA,
        IZQUIERDA,
        NINGUNO;
    }

    private static MapaGrafico mapaGrafico;

    public static void setMapaGrafico(MapaGrafico mapaGrafico) {
        Colisiones.mapaGrafico = mapaGrafico;
    }

    public static boolean colision(Pareja<Colision, Boolean> colision, Personaje personaje) {
        switch (colision.getPrimero()) {
            case ABAJO:
                return colision.getSegundo() ? colisionAbajo(personaje) : !colisionAbajo(personaje);
            case ARRIBA:
                return colision.getSegundo() ? colisionArriba(personaje) : !colisionArriba(personaje);
            case DERECHA:
                return colision.getSegundo() ? colisionDerecha(personaje) : !colisionDerecha(personaje);
            case IZQUIERDA:
                return colision.getSegundo() ? colisionIzquierda(personaje) : !colisionIzquierda(personaje);
            default:
                return true;
        }
    }


    public static void colisionMapa(Personaje p) {
        final MovimientosPersonaje mp = p.getMp();
        mp.colisionArriba = colisionArriba(p);
        mp.colisionAbajo = colisionAbajo(p);
        mp.colisionDerecha = colisionDerecha(p);
        mp.colisionIzquierda = colisionIzquierda(p);
    }

    public static boolean colisionArriba(Personaje p) {
        return colisionEjeVertical(p);
    }

    public static boolean colisionAbajo(Personaje p) {
        return colisionEjeVertical(p);
    }

    public static boolean colisionDerecha(Personaje p) {
        return colisionEjeHorizontal(p);
    }

    public static boolean colisionIzquierda(Personaje p) {
        return colisionEjeHorizontal(p);
    }

    private static boolean colisionEjeVertical(Personaje p) {
        final Rectangle rect_jugador = (Rectangle) p.getRectagulo().clone();
        rect_jugador.translate(0, p.getVelocidadY());
        final Point ptoRef = (Point) rect_jugador.getLocation().clone();
        ptoRef.translate((int) (rect_jugador.width * 0.5f), 0);
        if(p.getVelocidadY() > 0) {
            ptoRef.translate(0, rect_jugador.height);
        }
        final Rectangle rect_mapa = mapaGrafico.getCasilla(ptoRef);
        final Rectangle rc = null;
        final int terreno = mapaGrafico.getTerreno(mapaGrafico.getFila(rect_mapa.y), mapaGrafico.getColumna(rect_mapa.x), Capa.INFERIOR);
        if(terreno == -1) {
            return true;
        } else {
            return false;
        }
//        if(terreno != -1 && (rc = rect_jugador.intersection(rect_mapa)) != null) {
//            rect_jugador.translate(0, rc.height * p.getVelocidad() > 0 ? 1 : -1);
//            return true;
//        }
//        return false;
    }

    private static boolean colisionEjeHorizontal(Personaje p) {
        final Rectangle rect_jugador = (Rectangle) p.getRectagulo().clone();
        rect_jugador.translate(p.getVelocidadY(), 0);
        final Point ptoRef = (Point) rect_jugador.getLocation().clone();
        ptoRef.translate(0, (int) (rect_jugador.height * 0.5f));
        if(p.getVelocidadX() > 0) {
            ptoRef.translate(rect_jugador.width, 0);
        }
        final Rectangle rect_mapa = mapaGrafico.getCasilla(ptoRef);
        final Rectangle rc = null;
        final int terreno = mapaGrafico.getTerreno(mapaGrafico.getFila(rect_mapa.y), mapaGrafico.getColumna(rect_mapa.x), Capa.INFERIOR);
        if(terreno == -1) {
            return true;
        } else {
            return false;
        }
//        if(terreno != -1 && (rc = rect_jugador.intersection(rect_mapa)) != null) {
//            rect_jugador.translate(rc.width * p.getVelocidad() > 0 ? 1 : -1, 0);
//            return true;
//        }
//        return false;
    }

}
