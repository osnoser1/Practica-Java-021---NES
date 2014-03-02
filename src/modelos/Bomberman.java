/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import juego.Imagenes;
import lenguaje.utils.Pareja;
import modelos.LogicaJuego.Jugador;
import motor.core.Automata;
import motor.core.Automata.L;
import motor.core.Automata.P;
import motor.core.Automata.Relacion;
import motor.core.Automata.T;
import motor.core.Automata.Triple;
import motor.core.Colisiones.Colision;
import motor.core.ControlAnimacion;
import motor.core.Imagen;
import motor.core.MovimientosPersonaje.TipoMovimiento;
import motor.core.Personaje;
import static motor.core.EstadosSprite.Estado.*;
import motor.core.GamePad.Botones;
import motor.utils.Image;
import motor.core.Teclado.Control;

/**
 *
 *
 */
public class Bomberman extends Personaje {

    private static HashMap<Integer, TipoMovimiento> accionesJugador;
    private static HashMap<Integer, Pareja<Integer, ControlAnimacion>> animacionesJugador;
    private static Automata a;
    private static int velocidadJugadorX, velocidadJugadorY;
    private static Image bufferedImage;
    private Jugador player;

    static {
        animacionesJugador = new HashMap<Integer, Pareja<Integer, ControlAnimacion>>() {
            {
                put(INICIO.ordinal(), Pareja.de(0, new ControlAnimacion("0", 4000 / 60)));
                put(ARRIBA.ordinal(), Pareja.de(1, new ControlAnimacion("2,1,0,1", 4000 / 60)));
                put(ABAJO.ordinal(), Pareja.de(2, new ControlAnimacion("2,1,0,1", 4000 / 60)));
                put(DERECHA.ordinal(), Pareja.de(3, new ControlAnimacion("2,1,0,1", 4000 / 60)));
                put(IZQUIERDA.ordinal(), Pareja.de(4, new ControlAnimacion("2,1,0,1", 4000 / 60)));
                put(MUERTE.ordinal(), Pareja.de(5, new ControlAnimacion("0,1,2,3,4", 300)));
            }
        };
        accionesJugador = new HashMap<Integer, TipoMovimiento>() {
            {
                put(ARRIBA.ordinal(), TipoMovimiento.DESPLAZAR_Y);
                put(ABAJO.ordinal(), TipoMovimiento.DESPLAZAR_Y);
                put(DERECHA.ordinal(), TipoMovimiento.DESPLAZAR_X);
                put(IZQUIERDA.ordinal(), TipoMovimiento.DESPLAZAR_X);
                put(MUERTE.ordinal(), TipoMovimiento.NINGUNO);
                put(INICIO.ordinal(), TipoMovimiento.NINGUNO);
            }
        };
        a = new Automata(new HashMap<Integer, Relacion>() {{
            put(INICIO.ordinal(), new Relacion(new Triple[]{
                T.de(P.de(Colision.NINGUNO), L.de(Botones.NINGUNO), DERECHA)
            }, null));
            put(DERECHA.ordinal(), new Relacion(new Triple[]{
                T.de(P.de(Colision.NINGUNO), L.de(Botones.ARRIBA), ARRIBA),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.ABAJO), ABAJO),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.IZQUIERDA), IZQUIERDA),
            }, null));
            put(IZQUIERDA.ordinal(), new Relacion(new Triple[]{
                T.de(P.de(Colision.NINGUNO), L.de(Botones.ARRIBA), ARRIBA),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.ABAJO), ABAJO),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.DERECHA), DERECHA),
            }, null));
            put(ARRIBA.ordinal(), new Relacion(new Triple[]{
                T.de(P.de(Colision.NINGUNO), L.de(Botones.IZQUIERDA), IZQUIERDA),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.DERECHA), DERECHA),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.ABAJO), ABAJO),
            }, null));
            put(ABAJO.ordinal(), new Relacion(new Triple[]{
                T.de(P.de(Colision.NINGUNO), L.de(Botones.IZQUIERDA), IZQUIERDA),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.DERECHA), DERECHA),
                T.de(P.de(Colision.NINGUNO), L.de(Botones.ARRIBA), ARRIBA),
            }, null));
        }});
        velocidadJugadorX = velocidadJugadorY = 2;
        bufferedImage = Imagenes.BOMBERMAN;
    }

    public Bomberman(Point point, Control control, Image bufferedImage) {
        inicializar(new Imagen(bufferedImage, 5, 3, 1.0f), point, velocidadJugadorX, velocidadJugadorY, animacionesJugador, accionesJugador, a, control);
        setEstadoActual(INICIO);
    }
    
    public Bomberman(Point point, Control control) {
        inicializar(new Imagen(bufferedImage, 5, 3, 1.0f), point, velocidadJugadorX, velocidadJugadorY, animacionesJugador, accionesJugador, a, control);
        setEstadoActual(INICIO);
    }

//    @Override
//    public void estadoInicio(Juego escenaJuego, long tiempoTranscurrido) {
//        setEstadoActual(Estado.DERECHA);
//    }
//
//    @Override
//    public void estadoArriba(Juego escenaJuego, long tiempoTranscurrido) {
//        boolean colision = escenaJuego.hayColision(this);
//        if (isPress(Botones.DERECHA)) {
//            setEstadoActual(Estado.DERECHA);
//        }
//        if (isPress(Botones.ABAJO)) {
//            setEstadoActual(Estado.ABAJO);
//        }
//        if (isPress(Botones.IZQUIERDA)) {
//            setEstadoActual(Estado.IZQUIERDA);
//        }
//        if (!isPress(Botones.ARRIBA) && !isPress(Botones.ABAJO) && !isPress(Botones.DERECHA) && !isPress(Botones.IZQUIERDA)) {
//            return;
//        }
//        actualizarAnimacion(tiempoTranscurrido);
//    }
//
//    @Override
//    public void estadoAbajo(Juego escenaJuego, long tiempoTranscurrido) {
//        boolean colision = escenaJuego.hayColision(this);
//        if (isPress(Botones.ARRIBA)) {
//            setEstadoActual(Estado.ARRIBA);
//        }
//        if (isPress(Botones.DERECHA)) {
//            setEstadoActual(Estado.DERECHA);
//        }
//        if (isPress(Botones.IZQUIERDA)) {
//            setEstadoActual(Estado.IZQUIERDA);
//        }
//        if (!isPress(Botones.ARRIBA) && !isPress(Botones.ABAJO) && !isPress(Botones.DERECHA) && !isPress(Botones.IZQUIERDA)) {
//            return;
//        }
//        actualizarAnimacion(tiempoTranscurrido);
//    }
//
//    @Override
//    public void estadoDerecha(Juego escenaJuego, long tiempoTranscurrido) {
//        boolean colision = escenaJuego.hayColision(this);
//        if (isPress(Botones.ARRIBA)) {
//            setEstadoActual(Estado.ARRIBA);
//        }
//        if (isPress(Botones.ABAJO)) {
//            setEstadoActual(Estado.ABAJO);
//        }
//        if (isPress(Botones.IZQUIERDA)) {
//            setEstadoActual(Estado.IZQUIERDA);
//        }
//        if (!isPress(Botones.ARRIBA) && !isPress(Botones.ABAJO) && !isPress(Botones.DERECHA) && !isPress(Botones.IZQUIERDA)) {
//            return;
//        }
//        actualizarAnimacion(tiempoTranscurrido);
//    }
//
//    @Override
//    public void estadoIzquierda(Juego escenaJuego, long tiempoTranscurrido) {
//        boolean colision = escenaJuego.hayColision(this);
//        if (isPress(Botones.ARRIBA)) {
//            setEstadoActual(Estado.ARRIBA);
//        }
//        if (isPress(Botones.ABAJO)) {
//            setEstadoActual(Estado.ABAJO);
//        }
//        if (isPress(Botones.DERECHA)) {
//            setEstadoActual(Estado.DERECHA);
//        }
//        if (!isPress(Botones.ARRIBA) && !isPress(Botones.ABAJO) && !isPress(Botones.DERECHA) && !isPress(Botones.IZQUIERDA)) {
//            return;
//        }
//        actualizarAnimacion(tiempoTranscurrido);
//    }
//
//    @Override
//    public void estadoMuerte(Juego escenaJuego, long tiempoTranscurrido) {
//    
//    }

    public void asociar(Jugador player) {
        this.player = player;
    }
    
    public Jugador getPlayer() {
        return player;
    }
    
}
