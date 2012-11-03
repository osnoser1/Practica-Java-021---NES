/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagen;
import Dependencias.Imagenes;
import Dependencias.Mapa;
import GUI.JPanelJuego;
import Hilos.HiloPanelTransicionMuerte;
import Sonidos.Sonidos;
import Utilidades.Juego.GamePad;
import Utilidades.Juego.GamePad.Botones;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Bomberman extends Personaje {
        
    private boolean SPEED,DETONADOR,BOMBPASS,FLAMEPASS,MYSTERY;
    private int FLAMES,BOMBS;
    private static CopyOnWriteArrayList<Bomb> bombas;
    
    public Bomberman(int x,int y) {
        bombas = new CopyOnWriteArrayList<>();
        velocidad = SPEED_MID;
        BOMBS = 10;
        FLAMES = 5;
        SPEED = false;
        wallpass = true;
        DETONADOR = true;
        BOMBPASS = true;
        FLAMEPASS = false;
        MYSTERY = true;
        this.x = x;
        this.y = y;
        identificacion = "B";
        inicializar(Imagenes.BOMBERMAN, new Point(x, y), new GamePad());
    }
    
    public final void inicializar(BufferedImage imagen, Point posicion, GamePad gamePad) {
        inicializar(new Imagen(imagen, 6, 6, posicion, (float)2.5), posicion);
        super.gamePad = gamePad;
        super.animaciones = new HashMap<Integer, Animation>(){{
            put(Estado.INICIO.ordinal(), new Animation("0", 4000 / 60));
            put(Estado.ARRIBA.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.ABAJO.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.DERECHA.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.IZQUIERDA.ordinal(), new Animation("2,1,0,1", 4000 / 60));
            put(Estado.MUERTE.ordinal(), new Animation("0,1,2,3,4", 500));
        }};
        setEstadoActual(Estado.IZQUIERDA);
    }

    @Override
    public void actualizar(JPanelJuego jPanelJuego, long tiempoTranscurrido) {
        super.actualizar(jPanelJuego, tiempoTranscurrido);
        for(Bomb bomba : bombas) {
            bomba.actualizar(jPanelJuego, tiempoTranscurrido);
            if(bomba.getEstadoActual() == Personaje.Estado.ELIMINADO) {
                Mapa.getInstance().setObjeto("V", bomba.getPosicionMapa());
                bombas.remove(bomba);
            }
        }
    }

    public void colisionaCon(Personaje otro) {
        if(getEstadoActual() == Estado.MUERTE)
            return;
        Sonidos.getInstance().detenerSonidos(Sonidos.UP, Sonidos.DOWN, Sonidos.LEFT, Sonidos.RIGHT);
        Sonidos.getInstance().getSonido(Sonidos.DEATH).play();
        setEstadoActual(Personaje.Estado.MUERTE);
    }
    
    public void setDETONATOR(boolean DETONATOR) {
        this.DETONADOR = DETONATOR;
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

    public void incrementarBombas(int incremento) {
        BOMBS += incremento;
    }
    
    public void incrementarFlamas(int incremento) {
        FLAMES += incremento;
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
     public boolean getDETONADOR() {
        return DETONADOR;
    }
     public boolean getFLAMEPASS() {
        return FLAMEPASS;
    }
     public boolean getBOMBPASS() {
        return BOMBPASS;
    }
    public void crearBomba() {
        if(ChoqueCentral("B"))
            if(bombas.size()<BOMBS){
                if(!ChoqueCentral("X"))
                    dentroBomb = true;
                Sonidos.getInstance().getSonido(Sonidos.BOMB_PLANT).play();
                bombas.add(new Bomb((int)(posicionMapa.x * imagen.getAnchoEscalado()), (int)(posicionMapa.y * imagen.getAltoEscalado()), this));
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
        if(teclado.teclaPresionada(gamePad.getBoton(Botones.A))) {
            JPanelJuego.getInstance().primerJugador().crearBomba();
        }
        if(teclado.teclaPresionada(gamePad.getBoton(Botones.B))) {
            if(DETONADOR)
                detonarBomba();
        }
    }

    private boolean verificarMovimiento(JPanelJuego jPanelJuego) {
        boolean movimiento = true;
        if(teclado.teclaPresionada(gamePad.getBoton(Botones.ARRIBA))){
            setEstadoActual(Estado.ARRIBA);
            Sonidos.getInstance().getSonido(Sonidos.UP).play();
            MovimientoArriba();
        }else if(teclado.teclaPresionada(gamePad.getBoton(Botones.ABAJO))){
            Sonidos.getInstance().getSonido(Sonidos.UP).stop();
            setEstadoActual(Estado.ABAJO);
            Sonidos.getInstance().getSonido(Sonidos.DOWN).play();
            MovimientoAbajo();
        }else{
            Sonidos.getInstance().detenerSonidos(Sonidos.UP, Sonidos.DOWN);
            movimiento = false;
        }
        if(teclado.teclaPresionada(gamePad.getBoton(Botones.DERECHA))){
            setEstadoActual(Estado.DERECHA);
            Sonidos.getInstance().getSonido(Sonidos.LEFT).play();
            MovimientoDerecha();
//            if(MovimientoDerecha() && getCentro().x >= jPanelJuego.getCuartoImagen().x && getCentro().x <= jPanelJuego.getTresCuartosImagen().x) {
//                jPanelJuego.setPosicionX(jPanelJuego.getPosicionX() - getVelocidad() * jPanelJuego.getWidth() / jPanelJuego.getImagen().getWidth());
//            }
            movimiento = true;
        } else if(teclado.teclaPresionada(gamePad.getBoton(Botones.IZQUIERDA))) {
            Sonidos.getInstance().getSonido(Sonidos.LEFT).stop();
            setEstadoActual(Estado.IZQUIERDA);
            Sonidos.getInstance().getSonido(Sonidos.RIGHT).play();
            MovimientoIzquierda();
//            if(MovimientoIzquierda() && getCentro().x >= jPanelJuego.getCuartoImagen().x && getCentro().x <= jPanelJuego.getTresCuartosImagen().x) {
//                jPanelJuego.setPosicionX(jPanelJuego.getPosicionX() - getVelocidad() * jPanelJuego.getWidth() / jPanelJuego.getImagen().getWidth());
//            }
            movimiento = true;
        } else {
            Sonidos.getInstance().detenerSonidos(Sonidos.LEFT, Sonidos.RIGHT);
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

    @Override
    public void borrar(Graphics g, BufferedImage imagen) {
        super.borrar(g, imagen);
        for(Bomb bomba : bombas) {
            bomba.borrar(g, imagen);
        }
    }

}