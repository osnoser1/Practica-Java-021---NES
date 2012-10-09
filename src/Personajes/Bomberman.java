/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Personajes;

import Dependencias.Imagenes;
import GUI.JPanelJuego;
import Sonidos.Sonidos;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Bomberman extends Personaje {
    private boolean SPEED,DETONATOR,BOMBPASS,FLAMEPASS,MYSTERY;
    private int FLAMES,BOMBS;
    private static ArrayList<Bomb> Bombs=null;
    
    public Bomberman(int x,int y){
        super(new Animation(Imagenes.BOMBERMAN_1,0,3),new Animation(Imagenes.BOMBERMAN_1,3,3),new Animation(Imagenes.BOMBERMAN_1,6,3),new Animation(Imagenes.BOMBERMAN_1,9,3),new Animation(Imagenes.BOMBERMAN_1,12,6));
        Bombs=new ArrayList<>();

        this.Speed=this.SPEED_MID;
        this.BOMBS=10;
        this.FLAMES=5;
        this.SPEED=true;
        this.Wallpass=true;
        this.DETONATOR=true;
        this.BOMBPASS=true;
        this.FLAMEPASS=true;
        this.MYSTERY=true;
        this.x=x;
        this.y=y;
        this.Identificacion="B";
        
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
        
}