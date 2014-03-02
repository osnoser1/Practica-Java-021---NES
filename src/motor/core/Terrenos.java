/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package motor.core;

import motor.utils.Graphics;
import motor.utils.Image;

/**
 *
 * @author hp
 */
public class Terrenos {

    private final Imagen imagen;

    public Terrenos(Imagen imagen) {
        this.imagen = imagen;
    }

    public Mapa obtenerMapaFuncional(Mapa grafico) {
        return null;
    }

    public Image obtenerImagen(int fila, int columna) {
        return imagen.getSubImageWithout(fila, columna);
    }

    public void pintar(Graphics g, int x, int y, int fila, int columna) {
        g.drawImage(obtenerImagen(fila, columna), x, y, getAnchoEscalado(), getAltoEscalado());
    }

//    public enum Terreno {
//        TIERRA(6, 6, true),
//        VACIO(6, 6, false),
//        ESCALERA(3, 3, false);
//
//        int velocidad_x, velocidad_y;
//        boolean solido;
//
//        Terreno(int vx, int vy, boolean solido) {
//            this.velocidad_x = vx;
//            this.velocidad_y = vy;
//            this.solido = solido;
//        }
//        public boolean isSolido(){
//            return solido;
//        }
//    }
    public int getAnchoEscalado() {
        return imagen.getAnchoEscalado();
    }

    public int getAltoEscalado() {
        return imagen.getAltoEscalado();
    }
}
