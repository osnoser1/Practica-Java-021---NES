/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Mapa;
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
            //if(JPanelJuego.getInstance().primerJugador().getX()>=widthjPaneljuego/4&&JPanelJuego.getInstance().primerJugador().getX()<=3*widthjPaneljuego/4)
                
        }
        if(Speed!=0&&tx%(30/Speed)==0&&animation!=null)
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
        
        if(Speed!=0&&ty%(30/Speed)==0&&animation!=null)
            animation.MovimientoSprite();
    }
    
    public String getIdentificacion() {
        return Identificacion;
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
    
    public int getWidth() {
        return animation.getImageSprite().getWidth();
    }
    
    public int getHeight() {
        return animation.getImageSprite().getHeight();
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
    
    public BufferedImage getSpriteActual(){
        return animation == null ? null : animation.getImageSprite();
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
       return ((Mapa.getInstance().getObjetoMapa(JPanelJuego.getPosicionY(getCenterY()),JPanelJuego.getPosicionX(getCenterX()))==a  ));
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
                  (!ChoqueDerecha("X",1)&&Speed>0||!ChoqueIzquierda("X",1)&&Speed<0||JPanelJuego.getInstance().primerJugador().getBOMBPASS()||DentroBomb)&&Identificacion=="B"
                )
               );
    }

    public boolean AvanzarY() {
       return (
                (!ChoqueArriba("A",1)&&Speed<0||!ChoqueAbajo("A",1)&&Speed>0)&&
                ((!ChoqueArriba("L",1)&&Speed<0||!ChoqueAbajo("L",1)&&Speed>0)||Wallpass)&&
                (
                  (!ChoqueArriba("X",1)&&Speed<0||!ChoqueAbajo("X",1)&&Speed>0)&&Identificacion!="B"||
                  (!ChoqueArriba("X",1)&&Speed<0||!ChoqueAbajo("X",1)&&Speed>0||JPanelJuego.getInstance().primerJugador().getBOMBPASS()||DentroBomb)&&Identificacion=="B"
                )
               );
    }

    public static Sprite getInstance(String identificador){
        Sprite value = null;
        switch(identificador){
            case "B":
                value = new Bomberman(0, 0);
                break;
            case "b":
                value = new Balloom(0, 0);
                break;
            case "O":
                value = new Oneal(0, 0); 
                break;
            case "D":
                value = new Doll(0, 0);
                break;
            case "M":
                value = new Minvo(0, 0);  
                break;
            case "K":
                value = new Kondoria(0, 0); 
                break;
            case "o":
                value = new Ovapi(0, 0);
                break;
            case "P":
                value = new Pass(0, 0);
                break;
            case "p":
                value = new Pontan(0, 0);
                break;
        }
        return value;
    }
    
}
