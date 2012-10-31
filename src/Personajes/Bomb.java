/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Sonidos.Sonidos;
import Utilidades.Juego.GamePad;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public class Bomb extends Personaje {
    
    private boolean detonar;
    private Fire fire;
    
    public Bomb(final int x,final int y) {
        super();
        timer = new Timer(3200, new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!JPanelJuego.getInstance().primerJugador().getDETONATOR())
                    detonar = true;
            }            
        });
        timer.start();
        this.inicializar(Imagenes.BOMBA, new Point(x, y), null);
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        super.inicializar(posicion);
        activo = true;
        super.imagen = new Imagen(imagen, 1, 3, posicion, (float)2.5);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, Animation>(){{
            put(Estado.INICIO.ordinal(), new Animation("0,1,2", 400));
        }};
    }
    
    public Fire getFire() {
        return fire;
    }
    
    public void detonar(){
        setActivo(false);
        setEstadoActual(Estado.MUERTE);
        fire = new Fire(x, y, JPanelJuego.getInstance().primerJugador().getFLAMES());
        Sonidos.getInstance().getNewSonido(Sonidos.EXPLOSION_1).play();
    }


    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        if(fire != null)
            fire.actualizar(jPanelJuego, tiempoTranscurrido);
    }

    @Override
    public void pintar(Graphics g) {
        super.pintar(g);
        if(fire != null)
            fire.pintar(g);
    }
    
    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        actualizarAnimacion(tiempoTranscurrido);
        if(detonar)
            detonar();
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(fire.getEstadoActual() == Estado.ELIMINADO) 
            setEstadoActual(Estado.ELIMINADO);
    }
}
