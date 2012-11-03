/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Utilidades.Juego.GamePad;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author hp
 */
public class Ladrillo extends Personaje {
    
    private int tipo;
    private boolean especial;
    public LadrilloEspecial ladrilloespecial;
    
    public Ladrillo(int x, int y, int tipo) {
        this.tipo = tipo;
        if(tipo != -1)
            especial = true;
        inicializar(Imagenes.BLOQUE, new Point(x, y), null);
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        super.inicializar(new Imagen(imagen, 6, 6, posicion, (float)2.5), posicion);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, Animation>(){{
            put(Estado.INICIO.ordinal(), new Animation("0", 4000 / 60));
            put(Estado.MUERTE.ordinal(), new Animation("0,1,2,3,4,5", 4000 / 60));
        }};
        setEstadoActual(Estado.INICIO);
    }
    
    public LadrilloEspecial getLadrilloEspecial(){
        return ladrilloespecial;
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        if(ladrilloespecial != null) 
            ladrilloespecial.actualizar();
    }
    
    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) { }
    
    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido)) {
            setEstadoActual(Estado.ELIMINADO);
            if(especial) {
               ladrilloespecial = new LadrilloEspecial(getCentro(), tipo);
           }
        }
    }

    public boolean isEspecial() {
        return especial && !ladrilloespecial.isEstadoEliminado();
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }
    
}