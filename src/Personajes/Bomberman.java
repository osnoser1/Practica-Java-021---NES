/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.Personaje;
import motor.core.ControlAnimacion;
import motor.core.Imagen;
import Dependencias.Imagenes;
import Dependencias.Mapa;
import motor.core.Teclado.Control;
import gui.EscenaJuego;
import Sonidos.Sonidos;
import motor.core.GamePad;
import motor.core.GamePad.Botones;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import modelos.PersonajeJuego;

public class Bomberman extends PersonajeJuego {

    private boolean SPEED, DETONADOR, BOMBPASS, FLAMEPASS, MYSTERY;
    private int FLAMES, BOMBS;
    private static CopyOnWriteArrayList<Bomb> bombas;
    private boolean entroALaPuerta;

    public Bomberman(Point point, Control control) {
        bombas = new CopyOnWriteArrayList<>();
        BOMBS = 10;
        FLAMES = 10;
        SPEED = true;
        wallpass = true;
        DETONADOR = true;
        BOMBPASS = true;
        FLAMEPASS = true;
        MYSTERY = true;
        id = "B";
        inicializar(new Imagen(Imagenes.BOMBERMAN, 6, 6, 2.5f), point, SPEED_MID, SPEED_MID, null, null, null, control);
    }

    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        inicializar(new Imagen(imagen, 6, 6, posicion, (float) 2.5), posicion);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, ControlAnimacion>() {
            {
                put(Estado.INICIO.ordinal(), new ControlAnimacion("0", 4000 / 60));
                put(Estado.ARRIBA.ordinal(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(Estado.ABAJO.ordinal(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(Estado.DERECHA.ordinal(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(Estado.IZQUIERDA.ordinal(), new ControlAnimacion("2,1,0,1", 4000 / 60));
                put(Estado.MUERTE.ordinal(), new ControlAnimacion("0,1,2,3,4", 300));
            }
        };
        setEstadoActual(Estado.IZQUIERDA);
    }

    @Override
    public void actualizar(EscenaJuego jPanelJuego, long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        for (Bomb bomba : bombas) {
            bomba.actualizar(jPanelJuego, tiempoTranscurrido);
            if (bomba.getEstadoActual() == Personaje.Estado.ELIMINADO) {
                Mapa.getInstance().setObjeto("V", bomba.getPosicionMapa());
                bombas.remove(bomba);
            }
        }
    }

    public void colisionaCon(Personaje otro) {
        if (getEstadoActual() == Estado.MUERTE) {
            return;
        }
        Sonidos.getInstance().detener(Sonidos.UP, Sonidos.DOWN, Sonidos.LEFT, Sonidos.RIGHT);
        Sonidos.getInstance().get(Sonidos.DEATH).play();
        setEstadoActual(Personaje.Estado.MUERTE);
    }

    public void setDETONATOR(boolean DETONATOR) {
        this.DETONADOR = DETONATOR;
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if (SPEED) {
            velocidad = SPEED_FAST;
        } else {
            velocidad = SPEED_MID;
        }
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
    }

    public void incrementarBombas(int incremento) {
        BOMBS += incremento;
    }

    public void incrementarFlamas(int incremento) {
        FLAMES += incremento;
    }

    public void setBOMBPASS(boolean BOMBPASS) {
        this.BOMBPASS = BOMBPASS;
    }

    public int getBOMBS() {
        return BOMBS;
    }

    public boolean getMYSTERY() {
        return MYSTERY;
    }

    public int getFLAMES() {
        return FLAMES;
    }

    public boolean getDETONADOR() {
        return DETONADOR;
    }

    public boolean getFLAMEPASS() {
        return FLAMEPASS;
    }

    public boolean getBOMBPASS() {
        return BOMBPASS;
    }

    public void crearBomba() {
        if (ChoqueCentral("B")) {
            if (bombas.size() < BOMBS) {
                if (!ChoqueCentral("X")) {
                    dentroBomb = true;
                }
                Sonidos.getInstance().get(Sonidos.BOMB_PLANT).play();
                bombas.add(new Bomb(posicionMapa.x * imagen.getAnchoEscalado(), posicionMapa.y * imagen.getAltoEscalado(), this));
            }
        }
    }

    public CopyOnWriteArrayList<Bomb> getBombs() {
        return bombas;
    }

    public int bombasPuestas() {
        return bombas.size();
    }

    @Override
    public void estadoInicio(EscenaJuego jPanelJuego, long tiempoTranscurrido) {
        setEstadoActual(Estado.IZQUIERDA);
    }

    @Override
    public void estadoArriba(EscenaJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if (verificarMovimiento(jPanelJuego)) {
            actualizarAnimacion(tiempoTranscurrido);
        }
    }

    @Override
    public void estadoAbajo(EscenaJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if (verificarMovimiento(jPanelJuego)) {
            actualizarAnimacion(tiempoTranscurrido);
        }
    }

    @Override
    public void estadoDerecha(EscenaJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if (verificarMovimiento(jPanelJuego)) {
            actualizarAnimacion(tiempoTranscurrido);
        }
    }

    @Override
    public void estadoIzquierda(EscenaJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if (verificarMovimiento(jPanelJuego)) {
            actualizarAnimacion(tiempoTranscurrido);
        }
    }

    @Override
    public void estadoMuerte(EscenaJuego jPanelJuego, long tiempoTranscurrido) {
        if (actualizarAnimacion(tiempoTranscurrido)) {
            Sonidos.getInstance().detenerSonidos();
            setEstadoActual(Estado.ELIMINADO);
            Sonidos.getInstance().get(Sonidos.JUST_DIED).play();
        }
    }

    private void verificarTeclasAccion() {
        if (teclado.teclaPresionada(gamePad.get(Botones.A))) {
            crearBomba();
        }
        if (teclado.teclaPresionada(gamePad.get(Botones.B)) && DETONADOR) {
            detonarBomba();
        }
    }

    private boolean verificarMovimiento(EscenaJuego jPanelJuego) {
        if (entroALaPuerta) {
            return false;
        }
        boolean movimiento = true;
        if (teclado.teclaPresionada(get(Botones.ARRIBA))) {
            setEstadoActual(Estado.ARRIBA);
            Sonidos.getInstance().get(Sonidos.UP).play();
            movimientoArriba();
        } else if (teclado.teclaPresionada(get(Botones.ABAJO))) {
            Sonidos.getInstance().get(Sonidos.UP).stop();
            setEstadoActual(Estado.ABAJO);
            Sonidos.getInstance().get(Sonidos.DOWN).play();
            movimientoAbajo();
        } else {
            Sonidos.getInstance().detener(Sonidos.UP, Sonidos.DOWN);
            movimiento = false;
        }
        if (teclado.teclaPresionada(get(Botones.DERECHA))) {
            setEstadoActual(Estado.DERECHA);
            Sonidos.getInstance().get(Sonidos.LEFT).play();
            movimientoDerecha();
            movimiento = true;
        } else if (teclado.teclaPresionada(get(Botones.IZQUIERDA))) {
            Sonidos.getInstance().get(Sonidos.LEFT).stop();
            setEstadoActual(Estado.IZQUIERDA);
            Sonidos.getInstance().get(Sonidos.RIGHT).play();
            movimientoIzquierda();
            movimiento = true;
        } else {
            Sonidos.getInstance().detener(Sonidos.LEFT, Sonidos.RIGHT);
        }
        return movimiento;
    }

    public void reiniciar(int x, int y) {
        super.reiniciar();
        fijarCasilla(x, y);
        entroALaPuerta = false;
    }

    private void detonarBomba() {
        for (Bomb bomba : bombas) {
            if (bomba.getEstadoActual() == Estado.MUERTE) {
                continue;
            } else {
                bomba.detonar();
                return;
            }
        }
    }

    @Override
    public void borrar(Graphics g, BufferedImage imagen) {
        super.borrar(g, imagen);
        for (Bomb bomba : bombas) {
            bomba.borrar(g, imagen);
        }
    }

    public void setEntroALaPuerta(boolean b) {
        entroALaPuerta = true;
    }

    public boolean isEntroALaPuerta() {
        return entroALaPuerta;
    }

    @Override
    public void reiniciar() {
        super.reiniciar();
        entroALaPuerta = false;
        bombas.clear();
    }

}
