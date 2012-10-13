/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author hp
 */
public class Fire extends Sprite {
        int time = 6, SpaceDerecha, SpaceIzquierda, SpaceArriba, SpaceAbajo, Space;
        Bomb bomba;
        Timer timer;
        Animation Derecha;
        Animation Izquierda;
        Animation Arriba;
        Animation Abajo;
        Animation Horizontal;
        Animation Vertical;
        Animation Central;
                
        
    public Fire(final int x1,final int y1,int space, Bomb bomba) {
        super(new Animation(Imagenes.FIRE,0,4));
        Izquierda=new Animation(Imagenes.FIRE,0,4);
        Derecha=new Animation(Imagenes.FIRE,4,4);
        Arriba=new Animation(Imagenes.FIRE,8,4);
        Abajo=new Animation(Imagenes.FIRE,12,4);
        Horizontal=new Animation(Imagenes.FIRE,16,4);
        Vertical=new Animation(Imagenes.FIRE,20,4);
        Central=new Animation(Imagenes.FIRE,24,4);
        this.x=x1;
        this.y=y1;
        this.Space=space;
        this.bomba = bomba;
        SpaceIzquierda=Space;
        SpaceDerecha=Space;
        SpaceArriba=Space;
        SpaceAbajo=Space;
        
        this.determinarTamaño();

        timer=new Timer(80,new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
               updateAll();
               time--;
               
               if(time==0){
                    timer.stop();
                    if(JPanelJuego.getInstance().primerJugador() != null)
                        remover();
                   
               }
            }

            private void updateAll() {
                Izquierda.MovimientoSprite();
                Derecha.MovimientoSprite();
                Arriba.MovimientoSprite();
                Abajo.MovimientoSprite();
                Horizontal.MovimientoSprite();
                Vertical.MovimientoSprite();
                Central.MovimientoSprite();
            }
        });
        timer.start();
    }
    
    private void remover(){
        JPanelJuego.getInstance().primerJugador().getBombs().remove(bomba);
    }
    
    public void DibujarFire(Graphics g){
        
        g.drawImage(Central.getImageSprite(),x,y,JPanelJuego.getx(),JPanelJuego.gety(),null);
//        if(Space>SpaceIzquierda)
        int space=SpaceIzquierda==this.Space?SpaceIzquierda-1:SpaceIzquierda;
        for(int i=1;i<=space;i++)
            g.drawImage(Horizontal.getImageSprite(),x-i*JPanelJuego.getx(),y,JPanelJuego.getx(),JPanelJuego.gety(),null);
        space=SpaceDerecha==this.Space?SpaceDerecha-1:SpaceDerecha;
        for(int i=1;i<=space;i++)
             g.drawImage(Horizontal.getImageSprite(),x+i*JPanelJuego.getx(),y,JPanelJuego.getx(),JPanelJuego.gety(),null);
        space=SpaceArriba==this.Space?SpaceArriba-1:SpaceArriba;
        for(int i=1;i<=space;i++) 
             g.drawImage(Vertical.getImageSprite(),x,y-i*JPanelJuego.gety(),JPanelJuego.getx(),JPanelJuego.gety(),null);
        space=SpaceAbajo==this.Space?SpaceAbajo-1:SpaceAbajo;
        for(int i=1;i<=space;i++){   
            g.drawImage(Vertical.getImageSprite(),x,y+i*JPanelJuego.gety(),JPanelJuego.getx(),JPanelJuego.gety(),null);
        
        }
        if(SpaceDerecha==Space)
            g.drawImage(Derecha.getImageSprite(),x+SpaceDerecha*JPanelJuego.getx(),y,JPanelJuego.getx(),JPanelJuego.gety(),null);
        if(SpaceIzquierda==Space)
            g.drawImage(Izquierda.getImageSprite(),x-SpaceIzquierda*JPanelJuego.getx(),y,JPanelJuego.getx(),JPanelJuego.gety(),null);
        if(SpaceArriba==Space)    
            g.drawImage(Arriba.getImageSprite(),x,y-SpaceArriba*JPanelJuego.gety(),JPanelJuego.getx(),JPanelJuego.gety(),null);
        if(SpaceAbajo==Space)
            g.drawImage(Abajo.getImageSprite(),x,y+SpaceAbajo*JPanelJuego.gety(),JPanelJuego.getx(),JPanelJuego.gety(),null);
        
        
    }
    

    public int getSpace() {
        return Space;
    }


    public void determinarTamaño(){
        
        for(int i=1;i<=Space;i++) 
             if(ChoqueArriba("A",i)||ChoqueArriba("L",i)||ChoqueArriba("X",i)||ChoqueArriba("Q",i)||ChoqueArriba("S",i)){
                 SpaceArriba=i-1;
                 JPanelJuego.getInstance().borrarLadrillo(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 JPanelJuego.getInstance().borrarBombs(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 break;
             }else if(!ChoqueArriba("V",i)){
                 JPanelJuego.getInstance().borrarEnemigo(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 
                 if(!JPanelJuego.getInstance().primerJugador().getFLAMEPASS())
                    JPanelJuego.getInstance().borrarJugador(getCenterX(), getCenterY()-JPanelJuego.gety()*i);
                 
             }
        for(int i=1;i<=Space;i++) 
             if(ChoqueAbajo("A",i)||ChoqueAbajo("L",i)||ChoqueAbajo("X",i)||ChoqueAbajo("Q",i)||ChoqueAbajo("S",i)){
                 SpaceAbajo=i-1;
                 JPanelJuego.getInstance().borrarLadrillo(getCenterX(), getCenterY()+JPanelJuego.gety()*i);
                 JPanelJuego.getInstance().borrarBombs(getCenterX(), getCenterY()+JPanelJuego.gety()*i);
                 break;
             }else if(!ChoqueAbajo("V",i)){
                 JPanelJuego.getInstance().borrarEnemigo(getCenterX(), getCenterY()+JPanelJuego.gety()*i);
                 
                 if(!JPanelJuego.getInstance().primerJugador().getFLAMEPASS())
                    JPanelJuego.getInstance().borrarJugador(getCenterX(), getCenterY()+JPanelJuego.gety()*i);
                 
             }
        for(int i=1;i<=Space;i++) 
             if(ChoqueIzquierda("A",i)||ChoqueIzquierda("L",i)||ChoqueIzquierda("X",i)||ChoqueIzquierda("Q",i)||ChoqueIzquierda("S",i)){
                 SpaceIzquierda=i-1;
                 JPanelJuego.getInstance().borrarLadrillo(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 JPanelJuego.getInstance().borrarBombs(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 break;
             }else if(!ChoqueIzquierda("V",i)){
                 JPanelJuego.getInstance().borrarEnemigo(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 
                 if(!JPanelJuego.getInstance().primerJugador().getFLAMEPASS())
                    JPanelJuego.getInstance().borrarJugador(getCenterX()-JPanelJuego.getx()*i, getCenterY());
                 
             }
        for(int i=0;i<=Space;i++) 
             if(ChoqueDerecha("A",i)||ChoqueDerecha("L",i)||ChoqueDerecha("X",i)&&i!=0||ChoqueDerecha("Q",i)&&i!=0||ChoqueDerecha("S",i)&&i!=0){
                 if(i!=0)SpaceDerecha=i-1;
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
