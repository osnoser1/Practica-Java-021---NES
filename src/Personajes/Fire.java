/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import motor.core.ControlAnimacion;
import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Utilidades.Juego.Control;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author hp
 */
public class Fire extends Personaje {
    
    private int espacioDerecha, espacioIzquierda, espacioArriba, espacioAbajo, espacio;
    private Imagen[] imagenes;
        
    public Fire(int x, int y, int espacios) {
        this.x = x;
        this.y = y;
        this.espacio = espacios;
        espacioIzquierda = espacio;
        espacioDerecha = espacio;
        espacioArriba = espacio;
        espacioAbajo = espacio;
        inicializar(Imagenes.FUEGO, new Point(x, y), null);
        determinarTamaño();
        crearSprites();
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, Control gamePad) {
        super.inicializar(new Imagen(imagen, 7, 4, posicion, (float)2.5), posicion);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, ControlAnimacion>(){{
            put(0, new ControlAnimacion("0,1,2,3", 4000 / 60));
        }};
        setEstadoActual(Estado.INICIO);
    }

    private void crearSprites() {
        short indice = 0;
        imagenes = new Imagen[1 + espacioArriba + espacioAbajo + espacioDerecha + espacioIzquierda];
        imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x, imagen.getPosicion().y), (float)2.5, 0);
        if(espacio == espacioArriba) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x, imagen.getPosicion().y - espacio * imagen.getAltoEscalado()), (float)2.5, 1);
            espacioArriba--;
        }
        for(short i = 1; i <= espacioArriba; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x, imagen.getPosicion().y - i * imagen.getAltoEscalado()), (float)2.5, 6);
        }
        if(espacio == espacioAbajo) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x, imagen.getPosicion().y + espacio * imagen.getAltoEscalado()), (float)2.5, 2);
            espacioAbajo--;
        }
        for(short i = 1; i <= espacioAbajo; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x, imagen.getPosicion().y + i * imagen.getAltoEscalado()), (float)2.5, 6);
        }
        if(espacio == espacioDerecha) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x + espacio * imagen.getAnchoEscalado(), imagen.getPosicion().y), (float)2.5, 3);
            espacioDerecha--;
        }
        for(short i = 1; i <= espacioDerecha; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x + i * imagen.getAnchoEscalado(), imagen.getPosicion().y), (float)2.5, 5);
        }
        if(espacio == espacioIzquierda) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x - espacio * imagen.getAnchoEscalado(), imagen.getPosicion().y), (float)2.5, 4);
            espacioIzquierda--;
        }
        for(short i = 1; i <= espacioIzquierda; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(imagen.getPosicion().x - i * imagen.getAnchoEscalado(), imagen.getPosicion().y), (float)2.5, 5);
        }
    }

    @Override
    public void pintar(Graphics g) {
        if(getEstadoActual() == Estado.ELIMINADO || !activo)
            return;
        for(Imagen sprite : imagenes) {
            if(sprite == null)
                return;
            sprite.actualizar(animaciones.get(0).getCuadroActual());
            sprite.pintar(g);
        }
    }
    
    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido)) {
            setEstadoActual(Estado.ELIMINADO);
        }
    }
    
    public int getEspacios() {
        return espacio;
    }

    private void determinarTamaño() {
        for(int i=1;i<=espacio;i++) {
            if(ChoqueArriba("A",i)||ChoqueArriba("L",i)||ChoqueArriba("X",i)||ChoqueArriba("Q",i)||ChoqueArriba("S",i)){
                espacioArriba=i-1;
                JPanelJuego.getInstance(null).borrarLadrillo(new Point(posicionMapa.x, posicionMapa.y - i));
                JPanelJuego.getInstance(null).borrarBombs(new Point(posicionMapa.x, posicionMapa.y - i));
                break;
            }else if(!ChoqueArriba("V",i)){
                JPanelJuego.getInstance(null).borrarEnemigo(new Point(posicionMapa.x, posicionMapa.y - i));
                
                if(!JPanelJuego.getInstance(null).primerJugador().getFLAMEPASS())
                   JPanelJuego.getInstance(null).borrarJugador(new Point(posicionMapa.x, posicionMapa.y - i));
            }
        }
        for(int i=1;i<=espacio;i++) {
            if(ChoqueAbajo("A",i)||ChoqueAbajo("L",i)||ChoqueAbajo("X",i)||ChoqueAbajo("Q",i)||ChoqueAbajo("S",i)){
                espacioAbajo=i-1;
                JPanelJuego.getInstance(null).borrarLadrillo(new Point(posicionMapa.x, posicionMapa.y + i));
                JPanelJuego.getInstance(null).borrarBombs(new Point(posicionMapa.x, posicionMapa.y + i));     
                break;
            }else if(!ChoqueAbajo("V",i)) {
                JPanelJuego.getInstance(null).borrarEnemigo(new Point(posicionMapa.x, posicionMapa.y + i));
                if(!JPanelJuego.getInstance(null).primerJugador().getFLAMEPASS())
                   JPanelJuego.getInstance(null).borrarJugador(new Point(posicionMapa.x, posicionMapa.y + i));
            }
        }
        for(int i=1;i<=espacio;i++) {
            if(ChoqueIzquierda("A",i)||ChoqueIzquierda("L",i)||ChoqueIzquierda("X",i)||ChoqueIzquierda("Q",i)||ChoqueIzquierda("S",i)){
                espacioIzquierda=i-1;
                JPanelJuego.getInstance(null).borrarLadrillo(new Point(posicionMapa.x - i, posicionMapa.y));
                JPanelJuego.getInstance(null).borrarBombs(new Point(posicionMapa.x - i, posicionMapa.y));
                break;
            }else if(!ChoqueIzquierda("V",i)) {
                JPanelJuego.getInstance(null).borrarEnemigo(new Point(posicionMapa.x - i, posicionMapa.y));
                if(!JPanelJuego.getInstance(null).primerJugador().getFLAMEPASS())
                   JPanelJuego.getInstance(null).borrarJugador(new Point(posicionMapa.x - i, posicionMapa.y));
            }
        }
        for(int i=1;i<=espacio;i++) {
            if(ChoqueDerecha("A",i)||ChoqueDerecha("L",i)||ChoqueDerecha("X",i)||ChoqueDerecha("Q",i)||ChoqueDerecha("S",i)){
                espacioDerecha=i-1;
                JPanelJuego.getInstance(null).borrarLadrillo(new Point(posicionMapa.x + i, posicionMapa.y));
                JPanelJuego.getInstance(null).borrarBombs(new Point(posicionMapa.x + i, posicionMapa.y));
                break;
            }else if(!ChoqueDerecha("V",i)) {
                JPanelJuego.getInstance(null).borrarEnemigo(new Point(posicionMapa.x + i, posicionMapa.y));
                if(!JPanelJuego.getInstance(null).primerJugador().getFLAMEPASS())
                   JPanelJuego.getInstance(null).borrarJugador(new Point(posicionMapa.x + i, posicionMapa.y));
            }
        }

    }

    @Override
    public void borrar(Graphics g, BufferedImage imagen) {
        for(Imagen sprite : imagenes) {
            if(sprite == null)
                return;
            sprite.borrar(g, imagen);
        }
        
    }
    
}