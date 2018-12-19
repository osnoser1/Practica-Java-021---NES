/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.graphics.Imagen;
import Dependencias.Imagenes;
import Utilidades.Juego.Interfaz;
import game.players.fire.states.InicioState;
import gui.JPanelJuego;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import motor.core.graphics.AnimationWrapper;
import motor.core.graphics.spritedefaultstates.NullState;
import motor.core.map.Posicion;

/**
 *
 * @author hp
 */
public class Fire extends Personaje {

    private enum Direccion {

        ARRIBA, ABAJO, DERECHA, IZQUIERDA
    }

    private int[] espacioDirecciones;
    private Point[] pos;
    private final int espacio;
    private Imagen[] imagenes;

    public Fire(final int x, final int y, final int espacios, final JPanelJuego jPanelJuego) {
        super(new Imagen(Imagenes.FUEGO, 7, 4, 2.5f), x, y);
        this.espacio = espacios;
        inicializar(jPanelJuego);
        determinarTamañoYMuertePersonajes(jPanelJuego);
        crearSprites();
    }

    public final void inicializar(JPanelJuego jPanelJuego) {
        super.animaciones = new HashMap<>() {
            {
                put(InicioState.class, new AnimationWrapper(0, "0,1,2,3", 4000 / 60));
            }
        };
        espacioDirecciones = new int[4];
        setEstadoActual(InicioState::new);
        jPanelJuego.getMapa().agregar(this);
    }

    private Point getPosSprite(final Direccion d, int i) {
        return d == Direccion.ARRIBA ? new Point(x, y - i * imagen.getAlto()) : d == Direccion.ABAJO ? new Point(x, y + i * imagen.getAlto()) : d == Direccion.DERECHA ? new Point(x + i * imagen.getAncho(), y) : new Point(x - i * imagen.getAncho(), y);
    }

    private void crearSprites() {
        var bs = new boolean[4];
        short indice = 0;
        imagenes = new Imagen[1 + espacioDirecciones[0] + espacioDirecciones[1] + espacioDirecciones[2] + espacioDirecciones[3]];
        pos = new Point[1 + espacioDirecciones[0] + espacioDirecciones[1] + espacioDirecciones[2] + espacioDirecciones[3]];
        pos[indice] = new Point(x, y);
        imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, (float) 2.5, 0);
        for (var i = 1; i <= espacio; i++)
            for (var value : Direccion.values()) {
                if (bs[value.ordinal()])
                    continue;
                final var p = getPosSprite(value, i);
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
    public void actualizar(final Interfaz interfaz, final long tiempoTranscurrido) {
        super.actualizar(interfaz, tiempoTranscurrido);
        if (estadoActual instanceof NullState)
            return;
        final var i = animaciones.get(InicioState.class).animacion.getCuadroActual();
        for (final var sprite : imagenes)
            sprite.actualizar(i);
    }

    @Override
    public void pintar(final Graphics2D g) {
        if (estadoActual instanceof NullState || !isActivo())
            return;
        for (var i = 0; i < imagenes.length; i++)
            imagenes[i].pintar(g, pos[i].x, pos[i].y);
    }

    private void determinarTamañoYMuertePersonajes(final JPanelJuego jPanelJuego) {
        var posicion = jPanelJuego.getMapa().getPosicion(this);
        var bs = new boolean[4];
        for (var i = 1; i <= espacio; i++) {
            if (!bs[Direccion.ARRIBA.ordinal()] && detTamDir(jPanelJuego, Direccion.ARRIBA, i, posicion.fila - i, posicion.columna))
                bs[Direccion.ARRIBA.ordinal()] = true;
            if (!bs[Direccion.ABAJO.ordinal()] && detTamDir(jPanelJuego, Direccion.ABAJO, i, posicion.fila + i, posicion.columna))
                bs[Direccion.ABAJO.ordinal()] = true;
            if (!bs[Direccion.DERECHA.ordinal()] && detTamDir(jPanelJuego, Direccion.DERECHA, i, posicion.fila, posicion.columna + i))
                bs[Direccion.DERECHA.ordinal()] = true;
            if (!bs[Direccion.IZQUIERDA.ordinal()] && detTamDir(jPanelJuego, Direccion.IZQUIERDA, i, posicion.fila, posicion.columna - i))
                bs[Direccion.IZQUIERDA.ordinal()] = true;
        }
        comprobarMuertePersonajesCentroExplosion(jPanelJuego, posicion);
    }

    private boolean detTamDir(final JPanelJuego jPanelJuego, final Direccion d, final int i, final int fila, final int columna) {
        var m = jPanelJuego.getMapa();
        boolean b1, b2;
        if (d == Direccion.ARRIBA || d == Direccion.ABAJO) {
            b1 = choqueY(m, fila, Aluminio.class, Ladrillo.class, Bomb.class, LadrilloEspecial.class);
            b2 = choqueY(m, fila, Bomberman.class, Enemigo.class);
        } else {
            b1 = choqueX(m, columna, Aluminio.class, Ladrillo.class, Bomb.class, LadrilloEspecial.class);
            b2 = choqueX(m, columna, Bomberman.class, Enemigo.class);
        }
        if (b1) {
            jPanelJuego.borrarLadrillo(fila, columna);
            jPanelJuego.borrarBombs(fila, columna);
        } 
        if (b2) {
            jPanelJuego.borrarEnemigo(fila, columna);
            if (!jPanelJuego.primerJugador().getFLAMEPASS())
                jPanelJuego.borrarJugador(fila, columna);
        }
        if (!b1 && espacio != i)
            return false;
        espacioDirecciones[d.ordinal()] = b1 ? i - 1 : i;
        return true;
    }
    
    private void comprobarMuertePersonajesCentroExplosion(JPanelJuego jPanelJuego, Posicion posicion) {
        if(jPanelJuego.getMapa().contiene(posicion.fila, posicion.columna, Enemigo.class)) { 
            jPanelJuego.borrarEnemigo(posicion.fila, posicion.columna);
        } 
        if(choqueCentral(Bomberman.class) && !jPanelJuego.primerJugador().getFLAMEPASS()) {
            jPanelJuego.borrarJugador(posicion.fila, posicion.columna);
        }
    }
    
}
