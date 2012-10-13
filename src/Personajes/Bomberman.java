/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Sonidos.Sonidos;
import UtilidadesJuego.GamePad;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Bomberman extends Personaje {
    private boolean SPEED,DETONATOR,BOMBPASS,FLAMEPASS,MYSTERY;
    private int FLAMES,BOMBS;
    private static ArrayList<Bomb> Bombs=null;
    
    public Bomberman(int x,int y){
        super(new Animation(Imagenes.BOMBERMAN_1,0,3),new Animation(Imagenes.BOMBERMAN_1,3,3),new Animation(Imagenes.BOMBERMAN_1,6,3),new Animation(Imagenes.BOMBERMAN_1,9,3),new Animation(Imagenes.BOMBERMAN_1,12,6));
        Bombs = new ArrayList<>();
        this.Speed = this.SPEED_MID;
        this.BOMBS = 10;
        this.FLAMES = 5;
        this.SPEED = false;
        this.Wallpass = true;
        this.DETONATOR = true;
        this.BOMBPASS = true;
        this.FLAMEPASS = true;
        this.MYSTERY = true;
        this.x = x;
        this.y = y;
        this.Identificacion = "B";
    }

    public void setDETONATOR(boolean DETONATOR) {
        this.DETONATOR = DETONATOR;
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if(SPEED)
            Speed=this.SPEED_FAST;
        else
            Speed=this.SPEED_MID;
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
    }

    public void IncrementarBOMBS() {
        this.BOMBS++;
    }
    
    public void IncrementarFLAMES() {
        this.FLAMES++;
    }

    public void setBOMBPASS(boolean BOMBPASS) {
        this.BOMBPASS = BOMBPASS;
    }

    public int getBOMBS() {
        return BOMBS;
    }
    public boolean getMYSTERY() {
        return this.MYSTERY;
    }

    public int getFLAMES() {
        return FLAMES;
    }
     public boolean getDETONATOR() {
        return DETONATOR;
    }
     public boolean getFLAMEPASS() {
        return this.FLAMEPASS;
    }
     public boolean getBOMBPASS() {
        return this.BOMBPASS;
    }
    public void CrearBomb(){
        if(ChoqueCentral("B"))
            if(Bombs.size()<BOMBS){
                if(!ChoqueCentral("X"))
                DentroBomb=true;
                Sonidos.getInstance().getSonido(Sonidos.BOMB_PLANT).play();
                Bombs.add(new Bomb(JPanelJuego.getPosicionX(getCenterX())*JPanelJuego.getx(),JPanelJuego.getPosicionY(getCenterY())*JPanelJuego.gety()));
            }
    }
  
    public ArrayList<Bomb> getBombs() {
        return Bombs;
    }
    
    public void DibujarJugador(Graphics2D g){
        Dibujar(g);
    }

    public int bombasPuestas() {
        return Bombs.size();
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego) {
        switch(estado){
            case INICIO:
                estadoInicio();
                break;
            case ARRIBA:
                estadoArriba(jPanelJuego);
                break;
            case ABAJO:
                estadoAbajo(jPanelJuego);
                break;
            case DERECHA:
                estadoDerecha(jPanelJuego);
                break;
            case IZQUIERDA:
                estadoIzquierda(jPanelJuego);
                break;
            case MUERTE:
                estadoMuerte();
                break;
        }
    }

    @Override
    public boolean avanzarAnimacion() {
        return true;
    }
    
    @Override
    public void estadoInicio() {
        this.setEstado(Estado.DERECHA);
    }

    @Override
    public void estadoArriba(JPanelJuego jPanelJuego) {
        verificarTeclasAccion();
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.DERECHA))){
            this.setEstado(Estado.DERECHA);
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.IZQUIERDA))){
            this.setEstado(Estado.IZQUIERDA);
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ARRIBA))){
            //Mover jugador
            MovimientoArriba();
            Sonidos.getInstance().getSonido(Sonidos.UP).play();
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ABAJO))){
            this.setEstado(Estado.ABAJO);
        }else{
            return;
        }
        avanzarAnimacion();
    }

    @Override
    public void estadoAbajo(JPanelJuego jPanelJuego) {
        verificarTeclasAccion();
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.DERECHA))){
            this.setEstado(Estado.DERECHA);
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.IZQUIERDA))){
            this.setEstado(Estado.IZQUIERDA);
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ABAJO))){
            //Mover jugador
            MovimientoAbajo();
            Sonidos.getInstance().getSonido(Sonidos.DOWN).play();
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ARRIBA))){
            this.setEstado(Estado.ARRIBA);
        }else{
            return;
        }
        avanzarAnimacion();
    }

    @Override
    public void estadoDerecha(JPanelJuego jPanelJuego) {
        verificarTeclasAccion();
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ARRIBA))){
            this.setEstado(Estado.ARRIBA);
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ABAJO))){
            this.setEstado(Estado.ABAJO);
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.DERECHA))){
            //Mover jugador
            MovimientoDerecha();
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).play();
            //Desplazamiento de ventana temporal
            if(AvanzarX()&&getX()>=jPanelJuego.getWidth()/4&&getX()<=(3*jPanelJuego.getWidth()/4-30)){
                    jPanelJuego.setLocation(jPanelJuego.getX()-getSpeed(), 0);
            }
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.IZQUIERDA))){
            this.setEstado(Estado.IZQUIERDA);
        }else{
            return;
        }
        avanzarAnimacion();
    }

    @Override
    public void estadoIzquierda(JPanelJuego jPanelJuego) {
        verificarTeclasAccion();
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ARRIBA))){
            this.setEstado(Estado.ARRIBA);
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.ABAJO))){
            this.setEstado(Estado.ABAJO);
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.IZQUIERDA))){
            //Mover jugador
            MovimientoIzquierda();
            Sonidos.getInstance().getSonido(Sonidos.LEFT).play();
            //Desplazamiento de ventana temporal
            if(AvanzarX()&&getX()>=jPanelJuego.getWidth()/4&&getX()<=(3*jPanelJuego.getWidth()/4-30)){
                jPanelJuego.setLocation(jPanelJuego.getX()-getSpeed(), 0);
            }
        }else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.DERECHA))){
            this.setEstado(Estado.DERECHA);
        }else{
            return;
        }
        avanzarAnimacion();
    }

    @Override
    public void estadoMuerte() {
        if(!avanzarAnimacion()){
            activo = false;
        }
    }

    private void verificarTeclasAccion() {
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.A))){
            //Poner Bomba
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.B))){
            //Explotar primera bomba colocada
        }
    }

}