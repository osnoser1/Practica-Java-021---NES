
package Personajes;

import GUI.JPanelJuego;
import java.awt.Color;
import java.awt.Graphics2D;

public class Enemigo extends Personaje {

    protected int Point;
    
    public Enemigo(Animation Izquierda,Animation Derecha,Animation Arriba,Animation Abajo,Animation Muerte){
        super(Izquierda,Derecha,Arriba,Abajo,Muerte);
        
    }

    public void setPoint(int Point) {
        this.Point = Point;    
    }

    public int getPoint() {
        return Point;
    }
     public void DibujarEnemigo(Graphics2D g){
        if(Muerto){
//            g.setFont(Fuentes);
            g.setColor(Color.white);
            g.drawString(""+Point, x+JPanelJuego.getx()/5, getCenterY());
        }
        Dibujar(g); 
     }

    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void estadoArriba(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void estadoAbajo(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void estadoDerecha(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void estadoIzquierda(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   
}
