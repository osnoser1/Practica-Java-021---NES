/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import motor.core.graphics.Sprite;

/**
 *
 * @author AlfonsoAndrés
 */
public class Camara {
    
    private final Rectangle posicion;
    private final Dimension tamañoNivel;
    private final int cuarto, tercio;

    public Camara(final Rectangle posicion, final Dimension tamañoNivel) {
        this.posicion = posicion;
        this.tamañoNivel = tamañoNivel;
        cuarto = tamañoNivel.width / 4;
        tercio = 3 * cuarto;
    }
    
    public void actualizar(final Point puntoGuia) {
        if(puntoGuia.x > cuarto && puntoGuia.x < tercio)
            posicion.x = puntoGuia.x - cuarto;
        if(puntoGuia.x > tercio && puntoGuia.x < cuarto)
            posicion.x = cuarto - puntoGuia.x;
        if(posicion.x < 0)
            posicion.x = 0;
        if(posicion.x + posicion.width > tamañoNivel.width)
            posicion.x = tamañoNivel.width - posicion.width;
    }
    
    private boolean contiene(int x, int y, int ancho, int alto) {
        return posicion.intersects(x, y, ancho, alto);
    }

    public Rectangle getPosicion() {
        return posicion;
    }

    public void reiniciar() {
        posicion.x = posicion.y = 0;
    }

    public boolean contiene(final Sprite s) {
        return contiene(s.getX(), s.getY(), s.getAncho(), s.getAlto());
    }
    
}
