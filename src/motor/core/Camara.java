/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author AlfonsoAndrés
 */
public class Camara {

    private final Rectangle posicion;
    private final Dimension tamañoNivel;
    private final int inicioX, finX, inicioY, finY;

    public Camara(Rectangle posicion, Dimension tamañoNivel) {
        this.posicion = posicion;
        this.tamañoNivel = tamañoNivel;
        inicioX = posicion.width / 2;
        finX = tamañoNivel.width - inicioX;
        inicioY = posicion.height / 2;
        finY = tamañoNivel.height - inicioX;

    }

    public void actualizar(Point puntoGuia) {
        if (puntoGuia.x > inicioX && puntoGuia.x < finX) {
            posicion.x = puntoGuia.x - inicioX;
        } else if (posicion.x < 0) {
            posicion.x = 0;
        } else if (posicion.x + posicion.width > tamañoNivel.width) {
            posicion.x = tamañoNivel.width - posicion.width;
        }

        if (puntoGuia.y > inicioY && puntoGuia.y < finY) {
            posicion.y = puntoGuia.y - inicioY;
        } else if (posicion.y < 0) {
            posicion.y = 0;
        } else if (posicion.y + posicion.height > tamañoNivel.height) {
            posicion.y = tamañoNivel.height - posicion.height;
        }
//        System.out.println(posicion+"  "+inicio+"  "+fin+"  "+puntoGuia);

    }

    public boolean contiene(int x, int y, int ancho, int alto) {
        return posicion.intersects(x, y, ancho, alto);
    }

    public Rectangle getPosicion() {
        return posicion;
    }

    public void reiniciar() {
        posicion.x = posicion.y = 0;
    }

    public boolean contiene(Rectangle rectagulo) {
        return contiene(rectagulo.x, rectagulo.y, rectagulo.width, rectagulo.height);
    }
}
