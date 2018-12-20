/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
 */
package dependencies;

import java.util.ArrayList;
import javax.sound.sampled.Clip;
import engine.core.Resource;
import engine.core.java.resources.SoundResource;

/**
 *
 * @author Alfonso Andrés
 */
public class Sounds {
    public static final int PAUSE = 20;
    private static Sounds instance;
    public static final int TITLE_SCREEN = 0, LEVEL_START = 1, STAGE_THEME = 2, FIND_THE_DOOR = 3, GAME_OVER = 4, INVINCIBILITY_THEME = 5, JUST_DIED = 6, ENDING_THEME = 7, LEVEL_COMPLETE = 8, BONUS_STAGE = 9, BOMB_PLANT = 10, EXPLOSION_1 = 11, EXPLOSION_2 = 12, KICK = 13, POWER_UP_2 = 14, DEATH = 15, LEFT = 16, RIGHT = 17, UP = 18, DOWN = 19;
    final int soundsLength = PAUSE + 1;
    private final ArrayList<Sound> sounds;

    private Sounds() {
        this.sounds = new ArrayList<>();
        initComponents();
    }


    public static Sounds getInstance() {
        if(instance == null)
            instance = new Sounds();
        return instance;
    }

    private void initComponents() {
        for(var i = 0; i < soundsLength; i++) {
            this.sounds.add(new Sound(getClip(i)));
        }
    }

    private Clip getClip(int i) {
        String path;
        Resource r = new SoundResource();
        var location = new Location();
        if(i == TITLE_SCREEN) path = location.TITLE_SCREEN;
        else if(i == LEVEL_START) path = location.STAGE;
        else if(i == STAGE_THEME) path = location.STAGE_THEME;
        else if(i == FIND_THE_DOOR) path = location.FIND_THE_DOOR;
        else if(i == GAME_OVER) path = location.GAME_OVER;
        else if(i == INVINCIBILITY_THEME) path = location.INVINCIBILITY_THEME;
        else if(i == JUST_DIED) path = location.JUST_DIED;
        else if(i == ENDING_THEME) path = location.ENDING_THEME;
        else if(i == LEVEL_COMPLETE) path = location.LEVEL_COMPLETE;
        else if(i == BONUS_STAGE) path = location.BONUS_STAGE;
        else if(i == BOMB_PLANT) path = location.BOMB_PLANT;
        else if(i == EXPLOSION_1) path = location.EXPLOSION_1;
        else if(i == EXPLOSION_2) path = location.EXPLOSION_2;
        else if(i == KICK) path = location.KICK;
        else if(i == POWER_UP_2) path = location.POWER_UP_2;
        else if(i == DEATH) path = location.DEATH;
        else if(i == LEFT) path = location.LEFT;
        else if(i == RIGHT) path = location.RIGHT;
        else if(i == UP) path = location.UP;
        else if(i == PAUSE) path = location.PAUSE;
        else path = location.DOWN;
        return (Clip) r.load(path, SoundResource.WAV);
    }

    public Sound getNewSound(int sound) {
        return sound < sounds.size() ? sounds.get(sound).clone() : null;
    }

    public Sound get(int soundName) {
        return soundName < sounds.size() ? sounds.get(soundName) : null;
    }

    public Sound play(int sound) {
        if (sounds.size() <= sound)
            return null;
        var s = sounds.get(sound);
        s.play();
        return s;
    }

    public void play(int sound, boolean play) {
        if(play) {
            play(sound);
        } else {
            stop(sound);
        }
    }
    
    public void loop(int sound) {
        if (sound < sounds.size())
            sounds.get(sound).loop();
    }


    public void change(int current, int newSound) {
        stop(current);
        play(newSound);
    }

    public void change(int current, int newSound, boolean asLoop) {
        stop(current);
        if (asLoop) {
            loop(newSound);
        } else {
            play(newSound);
        }
    }
    
    public boolean isPlaying(int sound) {
        return sound < sounds.size() && sounds.get(sound).isPlaying();
    }

    public void pause() {
        for (var sound : sounds)
            sound.pause();
    }

    public void stop() {
        for (var sound : sounds)
            sound.stop();
    }

    public void stop(int... sounds) {
        if (this.sounds.isEmpty())
            return;
        for(var index : sounds) {
            var s = this.sounds.get(index);
            if(s.isPlaying()) {
                s.stop();
            }
        }
    }

    public void resume() {
        for(var sound : sounds)
            if(sound.isAlive())
                sound.play();
    }
}
