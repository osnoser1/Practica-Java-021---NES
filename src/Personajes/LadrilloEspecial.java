/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Hilos.HiloTransicionPuerta;
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
        Point punto = JPanelJuego.getInstance().primerJugador().getPosicionMapa();
        if(posicionMapa.equals(punto)) {
            if(tipo != Imagenes.LADRILLO_ESPECIAL.size() - 1 && !estadoEliminado) {
                determinarHabilidad();
                Sonidos.getInstance().getSonido(Sonidos.STAGE_THEME).stop();
                Sonidos.getInstance().getSonido(Sonidos.POWER_UP_2).play();
                Sonidos.getInstance().getSonido(Sonidos.FIND_THE_DOOR).loop();
                eliminarPowerup();
            }
            else if(JPanelJuego.getInstance().getCantidadEnemigos() == 0) {
                Sonidos.getInstance().detenerSonidos();
                Sonidos.getInstance().getSonido(Sonidos.LEVEL_COMPLETE).play();
                new HiloTransicionPuerta().start();
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
        else if(tipo==0)JPanelJuego.getInstance().primerJugador().incrementarFlamas(1);
        else if(tipo==1)JPanelJuego.getInstance().primerJugador().incrementarBombas(1);
        else if(tipo==2)JPanelJuego.getInstance().primerJugador().setDETONATOR(true);
        else if(tipo==3)JPanelJuego.getInstance().primerJugador().setSPEED(true);
        else if(tipo==4)JPanelJuego.getInstance().primerJugador().setBOMBPASS(true);
        else if(tipo==5)JPanelJuego.getInstance().primerJugador().setWallpass(true);
        else if(tipo==6)JPanelJuego.getInstance().primerJugador().setFLAMEPASS(true);
        else if(tipo==7)JPanelJuego.getInstance().primerJugador().setMYSTERY(true); 
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
                JPanelJuego.getInstance().getEnemigos().add(JPanelJuego.getInstance().determinarEnemigo(JPanelJuego.getPosicionX(x), JPanelJuego.getPosicionY(y), JPanelJuego.getInstance().determinarEnemigo(3)));  
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