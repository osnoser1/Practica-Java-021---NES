/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Mapa;
import GUI.JPanelJuego;

/**
 *
 * @author hp
 */

public class Sprite {
    
    protected boolean Wallpass,DentroBomb;
    protected int x,y,varx=3,vary=3,Speed,tx=0,ty=0;

    protected String identificacion;

    public Sprite() { }

    public void updateX(){
        tx++;
//         if(animation.getCont()%2==1)
        if(!ChoqueCentral("X"))
            DentroBomb=false;
        if(AvanzarX()){
            x += Speed ;
//            if(JPanelJuego.getInstance().primerJugador().getX()>=widthjPaneljuego/4&&JPanelJuego.getInstance().primerJugador().getX()<=3*widthjPaneljuego/4)
        }
    }
    

    public void setDentroBomb(boolean DentroBomb) {
        this.DentroBomb = DentroBomb;
    }
    public void updateY(){
        ty++;
        if(!ChoqueCentral("X"))
            DentroBomb=false;
//        if(animation.getCont()%2==1)
        if(AvanzarY())
            y += Speed ;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }

    public int getX() {
        return x;
    }
    
    public int getCenterX() {
        return x + JPanelJuego.getx() / 2;
    }
    
    public int getCenterY() {
        return y + JPanelJuego.gety() / 2;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return Speed;
    }
    
    public void setSpeed(int velocityX) {
        this.Speed = velocityX;
    }

    public boolean ChoqueDerecha(String a,int n){
        return ((Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y+2*vary),JPanelJuego.getPosicionX(x+varx)+n)==a)||
                (Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-2*vary),JPanelJuego.getPosicionX(x+varx)+n)==a));
    }
    
    public boolean ChoqueIzquierda(String a,int n){
        return ((Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y+2*vary),JPanelJuego.getPosicionX(x+JPanelJuego.getx()-varx)-n)==a)||
                (Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-2*vary),JPanelJuego.getPosicionX(x+JPanelJuego.getx()-varx)-n)==a));
    }
    
    public boolean ChoqueArriba(String a,int n){
        return (
                (Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-vary)-n,JPanelJuego.getPosicionX(x+2*varx))==a  )||
                (Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-vary)-n,JPanelJuego.getPosicionX(x+JPanelJuego.getx()-2*varx))==a  ));
    }
    
    public boolean ChoqueAbajo(String a,int n){
        return ((Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y)+n,JPanelJuego.getPosicionX(x+2*varx))==a  )||
                (Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(y)+n,JPanelJuego.getPosicionX(x+JPanelJuego.getx()-2*varx))==a  ));
    }

    public boolean ChoqueCentral(String a){
       return a.equals(Mapa.getInstance().getObjeto(JPanelJuego.getPosicionY(getCenterY()),JPanelJuego.getPosicionX(getCenterX())));
    }
    
    public void setWallpass(boolean Wallpass) {
        this.Wallpass = Wallpass;
    }
    
    public boolean getWallpass() {
        return Wallpass;
    }

    public boolean AvanzarX() {
       return (
                (!ChoqueDerecha("A",1)&&Speed>0||!ChoqueIzquierda("A",1)&&Speed<0)&&
                ((!ChoqueDerecha("L",1)&&Speed>0||!ChoqueIzquierda("L",1)&&Speed<0)||Wallpass)&&
                (
                  (!ChoqueDerecha("X",1)&&Speed>0||!ChoqueIzquierda("X",1)&&Speed<0)&&!"B".equals(identificacion)||
                  (!ChoqueDerecha("X",1)&&Speed>0||!ChoqueIzquierda("X",1)&&Speed<0||JPanelJuego.getInstance().primerJugador().getBOMBPASS()||DentroBomb)&&"B".equals(identificacion)
                )
               );
    }

    public boolean AvanzarY() {
       return (
                (!ChoqueArriba("A",1)&&Speed<0||!ChoqueAbajo("A",1)&&Speed>0)&&
                (!ChoqueArriba("L",1)&&Speed<0||!ChoqueAbajo("L",1)&&Speed>0||Wallpass)&&
                (
                  (!ChoqueArriba("X",1)&&Speed<0||!ChoqueAbajo("X",1)&&Speed>0)&&!"B".equals(identificacion)||
                  (!ChoqueArriba("X",1)&&Speed<0||!ChoqueAbajo("X",1)&&Speed>0||JPanelJuego.getInstance().primerJugador().getBOMBPASS()||DentroBomb)&&"B".equals(identificacion)
                )
               );
    }
    
}
