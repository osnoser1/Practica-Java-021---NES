package Personajes;

import motor.core.ControlAnimacion;
import motor.core.graphics.Imagen;
import gui.JPanelJuego;
import motor.core.input.GamePad;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import static juego.constantes.Estado.*;

public class Enemigo extends Personaje {

    protected int puntaje;

    public Enemigo(final Image image, final int x, final int y, final GamePad gamePad) {
        super(new Imagen(image, 6, 5, (float) 2.5), x, y);
        this.gamePad = gamePad;
        inicializar();
    }

    public final void inicializar() {
        animaciones = new HashMap<Integer, ControlAnimacion>() {
            {
                put(INICIO.val(), new ControlAnimacion("0", 4000 / 60));
                put(ARRIBA.val(), new ControlAnimacion("0,1,2", 4000 / 60));
                put(ABAJO.val(), new ControlAnimacion("0,1,2", 4000 / 60));
                put(DERECHA.val(), new ControlAnimacion("0,1,2", 4000 / 60));
                put(IZQUIERDA.val(), new ControlAnimacion("0,1,2", 4000 / 60));
                put(MUERTE.val(), new ControlAnimacion("0,1,2,3,4", 500));
            }
        };
        setEstadoActual(IZQUIERDA.val());
        inteligencia = new Inteligencia(this);
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getPuntaje() {
        return puntaje;
    }

    @Override
    public void pintar(final Graphics2D g) {
        super.pintar(g);
        if (getEstadoActual() == MUERTE.val()) {
            g.setColor(Color.white);
            g.drawString("" + puntaje, getX() + imagen.getAncho() / 5, getCentro().y);
        }
    }

    public void muerte(JPanelJuego jPanelJuego) {
        setEstadoActual(MUERTE.val());
        jPanelJuego.getMapa().remover(this);
        detenerInteligencia();
    }

    @Override
    public void estadoArriba(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        estadoGenerico(jPanelJuego, tiempoTranscurrido);
    }

    @Override
    public void estadoAbajo(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        estadoGenerico(jPanelJuego, tiempoTranscurrido);
    }

    @Override
    public void estadoDerecha(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        estadoGenerico(jPanelJuego, tiempoTranscurrido);
    }

    @Override
    public void estadoIzquierda(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        estadoGenerico(jPanelJuego, tiempoTranscurrido);
    }

    @Override
    public void estadoMuerte(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if (actualizarAnimacion(tiempoTranscurrido)) {
            setEstadoActual(ELIMINADO.val());
        }
    }

    public void estadoGenerico(final JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if (verificarMovimiento(jPanelJuego)) {
            actualizarAnimacion(tiempoTranscurrido);
        }
    }

    private boolean verificarMovimiento(final JPanelJuego jPanelJuego) {
        boolean movimiento = true;
        if (gamePad.isPress(GamePad.Botones.ARRIBA)) {
            setEstadoActual(ARRIBA.val());
            movimientoArriba(jPanelJuego);
        } else if (gamePad.isPress(GamePad.Botones.ABAJO)) {
            setEstadoActual(ABAJO.val());
            movimientoAbajo(jPanelJuego);
        } else {
            movimiento = false;
        }
        if (gamePad.isPress(GamePad.Botones.DERECHA)) {
            setEstadoActual(DERECHA.val());
            movimientoDerecha(jPanelJuego);
            movimiento = true;
        } else if (gamePad.isPress(GamePad.Botones.IZQUIERDA)) {
            setEstadoActual(IZQUIERDA.val());
            movimientoIzquierda(jPanelJuego);
            movimiento = true;
        }
        return movimiento;
    }

}
