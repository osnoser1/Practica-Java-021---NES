/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package Dependencias;

import java.util.ArrayList;
import javax.sound.sampled.Clip;
import motor.core.Resource;
import motor.core.java.resources.SoundResource;

/**
 *
 * @author Alfonso Andrés
 */
public class Sonidos {
    public static int PAUSE = 20;
    private static Sonidos musica = null;
    public static final int TITLE_SCREEN = 0, LEVEL_START = 1, STAGE_THEME = 2, FIND_THE_DOOR = 3, GAME_OVER = 4, INVINCIBILITY_THEME = 5, JUST_DIED = 6, ENDING_THEME = 7, LEVEL_COMPLETE = 8, BONUS_STAGE = 9, BOMB_PLANT = 10, EXPLOSION_1 = 11, EXPLOSION_2 = 12, KICK = 13, POWER_UP_2 = 14, DEATH = 15, LEFT = 16, RIGHT = 17, UP = 18, DOWN = 19;
    final int cantidadDeSonidos = PAUSE + 1;
    private final ArrayList<Sonido> sonidos;

    private Sonidos() {
        this.sonidos = new ArrayList<>();
//        initComponents();
    }


    public static Sonidos getInstance() {
        if(musica == null)
            musica = new Sonidos();
        return musica;
    }

    private void initComponents() {
        for(int i = 0; i < cantidadDeSonidos; i++) {
            this.sonidos.add(new Sonido(getClip(i)));
        }
    }

    private Clip getClip(int i) {
        String path;
        Resource r = new SoundResource();
        Ubicacion ubicacion = new Ubicacion();
        if(i == TITLE_SCREEN) path = ubicacion.TITLE_SCREEN;
        else if(i == LEVEL_START) path = ubicacion.STAGE;
        else if(i == STAGE_THEME) path = ubicacion.STAGE_THEME;
        else if(i == FIND_THE_DOOR) path = ubicacion.FIND_THE_DOOR;
        else if(i == GAME_OVER) path = ubicacion.GAME_OVER;
        else if(i == INVINCIBILITY_THEME) path = ubicacion.INVINCIBILITY_THEME;
        else if(i == JUST_DIED) path = ubicacion.JUST_DIED;
        else if(i == ENDING_THEME) path = ubicacion.ENDING_THEME;
        else if(i == LEVEL_COMPLETE) path = ubicacion.LEVEL_COMPLETE;
        else if(i == BONUS_STAGE) path = ubicacion.BONUS_STAGE;
        else if(i == BOMB_PLANT) path = ubicacion.BOMB_PLANT;
        else if(i == EXPLOSION_1) path = ubicacion.EXPLOSION_1;
        else if(i == EXPLOSION_2) path = ubicacion.EXPLOSION_2;
        else if(i == KICK) path = ubicacion.KICK;
        else if(i == POWER_UP_2) path = ubicacion.POWER_UP_2;
        else if(i == DEATH) path = ubicacion.DEATH;
        else if(i == LEFT) path = ubicacion.LEFT;
        else if(i == RIGHT) path = ubicacion.RIGHT;
        else if(i == UP) path = ubicacion.UP;
        else if(i == PAUSE) path = ubicacion.PAUSE;
        else path = ubicacion.DOWN;
        return (Clip) r.load(path, SoundResource.WAV);
    }

    public Sonido getNewSonido(int sonido) {
        return sonido < sonidos.size() ? sonidos.get(sonido).clonar() : null;
    }

    public Sonido get(int nombreSonido) {
        return nombreSonido < sonidos.size() ? sonidos.get(nombreSonido) : null;
    }

    public Sonido play(int sonido) {
        if (sonidos.size() <= sonido)
            return null;
        Sonido s = sonidos.get(sonido);
        s.play();
        return s;
    }

    public void loop(int sonido) {
        if (sonido < sonidos.size())
            sonidos.get(sonido).play();
    }

    public boolean isPlaying(int sonido) {
        return sonido < sonidos.size() ? sonidos.get(sonido).isPlaying() : false;
    }

    public void pausar() {
        for (Sonido sonido : sonidos)
            sonido.pause();
    }

    public void detener() {
        for (Sonido sonido : sonidos)
            sonido.stop();
    }

    public void detener(int... sonido) {
        if (sonidos.isEmpty())
            return;
        for(int indice : sonido)
            sonidos.get(indice).stop();
    }

    public void reanudar() {
        for(Sonido sonido : sonidos) 
            if(sonido.isAlive())
                sonido.play();
    }
}
