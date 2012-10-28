/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import UtilidadesJuego.GamePad;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author hp
 */
public class Ladrillo extends Personaje {
    
    int tipo;
    private boolean especial;
    public LadrilloEspecial ladrilloespecial;
    
    public Ladrillo(int x1, int y1, int tipo1) {
        super();
        Speed = 0;
        this.tipo = tipo1;
        if(tipo != -1)
            especial = true;
        this.inicializar(Imagenes.BLOQUE, new Point(x1, y1), null);
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        super.inicializar(posicion);
        activo = true;
        super.imagen = new Imagen(imagen, 6, 6, posicion, (float)2.5);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, Animation>(){{
            put(Estado.INICIO.ordinal(), new Animation("0", 4000 / 60));
            put(Estado.MUERTE.ordinal(), new Animation("0,1,2,3,4,5", 4000 / 60));
        }};
    }
    
    public LadrilloEspecial getLadrilloEspecial(){
        return ladrilloespecial;
    }

    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
//        setEstadoActual(Estado.MUERTE);
    }
    
    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido)) {
            setEstadoActual(Estado.ELIMINADO);
            if(isEspecial()) {
               ladrilloespecial = new LadrilloEspecial(x, y, tipo);
           }
        }
    }

    public boolean isEspecial() {
        return especial;
    }

    public void setEspecial(boolean especial) {
        this.especial = especial;
    }
    
}