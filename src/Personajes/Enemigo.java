
package Personajes;

import Dependencias.Imagen;
import GUI.JPanelJuego;
import Utilidades.Juego.GamePad;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Enemigo extends Personaje {

    protected int Point;
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        super.inicializar(posicion);
        activo = true;
        super.imagen = new Imagen(imagen, 6, 5, posicion, (float)2.5);
        super.gamePad = gamePad;
        animaciones = new HashMap<Integer, Animation>(){{
            put(Estado.INICIO.ordinal(), new Animation("0", 4000 / 60));
            put(Estado.ARRIBA.ordinal(), new Animation("0,1,2", 4000 / 60));
            put(Estado.ABAJO.ordinal(), new Animation("0,1,2", 4000 / 60));
            put(Estado.DERECHA.ordinal(), new Animation("0,1,2", 4000 / 60));
            put(Estado.IZQUIERDA.ordinal(), new Animation("0,1,2", 4000 / 60));
            put(Estado.MUERTE.ordinal(), new Animation("0,1,2,3,4", 500));
        }};
        setEstadoActual(Estado.IZQUIERDA);
    }
    
    public Enemigo(){
        super();
    }

    public void setPoint(int Point) {
        this.Point = Point;    
    }

    public int getPoint() {
        return Point;
    }

    @Override
    public void pintar(Graphics g) {
        super.pintar(g);
        if(getEstadoActual() == Estado.MUERTE) {
            g.setColor(Color.white);
            g.drawString("" + Point, x + JPanelJuego.getx() / 5, getCenterY());
        }
    }

    public void muerte(){
        setEstadoActual(Estado.MUERTE);
        detenerInteligencia();
    }
    
    @Override
    public void estadoArriba(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoAbajo(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoDerecha(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoIzquierda(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido))
            setEstadoActual(Estado.ELIMINADO);
    }
   
}