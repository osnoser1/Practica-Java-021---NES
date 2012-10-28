/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import UtilidadesJuego.GamePad;
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
        
    public Fire(final int x,final int y,int espacios) {
        super();
        this.x = x;
        this.y = y;
        this.espacio = espacios;
        espacioIzquierda = espacio;
        espacioDerecha = espacio;
        espacioArriba = espacio;
        espacioAbajo = espacio;
        determinarTamaño();
        inicializar(Imagenes.FUEGO, new Point(x, y), null);
        crearSprites();
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        super.inicializar(posicion);
        activo = true;
        super.imagen = new Imagen(imagen, 7, 4, posicion, (float)2.5);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, Animation>(){{
            put(0, new Animation("0,1,2,3", 4000 / 60));
        }};
    }

    private void crearSprites() {
        short indice = 0;
        imagenes = new Imagen[1 + espacioArriba + espacioAbajo + espacioDerecha + espacioIzquierda];
        imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX(), getCenterY()), (float)2.5, 0);
        if(espacio == espacioArriba) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX(), getCenterY() - espacio * (int)imagen.getAltoEscalado()), (float)2.5, 1);
            espacioArriba--;
        }
        for(short i = 1; i <= espacioArriba; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX(), getCenterY() - i * (int)imagen.getAltoEscalado()), (float)2.5, 6);
        }
        if(espacio == espacioAbajo) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX(), getCenterY() + espacio * (int)imagen.getAltoEscalado()), (float)2.5, 2);
            espacioAbajo--;
        }
        for(short i = 1; i <= espacioAbajo; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX(), getCenterY() + i * (int)imagen.getAltoEscalado()), (float)2.5, 6);
        }
        if(espacio == espacioDerecha) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX() + espacio * (int)imagen.getAnchoEscalado(), getCenterY()), (float)2.5, 3);
            espacioDerecha--;
        }
        for(short i = 1; i <= espacioDerecha; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX() + i * (int)imagen.getAnchoEscalado(), getCenterY()), (float)2.5, 5);
        }
        if(espacio == espacioIzquierda) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX() - espacio * (int)imagen.getAnchoEscalado(), getCenterY()), (float)2.5, 4);
            espacioIzquierda--;
        }
        for(short i = 1; i <= espacioIzquierda; i++) {
            imagenes[indice++] = new Imagen(imagen.getImagen(), 7, 4, new Point(getCenterX() - i * (int)imagen.getAnchoEscalado(), getCenterY()), (float)2.5, 5);
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

    private void determinarTamaño(){
        for(int i=1;i<=espacio;i++) 
             if(ChoqueArriba("A",i)||ChoqueArriba("L",i)||ChoqueArriba("X",i)||ChoqueArriba("Q",i)||ChoqueArriba("S",i)){
                 espacioArriba=i-1;
                 JPanelJuego.getInstance().borrarLadrillo(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 JPanelJuego.getInstance().borrarBombs(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 break;
             }else if(!ChoqueArriba("V",i)){
                 JPanelJuego.getInstance().borrarEnemigo(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 
                 if(!JPanelJuego.getInstance().primerJugador().getFLAMEPASS())
                    JPanelJuego.getInstance().borrarJugador(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 
             }
        for(int i=1;i<=espacio;i++) 
             if(ChoqueAbajo("A",i)||ChoqueAbajo("L",i)||ChoqueAbajo("X",i)||ChoqueAbajo("Q",i)||ChoqueAbajo("S",i)){
                 espacioAbajo=i-1;
                 JPanelJuego.getInstance().borrarLadrillo(getCenterX(), getCenterY()+JPanelJuego.gety()*i);
                 JPanelJuego.getInstance().borrarBombs(getCenterX(), getCenterY()+JPanelJuego.gety()*i);     
                 break;
             }else if(!ChoqueAbajo("V",i)){
                 JPanelJuego.getInstance().borrarEnemigo(getCenterX(), getCenterY()+JPanelJuego.gety()*i);
                 
                 if(!JPanelJuego.getInstance().primerJugador().getFLAMEPASS())
                    JPanelJuego.getInstance().borrarJugador(getCenterX(), getCenterY()+JPanelJuego.gety()*i);
                 
             }
        for(int i=1;i<=espacio;i++) 
             if(ChoqueIzquierda("A",i)||ChoqueIzquierda("L",i)||ChoqueIzquierda("X",i)||ChoqueIzquierda("Q",i)||ChoqueIzquierda("S",i)){
                 espacioIzquierda=i-1;
                 JPanelJuego.getInstance().borrarLadrillo(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 JPanelJuego.getInstance().borrarBombs(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 break;
             }else if(!ChoqueIzquierda("V",i)){
                 JPanelJuego.getInstance().borrarEnemigo(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 
                 if(!JPanelJuego.getInstance().primerJugador().getFLAMEPASS())
                    JPanelJuego.getInstance().borrarJugador(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 
             }
        for(int i=0;i<=espacio;i++) 
             if(ChoqueDerecha("A",i)||ChoqueDerecha("L",i)||ChoqueDerecha("X",i)&&i!=0||ChoqueDerecha("Q",i)&&i!=0||ChoqueDerecha("S",i)&&i!=0){
                 if(i!=0)espacioDerecha=i-1;
                 JPanelJuego.getInstance().borrarLadrillo(getCenterX()+JPanelJuego.getx()*i, getCenterY());
                 if(i!=0)
                     JPanelJuego.getInstance().borrarBombs(getCenterX()+JPanelJuego.getx()*i, getCenterY());
                 break;
             }else if(!ChoqueDerecha("V",i)){
                 JPanelJuego.getInstance().borrarEnemigo(getCenterX()+JPanelJuego.getx()*i, getCenterY());
                 if(!JPanelJuego.getInstance().primerJugador().getFLAMEPASS())
                    JPanelJuego.getInstance().borrarJugador(getCenterX()+JPanelJuego.getx()*i, getCenterY());
                 
             }

    }
    
}