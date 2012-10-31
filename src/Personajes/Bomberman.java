/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Hilos.HiloPanelTransicionMuerte;
import Sonidos.Sonidos;
import Utilidades.Juego.GamePad;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bomberman extends Personaje {
        
    private boolean SPEED,DETONATOR,BOMBPASS,FLAMEPASS,MYSTERY;
    private int FLAMES,BOMBS;
    private static CopyOnWriteArrayList<Bomb> bombas;
    
    public Bomberman(int x,int y){
        super();
        bombas = new CopyOnWriteArrayList<>();
        velocidad = SPEED_MID;
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
        identificacion = "B";
        inicializar(Imagenes.BOMBERMAN, new Point(x, y), new GamePad());
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        super.inicializar(posicion);
        activo = true;
        super.imagen = new Imagen(imagen, 6, 6, posicion, (float)2.5);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, Animation>(){{
            put(Estado.INICIO.ordinal(), new Animation("0", 4000 / 60));
            put(Estado.ARRIBA.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.ABAJO.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.DERECHA.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.IZQUIERDA.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.MUERTE.ordinal(), new Animation("0,1,2,3,4", 500));
        }};
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        for(Bomb bomba : bombas) {
            bomba.actualizar(jPanelJuego, tiempoTranscurrido);
        }
    }
    
    public void colisionaCon(Personaje otro) {
        if(getEstadoActual() == Estado.MUERTE)
            return;
        Sonidos.getInstance().getSonido(Sonidos.UP).stop();
        Sonidos.getInstance().getSonido(Sonidos.DOWN).stop();
        Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
        Sonidos.getInstance().getSonido(Sonidos.RIGHT).stop();
        Sonidos.getInstance().getSonido(Sonidos.DEATH).play();
        setEstadoActual(Personaje.Estado.MUERTE);
    }
    
    public void setDETONATOR(boolean DETONATOR) {
        this.DETONATOR = DETONATOR;
    }

    public void setSPEED(boolean SPEED) {
        this.SPEED = SPEED;
        if(SPEED)
            velocidad=SPEED_FAST;
        else
            velocidad=SPEED_MID;
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
    public void crearBomba(){
        if(ChoqueCentral("B"))
            if(bombas.size()<BOMBS){
                if(!ChoqueCentral("X"))
                DentroBomb=true;
                Sonidos.getInstance().getSonido(Sonidos.BOMB_PLANT).play();
                bombas.add(new Bomb(JPanelJuego.getPosicionX(getCenterX())*JPanelJuego.getx(),JPanelJuego.getPosicionY(getCenterY())*JPanelJuego.gety()));
            }
    }
  
    public CopyOnWriteArrayList<Bomb> getBombs() {
        return bombas;
    }

    public int bombasPuestas() {
        return bombas.size();
    }
    
    @Override
    public void estadoInicio(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido))
            setEstadoActual(Estado.IZQUIERDA);
    }

    @Override
    public void estadoArriba(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoAbajo(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoDerecha(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoIzquierda(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        verificarTeclasAccion();
        if(verificarMovimiento(jPanelJuego))
            actualizarAnimacion(tiempoTranscurrido);
    }

    @Override
    public void estadoMuerte(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        if(actualizarAnimacion(tiempoTranscurrido)) {
            Sonidos.getInstance().detenerSonidos();
            Sonidos.getInstance().getSonido(Sonidos.JUST_DIED).play();
            new HiloPanelTransicionMuerte().start();
            setEstadoActual(Estado.ELIMINADO);
        }
    }

    private void verificarTeclasAccion() {
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.A))) {
            //Poner Bomba
            JPanelJuego.getInstance().primerJugador().crearBomba();
        }
        if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.B))) {
            if(getDETONATOR())
                detonarBomba();
        }
    }

    private boolean verificarMovimiento(JPanelJuego jPanelJuego) {
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
            if(MovimientoDerecha() && getCenterX() >= jPanelJuego.getCuartoImagen().x && getCenterX() <= jPanelJuego.getTresCuartosImagen().x) {
                jPanelJuego.setPosicionX(jPanelJuego.getPosicionX() - getSpeed() * jPanelJuego.getWidth() / jPanelJuego.getImagen().getWidth());
            }
            movimiento = true;
        } else if(teclado.teclaPresionada(gamePad.getBoton(GamePad.Botones.IZQUIERDA))) {
            Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
            setEstadoActual(Estado.IZQUIERDA);
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).play();
            if(MovimientoIzquierda() && getCenterX() >= jPanelJuego.getCuartoImagen().x && getCenterX() <= jPanelJuego.getTresCuartosImagen().x) {
                jPanelJuego.setPosicionX(jPanelJuego.getPosicionX() - getSpeed() * jPanelJuego.getWidth() / jPanelJuego.getImagen().getWidth());
            }
            movimiento = true;
        } else {
            Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).stop();
        }
        return movimiento;
    }

    public void reiniciar(int x, int y) {
        super.reiniciar();
        fijarCasilla(x, y);
    }

    private void detonarBomba() {
        for(Bomb bomba : bombas) {
            if(bomba.getEstadoActual() == Estado.MUERTE)
                continue;
            else {
                bomba.detonar();
                return;
            }
        }
    }

}