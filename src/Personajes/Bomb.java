/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Sonido;
import motor.core.ControlAnimacion;
import motor.core.graphics.Imagen;
import Dependencias.Imagenes;
import gui.JPanelJuego;
import Dependencias.Sonidos;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import juego.constantes.Estado;

public class Bomb extends Personaje {

    private boolean detonar;
    private Fire fire;
    private final Timer timer;
    private final Personaje p;

    public Bomb(final int x, final int y, final Bomberman jugador) {
        super(new Imagen(Imagenes.BOMBA, 1, 3, (float) 2.5), x, y);
        p = jugador;
        id = "X";
        inicializar();
        timer = jugador.getDETONADOR() ? null : new Timer(3200, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detonar = jugador.getEstadoActual() == Estado.MUERTE.val();
                timer.stop();
            }
        });
        if (timer != null)
            timer.start();
    }

    public final void inicializar() {
        super.animaciones = new HashMap<Integer, ControlAnimacion>() {
            {
                put(Estado.INICIO.ordinal(), new ControlAnimacion("0,1,2", 400));
            }
        };
        setEstadoActual(Estado.INICIO.val());
    }

    public void detonar(final JPanelJuego jPanelJuego) {
        setActivo(false);
        setEstadoActual(Estado.MUERTE.val());
        fire = new Fire(x, y, jPanelJuego.primerJugador().getFLAMES(), jPanelJuego);
        Sonido s = Sonidos.getInstance().getNewSonido(Sonidos.EXPLOSION_1);
        if (s != null)
            s.play();
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        if (fire != null)
            fire.actualizar(jPanelJuego, tiempoTranscurrido);
    }

    @Override
    public void pintar(Graphics2D g) {
        super.pintar(g);
        if (fire != null)
            fire.pintar(g);
    }

    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        actualizarAnimacion(tiempoTranscurrido);
        if (detonar)
            detonar(jPanelJuego);
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if (fire.getEstadoActual() == Estado.ELIMINADO.val())
            setEstadoActual(Estado.ELIMINADO.val());
    }

}
