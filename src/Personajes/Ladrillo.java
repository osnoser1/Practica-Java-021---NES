/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.ControlAnimacion;
import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import motor.core.Sprite;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author hp
 */
public class Ladrillo extends Sprite {
    
    private int tipo;
    private boolean especial;
    public LadrilloEspecial ladrilloespecial;
    
    public Ladrillo(int x, int y, int tipo) {
        identificacion = "L";
        this.tipo = tipo;
        if(tipo != -1)
            especial = true;
        inicializar(Imagenes.BLOQUE, new Point(x, y));
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion) {
        super.inicializar(new Imagen(imagen, 6, 6, posicion, (float)2.5), posicion);
        super.animaciones = new HashMap<Integer, ControlAnimacion>(){{
            put(Sprite.Estado.INICIO.ordinal(), new ControlAnimacion("0", 4000 / 60));
            put(Sprite.Estado.MUERTE.ordinal(), new ControlAnimacion("0,1,2,3,4,5", 4000 / 60));
        }};
        setEstadoActual(Sprite.Estado.INICIO);
    }
    
    public LadrilloEspecial getLadrilloEspecial(){
        return ladrilloespecial;
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        switch(getEstadoActual()){
            case INICIO:
                estadoInicio(jPanelJuego, tiempoTranscurrido);
                break;
            case MUERTE:
                estadoMuerte(jPanelJuego, tiempoTranscurrido);
                break;
        }
        if(ladrilloespecial != null) 
            ladrilloespecial.actualizar();
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido)) {
            setEstadoActual(Sprite.Estado.ELIMINADO);
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