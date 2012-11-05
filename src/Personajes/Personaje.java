/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Mapa;
import Dependencias.Teclado;
import GUI.JPanelJuego;
import Utilidades.Juego.GamePad;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import javax.swing.Timer;

public abstract class Personaje {
    
    protected int x, y, varx = 3, vary = 3, velocidad, smart;
    protected final int SPEED_SLOWEST=1,SPEED_SLOW=2,SPEED_MID=4,SPEED_FAST=5,SMART_LOW=1,SMART_MID=2,SMART_HIGH=3,SMART_IMPOSSIBLE = 4;
    protected Smart inteligencia;
    protected Timer timer;
    private Estado estadoAnterior, estadoActual;
    protected Teclado teclado;
    protected GamePad gamePad;
    protected boolean activo, wallpass, dentroBomb;
    protected HashMap<Integer, Animation> animaciones;
    protected Imagen imagen;
    protected Point posicionMapa;
    protected String identificacion;

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
    
    public java.awt.Rectangle getRectagulo() {
        return new java.awt.Rectangle(x, y, imagen.getAnchoEscalado(), imagen.getAltoEscalado());
    }
    
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
        imagen.setPosicion(new Point(x + imagen.getAnchoEscalado() / 2, y + imagen.getAltoEscalado() / 2));
        posicionMapa.x = x / imagen.getAnchoEscalado();
        posicionMapa.y = y / imagen.getAltoEscalado();
    }
    
    public Point getCentro(){
        return imagen.getPosicion();
    }
    
    public void trasladar(int dx, int dy) {
        x += dx;
        y += dy;
        imagen.trasladar(dx, dy);
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
    
    public Point getPosicionMapa() {
        return posicionMapa;
    }
    
    public void reiniciar(){
        setEstadoActual(Estado.INICIO);
        activo = true;
    }
    
    public void fijarCasilla(int x, int y){
        setLocation(x * imagen.getAnchoEscalado(), y * imagen.getAltoEscalado());
    }
    
    protected boolean actualizarAnimacion(long tiempoTranscurrido) {
        return animaciones.get(getEstadoActual().ordinal()).actualizar(tiempoTranscurrido);
    }
    
    public void pintar(Graphics g) {
        if(!activo || getEstadoActual() == Estado.ELIMINADO)
            return;
        imagen.actualizar(getEstadoActual().ordinal(), animaciones.get(getEstadoActual().ordinal()).getCuadroActual());
        imagen.pintar(g);
    }
    
    protected void inicializar(Imagen imagen, Point posicion) {
        this.imagen = imagen;
        x = posicion.x;
        y = posicion.y;
        imagen.setPosicion(new Point(x + imagen.getAnchoEscalado() / 2, y + imagen.getAltoEscalado() / 2));
        posicionMapa = new Point(getCentro().x / imagen.getAnchoEscalado(), getCentro().y / imagen.getAltoEscalado());
        activo = true;
        teclado = Teclado.getInstance();
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
        if(inteligencia!=null)
            inteligencia.iniciarInteligencia();
    }
    
    public void detenerInteligencia(){
        if(inteligencia!=null)
            inteligencia.detenerInteligencia();
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
    
    public String getIdentificacion() {
        return identificacion;
    }

    public int getVelocidad() {
        return velocidad;
    }
    
    public void setVelocidad(int velocityX) {
        velocidad = velocityX;
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void borrar(Graphics g, java.awt.image.BufferedImage imagen) {
        g.drawImage(imagen.getSubimage(x, y, this.imagen.getAnchoEscalado(), this.imagen.getAltoEscalado()), x, y, null);
    }
    
    public short getPosicionX(int X) {
        return (short)(X / imagen.getAnchoEscalado());
    }

    public short getPosicionY(int Y) {
        return (short)(Y / imagen.getAltoEscalado());
    }
    
}