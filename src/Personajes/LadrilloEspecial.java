/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Hilos.HiloTransicionPuerta;
import Sonidos.Sonidos;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author hp
 */
public class LadrilloEspecial {
    BufferedImage Imagen;
    Timer timer;
    Timer timer1;
    int x,y;
    int i,tipo;
    public LadrilloEspecial(int x1,int y1,int tipo1,int i1) {
        this.x=x1;
        this.y=y1;
        this.i=i1;
        this.tipo=tipo1;
        Imagen=Imagenes.LADRILLO_ESPECIAL.get(tipo);
        timer=new Timer(10,new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JPanelJuego.getJugador()==null){
                    timer.stop();
                    return;
                }
                if(JPanelJuego.getPosicionX(JPanelJuego.getJugador().getCenterX())==JPanelJuego.getPosicionX(getCenterX())&&JPanelJuego.getPosicionY(JPanelJuego.getJugador().getCenterY())==JPanelJuego.getPosicionY(getCenterY())){
                    if(tipo!=Imagenes.LADRILLO_ESPECIAL.size()-1){
                        DeterminarHabilidad();
                        Sonidos.getInstance().getSonido(Sonidos.STAGE_THEME).stop();
                        Sonidos.getInstance().getSonido(Sonidos.POWER_UP_2).play();
                        Sonidos.getInstance().getSonido(Sonidos.FIND_THE_DOOR).loop();
                        EliminarPowerup();
                    //     JPanelJuego.getLadrillos().remove(i);
                        //    JPanelJuego.getLadrillos().set(i,null);
                        }
                    else if(JPanelJuego.getJPanelGrafico().getJPanelJuego().getCantidadEnemigos()==0){
                        Sonidos.getInstance().detenerSonidos();
                        Sonidos.getInstance().getSonido(Sonidos.LEVEL_COMPLETE).play();
                        JPanelJuego.getJPanelGrafico().getJPanelJuego().removeKeyListener(JPanelJuego.getJPanelGrafico().getControladorKeyBoardJPanelJuego());
                        new HiloTransicionPuerta(JPanelJuego.getJPanelGrafico().getJPanelContenedor()).start();
                        System.out.println("Entro en la puerta");
                        timer.stop();
                    }
                }
            }

            
        });
        timer.start();
        
    }
    public int getPuerta(){
        return Imagenes.LADRILLO_ESPECIAL.size()-1;
    }

    public int getTipo() {
        return tipo;
    }
    public boolean esPuerta(){
        return (tipo==getPuerta());
    }
    public void EliminarPowerup() {
        if(tipo!=Imagenes.LADRILLO_ESPECIAL.size()-1){
            timer.stop();
            Imagen=null;
        }
    }
    public int getCenterX(){
        return x+JPanelJuego.getx()/2;
    }
    
    public int getCenterY(){
        return y+JPanelJuego.gety()/2;
    }
    
    public void DeterminarHabilidad(){
        if(tipo==7)return;
        else if(tipo==0)JPanelJuego.getJugador().IncrementarFLAMES();
        else if(tipo==1)JPanelJuego.getJugador().IncrementarBOMBS();
        else if(tipo==2)JPanelJuego.getJugador().setDETONATOR(true);
        else if(tipo==3)JPanelJuego.getJugador().setSPEED(true);
        else if(tipo==4)JPanelJuego.getJugador().setBOMBPASS(true);
        else if(tipo==5)JPanelJuego.getJugador().setWallpass(true);
        else if(tipo==6)JPanelJuego.getJugador().setFLAMEPASS(true);
        else if(tipo==7)JPanelJuego.getJugador().setMYSTERY(true);
          
    }
    public void Dibujar(Graphics g){
        if(Imagen!=null);
            g.drawImage(Imagen, x, y,JPanelJuego.getx(),JPanelJuego.gety(), null);
    }

    public BufferedImage getImagen() {
        return Imagen;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void CrearEnemigos() {
        
        timer1=new Timer(500,new AbstractAction(){
            int time=5;    
            public void actionPerformed(ActionEvent e){
                time--;
                JPanelJuego.getenemigos().add(JPanelJuego.getJPanelGrafico().getJPanelJuego().DeterminarEnemigo(JPanelJuego.getPosicionX(x),JPanelJuego.getPosicionY(y),JPanelJuego.getJPanelGrafico().DeterminarEnemigo(3)));  
                   if(time==0){
                       timer1.stop();
                   }
            }
        });
        timer1.start();
       
           
    }
}
