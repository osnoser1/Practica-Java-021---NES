/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import gui.EscenaJuego;
import Sonidos.Sonidos;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.swing.AbstractAction;
import javax.swing.Timer;

/**
 *
 * @author hp
 */
public class LadrilloEspecial {
    
    private BufferedImage imagen;
    private Timer timer;
    private Point centro, posicionMapa;
    private int tipo, anchoEscalado, altoEscalado, x, y;
    private boolean estadoEliminado;
    
    public LadrilloEspecial(final Point centro, int tipo1) {
        this.centro = centro;
        this.tipo = tipo1;
        anchoEscalado = 40;
        altoEscalado = 40;
        x = centro.x - anchoEscalado / 2;
        y = centro.y - altoEscalado / 2;
        posicionMapa = new Point(centro.x / anchoEscalado, centro.y / altoEscalado);
        imagen = Imagenes.LADRILLO_ESPECIAL.get(tipo);
    }
    
    public void actualizar() {
        if(EscenaJuego.getInstance(null).primerJugador().isEntroALaPuerta())
            return;
        Point punto = EscenaJuego.getInstance(null).primerJugador().getPosicionMapa();
        if(posicionMapa.equals(punto)) {
            if(tipo != Imagenes.LADRILLO_ESPECIAL.size() - 1 && !estadoEliminado) {
                determinarHabilidad();
                Sonidos.getInstance().get(Sonidos.STAGE_THEME).stop();
                Sonidos.getInstance().get(Sonidos.POWER_UP_2).play();
                Sonidos.getInstance().get(Sonidos.FIND_THE_DOOR).loop();
                eliminarPowerup();
            }
            else if(EscenaJuego.getInstance(null).getCantidadEnemigos() == 0) {
                Sonidos.getInstance().detenerSonidos();
                Sonidos.getInstance().get(Sonidos.LEVEL_COMPLETE).play();
                EscenaJuego.getInstance(null).primerJugador().setEntroALaPuerta(true);
                System.out.println("Entro en la puerta");
            }
        }
    }
    
    public int getPuerta(){
        return Imagenes.LADRILLO_ESPECIAL.size()-1;
    }

    public int getTipo() {
        return tipo;
    }
    
    public boolean esPuerta(){
        return tipo == getPuerta();
    }
    
    public void eliminarPowerup() {
        if(tipo != Imagenes.LADRILLO_ESPECIAL.size() - 1){
            estadoEliminado = true;
            imagen=null;
        }
    }
    public Point getCentro(){
        return centro;
    }
    
    public void determinarHabilidad(){
        if(tipo==7)return;
        else if(tipo==0)EscenaJuego.getInstance(null).primerJugador().incrementarFlamas(1);
        else if(tipo==1)EscenaJuego.getInstance(null).primerJugador().incrementarBombas(1);
        else if(tipo==2)EscenaJuego.getInstance(null).primerJugador().setDETONATOR(true);
        else if(tipo==3)EscenaJuego.getInstance(null).primerJugador().setSPEED(true);
        else if(tipo==4)EscenaJuego.getInstance(null).primerJugador().setBOMBPASS(true);
        else if(tipo==5)EscenaJuego.getInstance(null).primerJugador().setWallpass(true);
        else if(tipo==6)EscenaJuego.getInstance(null).primerJugador().setFLAMEPASS(true);
        else if(tipo==7)EscenaJuego.getInstance(null).primerJugador().setMYSTERY(true); 
    }
    
    public void Dibujar(Graphics g){
        g.drawImage(imagen, x, y, anchoEscalado, altoEscalado, null);
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void crearEnemigos() {
        timer = new Timer(500,new AbstractAction(){
            int time=5;    
            @Override
            public void actionPerformed(ActionEvent e){
                time--;
                EscenaJuego.getInstance(null).getEnemigos().add(EscenaJuego.getInstance(null).determinarEnemigo(posicionMapa.y, posicionMapa.x, EscenaJuego.getInstance(null).determinarEnemigo(3)));  
                   if(time==0){
                       timer.stop();
                   }
            }
        });
        timer.start();  
    }

    public boolean isEstadoEliminado() {
        return estadoEliminado;
    }
    
}