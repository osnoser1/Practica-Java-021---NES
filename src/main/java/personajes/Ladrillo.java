/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package personajes;

import motor.core.graphics.Imagen;
import dependencias.Imagenes;
import utilidades.juego.Interfaz;
import game.players.ladrillo.states.MuerteState;
import motor.core.graphics.Sprite;
import java.awt.Graphics2D;
import java.util.HashMap;
import motor.core.graphics.AnimationWrapper;
import motor.core.graphics.spritedefaultstates.EmptyState;
import motor.core.map.Mapa;

/**
 *
 * @author hp
 */
public class Ladrillo extends Sprite {

    private final int tipo;
    private boolean especial;
    private LadrilloEspecial ladrilloespecial;

    public Ladrillo(final int x, final int y, final int tipo) {
        super(new Imagen(Imagenes.BLOQUE, 6, 6, (float) 2.5), x, y);
        id = "L";
        this.tipo = tipo;
        if (tipo != -1)
            especial = true;
        inicializar();
    }

    public final void inicializar() {
        super.animaciones = new HashMap<>() {
            {
                put(EmptyState.class, new AnimationWrapper(0, "0", 4000 / 60));
                put(MuerteState.class, new AnimationWrapper(5, "0,1,2,3,4,5", 4000 / 60));
            }
        };
        setEstadoActual(EmptyState::new);
    }

    public LadrilloEspecial getLadrilloEspecial() {
        return ladrilloespecial;
    }

    @Override
    public void actualizar(Interfaz interfaz, long tiempoTranscurrido) {
        super.actualizar(interfaz, tiempoTranscurrido);
        if (ladrilloespecial != null)
            ladrilloespecial.actualizar(interfaz, tiempoTranscurrido);
    }

    @Override
    public void pintar(final Graphics2D g) {
        super.pintar(g);
        if (ladrilloespecial != null)
            ladrilloespecial.pintar(g);
    }

    public boolean isEspecial() {
        return especial && !ladrilloespecial.isEstadoEliminado();
    }

    public void activateLadrilloEspecial() {
        if(!especial) {
            return;
        }
        var mapa = Mapa.getInstance();
        ladrilloespecial = new LadrilloEspecial(x, y, tipo);
        mapa.remover(this);
        id = "Q";
        mapa.agregar(ladrilloespecial);
    }

    public void explotar() {
        setEstadoActual(MuerteState::new);
    }

}
