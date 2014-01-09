/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Mapa;
import motor.core.Teclado;
import GUI.JPanelJuego;
import motor.core.Sprite;
import motor.core.GamePad;
import motor.core.GamePad.Botones;
import javax.swing.Timer;

public abstract class Personaje extends Sprite {
    
    protected int varx = 3, vary = 3, smart;
    protected final int SPEED_SLOWEST=1,SPEED_SLOW=2,SPEED_MID=4,SPEED_FAST=5,SMART_LOW=1,SMART_MID=2,SMART_HIGH=3,SMART_IMPOSSIBLE = 4;
    protected Inteligencia inteligencia;
    protected Timer timer;
    protected Teclado teclado;
    protected GamePad gamePad;
    protected boolean wallpass, dentroBomb;
    
    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        switch(getEstadoActual()){
            case INICIO:
                estadoInicio(jPanelJuego, tiempoTranscurrido);
                break;
            case ARRIBA:
                estadoArriba(jPanelJuego, tiempoTranscurrido);
                break;
            case ABAJO:
                estadoAbajo(jPanelJuego, tiempoTranscurrido);
                break;
            case DERECHA:
                estadoDerecha(jPanelJuego, tiempoTranscurrido);
                break;
            case IZQUIERDA:
                estadoIzquierda(jPanelJuego, tiempoTranscurrido);
                break;
            case MUERTE:
                estadoMuerte(jPanelJuego, tiempoTranscurrido);
                break;
        }
    }

    public Inteligencia getInteligencia() {
        return inteligencia;
    }

    public void movimientoDerecha() {
        velocidad = Math.abs(velocidad);
        updateX();
    }
    public void movimientoIzquierda() {
        velocidad = -Math.abs(velocidad);
        updateX();
    }
    public void movimientoArriba() {
        velocidad = -Math.abs(velocidad);
        updateY();
    }
    public void movimientoAbajo() {
        velocidad = Math.abs(velocidad);
        updateY();
    }
    
    public void iniciarInteligencia() {
        inteligencia = new Inteligencia(this);
        inteligencia.iniciar();
    }
    
    public void detenerInteligencia(){
        if(inteligencia == null)
            return;
        inteligencia.detenerInteligencia();
        inteligencia = null;
    }

    public void updateX(){
        if(!ChoqueCentral("X"))
            dentroBomb = false;
        if(AvanzarX()) {
            trasladar(velocidad, 0);
            posicionMapa.x = imagen.getPosicion().x / imagen.getAnchoEscalado();
        }
    }

    public void setDentroBomb(boolean dentroBomb) {
        this.dentroBomb = dentroBomb;
    }
    
    public void updateY(){
        if(!ChoqueCentral("X"))
            dentroBomb = false;
        if(AvanzarY()) {
            trasladar(0, velocidad);
            posicionMapa.y = imagen.getPosicion().y / imagen.getAltoEscalado();
        }
    }

    public boolean ChoqueDerecha(String a,int n){
        return ((Mapa.getInstance().getObjetoMapa(getPosicionY(y+2*vary),getPosicionX(x+varx)+n)==a)||
                (Mapa.getInstance().getObjetoMapa(getPosicionY(y+imagen.getAltoEscalado()-2*vary),getPosicionX(x+varx)+n)==a));
    }
    
    public boolean ChoqueIzquierda(String a,int n){
        return ((Mapa.getInstance().getObjetoMapa(getPosicionY(y+2*vary),getPosicionX(x+imagen.getAnchoEscalado()-varx)-n)==a)||
                (Mapa.getInstance().getObjetoMapa(getPosicionY(y+imagen.getAltoEscalado()-2*vary),getPosicionX(x+imagen.getAnchoEscalado()-varx)-n)==a));
    }
    
    public boolean ChoqueArriba(String a,int n){
        return (
                (Mapa.getInstance().getObjetoMapa(getPosicionY(y+imagen.getAltoEscalado()-vary)-n,getPosicionX(x+2*varx))==a  )||
                (Mapa.getInstance().getObjetoMapa(getPosicionY(y+imagen.getAltoEscalado()-vary)-n,getPosicionX(x+imagen.getAnchoEscalado()-2*varx))==a  ));
    }
    
    public boolean ChoqueAbajo(String a,int n){
        return ((Mapa.getInstance().getObjetoMapa(getPosicionX(y) + n, getPosicionX(x+2*varx)) == a  )||
                (Mapa.getInstance().getObjetoMapa(getPosicionX(y) + n, getPosicionX(x+imagen.getAnchoEscalado()-2*varx))==a  ));
    }

    public boolean ChoqueCentral(String a){
       return a.equals(Mapa.getInstance().getObjeto(posicionMapa));
    }
    
    public void setWallpass(boolean Wallpass) {
        this.wallpass = Wallpass;
    }
    
    public boolean getWallpass() {
        return wallpass;
    }

    public boolean AvanzarX() {
       return (
                (!ChoqueDerecha("A",1)&&velocidad>0||!ChoqueIzquierda("A",1)&&velocidad<0)&&
                ((!ChoqueDerecha("L",1)&&velocidad>0||!ChoqueIzquierda("L",1)&&velocidad<0)||wallpass)&&
                (
                  (!ChoqueDerecha("X",1)&&velocidad>0||!ChoqueIzquierda("X",1)&&velocidad<0)&&!"B".equals(identificacion)||
                  (!ChoqueDerecha("X",1)&&velocidad>0||!ChoqueIzquierda("X",1)&&velocidad<0||JPanelJuego.getInstance(null).primerJugador().getBOMBPASS()||dentroBomb)&&"B".equals(identificacion)
                )
               );
    }

    public boolean AvanzarY() {
       return (
                (!ChoqueArriba("A",1)&&velocidad<0||!ChoqueAbajo("A",1)&&velocidad>0)&&
                (!ChoqueArriba("L",1)&&velocidad<0||!ChoqueAbajo("L",1)&&velocidad>0||wallpass)&&
                (
                  (!ChoqueArriba("X",1)&&velocidad<0||!ChoqueAbajo("X",1)&&velocidad>0)&&!"B".equals(identificacion)||
                  (!ChoqueArriba("X",1)&&velocidad<0||!ChoqueAbajo("X",1)&&velocidad>0||JPanelJuego.getInstance(null).primerJugador().getBOMBPASS()||dentroBomb)&&"B".equals(identificacion)
                )
               );
    }
    
    public boolean isInteligenciaActivada() {
        return inteligencia != null;
    }
    
    public int get(Botones boton) {
        return gamePad.get(boton);
    }
    
}