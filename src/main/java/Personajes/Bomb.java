/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Sonido;
import motor.core.graphics.Imagen;
import Dependencias.Imagenes;
import gui.JPanelJuego;
import Dependencias.Sonidos;
import Utilidades.Juego.Interfaz;
import game.players.bomb.states.InicioState;
import game.players.bomb.states.MuerteState;
import java.awt.Graphics2D;
import java.util.HashMap;
import javax.swing.Timer;
import motor.core.graphics.AnimationWrapper;
import motor.core.graphics.SpriteState;
import motor.core.graphics.spritedefaultstates.NullState;

public class Bomb extends Personaje {

    private boolean detonar;
    private Fire fire;
    private Timer timer;
    private final Bomberman bomberman;

    public Bomb(final int x, final int y, final Bomberman jugador) {
        super(new Imagen(Imagenes.BOMBA, 1, 3, (float) 2.5), x, y);
        bomberman = jugador;
        id = "X";
        inicializar();
        timer = jugador.getDETONADOR() ? null : new Timer(3200, e -> {
            detonar = true;
            timer.stop();
        });
        if (timer != null)
            timer.start();
    }

    public final void inicializar() {
        super.animaciones = new HashMap<Class<? extends SpriteState>, AnimationWrapper>() {
            {
                put(InicioState.class, new AnimationWrapper(0, "0,1,2", 400));
            }
        };
        setEstadoActual(InicioState::new);
    }

    public void detonar(final Interfaz interfaz) {
        if(!isActivo()) {
            return;
        }
        setActivo(false);
        setEstadoActual(MuerteState::new);
        fire = new Fire(x, y, bomberman.getFLAMES(), (JPanelJuego) interfaz);
        Sonido s = Sonidos.getInstance().getNewSonido(Sonidos.EXPLOSION_1);
        if (s != null)
            s.play();
    }

    @Override
    public void actualizar(Interfaz interfaz, long tiempoTranscurrido) {
        super.actualizar(interfaz, tiempoTranscurrido);
        if (fire != null)
            fire.actualizar(interfaz, tiempoTranscurrido);
    }

    @Override
    public void pintar(Graphics2D g) {
        super.pintar(g);
        if (fire != null)
            fire.pintar(g);
    }
    
    public boolean isExplosionEnded() {
        return fire != null && fire.getEstadoActual() instanceof NullState;
    }
    
    public boolean isTimeOver() {
        return detonar;
    }

    boolean hasDetonated() {
        return !isActivo();
    }
    
}
