/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.graphics.Imagen;
import Dependencias.Imagenes;
import gui.JPanelJuego;
import Dependencias.Sonidos;
import Utilidades.Juego.Interfaz;
import game.core.input.PlayerOneKeyboardController;
import game.players.bomberman.states.*;
import motor.core.input.GamePad;
import motor.core.input.GamePad.Botones;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import motor.core.graphics.AnimationWrapper;
import motor.core.graphics.SpriteState;
import motor.core.graphics.spritedefaultstates.NullState;

public class Bomberman extends Personaje {

    private boolean SPEED, DETONADOR, FLAMEPASS, MYSTERY;
    private int FLAMES, BOMBS;
    private static CopyOnWriteArrayList<Bomb> bombas;
    private boolean entroALaPuerta;

    public Bomberman(final int x, final int y) {
        super(new Imagen(Imagenes.BOMBERMAN, 6, 6, (float) 2.5), x, y);
        gamePad = new GamePad();
        padController = PlayerOneKeyboardController.getInstance();
        bombas = new CopyOnWriteArrayList<>();
        velocidad = SPEED_MID;
        BOMBS = 1;
        FLAMES = 1;
        SPEED = false;
        wallpass = false;
        DETONADOR = true;
        BOMBPASS = false;
        FLAMEPASS = false;
        MYSTERY = false;
        id = "B";
        inicializar();
    }

    public final void inicializar() {
        super.animaciones = new HashMap<Class<? extends SpriteState>, AnimationWrapper>() {
            {
                put(InicioState.class, new AnimationWrapper(0, "0", 4000 / 60));
                put(ArribaState.class, new AnimationWrapper(1, "2,1,0,1", 4000 / 60));
                put(AbajoState.class, new AnimationWrapper(2, "2,1,0,1", 4000 / 60));
                put(DerechaState.class, new AnimationWrapper(3, "2,1,0,1", 4000 / 60));
                put(IzquierdaState.class, new AnimationWrapper(4, "2,1,0,1", 4000 / 60));
                put(MuerteState.class, new AnimationWrapper(5, "0,1,2,3,4", 300));
            }
        };
        setEstadoActual(IzquierdaState::new);
    }

    @Override
    public void actualizar(final Interfaz interfaz, final long tiempoTranscurrido) {
        JPanelJuego jPanelJuego = (JPanelJuego) interfaz;
        padController.update(gamePad);
        verificarTeclasAccion(jPanelJuego);
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        comprobarMuerte(jPanelJuego);
        bombas.stream().map((bomba) -> {
            bomba.actualizar(jPanelJuego, tiempoTranscurrido);
            return bomba;
        }).filter((bomba) -> (bomba.getEstadoActual() instanceof NullState)).map((bomba) -> {
            jPanelJuego.getMapa().remover(bomba);
            return bomba;
        }).forEachOrdered((bomba) -> {
            bombas.remove(bomba);
        });
    }
    
    public void morir() {
        Sonidos.getInstance().detener(Sonidos.UP, Sonidos.DOWN, Sonidos.LEFT, Sonidos.RIGHT);
        Sonidos.getInstance().play(Sonidos.DEATH);
        setEstadoActual(MuerteState::new);
    }
    
    private void comprobarMuerte(JPanelJuego jPanelJuego) {
        if(estadoActual instanceof MuerteState || estadoActual instanceof NullState 
                || !jPanelJuego.getMapa().contiene(this, Enemigo.class))
            return;
        morir();
    }
    
    public void setDETONATOR(boolean DETONATOR) {
        this.DETONADOR = DETONATOR;
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if (SPEED)
            velocidad = SPEED_FAST;
        else
            velocidad = SPEED_MID;
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
    }

    public void incrementarBombas(final int incremento) {
        BOMBS += incremento;
    }

    public void incrementarFlamas(final int incremento) {
        FLAMES += incremento;
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

    public void crearBomba(final JPanelJuego jPanelJuego) {
        if (choqueCentral(Bomb.class)) {
            dentroBomb = true;
            return;
        }
        if (!choqueCentral(Ladrillo.class, LadrilloEspecial.class) && bombas.size() < BOMBS) {
            Sonidos.getInstance().play(Sonidos.BOMB_PLANT);
            final Bomb b = new Bomb(getCentro().x / imagen.getAncho() * imagen.getAncho(),
                    getCentro().y / imagen.getAlto() * imagen.getAlto(), this);
            jPanelJuego.getMapa().agregar(b);
            bombas.add(b);
            dentroBomb = true;
        }
    }

    public CopyOnWriteArrayList<Bomb> getBombs() {
        return bombas;
    }
    
    private void verificarTeclasAccion(final JPanelJuego jPanelJuego) {
        if(estadoActual instanceof MuerteState || estadoActual instanceof NullState)
            return;
        if (gamePad.isPress(Botones.A))
            crearBomba(jPanelJuego);
        if (gamePad.isPress(Botones.B) && DETONADOR)
            detonarBomba(jPanelJuego);
    }

    public void reiniciar(int x, int y) {
        setEstadoActual(InicioState::new);
        fijarCasilla(x, y);
        bombas.clear();
        imagen.setActive(true);
        entroALaPuerta = false;
    }

    private void detonarBomba(final Interfaz interfaz) {
        bombas.stream().filter((bomba) -> (!bomba.hasDetonated())).forEachOrdered((bomba) -> {
            bomba.detonar(interfaz);
        });
    }

    public void setEntroALaPuerta(boolean b) {
        entroALaPuerta = true;
    }

    public boolean isEntroALaPuerta() {
        return entroALaPuerta;
    }

}
