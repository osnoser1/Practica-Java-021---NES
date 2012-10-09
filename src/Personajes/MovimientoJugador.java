/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author hp
 */
public class MovimientoJugador implements Runnable{
    
    Thread hilo;
    int cont=0,DirX,DirY,aux=1,CantImg,Inicio,Fin;
    ArrayList<BufferedImage> Sprite;
    
    public MovimientoJugador(ArrayList<BufferedImage> Sprite,int CantImg,int dirx,int diry) {
        this.Sprite=new ArrayList<>();
        this.Sprite=Sprite;
        this.CantImg=CantImg;
        DirX=dirx;
        DirY=diry;
        ActualizarMovimiento();
        hilo=new Thread(this);
    }
    
 
    

    @Override
    public void run() {
        while(true){

                MovimientoSprite();
            
            try {
                hilo.sleep(1000/6);
            } catch (InterruptedException ex) {}
        }
         
    }
    public void start(){
        hilo.start();
    }
    public void suspend(){
        hilo.suspend();
    }
    public void resume(){
        hilo.resume();
    }

    public int getDirY() {
        return DirY;
        
    }

    public int getDirX() {
        return DirX;
    }

    public void setDirX(int DirX) {
        this.DirX = DirX;
       // ActualizarMovimiento();
    }

    public void setDirY(int DirY) {
        this.DirY = DirY;
        //ActualizarMovimiento();
    }
    public void setDir(int DirX,int DirY){
        this.DirX = DirX;
        this.DirY = DirY;
       // ActualizarMovimiento();
    }
    private void MovimientoSprite() {
        ActualizarMovimiento();
        cont+=aux;
        
        
        if(cont==Fin||cont==Inicio)
            aux*=-1;

        
        
    }

     public void resetCont(){
         cont=Inicio+1;
     }
     
     public void ActualizarMovimiento(){
         if(DirX==1){
                Inicio=CantImg;
                Fin=2*CantImg-1;
            }
            else if(DirX==-1){
                Inicio=0;
                Fin=CantImg-1;
            }
            if(DirY==1){
                Inicio=2*CantImg;
                Fin=3*CantImg-1;
            }
            else if(DirY==-1){
                Inicio=3*CantImg;
                Fin=4*CantImg-1;
            }
            cont=Inicio;
     }

    public BufferedImage getImageSprite(){
        return Sprite.get(cont);
    }
    public void Dibujar(Graphics g,int x,int y,int width,int height){
       g.drawImage(Sprite.get(cont),x,y,width,height,null); 
    }
    
}
