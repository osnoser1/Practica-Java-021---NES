/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import gui.JPanelJuego;
import Dependencias.Sonidos;
import Utilidades.Juego.Interfaz;
import motor.core.graphics.Sprite;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.AbstractAction;
import javax.swing.Timer;
import motor.core.graphics.Imagen;
import motor.core.map.Posicion;

/**
 *
 * @author hp
 */
public class LadrilloEspecial extends Sprite {

    private Timer timer;
    private final int tipo;
    private boolean estadoEliminado;
    private final AtomicInteger nroEnemigos;
    private Posicion posicion;

    public LadrilloEspecial(final int x, final int y, int tipo) {
        super(new Imagen(Imagenes.LADRILLO_ESPECIAL, 1, 9, 2.5f), x, y);
        imagen.actualizar(tipo);
        this.tipo = tipo;
        this.nroEnemigos = new AtomicInteger();
    }

    @Override
    public void actualizar(Interfaz interfaz, long tiempoTranscurrido) {
        var jPanelJuego = (JPanelJuego) interfaz;
        if (jPanelJuego.primerJugador().isEntroALaPuerta())
            return;
        agregarEnemigosPendientes(jPanelJuego);
        var punto = jPanelJuego.getMapa().getPosicion(jPanelJuego.primerJugador());
        if (punto != null && punto.equals(jPanelJuego.getMapa().getPosicion(this)))
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
        posicion = jPanelJuego.getMapa().getPosicion(this);
        if(timer != null && timer.isRunning()) {
            return;
        } 
        timer = new Timer(500, new AbstractAction() {
            int time = 5;

            @Override
            public void actionPerformed(ActionEvent e) {
                incrementarContadorEnemigos();
                if (--time < 0) {
                    timer.stop();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }
    
    private void incrementarContadorEnemigos() {
        if(nroEnemigos.get() < 5) {
            nroEnemigos.incrementAndGet();
        }
    }
 
    private void agregarEnemigosPendientes(JPanelJuego jPanelJuego) {
        for (int i = 0, n = nroEnemigos.get(); i < n; i++) {
            jPanelJuego.agregarEnemigo(posicion.fila, posicion.columna, jPanelJuego.determinarEnemigo(3));
        }
        nroEnemigos.set(0);
    }   
    
    public boolean isEstadoEliminado() {
        return estadoEliminado;
    }

}
