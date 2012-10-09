
package Personajes;

import GUI.JPanelInformacion;
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
    
   
}
