/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.ControlAnimacion;
import motor.core.graphics.Imagen;
import Dependencias.Imagenes;
import gui.JPanelJuego;
import motor.core.graphics.Sprite;
import java.awt.Graphics2D;
import java.util.HashMap;
import static juego.constantes.Estado.*;
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
        super.animaciones = new HashMap<Integer, ControlAnimacion>() {
            {
                put(INICIO.ordinal(), new ControlAnimacion("0", 4000 / 60));
                put(MUERTE.ordinal(), new ControlAnimacion("0,1,2,3,4,5", 4000 / 60));
            }
        };
        setEstadoActual(INICIO.val());
    }

    public LadrilloEspecial getLadrilloEspecial() {
        return ladrilloespecial;
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        final int actual = getEstadoActual();
        if (INICIO.val() == actual)
            estadoInicio(jPanelJuego, tiempoTranscurrido);
        else if (MUERTE.val() == actual)
            estadoMuerte(jPanelJuego, tiempoTranscurrido);
        if (ladrilloespecial != null)
            ladrilloespecial.actualizar(jPanelJuego, tiempoTranscurrido);
    }

    @Override
    public void pintar(final Graphics2D g) {
        super.pintar(g);
        if (ladrilloespecial != null)
            ladrilloespecial.pintar(g);
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if (actualizarAnimacion(tiempoTranscurrido)) {
            setEstadoActual(ELIMINADO.val());
            if (especial) {
                ladrilloespecial = new LadrilloEspecial(x, y, tipo);
                // Excepción por referencia circular si no se crea la instancia mapa
                Mapa mapa = jPanelJuego.getMapa();
                mapa.remover(this);
                id = "Q";
                mapa.agregar(ladrilloespecial);
            }
        }
    }

    public boolean isEspecial() {
        return especial && !ladrilloespecial.isEstadoEliminado();
    }

}
