/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Sonidos;

import Dependencias.ManejadorDeArchivos;
import Dependencias.Sonido;
import Dependencias.Ubicacion;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

/**
 *
 * @author Alfonso Andrés
 */
public class Sonidos {
    public static int PAUSE = 20;
    
    private ArrayList<Sonido> sonidos;
    private static Sonidos musica=null;
    public static final int TITLE_SCREEN=0,LEVEL_START=1,STAGE_THEME=2,FIND_THE_DOOR=3,GAME_OVER=4,INVINCIBILITY_THEME=5,JUST_DIED=6,ENDING_THEME=7,LEVEL_COMPLETE=8,BONUS_STAGE=9,BOMB_PLANT=10,EXPLOSION_1=11,EXPLOSION_2=12,KICK=13,POWER_UP_2=14,DEATH=15,LEFT=16,RIGHT=17,UP=18,DOWN=19;
    final int cantidadDeSonidos=PAUSE+1;

    private Sonidos() {
        this.sonidos=new ArrayList<>();
        initComponents();
    }
    
    public static Sonidos getInstance(){
        if(musica==null)
            musica=new Sonidos();
        return musica;
    }
    
    public Sonido getSonido(int nombreSonido){
        return nombreSonido < sonidos.size() ? sonidos.get(nombreSonido) : null;
    }

    private void initComponents() {
        for(int i=0;i<cantidadDeSonidos;i++){
            this.sonidos.add(new Sonido(getClip(i)));
        }
    }

    private Clip getClip(int i) {
        String path;
        Ubicacion ubicacion = new Ubicacion();
        if(i==TITLE_SCREEN) path = ubicacion.TITLE_SCREEN;
        else if(i==LEVEL_START) path = ubicacion.STAGE;
        else if(i==STAGE_THEME) path = ubicacion.STAGE_THEME;
        else if(i==FIND_THE_DOOR) path = ubicacion.FIND_THE_DOOR;
        else if(i==GAME_OVER) path = ubicacion.GAME_OVER;
        else if(i==INVINCIBILITY_THEME) path = ubicacion.INVINCIBILITY_THEME;
        else if(i==JUST_DIED) path = ubicacion.JUST_DIED;
        else if(i==ENDING_THEME) path = ubicacion.ENDING_THEME;
        else if(i==LEVEL_COMPLETE) path = ubicacion.LEVEL_COMPLETE;
        else if(i==BONUS_STAGE) path = ubicacion.BONUS_STAGE;
        else if(i==BOMB_PLANT) path = ubicacion.BOMB_PLANT;
        else if(i==EXPLOSION_1) path = ubicacion.EXPLOSION_1;
        else if(i==EXPLOSION_2) path = ubicacion.EXPLOSION_2;
        else if(i==KICK) path = ubicacion.KICK;
        else if(i==POWER_UP_2) path = ubicacion.POWER_UP_2;
        else if(i==DEATH) path = ubicacion.DEATH;
        else if(i==LEFT) path = ubicacion.LEFT;
        else if(i==RIGHT) path = ubicacion.RIGHT;
        else if(i==UP) path = ubicacion.UP;
        else if(i==PAUSE) path = ubicacion.PAUSE;
        else path = ubicacion.DOWN;
        return ManejadorDeArchivos.getInstance().cargarClipJAR(path);
    }

    public Sonido getNewSonido(int nombreSonido) {
        return nombreSonido<sonidos.size()?sonidos.get(nombreSonido).clonar():null;
    }
    
    public void pausarSonidos(){
        for(int size=sonidos.size(),i=0;i<size;i++){
            sonidos.get(i).pause();
        }
    }
    
    public void detenerSonidos(){
        for(int size=sonidos.size(),i=0;i<size;i++){
            sonidos.get(i).stop();
        }
    }
    
    public void reanudarSonidos(){
        for(int size=sonidos.size(),i=0;i<size;i++){
            Sonido get = sonidos.get(i);
            if(get.isAlive())
                get.play();
        }
    }
    
}
