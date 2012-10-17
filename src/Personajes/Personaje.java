/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Mapa;
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
    protected Smart Inteligencia;
    protected Animation Izquierda;    
    protected Animation Derecha;
    protected Animation Arriba;
    protected Animation Abajo;
    protected Animation Muerte;
    private Estado estadoAnterior;
    private Estado estadoActual;
    protected final Teclado teclado;
    protected GamePad gamePad;
    protected boolean activo;
    protected HashMap<Estado, Animation> animaciones;
    protected Imagen imagen;
    
    public enum Estado {
        INICIO,
        ARRIBA,
        ABAJO,
        DERECHA,
        IZQUIERDA,
        MUERTE
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
        this.x = x * JPanelJuego.getInstance().getImagen().getWidth() / Mapa.COLUMNAS;
        this.y = x * JPanelJuego.getInstance().getImagen().getHeight() / Mapa.FILAS;
    }
    
    public boolean actualizarAnimacion(long tiempoTranscurrido) {
        return animaciones.get(getEstadoActual()).actualizar(tiempoTranscurrido);
    }
    
    public void pintar(Graphics g) {
        if(!activo)
            return;
        imagen.setPosicion(new Point(getCenterX(), getCenterY()));
        imagen.actualizar(getEstadoActual().ordinal(), animaciones.get(getEstadoActual()).getCuadroActual());
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
    
    public abstract void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido);
    public abstract void estadoArriba(JPanelJuego jPanelJuego, long tiempoTranscurrido);
    public abstract void estadoAbajo(JPanelJuego jPanelJuego, long tiempoTranscurrido);
    public abstract void estadoDerecha(JPanelJuego jPanelJuego, long tiempoTranscurrido);
    public abstract void estadoIzquierda(JPanelJuego jPanelJuego, long tiempoTranscurrido);
    public abstract void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido);
    public abstract void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido);
    
    public Smart getInteligencia() {
        return Inteligencia;
    }
//    protected int PosicionArrayList;
    protected Timer timer;
    protected boolean Muerto = false;

    public Personaje(Animation Izquierda,Animation Derecha,Animation Arriba,Animation Abajo,Animation Muerte){
        super(Izquierda);
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
    private Personaje personaje;
    public void Muerte(Personaje personaje){
        this.personaje = personaje;
        if(personaje != null)
            Inteligencia.getTimer().stop();
        Muerto = true;
        animation = Muerte;
        if(personaje == null){
            setEstadoActual(Estado.MUERTE);
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
                 tiempoIgualCero();
               }
            }            
        });
        timer.start();

    }
    
    private void tiempoIgualCero(){
        if(personaje == null){
             Sonidos.getInstance().detenerSonidos();
             Sonidos.getInstance().getSonido(Sonidos.JUST_DIED).play();
             new HiloPanelTransicionMuerte().start();
         }
         else {
            remover();
         }
    }
    private void remover(){
        JPanelJuego.getInstance().removerEnemigo(personaje);
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