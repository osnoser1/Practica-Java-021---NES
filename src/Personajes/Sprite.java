/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import GUI.JPanelJuego;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author hp
 */

public class Sprite {
    
    
    protected ArrayList<BufferedImage> Sprite;
    protected Animation animation;
    protected boolean Wallpass,DentroBomb;
    protected int x,y,varx=3,vary=3,Speed,tx=0,ty=0;

    protected String Identificacion;

    public Sprite( Animation anim ) {
        this.animation = anim;
    }
    public void update(){
        if(animation!=null)
            animation.MovimientoSprite();
    }
    public void updateX(){
        tx++;
        // if(animation.getCont()%2==1)
        if(!ChoqueCentral("X"))
            DentroBomb=false;
        if(AvanzarX()){
            x += Speed ;
            //if(JPanelJuego.getJugador().getX()>=widthjPaneljuego/4&&JPanelJuego.getJugador().getX()<=3*widthjPaneljuego/4)
                
        }
        if(tx%(30/Speed)==0&&animation!=null&&Speed!=0)
            animation.MovimientoSprite();
    }
    

    public void setDentroBomb(boolean DentroBomb) {
        this.DentroBomb = DentroBomb;
    }
    public void updateY(){
        ty++;
        if(!ChoqueCentral("X"))
            DentroBomb=false;
        //if(animation.getCont()%2==1)
        if(AvanzarY())
            y += Speed ;
        
        if(ty%(30/Speed)==0&&animation!=null&&Speed!=0)
            animation.MovimientoSprite();
    }
    
    public String getIdentificacion() {
        return Identificacion;
    }

    public int getX() {
        return x;
    }
    public int getCenterX() {
        return x+JPanelJuego.getx()/2;
    }
    public int getCenterY() {
        return y+JPanelJuego.gety()/2;
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
 

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }


    public Animation getAnimation() {
        return animation;
    }
    

    public void Dibujar(Graphics2D g){
        if(animation!=null)
            g.drawImage(animation.getImageSprite(),x,y,JPanelJuego.getx(),JPanelJuego.gety(),null);
    }
    
    public void Dibujar(Graphics2D g,int x1,int y1,int width,int height){
        if(animation!=null) 
            g.drawImage(animation.getImageSprite(),x1,y1,width,height,null);
    }
    
    public void Dibujar(Graphics2D g,int x1,int y1){
        if(animation!=null)
            g.drawImage(animation.getImageSprite(),x1,y1,JPanelJuego.getx(),JPanelJuego.gety(),null);
    }
    
    public ArrayList<BufferedImage> getSprite() {
        return Sprite;
    }

    public void setSprite(ArrayList<BufferedImage> Sprite) {
        this.Sprite = Sprite;
    }
   
    public Object clone() {
        return new Sprite( animation );
    }
    public boolean ChoqueDerecha(String a,int n){
        return ((JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y+2*vary),JPanelJuego.getPosicionX(x+varx)+n)==a)||
                (JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-2*vary),JPanelJuego.getPosicionX(x+varx)+n)==a));

    }
    public boolean ChoqueIzquierda(String a,int n){
        return ((JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y+2*vary),JPanelJuego.getPosicionX(x+JPanelJuego.getx()-varx)-n)==a)||
                (JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-2*vary),JPanelJuego.getPosicionX(x+JPanelJuego.getx()-varx)-n)==a));
    }
    public boolean ChoqueArriba(String a,int n){
        return (
                (JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-vary)-n,JPanelJuego.getPosicionX(x+2*varx))==a  )||
                (JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y+JPanelJuego.gety()-vary)-n,JPanelJuego.getPosicionX(x+JPanelJuego.getx()-2*varx))==a  ));
    }
    public boolean ChoqueAbajo(String a,int n){
        return ((JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y)+n,JPanelJuego.getPosicionX(x+2*varx))==a  )||
                (JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(y)+n,JPanelJuego.getPosicionX(x+JPanelJuego.getx()-2*varx))==a  ));
    }

       
    public boolean ChoqueCentral(String a){
       return ((JPanelJuego.getMapa().getObjetoMapa(JPanelJuego.getPosicionY(getCenterY()),JPanelJuego.getPosicionX(getCenterX()))==a  ));
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
                  (!ChoqueDerecha("X",1)&&Speed>0||!ChoqueIzquierda("X",1)&&Speed<0)&&Identificacion!="B"||
                  (!ChoqueDerecha("X",1)&&Speed>0||!ChoqueIzquierda("X",1)&&Speed<0||JPanelJuego.getJugador().getBOMBPASS()||DentroBomb)&&Identificacion=="B"
                )
               );
    }

    public boolean AvanzarY() {
       return (
                (!ChoqueArriba("A",1)&&Speed<0||!ChoqueAbajo("A",1)&&Speed>0)&&
                ((!ChoqueArriba("L",1)&&Speed<0||!ChoqueAbajo("L",1)&&Speed>0)||Wallpass)&&
                (
                  (!ChoqueArriba("X",1)&&Speed<0||!ChoqueAbajo("X",1)&&Speed>0)&&Identificacion!="B"||
                  (!ChoqueArriba("X",1)&&Speed<0||!ChoqueAbajo("X",1)&&Speed>0||JPanelJuego.getJugador().getBOMBPASS()||DentroBomb)&&Identificacion=="B"
                )
               );
    }

}
