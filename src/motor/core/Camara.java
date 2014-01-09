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
    
    private Rectangle posicion;
    private Dimension tamañoNivel;
    private int cuarto, tercio;

    public Camara(Rectangle posicion, Dimension tamañoNivel) {
        this.posicion = posicion;
        this.tamañoNivel = tamañoNivel;
        cuarto = tamañoNivel.width / 4;
        tercio = 3 * cuarto;
    }
    
    public void actualizar(Point puntoGuia) {
        if(puntoGuia.x > cuarto && puntoGuia.x < tercio)
            posicion.x = puntoGuia.x - cuarto;
        if(puntoGuia.x > tercio && puntoGuia.x < cuarto)
            posicion.x = cuarto - puntoGuia.x;
        if(posicion.x < 0)
            posicion.x = 0;
        if(posicion.x + posicion.width > tamañoNivel.width)
            posicion.x = tamañoNivel.width - posicion.width;
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
