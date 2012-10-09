/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Teclado;
import GUI.JPanelJuego;
import Hilos.HiloPanelTransicionMuerte;
import Sonidos.Sonidos;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public abstract class Personaje extends Sprite {
    
    protected int Smart,PosicionX,PosicionY;
    protected final int SPEED_SLOWEST=1,SPEED_SLOW=2,SPEED_MID=4,SPEED_FAST=7,SMART_LOW=1,SMART_MID=2,SMART_HIGH=3;
    protected Smart Inteligencia;
    protected Animation Izquierda;    
    protected Animation Derecha;
    protected Animation Arriba;
    protected Animation Abajo;
    protected Animation Muerte;
    protected Estado estado;
    protected final Teclado teclado;
    public enum Estado{
        INICIO,
        ARRIBA,
        ABAJO,
        DERECHA,
        IZQUIERDA,
        MUERTE
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    public void reiniciar(){
        estado = Estado.INICIO;
    }
    
    public abstract void estadoInicio();
    public abstract void estadoArriba();
    public abstract void estadoAbajo();
    public abstract void estadoDerecha();
    public abstract void estadoIzquierda();
    public abstract void estadoMuerte();
    public abstract void actualizar(JPanelJuego jPanelJuego);
    
    public Personajes.Smart getInteligencia() {
        return Inteligencia;
    }
    protected int PosicionArrayList;
    protected Timer timer;
    protected boolean Muerto = false;

    public Personaje(Animation Izquierda,Animation Derecha,Animation Arriba,Animation Abajo,Animation Muerte){
        super(Izquierda);
        this.Izquierda=Izquierda;
        this.Derecha=Derecha;
        this.Arriba=Arriba;
        this.Abajo=Abajo;
        this.Muerte=Muerte;
        this.estado = Estado.INICIO;
        this.teclado = Teclado.getInstance();
    } 

    public void MovimientoDerecha(){
        animation=Derecha;
        Speed=Math.abs(Speed);
        updateX();
    }
    public void MovimientoIzquierda(){
        animation=Izquierda;
        Speed=-Math.abs(Speed);
        updateX();
    }
    public void MovimientoArriba(){
        animation=Arriba;
        Speed=-Math.abs(Speed);
        updateY();
    }
    public void MovimientoAbajo(){
        animation=Abajo;
        Speed=Math.abs(Speed);
        updateY();
    }
    public void Muerte(int a){
        if(a!=-1)Inteligencia.getTimer().stop();
        Muerto=true;
        animation=Muerte;
        PosicionArrayList=a;
        if(a==-1){
            JPanelJuego.getJPanelGrafico().getJPanelJuego().removeKeyListener(JPanelJuego.getJPanelGrafico().getControladorKeyBoardJPanelJuego());
            Sonidos.getInstance().getSonido(Sonidos.UP).stop();
            Sonidos.getInstance().getSonido(Sonidos.DOWN).stop();
            Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).stop();
            Sonidos.getInstance().getSonido(Sonidos.DEATH).play();
        }
        timer=new Timer(300,new AbstractAction(){
            int time=5;
            @Override
            public void actionPerformed(ActionEvent e) {
               
               animation.MovimientoSprite();
               time--;
               
               if(time==0){
                 
                 timer.stop();
                  if(PosicionArrayList==-1){
                     Sonidos.getInstance().detenerSonidos();
                     Sonidos.getInstance().getSonido(Sonidos.JUST_DIED).play();
                     new HiloPanelTransicionMuerte(JPanelJuego.getJPanelGrafico().getJPanelContenedor()).start();
                 }
                 else {
                   //  JPanelJuego.getenemigos().set(PosicionArrayList,null);
                 
                     JPanelJuego.getenemigos().remove(PosicionArrayList);
                 
                 }
               }
            }            
        });
        timer.start();

    }
    public Animation getDerecha() {
        return Derecha;
    }
    public Animation getIzquierda() {
        return Izquierda;
    }
    public Animation getAbajo() {
        return Abajo;
    }
    public Animation getArriba() {
        return Arriba;
    }
    public void iniciarInteligencia(){
        if(Inteligencia!=null)
            Inteligencia.iniciarInteligencia();
    }
    public void detenerInteligencia(){
        if(Inteligencia!=null)
            Inteligencia.detenerInteligencia();
    }
}