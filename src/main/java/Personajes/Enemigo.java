package Personajes;

import game.players.bomberman.states.*;
import motor.core.ControlAnimacion;
import motor.core.graphics.Imagen;
import gui.JPanelJuego;
import motor.core.input.GamePad;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.HashMap;
import static juego.constantes.Estado.*;
import motor.core.graphics.AnimationWrapper;
import motor.core.graphics.SpriteState;

public class Enemigo extends Personaje {

    protected int puntaje;

    public Enemigo(final Image image, final int x, final int y, final GamePad gamePad) {
        super(new Imagen(image, 6, 5, (float) 2.5), x, y);
        this.gamePad = gamePad;
        inicializar();
    }

    public final void inicializar() {
        animaciones = new HashMap<Class<? extends SpriteState>, AnimationWrapper>() {
            {
                put(InicioState.class, new AnimationWrapper(0, "0", 4000 / 60));
                put(ArribaState.class, new AnimationWrapper(1, "0,1,2", 4000 / 60));
                put(AbajoState.class, new AnimationWrapper(2, "0,1,2", 4000 / 60));
                put(DerechaState.class, new AnimationWrapper(3, "0,1,2", 4000 / 60));
                put(IzquierdaState.class, new AnimationWrapper(4, "0,1,2", 4000 / 60));
                put(MuerteState.class, new AnimationWrapper(5, "0,1,2,3,4", 500));
            }
        };
        setEstadoActual(IzquierdaState::new);
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
        if (estadoActual instanceof MuerteState) {
            g.setColor(Color.white);
            g.drawString("" + puntaje, getX() + imagen.getAncho() / 5, getCentro().y);
        }
    }

    public void muerte(JPanelJuego jPanelJuego) {
        setEstadoActual(MuerteState::new);
        jPanelJuego.getMapa().remover(this);
        detenerInteligencia();
    }

}
