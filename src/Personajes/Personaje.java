/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Teclado;
import GUI.JPanelJuego;
import Hilos.HiloPanelTransicionMuerte;
import Sonidos.Sonidos;
import UtilidadesJuego.GamePad;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public abstract class Personaje extends Sprite {
    
    protected int Smart,PosicionX,PosicionY;
    protected final int SPEED_SLOWEST=1,SPEED_SLOW=2,SPEED_MID=4,SPEED_FAST=5,SMART_LOW=1,SMART_MID=2,SMART_HIGH=3,SMART_IMPOSSIBLE = 4;
    protected Smart inteligencia;
    protected Animation Izquierda;    
    protected Animation Derecha;
    protected Animation Arriba;
    protected Animation Abajo;
    protected Animation Muerte;
//    protected int PosicionArrayList;
    protected Timer timer;
    private Estado estadoAnterior;
    private Estado estadoActual;
    protected final Teclado teclado;
    protected GamePad gamePad;
    protected boolean activo;
    protected HashMap<Integer, Animation> animaciones;
    protected Imagen imagen;
    
    public enum Estado {
        INICIO,
        ARRIBA,
        ABAJO,
        DERECHA,
        IZQUIERDA,
        MUERTE,
        ELIMINADO
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado estado) {
        estadoAnterior = estadoActual;
        estadoActual = estado;
    }
    
    public Estado getEstadoAnterior(){
        return estadoAnterior;
    }
    
    
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Point getCentro(){
        return imagen.getPosicion();
    }
    
    /**
     *
     * @return Devuelve true si el personaje esta activo, false si no lo está.
     */
    public boolean isActivo() {
        return activo;
    }
    
    /**
     *
     * @param activo indica si quieres activar o no el personaje
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public void reiniciar(){
        estadoActual = Estado.INICIO;
        activo = true;
    }
    
    public void fijarCasilla(int x, int y){
        this.x = (int) (x * imagen.getAncho() * imagen.getEscala());
        this.y = (int) (y * imagen.getAlto() * imagen.getEscala());
    }
    
    protected boolean actualizarAnimacion(long tiempoTranscurrido) {
        return animaciones.get(getEstadoActual().ordinal()).actualizar(tiempoTranscurrido);
    }
    
    public void pintar(Graphics g) {
        if(!activo || getEstadoActual() == Estado.ELIMINADO)
            return;
        imagen.setPosicion(new Point(getCenterX(), getCenterY()));
        imagen.actualizar(getEstadoActual().ordinal(), animaciones.get(getEstadoActual().ordinal()).getCuadroActual());
        imagen.pintar(g);
    }
    
    protected void inicializar(Point posicion) {
        x = posicion.x;
        y = posicion.y;
    }
    
     /**
     * @return the imagen
     */
    public Imagen getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
    
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
    
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) { }
    public void estadoArriba(JPanelJuego jPanelJuego, long tiempoTranscurrido) { }
    public void estadoAbajo(JPanelJuego jPanelJuego, long tiempoTranscurrido) { }
    public void estadoDerecha(JPanelJuego jPanelJuego, long tiempoTranscurrido) { }
    public void estadoIzquierda(JPanelJuego jPanelJuego, long tiempoTranscurrido) { }
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) { }
    
    public Smart getInteligencia() {
        return inteligencia;
    }

    public Personaje() { 
        super();
        this.teclado = Teclado.getInstance();
        this.estadoActual = Estado.INICIO;
    }
    
    public Personaje(Animation Izquierda,Animation Derecha,Animation Arriba,Animation Abajo,Animation Muerte){
        super();
        this.Izquierda=Izquierda;
        this.Derecha=Derecha;
        this.Arriba=Arriba;
        this.Abajo=Abajo;
        this.Muerte=Muerte;
        this.estadoActual = Estado.INICIO;
        this.teclado = Teclado.getInstance();
        this.gamePad = new GamePad();
    } 

    public void MovimientoDerecha(){
//        animation=Derecha;
        Speed=Math.abs(Speed);
        updateX();
    }
    public void MovimientoIzquierda(){
//        animation=Izquierda;
        Speed=-Math.abs(Speed);
        updateX();
    }
    public void MovimientoArriba(){
//        animation=Arriba;
        Speed=-Math.abs(Speed);
        updateY();
    }
    public void MovimientoAbajo(){
//        animation=Abajo;
        Speed=Math.abs(Speed);
        updateY();
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
        if(inteligencia!=null)
            inteligencia.iniciarInteligencia();
    }
    
    public void detenerInteligencia(){
        if(inteligencia!=null)
            inteligencia.detenerInteligencia();
    }
}