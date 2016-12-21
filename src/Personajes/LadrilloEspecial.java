/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import gui.JPanelJuego;
import Dependencias.Sonidos;
import motor.core.graphics.Sprite;
import java.awt.Point;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import motor.core.graphics.Imagen;

/**
 *
 * @author hp
 */
public class LadrilloEspecial extends Sprite {

    private Timer timer;
    private final int tipo;
    private boolean estadoEliminado;

    public LadrilloEspecial(final int x, final int y, int tipo) {
        super(new Imagen(Imagenes.LADRILLO_ESPECIAL, 1, 9, 2.5f), x, y);
        imagen.actualizar(tipo);
        this.tipo = tipo;
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if (jPanelJuego.primerJugador().isEntroALaPuerta())
            return;
        Point punto = jPanelJuego.primerJugador().getPosicionMapa();
        if (posicionMapa.equals(punto))
            if (tipo != getPuerta() && !estadoEliminado) {
                determinarHabilidad(jPanelJuego);
                Sonidos.getInstance().detener(Sonidos.STAGE_THEME);
                Sonidos.getInstance().play(Sonidos.POWER_UP_2);
                Sonidos.getInstance().loop(Sonidos.FIND_THE_DOOR);
                eliminarPowerup();
            } else if (jPanelJuego.getCantidadEnemigos() == 0) {
                Sonidos.getInstance().detener();
                Sonidos.getInstance().play(Sonidos.LEVEL_COMPLETE);
                jPanelJuego.primerJugador().setEntroALaPuerta(true);
                System.out.println("Entro en la puerta");
            }
    }

    private int getPuerta() {
        return 8;
    }

    public boolean esPuerta() {
        return tipo == getPuerta();
    }

    public void eliminarPowerup() {
        if (tipo != getPuerta())
            estadoEliminado = true;
    }

    public void determinarHabilidad(JPanelJuego jPanelJuego) {
        if (tipo == 7) {
        } else if (tipo == 0)
            jPanelJuego.primerJugador().incrementarFlamas(1);
        else if (tipo == 1)
            jPanelJuego.primerJugador().incrementarBombas(1);
        else if (tipo == 2)
            jPanelJuego.primerJugador().setDETONATOR(true);
        else if (tipo == 3)
            jPanelJuego.primerJugador().setSPEED(true);
        else if (tipo == 4)
            jPanelJuego.primerJugador().setBOMBPASS(true);
        else if (tipo == 5)
            jPanelJuego.primerJugador().setWallpass(true);
        else if (tipo == 6)
            jPanelJuego.primerJugador().setFLAMEPASS(true);
        else if (tipo == 7)
            jPanelJuego.primerJugador().setMYSTERY(true);
    }

    public void crearEnemigos(final JPanelJuego jPanelJuego) {
        timer = new Timer(500, new AbstractAction() {
            int time = 5;

            @Override
            public void actionPerformed(ActionEvent e) {
                time--;
                jPanelJuego.getEnemigos().add(jPanelJuego.determinarEnemigo(posicionMapa.y, posicionMapa.x, jPanelJuego.determinarEnemigo(3)));
                if (time == 0)
                    timer.stop();
            }
        });
        timer.start();
    }

    public boolean isEstadoEliminado() {
        return estadoEliminado;
    }

}
