/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Mapa;
import motor.core.ControlAnimacion;
import motor.core.graphics.Imagen;
import Dependencias.Imagenes;
import gui.JPanelJuego;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import juego.constantes.Estado;

/**
 *
 * @author hp
 */
public class Fire extends Personaje {

    private static enum Direccion {

        ARRIBA, ABAJO, DERECHA, IZQUIERDA;
    }

    private int[] espacioDirecciones;
    private Point[] pos;
    private final int espacio;
    private Imagen[] imagenes;

    public Fire(final int x, final int y, final int espacios, final JPanelJuego jPanelJuego) {
        super(new Imagen(Imagenes.FUEGO, 7, 4, 2.5f), x, y);
        this.espacio = espacios;
        inicializar();
        determinarTamaño(jPanelJuego);
        crearSprites();
    }

    public final void inicializar() {
        super.animaciones = new HashMap<Integer, ControlAnimacion>() {
            {
                put(0, new ControlAnimacion("0,1,2,3", 4000 / 60));
            }
        };
        espacioDirecciones = new int[4];
        setEstadoActual(Estado.INICIO.val());
    }

    private Point getPosSprite(final Direccion d, int i) {
        return d == Direccion.ARRIBA ? new Point(x, y - i * imagen.getAlto()) : d == Direccion.ABAJO ? new Point(x, y + i * imagen.getAlto()) : d == Direccion.DERECHA ? new Point(x + i * imagen.getAncho(), y) : new Point(x - i * imagen.getAncho(), y);
    }

    private void crearSprites() {
        boolean[] bs = new boolean[4];
        short indice = 0;
        imagenes = new Imagen[1 + espacioDirecciones[0] + espacioDirecciones[1] + espacioDirecciones[2] + espacioDirecciones[3]];
        pos = new Point[1 + espacioDirecciones[0] + espacioDirecciones[1] + espacioDirecciones[2] + espacioDirecciones[3]];
        pos[indice] = new Point(x, y);
        imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, (float) 2.5, 0);
        for (int i = 1; i <= espacio; i++)
            for (Direccion value : Direccion.values()) {
                if (bs[value.ordinal()])
                    continue;
                final Point p = getPosSprite(value, i);
                if (i <= espacioDirecciones[value.ordinal()] && i != espacio) {
                    pos[indice] = p;
                    imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, 2.5f, value == Direccion.ARRIBA || value == Direccion.ABAJO ? 6 : 5);
                }
                if (i < espacioDirecciones[value.ordinal()] || espacioDirecciones[value.ordinal()] == 0)
                    continue;
                if (i == espacio) {
                    pos[indice] = p;
                    imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, 2.5f, value.ordinal() + 1);
                }
                bs[value.ordinal()] = true;
            }
    }

    @Override
    public void actualizar(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
        if (getEstadoActual() == Estado.ELIMINADO.val())
            return;
        estadoInicio(jPanelJuego, tiempoTranscurrido);
        final int i = animaciones.get(0).getCuadroActual();
        for (final Imagen sprite : imagenes)
            sprite.actualizar(i);
    }

    @Override
    public void pintar(final Graphics2D g) {
        if (getEstadoActual() == Estado.ELIMINADO.val() || !isActivo())
            return;
        for (int i = 0; i < imagenes.length; i++)
            imagenes[i].pintar(g, pos[i].x, pos[i].y);
    }

    @Override
    public void estadoInicio(final JPanelJuego jPanelJuego, final long tiempoTranscurrido) {
        if (actualizarAnimacion(tiempoTranscurrido))
            setEstadoActual(Estado.ELIMINADO.val());
    }

    private void determinarTamaño(final JPanelJuego jPanelJuego) {
        boolean[] bs = new boolean[4];
        for (int i = 1; i <= espacio; i++) {
            if (!bs[Direccion.ARRIBA.ordinal()] && detTamDir(jPanelJuego, Direccion.ARRIBA, i, posicionMapa.x, posicionMapa.y - i))
                bs[Direccion.ARRIBA.ordinal()] = true;
            if (!bs[Direccion.ABAJO.ordinal()] && detTamDir(jPanelJuego, Direccion.ABAJO, i, posicionMapa.x, posicionMapa.y + i))
                bs[Direccion.ABAJO.ordinal()] = true;
            if (!bs[Direccion.DERECHA.ordinal()] && detTamDir(jPanelJuego, Direccion.DERECHA, i, posicionMapa.x + i, posicionMapa.y))
                bs[Direccion.DERECHA.ordinal()] = true;
            if (!bs[Direccion.IZQUIERDA.ordinal()] && detTamDir(jPanelJuego, Direccion.IZQUIERDA, i, posicionMapa.x - i, posicionMapa.y))
                bs[Direccion.IZQUIERDA.ordinal()] = true;
        }
    }

    private boolean detTamDir(final JPanelJuego jPanelJuego, final Direccion d, final int i, final int x, final int y) {
        Mapa m = jPanelJuego.getMapa();
        boolean b1, b2;
        if (d == Direccion.ARRIBA || d == Direccion.ABAJO) {
            b1 = choqueY(m, y, "A", "L", "X", "Q", "S");
            b2 = !choqueY(m, y, "V");
        } else {
            b1 = choqueX(m, x, "A", "L", "X", "Q", "S");
            b2 = !choqueX(m, x, "V");
        }
        if (b1) {
            jPanelJuego.borrarLadrillo(x, y);
            jPanelJuego.borrarBombs(x, y);
        } else if (b2) {
            jPanelJuego.borrarEnemigo(x, y);
            if (!jPanelJuego.primerJugador().getFLAMEPASS())
                jPanelJuego.borrarJugador(x, y);
        }
        if (!b1 && espacio != i)
            return false;
        espacioDirecciones[d.ordinal()] = b1 ? i - 1 : i;
        return true;
    }

}
