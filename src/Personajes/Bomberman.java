/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Sonidos.Sonidos;
import UtilidadesJuego.GamePad;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Bomberman extends Personaje {
        
    private boolean SPEED,DETONATOR,BOMBPASS,FLAMEPASS,MYSTERY;
    private int FLAMES,BOMBS;
    private static ArrayList<Bomb> bombas;
    
    public Bomberman(int x,int y){
        super(new Animation(Imagenes.BOMBERMAN_1,0,3),new Animation(Imagenes.BOMBERMAN_1,3,3),new Animation(Imagenes.BOMBERMAN_1,6,3),new Animation(Imagenes.BOMBERMAN_1,9,3),new Animation(Imagenes.BOMBERMAN_1,12,6));
        bombas = new ArrayList<>();
        Speed = SPEED_MID;
        BOMBS = 10;
        FLAMES = 5;
        SPEED = false;
        Wallpass = true;
        DETONATOR = true;
        BOMBPASS = true;
        FLAMEPASS = false;
        MYSTERY = true;
        this.x = x;
        this.y = y;
        Identificacion = "B";
        inicializar(Imagenes.BOMBERMAN, new Point(x, y), gamePad);
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        super.inicializar(posicion);
        activo = true;
        super.imagen = new Imagen(imagen, 6, 6, posicion, (float)2.5);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Estado, Animation>(){{
            put(Estado.INICIO, new Animation("0", 4000 / 60));
            put(Estado.ARRIBA, new Animation("2,1,0,1", 4000 / 60));
            put(Estado.ABAJO, new Animation("2,1,0,1", 4000 / 60));
            put(Estado.DERECHA, new Animation("2,1,0,1", 4000 / 60));
            put(Estado.IZQUIERDA, new Animation("2,1,0,1", 4000 / 60));
            put(Estado.MUERTE, new Animation("0,1,2,3,4", 500));
        }};
    }
    
    public void setDETONATOR(boolean DETONATOR) {
        this.DETONATOR = DETONATOR;
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if(SPEED)
            Speed=SPEED_FAST;
        else
            Speed=SPEED_MID;
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
    }

    public void IncrementarBOMBS() {
        BOMBS++;
    }
    
    public void IncrementarFLAMES() {
        FLAMES++;
    }

    public void setBOMBPASS(boolean BOMBPASS) {
        this.BOMBPASS = BOMBPASS;
    }

    public int getBOMBS() {
        return BOMBS;
    }
    public boolean getMYSTERY() {
        return MYSTERY;
    }

    public int getFLAMES() {
        return FLAMES;
    }
     public boolean getDETONATOR() {
        return DETONATOR;
    }
     public boolean getFLAMEPASS() {
        return FLAMEPASS;
    }
     public boolean getBOMBPASS() {
        return BOMBPASS;
    }
    public void CrearBomb(){
        if(ChoqueCentral("B"))
            if(bombas.size()<BOMBS){
                if(!ChoqueCentral("X"))
                DentroBomb=true;
                Sonidos.getInstance().getSonido(Sonidos.BOMB_PLANT).play();
                bombas.add(new Bomb(JPanelJuego.getPosicionX(getCenterX())*JPanelJuego.getx(),JPanelJuego.getPosicionY(getCenterY())*JPanelJuego.gety()));
            }
    }
  
    public ArrayList<Bomb> getBombs() {
        return bombas;
    }
    
    public void DibujarJugador(Graphics2D g){
        Dibujar(g);
    }

    public int bombasPuestas() {
        return bombas.size();
    }

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
    
    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido))
            setEstadoActual(Estado.IZQUIERDA);
    }

    @Override
    public void estadoArriba(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento())
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoAbajo(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento())
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoDerecha(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento())
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoIzquierda(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento())
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido))
            activo = false;
    }

    private void verificarTeclasAccion() {
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.A))){
            //Poner Bomba
            JPanelJuego.getInstance().primerJugador().CrearBomb();
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.B))){
            //Explotar primera bomba colocada
            if(JPanelJuego.getInstance().primerJugador().getDETONATOR()&&JPanelJuego.getInstance().primerJugador().bombasPuestas()!=0)
                JPanelJuego.getInstance().primerJugador().getBombs().get(0).detonar();
        }
    }

    private boolean verificarMovimiento() {
        boolean movimiento = true;
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ARRIBA))){
            setEstadoActual(Estado.ARRIBA);
            Sonidos.getInstance().getSonido(Sonidos.UP).play();
            MovimientoArriba();
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ABAJO))){
            Sonidos.getInstance().getSonido(Sonidos.UP).stop();
            setEstadoActual(Estado.ABAJO);
            Sonidos.getInstance().getSonido(Sonidos.DOWN).play();
            MovimientoAbajo();
        }else{
            Sonidos.getInstance().getSonido(Sonidos.UP).stop();
            Sonidos.getInstance().getSonido(Sonidos.DOWN).stop();
            movimiento = false;
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.DERECHA))){
            setEstadoActual(Estado.DERECHA);
            Sonidos.getInstance().getSonido(Sonidos.LEFT).play();
            MovimientoDerecha();
            movimiento = true;
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.IZQUIERDA))){
            Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
            setEstadoActual(Estado.IZQUIERDA);
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).play();
            MovimientoIzquierda();
            movimiento = true;
        }else{
            Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).stop();
        }
        return movimiento;
    }

    public void reiniciar(int x, int y) {
        super.reiniciar();
        fijarCasilla(x, y);
    }

}